package org.example;
import javafx.scene.effect.Glow;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import org.deeplearning4j.models.embeddings.loader.WordVectorSerializer;
import org.deeplearning4j.models.paragraphvectors.ParagraphVectors;
import org.deeplearning4j.text.documentiterator.FileLabelAwareIterator;
import org.deeplearning4j.text.documentiterator.LabelAwareIterator;
import org.deeplearning4j.text.documentiterator.LabelledDocument;
import org.deeplearning4j.text.tokenization.tokenizer.preprocessor.CommonPreprocessor;
import org.deeplearning4j.text.tokenization.tokenizerfactory.DefaultTokenizerFactory;
import org.deeplearning4j.text.tokenization.tokenizerfactory.TokenizerFactory;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.factory.Nd4j;

import java.io.*;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.*;
import java.util.stream.Collectors;

public class ValueDetecModel {
    private static final TokenizerFactory tokenizerFactory;
    private static Set<String> vocabSet = new HashSet<>();
    public static File testfolder;
    private static File[] testFiles;
    static {
        File jarDir = null;
        try {
            jarDir = new File(Main.class.getProtectionDomain()
                    .getCodeSource()
                    .getLocation()
                    .toURI())
                    .getParentFile();
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }


        File dir = new File(jarDir, "ValueDetec_ModelDetails/Test");
        if (!dir.exists()) {
            dir.mkdirs();
        }
        testfolder= new File(dir.getPath());
        testFiles=testfolder.listFiles((dirr, name) -> name.toLowerCase().endsWith(".txt"));
        tokenizerFactory= new DefaultTokenizerFactory();
        tokenizerFactory.setTokenPreProcessor(new CommonPreprocessor());
    }
    public static void ReadModelOutput() throws IOException {
        if (testfolder.exists() && testfolder.isDirectory() && Objects.requireNonNull(testfolder.listFiles()).length > 0) {
            ParagraphVectors model=InitialiseModel();
            for (File file : testFiles) {
                if (!file.exists()) continue;
                String content = Files.readString(file.toPath(), StandardCharsets.UTF_8);
                LabelledDocument doc = new LabelledDocument();
                INDArray inferredVector;
                doc.setContent(content.toString());
                inferredVector = safeInferVector(model,tokenizerFactory, content);
                if(inferredVector==null){
                    continue;
                }
                Collection<String> nearestLabels = model.nearestLabels(inferredVector, 1);
                String predictedLabel = nearestLabels.iterator().next();
                if(predictedLabel.equals("Useful")){
                    Text text= new Text(content);
                    text.setFill(Paint.valueOf("#FFD700"));
                    text.setEffect(new Glow(1.0));
                    if(content.length()>50){
                        FuturisticJavaFXGUI.TailoredReviewVPanel.getChildren().add(text);
                    }

                }
            }


        }
    }
    private static ParagraphVectors InitialiseModel() throws IOException {
        try {
            File jarDir = new File(Main.class.getProtectionDomain()
                    .getCodeSource()
                    .getLocation()
                    .toURI())
                    .getParentFile();


            File dir = new File(jarDir, "ValueDetec_ModelDetails/Model");
            if (!dir.exists()) {
                dir.mkdirs();
            }
            File save = new File(dir,"model.zip");
            File vocabFile = new File(dir,"vocab.txt");
            ParagraphVectors model;
            model = WordVectorSerializer.readParagraphVectors(save);
            model.setTokenizerFactory(tokenizerFactory);
            if (vocabFile.exists()) {
                List<String> vocabWords = Files.readAllLines(vocabFile.toPath());
                vocabSet.addAll(vocabWords);
                FuturisticJavaFXGUI.progressBar.setProgress(1.0);
            }
            return model;

        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }


    }
    public static INDArray safeInferVector(ParagraphVectors model, TokenizerFactory tokenizerFactory, String text) {
        List<String> tokens = tokenizerFactory.create(text).getTokens();
        List<String> knownTokens = tokens.stream()
                .filter(vocabSet::contains)
                .collect(Collectors.toList());
        String filteredText = String.join(" ", knownTokens);
        if (knownTokens.isEmpty()) {
            return null;
        }
        int runs = 10;
        List<INDArray> vectors = new ArrayList<>();
        for (int i = 0; i < runs; i++) {
            vectors.add(model.inferVector(filteredText));
        }
        INDArray sum = vectors.get(0).dup();
        for (int i = 1; i < vectors.size(); i++) {
            sum.addi(vectors.get(i));
        }
        return sum.div(runs);
    }

    }
