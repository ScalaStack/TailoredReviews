package org.example;
import org.deeplearning4j.models.embeddings.loader.WordVectorSerializer;
import org.deeplearning4j.models.paragraphvectors.ParagraphVectors;
import org.jsoup.*;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.*;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.awt.Dimension;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.*;
import java.awt.*;
import java.util.List;

public class Main {
    private static final Random random = new Random();
    protected static Thread Launch_AI;
    public static Dimension screensize;
    public static double width;
    public static double height;
    static{
        screensize=Toolkit.getDefaultToolkit().getScreenSize();
        width=screensize.getWidth();
        height=screensize.getHeight();
    }

    public static void main(String[] args){
        FuturisticJavaFXGUI.main();





    }
    public static void SearchSteam(int id,int t) throws InterruptedException, IOException {
        String url="https://steamcommunity.com/app/"+id+"/reviews";
        url = url.replace("\"", "");
        WebDriver driver = new ChromeDriver(ChromeOptions());
        driver.get(url);
        driver.navigate().to(url);
        Thread.sleep(t* 1000L);
        String source=driver.getPageSource();
        Jsoup.connect(url);
        Document doc=Jsoup.parse(source);
        Elements elements = doc.getElementsByClass("apphub_CardTextContent");

        for(Element element:elements){
            try {
                int n = random.nextInt(10000000);
                File jarDir = new File(Main.class.getProtectionDomain()
                        .getCodeSource()
                        .getLocation()
                        .toURI())
                        .getParentFile();


                File dir = new File(jarDir, "ValueDetec_ModelDetails/Test");
                if (!dir.exists()) {
                    dir.mkdirs();
                }

                File file = new File(dir, "message" + n + ".txt");
                if (!file.exists()) {
                    file.createNewFile();
                }

                System.out.println("File path: " + file.getAbsolutePath());
                FileWriter fileWriter = new FileWriter(file);
                fileWriter.write(element.text());
                fileWriter.close();
            } catch (URISyntaxException | java.io.IOException e) {
                e.printStackTrace();
            }
        }
        driver.quit();
        Thread.sleep(1000);

    }
    public static void ClearTestDirectory(){
        try {
            File jarDir = new File(Main.class.getProtectionDomain()
                    .getCodeSource()
                    .getLocation()
                    .toURI())
                    .getParentFile();


            File dir = new File(jarDir, "ValueDetec_ModelDetails/Test");
            if (!dir.exists()) {
                dir.mkdirs();
            }
            if(!dir.exists()){}
            if(dir.isDirectory()){
                File[] tests= dir.listFiles();
                if(tests.length>0)
                    for(File test:tests){
                        test.delete();
                    }
            }

        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }
    public static void SearchOpenCritic(String link,int t) throws InterruptedException, IOException {
        String url=link;
        url = url.replace("\"", "");
        WebDriver driver = new ChromeDriver(ChromeOptions());
        driver.get(url);
        driver.navigate().to(url);
        Thread.sleep(t* 1000L);
        String source=driver.getPageSource();
        Jsoup.connect(url);
        Document doc=Jsoup.parse(source);
        Elements elements = doc.getElementsByAttribute("href");
        HashSet<String> sites = new HashSet<>();
        for(Element element:elements){
            String absoluteUrl = element.absUrl("href");
            sites.add(absoluteUrl);
        }
        for(String site:sites){
            Connection con;
            if(!site.isEmpty()){
                Thread.sleep(5000);
                con = Jsoup.connect(site)
                        .ignoreContentType(true)
                        .ignoreHttpErrors(true)
                        .followRedirects(false);
                Elements texts= con.get().select("p");
                for(Element text:texts){
                    try {
                        int n = random.nextInt(10000000);
                        File jarDir = new File(Main.class.getProtectionDomain()
                                .getCodeSource()
                                .getLocation()
                                .toURI())
                                .getParentFile();


                        File dir = new File(jarDir, "ValueDetec_ModelDetails/Test");
                        if (!dir.exists()) {
                            dir.mkdirs();
                        }

                        File file = new File(dir, "message" + n + ".txt");
                        file.createNewFile();


                        System.out.println("File path: " + file.getAbsolutePath());
                        FileWriter fileWriter = new FileWriter(file);
                        fileWriter.write(text.text());
                        fileWriter.close();
                    } catch (URISyntaxException | java.io.IOException e) {
                        e.printStackTrace();
                    }
                }
                driver.quit();
                Thread.sleep(1000);


            }
        }

    }
    public static void SearchRottenTomatoes(String name,String media, String s,int t) throws InterruptedException, IOException {
        String url;
        if(!s.equals("NA")&&media.equals("tv")){
            url="https://www.rottentomatoes.com/"+media+"/"+NametoURL(name)+"/"+s+"/reviews?type=user";
        }
        else{
            url="https://www.rottentomatoes.com/"+media+"/"+NametoURL(name)+"/"+"reviews?type=user";
        }
        url = url.replace("\"", "");
        WebDriver driver = new ChromeDriver(ChromeOptions());
        driver.get(url);
        driver.navigate().to(url);
        Thread.sleep(t* 1000L);
        String source=driver.getPageSource();
        Jsoup.connect(url);
        Document doc=Jsoup.parse(source);
        Elements elements = doc.getElementsByClass("audience-reviews__review js-review-text");
        for(Element text:elements){
            try {
                int n = random.nextInt(10000000);
                File jarDir = new File(Main.class.getProtectionDomain()
                        .getCodeSource()
                        .getLocation()
                        .toURI())
                        .getParentFile();


                File dir = new File(jarDir, "ValueDetec_ModelDetails/Test");
                if (!dir.exists()) {
                    dir.mkdirs();
                }

                File file = new File(dir, "message" + n + ".txt");
                file.createNewFile();


                System.out.println("File path: " + file.getAbsolutePath());
                FileWriter fileWriter = new FileWriter(file);
                fileWriter.write(text.text());
                fileWriter.close();
            } catch (URISyntaxException | java.io.IOException e) {
                e.printStackTrace();
            }

        }
        driver.quit();
        Thread.sleep(1000);

    }
    public static void SearchMetaCritic(String name,String media, String s,int t) throws InterruptedException, IOException {
        if(!media.equals("game")){
            if(s.contains("0")){
                s=s.replace("0","");

            }
            s="season-".concat(s);
            String url;
            if(!s.equals("NA")&&media.equals("tv")){
                url="https://www.metacritic.com/"+media+"/"+NametoURL(name)+"/"+s+"/user-reviews/";
            }
            else{
                url="https://www.metacritic.com/"+media+"/"+NametoURL(name)+"/"+"user-reviews/";
            }
            url = url.replace("\"", "");
            WebDriver driver = new ChromeDriver(ChromeOptions());
            driver.get(url);
            driver.navigate().to(url);
            ArrayList<String> readmoretextArray= new ArrayList<>();
            Thread.sleep(t* 1000L);
            long start = System.currentTimeMillis();
            while (System.currentTimeMillis() - start < t* 1000L) {
                List<WebElement> elements=driver.findElements(By.className("c-siteReviewReadMore_wrapper"));
                try {
                    for(WebElement element:elements){
                        try {
                            readmoretextArray.add(element.getText());
                        }catch (StaleElementReferenceException e ){
                            continue;
                        }

                    }
                }
                catch (NoSuchElementException e) {
                    continue;
                }
                Thread.sleep(10);
            }
            for(int i=0; i<readmoretextArray.size()-1; i++){
                if(readmoretextArray.get(i).equals(readmoretextArray.get(i+1))){
                    readmoretextArray.remove(i);
                    i--;
                }
            }
            for(String Readmoretxt:readmoretextArray){
                try {
                    int n = random.nextInt(10000000);
                    File jarDir = new File(Main.class.getProtectionDomain()
                            .getCodeSource()
                            .getLocation()
                            .toURI())
                            .getParentFile();


                    File dir = new File(jarDir, "ValueDetec_ModelDetails/Test");
                    if (!dir.exists()) {
                        dir.mkdirs();
                    }

                    File file = new File(dir, "message" + n + ".txt");
                    file.createNewFile();


                    System.out.println("File path: " + file.getAbsolutePath());
                    FileWriter fileWriter = new FileWriter(file);
                    fileWriter.write(Readmoretxt);
                    fileWriter.close();
                } catch (URISyntaxException | java.io.IOException e) {
                    e.printStackTrace();
                }

            }
            driver.quit();
            Thread.sleep(1000);

        }


    }
    private static ChromeOptions ChromeOptions(){
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--window-size="+width+","+height);
        options.addArguments("--disable-extensions");
        options.addArguments("--disable-gpu");
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--no-sandbox");
        options.setExperimentalOption("useAutomationExtension", false);
        return options;
    }
    private static String NametoURL(String name){
        name=name.toLowerCase();
        if(name.contains(" ")){
            name=name.replace(" ", "_");
        }
        if(name.contains(":")){
            name=name.replace(":","");
        }
        return name;
    }

}






