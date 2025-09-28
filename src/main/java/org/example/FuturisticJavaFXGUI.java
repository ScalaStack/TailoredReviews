package org.example;
import javafx.animation.*;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.effect.*;
import javafx.scene.layout.*;
import javafx.scene.paint.*;
import javafx.scene.shape.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class FuturisticJavaFXGUI extends Application {
    public static VBox TailoredReviewVPanel;
    public static List<Button> buttons= new ArrayList<>();
    public static ProgressBar progressBar;

    static {
        FuturisticJavaFXGUI.TailoredReviewVPanel = new VBox(20);
    }
    public Button returntvButton(){
        Button button = createFuturisticButton("Film/Show Reviews", buildTvIcon(), Color.web("#9d4edd"));
        return  button;
    }
    public Button returnconsoleButton(){
        Button button= createFuturisticButton("Game Reviews", buildConsoleIcon(), Color.web("#ff6d00"));
        return button;
    }
    public Button returnexitButton(){
        Button button=createFuturisticButton("Exit", buildExitIcon(), Color.web("#ff0054"));
        return button;
    }
    public Button returnTrainAI(){
        Button button=createFuturisticButton("Train AI", buldRoboIcon(), Color.web("#4682B4"));
        return button;
    }
    public Button returnTailoredReviewPage(){
        Button button=createFuturisticButton("TailoredReviews",buildMoonIcon(),Color.web("#0b0c10"));
        return  button;
    }
    public Button returnMetaCriticButton(){
        Button button =createFuturisticButton("MetaCritic",buildPowerIcon(),Color.web("#FF4500"));
        return button;
    }
    public Button returnRottenTomatoesButton(){
        Button button=createFuturisticButton("Rotten Tomatoes",buildVolumeIcon(true),Color.web("#1E90FF"));
        return button;
    }
    public Button returnOpenCriticButton(){
        Button button=createFuturisticButton("OpenCritic",buildGameControllerIcon(),Color.web("#9370DB"));
        return button;
    }
    public Button returnSteamButton(){
        Button button=createFuturisticButton("Steam",buildGearIcon(),Color.web("#20B2AA"));
        return button;
    }
    public Button returnbackButton(){
        Button button= createFuturisticButton("BACK TO MAIN", buildBackIcon());
        return button;
    }

    @Override
    public void start(Stage stage) {
        StackPane root = new StackPane();
        root.setStyle("-fx-background-color: linear-gradient(to bottom right, #0a0f1a 0%, #050a14 40%, #000811 100%);");
        Group backgroundElements = createAdvancedBackground();
        Group orbits = buildOrbitRings();
        Button tvButton =returntvButton();
        Button consoleButton= returnconsoleButton();
        Button exitButton = returnexitButton();
        Button TrainAI= returnTrainAI();
        Button TailoredReviewsPage= returnTailoredReviewPage();
        Button MetaCriticButton= returnMetaCriticButton();
        Button RottenTomatoesButton= returnRottenTomatoesButton();
        Button OpenCriticButton= returnOpenCriticButton();
        Button SteamButton= returnSteamButton();

        tvButton.setOnAction(e -> {
            buttons.clear();
            buttons.add(MetaCriticButton);
            buttons.add(RottenTomatoesButton);
            openSecondaryGUI("Movie Reviews", stage, buttons);
        });
        consoleButton.setOnAction(e -> {
            buttons.clear();
            buttons.add(SteamButton);
            buttons.add(OpenCriticButton);
            openSecondaryGUI("Game Reviews", stage, buttons);

        });
        SteamButton.setOnAction(e->{
            openSteamSite();
        });
        OpenCriticButton.setOnAction(e->{
            openCriticSite();

        });
        RottenTomatoesButton.setOnAction(e->{
            openRottenTomatoes();

        });
        MetaCriticButton.setOnAction(e->{
            openMetaCritic();

        });

        exitButton.setOnAction(e -> stage.close());
        TrainAI.setOnAction(e-> {
            if(ValueDetecModel.testfolder.listFiles().length>0&&ValueDetecModel.testfolder!=null){
                Main.Launch_AI=new Thread(()-> {
                    try {
                        ValueDetecModel.ReadModelOutput();
                    } catch (IOException s) {
                        s.printStackTrace();

                    }
                });
                Main.Launch_AI.start();
            }
        });
        TailoredReviewsPage.setOnAction(e->TailoredReviewsPage(stage));

        HBox mainButtonsRow = new HBox(40, tvButton, consoleButton,TrainAI,TailoredReviewsPage);
        mainButtonsRow.setAlignment(Pos.CENTER);

        HBox exitButtonContainer = new HBox(exitButton);
        exitButtonContainer.setAlignment(Pos.BOTTOM_CENTER);
        progressBar= new ProgressBar(0.0);
        VBox buttonsLayout = new VBox(30, mainButtonsRow, exitButtonContainer,progressBar);
        buttonsLayout.setAlignment(Pos.CENTER);
        root.getChildren().addAll(backgroundElements, orbits, buttonsLayout);
        StackPane.setAlignment(buttonsLayout, Pos.CENTER);
        Scene scene = new Scene(root,Main.width,Main.height );
        stage.setScene(scene);
        stage.setWidth(Main.width);
        stage.setHeight(Main.height);
        stage.setTitle("TailoredReviews");
        stage.show();

        startOrbitAnimation(orbits);
        startBackgroundAnimation(backgroundElements);
    }

    private Group createAdvancedBackground() {
        Group group = new Group();


        for (int i = 0; i < 50; i++) {
            Circle particle = new Circle(1 + Math.random() * 3, Color.web("#00e6ff", 0.3));
            particle.setLayoutX(Math.random() * Main.width);
            particle.setLayoutY(Math.random() * Main.height);
            particle.setEffect(new Glow(0.8));
            group.getChildren().add(particle);
        }


        for (int i = 0; i < 20; i++) {
            Line gridLine = new Line(0, i * 40, 1200, i * 40);
            gridLine.setStroke(Color.web("#0a2a3a", 0.1));
            gridLine.setStrokeWidth(0.5);
            group.getChildren().add(gridLine);

            Line gridLine2 = new Line(i * 60, 0, i * 60, 750);
            gridLine2.setStroke(Color.web("#0a2a3a", 0.1));
            gridLine2.setStrokeWidth(0.5);
            group.getChildren().add(gridLine2);

        }

        return group;
    }

    private void startBackgroundAnimation(Group group) {
        for (Node node : group.getChildren()) {
            if (node instanceof Circle) {
                Circle circle = (Circle) node;


                PathTransition pt = new PathTransition();
                pt.setNode(circle);
                pt.setDuration(Duration.seconds(15 + Math.random() * 15));

                Path path = new Path();
                path.getElements().add(new MoveTo(circle.getCenterX(), circle.getCenterY()));
                path.getElements().add(new CubicCurveTo(
                        circle.getCenterX() + 200 - Math.random() * 400,
                        circle.getCenterY() + 200 - Math.random() * 400,
                        circle.getCenterX() + 200 - Math.random() * 400,
                        circle.getCenterY() + 200 - Math.random() * 400,
                        circle.getCenterX() + 400 - Math.random() * 800,
                        circle.getCenterY() + 300 - Math.random() * 600
                ));

                pt.setPath(path);
                pt.setCycleCount(Animation.INDEFINITE);
                pt.setAutoReverse(true);
                pt.play();


                ScaleTransition st = new ScaleTransition(Duration.seconds(2 + Math.random() * 3), circle);
                st.setByX(0.5);
                st.setByY(0.5);
                st.setCycleCount(Animation.INDEFINITE);
                st.setAutoReverse(true);
                st.play();
            }
        }
    }

    private Text createGlowingText(String content, double size, Color fill, Color glowColor) {
        Text text = new Text(content);
        text.setFont(Font.font("Montserrat", FontWeight.EXTRA_BOLD, size));
        text.setFill(fill);

        Blend glowEffect = new Blend();
        glowEffect.setMode(BlendMode.ADD);

        Bloom bloom = new Bloom();
        bloom.setThreshold(0.1);

        Glow glow = new Glow();
        glow.setLevel(0.9);

        DropShadow ds = new DropShadow(BlurType.GAUSSIAN, glowColor, 30, 0.7, 0, 0);

        glowEffect.setTopInput(bloom);
        glowEffect.setBottomInput(ds);

        text.setEffect(glowEffect);
        return text;
    }

    private FuturisticTextField createFuturisticTextField(String promptText, double width) {
        return new FuturisticTextField(promptText, width);
    }

    private static class FuturisticTextField extends StackPane {
        private final TextField textField;
        public FuturisticTextField(String promptText, double width) {
            setMaxWidth(width);

            textField = new TextField();
            textField.setPromptText(promptText);
            textField.setFont(Font.font("Segoe UI", FontWeight.BOLD, 16));
            textField.setAlignment(Pos.CENTER_LEFT);
            textField.setStyle(
                    "-fx-background-color: transparent; -fx-text-fill: #bfe9ff; -fx-prompt-text-fill: #6b8a9e;"
            );

            Rectangle bg = new Rectangle(width, 48);
            bg.setArcWidth(25);
            bg.setArcHeight(25);
            bg.setFill(Color.rgb(10, 30, 50, 0.4));


            LinearGradient borderGradient = new LinearGradient(0, 0, 1, 0, true, CycleMethod.NO_CYCLE,
                    new Stop(0, Color.TRANSPARENT),
                    new Stop(0.3, Color.web("#00e6ff")),
                    new Stop(0.7, Color.web("#00e6ff")),
                    new Stop(1, Color.TRANSPARENT)
            );
            bg.setStroke(borderGradient);
            bg.setStrokeWidth(1.5);


            InnerShadow innerGlow = new InnerShadow(BlurType.GAUSSIAN, Color.web("#00e6ff", 0.4), 10, 0, 0, 0);
            bg.setEffect(innerGlow);
            getChildren().addAll(bg, textField);

            DropShadow glow = new DropShadow(BlurType.GAUSSIAN, Color.web("#00e6ff"), 25, 0.7, 0, 0);
            textField.focusedProperty().addListener((obs, old, isFocused) -> {
                if (isFocused) {
                    bg.setStroke(Color.web("#00e6ff"));
                    bg.setEffect(new Glow(0.5));
                } else {
                    bg.setStroke(borderGradient);
                    bg.setEffect(innerGlow);
                }
            });

        }

        public TextField getTextField() {
            return textField;
        }
        public String getText() {
            return textField.getText();
        }
    }

    private void TailoredReviewsPage(Stage stage){
        VBox vBox= new VBox();
        Button back = new Button();
        back.setContentDisplay(ContentDisplay.TOP);
        back.setStyle("-fx-background-color: #FFD700; " +
                "-fx-text-fill: white; " +
                "-fx-font-weight: bold; " +
                "-fx-background-radius: 20; " +
                "-fx-border-radius: 20; ");
        back.setFont(Font.font("Segoe UI", FontWeight.BOLD, 16));


        LinearGradient borderGradient = new LinearGradient(0, 0, 1, 0, true, CycleMethod.NO_CYCLE,
                new Stop(0, Color.TRANSPARENT),
                new Stop(0.3, Color.web("#FFD700")),
                new Stop(0.7, Color.web("#FFD700")),
                new Stop(1, Color.TRANSPARENT)
        );

        back.setBorder(new Border(new BorderStroke(
                borderGradient,
                BorderStrokeStyle.SOLID,
                new CornerRadii(20),
                new BorderWidths(2))
        ));

        DropShadow glow = new DropShadow(BlurType.GAUSSIAN, Color.web("#FFD700"), 20, 0.5, 0, 0);
        back.setEffect(glow);


        back.setOnMouseEntered(e -> {
            back.setEffect(new DropShadow(BlurType.GAUSSIAN, Color.web("#FFD700"), 30, 0.7, 0, 0));
            back.setStyle(back.getStyle() + "-fx-background-color: #FFD700;");
        });

        back.setOnMouseExited(e -> {
            back.setEffect(glow);
            back.setStyle(back.getStyle().replace("-fx-background-color: #FFD700;",
                    "-fx-background-color: #FFD700;"));
        });


        ScrollPane scrollPane = new ScrollPane(TailoredReviewVPanel);
        scrollPane.setStyle("-fx-background:linear-gradient(to bottom, #0e0e1f, #11142d, #141a3c);");
        scrollPane.setFitToWidth(true);
        scrollPane.setPannable(true);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
        vBox.getChildren().add(back);
        vBox.getChildren().add(scrollPane);
        vBox.setStyle("-fx-background:linear-gradient(to bottom, #0e0e1f, #11142d, #141a3c);");
        Scene scene = new Scene(vBox,Main.width,Main.height);
        stage.setScene(scene);
        back.setOnAction(e -> {
            clearTailoredReviewPage();
            Main.ClearTestDirectory();
            start(stage);
        });
        stage.show();
    }
    private void clearTailoredReviewPage(){
        TailoredReviewVPanel.getChildren().clear();

    }
    private void openRottenTomatoes() {
        Stage settingsStage = new Stage();
        StackPane root = new StackPane();
        root.setPadding(new Insets(30));
        root.setStyle("-fx-background-color: linear-gradient(to bottom, #0e0e1f, #11142d, #141a3c);");
        Rectangle glowRect = new Rectangle(450, 400);
        glowRect.setFill(Color.TRANSPARENT);
        glowRect.setStroke(LinearGradient.valueOf("linear-gradient(to right, #9d4edd, #5a189a)"));
        glowRect.setStrokeWidth(2);
        glowRect.setArcWidth(30);
        glowRect.setArcHeight(30);
        glowRect.setEffect(new InnerShadow(BlurType.GAUSSIAN, Color.web("#9d4edd", 0.5), 20, 0, 0, 0));
        root.getChildren().add(glowRect);
        VBox content = new VBox(20);
        content.setAlignment(Pos.CENTER);
        content.setPadding(new Insets(30));
        content.setMaxWidth(400);
        Text title = createGlowingText("Movies/TV Shows", 24, Color.WHITE, Color.web("#9370DB"));
        Text subtitle = new Text("Adjust your Movie/Show preferences");
        subtitle.setFont(Font.font("Segoe UI", FontWeight.BOLD, 16));
        subtitle.setFill(Color.web("#bfe9ff"));
        subtitle.setEffect(new Glow(0.5));
        FuturisticTextField name = createFuturisticTextField("Name of Media", 500);
        FuturisticTextField media = createFuturisticTextField("Media Type ('m' for Movie, 'tv' for show)", 500);
        FuturisticTextField s = createFuturisticTextField("Season if applicable (Format: s02,s03,s11, NA etc)", 500);
        FuturisticTextField t = createFuturisticTextField("Enter Time for Scroll (Seconds)", 500);
        Button Search = createFuturisticButton("Search", buildSaveIcon(), Color.web("#7CFC00"));
        HBox controls = new HBox(30, Search);
        controls.setAlignment(Pos.CENTER);
        content.getChildren().addAll(title, subtitle, name,media,s,t, controls);
        root.getChildren().add(content);
        Scene scene = new Scene(root,Main.width ,Main.height );
        settingsStage.setScene(scene);
        settingsStage.setTitle("Movies/TV Shows");
        settingsStage.show();
        Search.setOnAction(e-> {
            try {
                returnRottenTomatoeDetails(name,media,s,t);
                settingsStage.close();
            } catch (InterruptedException ex) {
                throw new RuntimeException(ex);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });
    }
    private void openMetaCritic() {
        Stage settingsStage = new Stage();
        StackPane root = new StackPane();
        root.setPadding(new Insets(30));
        root.setStyle("-fx-background-color: linear-gradient(to bottom, #0e0e1f, #11142d, #141a3c);");
        Rectangle glowRect = new Rectangle(450, 400);
        glowRect.setFill(Color.TRANSPARENT);
        glowRect.setStroke(LinearGradient.valueOf("linear-gradient(to right, #9d4edd, #5a189a)"));
        glowRect.setStrokeWidth(2);
        glowRect.setArcWidth(30);
        glowRect.setArcHeight(30);
        glowRect.setEffect(new InnerShadow(BlurType.GAUSSIAN, Color.web("#9d4edd", 0.5), 20, 0, 0, 0));
        root.getChildren().add(glowRect);
        VBox content = new VBox(20);
        content.setAlignment(Pos.CENTER);
        content.setPadding(new Insets(30));
        content.setMaxWidth(400);
        Text title = createGlowingText("Movies/TV Shows", 24, Color.WHITE, Color.web("#9370DB"));
        Text subtitle = new Text("Adjust your Movie/Show preferences");
        subtitle.setFont(Font.font("Segoe UI", FontWeight.BOLD, 16));
        subtitle.setFill(Color.web("#bfe9ff"));
        subtitle.setEffect(new Glow(0.5));
        FuturisticTextField name = createFuturisticTextField("Name of Media", 500);
        FuturisticTextField media = createFuturisticTextField("Media Type ('movie' for Movie, 'tv' for show)", 500);
        FuturisticTextField s = createFuturisticTextField("Season if applicable (Format: 2,3,11,NA etc)", 500);
        FuturisticTextField t = createFuturisticTextField("Enter Time for Scroll (Seconds)", 500);
        Button Search = createFuturisticButton("Search", buildSaveIcon(), Color.web("#7CFC00"));
        HBox controls = new HBox(30, Search);
        controls.setAlignment(Pos.CENTER);
        content.getChildren().addAll(title, subtitle, name,media,s,t, controls);
        root.getChildren().add(content);
        Scene scene = new Scene(root, Main.width,Main.height );
        settingsStage.setScene(scene);
        settingsStage.setTitle("Movies/TV Shows");
        settingsStage.show();
        Search.setOnAction(e-> {
            try {
                returnMetaCriticDetails(name,media,s,t);
                settingsStage.close();
            } catch (InterruptedException ex) {
                throw new RuntimeException(ex);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });
    }
    private void openSteamSite() {
        Stage settingsStage = new Stage();
        StackPane root = new StackPane();
        root.setPadding(new Insets(30));
        root.setStyle("-fx-background-color: linear-gradient(to bottom, #0e0e1f, #11142d, #141a3c);");


        Rectangle glowRect = new Rectangle(450, 400);
        glowRect.setFill(Color.TRANSPARENT);
        glowRect.setStroke(LinearGradient.valueOf("linear-gradient(to right, #9d4edd, #5a189a)"));
        glowRect.setStrokeWidth(2);
        glowRect.setArcWidth(30);
        glowRect.setArcHeight(30);
        glowRect.setEffect(new InnerShadow(BlurType.GAUSSIAN, Color.web("#9d4edd", 0.5), 20, 0, 0, 0));
        root.getChildren().add(glowRect);

        VBox content = new VBox(20);
        content.setAlignment(Pos.CENTER);
        content.setPadding(new Insets(30));
        content.setMaxWidth(400);

        Text title = createGlowingText("App ID", 24, Color.WHITE, Color.web("#9370DB"));
        Text subtitle = new Text("Adjust your gaming preferences");
        subtitle.setFont(Font.font("Segoe UI", FontWeight.BOLD, 16));
        subtitle.setFill(Color.web("#bfe9ff"));
        subtitle.setEffect(new Glow(0.5));

        FuturisticTextField id = createFuturisticTextField("App ID", 300);
        FuturisticTextField t = createFuturisticTextField("Enter Time for Scroll (Seconds)", 300);

        Button Search = createFuturisticButton("Search", buildSaveIcon(), Color.web("#7CFC00"));
        HBox controls = new HBox(30, Search);
        controls.setAlignment(Pos.CENTER);
        content.getChildren().addAll(title, subtitle, id,t, controls);
        root.getChildren().add(content);
        Scene scene = new Scene(root,Main.width ,Main.height );
        settingsStage.setScene(scene);
        settingsStage.setTitle("Game ID");
        settingsStage.show();

        Search.setOnAction(e-> {
            try {
                returnSteamDetails(id,t);
                settingsStage.close();
            } catch (InterruptedException | IOException ex) {
                throw new RuntimeException(ex);
            }
        });

    }
    private void openCriticSite() {
        Stage settingsStage = new Stage();
        StackPane root = new StackPane();
        root.setPadding(new Insets(30));
        root.setStyle("-fx-background-color: linear-gradient(to bottom, #0e0e1f, #11142d, #141a3c);");


        Rectangle glowRect = new Rectangle(450, 400);
        glowRect.setFill(Color.TRANSPARENT);
        glowRect.setStroke(LinearGradient.valueOf("linear-gradient(to right, #9d4edd, #5a189a)"));
        glowRect.setStrokeWidth(2);
        glowRect.setArcWidth(30);
        glowRect.setArcHeight(30);
        glowRect.setEffect(new InnerShadow(BlurType.GAUSSIAN, Color.web("#9d4edd", 0.5), 20, 0, 0, 0));
        root.getChildren().add(glowRect);

        VBox content = new VBox(20);
        content.setAlignment(Pos.CENTER);
        content.setPadding(new Insets(30));
        content.setMaxWidth(400);
        Text subtitle = new Text("Adjust your gaming preferences");
        subtitle.setFont(Font.font("Segoe UI", FontWeight.BOLD, 16));
        subtitle.setFill(Color.web("#bfe9ff"));
        subtitle.setEffect(new Glow(0.5));

        FuturisticTextField n = createFuturisticTextField("Link To Game", 300);
        FuturisticTextField t = createFuturisticTextField("Enter Time for Scroll (Seconds)", 300);

        Button Search = createFuturisticButton("Search", buildSaveIcon(), Color.web("#7CFC00"));
        HBox controls = new HBox(30, Search);
        controls.setAlignment(Pos.CENTER);
        content.getChildren().addAll(subtitle, n,t, controls);
        root.getChildren().add(content);

        Scene scene = new Scene(root, 500, 450);
        settingsStage.setScene(scene);
        settingsStage.setTitle("Game Name");
        settingsStage.show();
        Search.setOnAction(e-> {
            try {
                returnOpenCriticDetails(n,t);
                settingsStage.close();
            } catch (InterruptedException ex) {
                throw new RuntimeException(ex);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });


    }

    private void returnSteamDetails(FuturisticTextField a,FuturisticTextField b) throws InterruptedException, IOException {
        int ID = Integer.parseInt(a.getText());
        int t = Integer.parseInt(b.getText());
        Main.SearchSteam(ID,t);
    }
    private void returnOpenCriticDetails(FuturisticTextField a,FuturisticTextField b) throws InterruptedException, IOException {
        String link=a.getText();
        int t = Integer.parseInt(b.getText());
        Main.SearchOpenCritic(link,t);
    }
    private void returnRottenTomatoeDetails(FuturisticTextField a, FuturisticTextField b, FuturisticTextField c, FuturisticTextField d) throws IOException, InterruptedException {
        Main.SearchRottenTomatoes(a.getText(),b.getText(),c.getText(), Integer.parseInt(d.getText()));
    }
    private void returnMetaCriticDetails(FuturisticTextField a, FuturisticTextField b, FuturisticTextField c, FuturisticTextField d)throws IOException, InterruptedException{
        Main.SearchMetaCritic(a.getText(),b.getText(),c.getText(), Integer.parseInt(d.getText()));
    }
    private void openSecondaryGUI(String title, Stage stage, List<Button> buttons) {
        StackPane root = new StackPane();
        root.setPadding(new Insets(40));
        root.setStyle("-fx-background-color: linear-gradient(to bottom right, #0f2027, #1d3a43, #2c5364);");


        for (int i = 0; i < 30; i++) {
            Circle particle = new Circle(1 + Math.random() * 2, Color.web("#00e6ff", 0.2));
            particle.setLayoutX(Math.random() * 1200);
            particle.setLayoutY(Math.random() * 750);
            particle.setEffect(new Glow(0.8));
            root.getChildren().add(particle);
        }

        Text titleText = createGlowingText(title.toUpperCase(), 28, Color.WHITE, Color.web("#00bcd4"));
        titleText.setTranslateY(-220);
        HBox hBox= new HBox(20);
        GridPane optionsPane = new GridPane();
        optionsPane.setAlignment(Pos.CENTER);
        optionsPane.setHgap(40);
        optionsPane.setVgap(30);
        optionsPane.setPadding(new Insets(20));
        Button backButton =  returnbackButton();
        backButton.setOnAction(e -> start(stage));
        HBox backButtonContainer = new HBox(backButton);
        backButtonContainer.setAlignment(Pos.BASELINE_CENTER);
        backButtonContainer.setPadding(new Insets(40, 0, 0, 0));
        hBox.setAlignment(Pos.CENTER);
        hBox.setFillHeight(true);
        for (Button button : buttons) {
            hBox.getChildren().add(button);



        }
        VBox content= new VBox(20, optionsPane, backButtonContainer,hBox);
        content.setAlignment(Pos.CENTER);
        root.getChildren().addAll(titleText, content);
        StackPane.setAlignment(content, Pos.CENTER);

        Scene scene = new Scene(root,Main.width , Main.height);
        stage.setScene(scene);
        stage.setTitle(title);
    }

    private Node buildMoonIcon(){
        Circle big = new Circle(100, 100, 60);
        big.setFill(Color.web("#e6e6e6"));
        big.setStroke(Color.BLACK);
        return big;
    }

    private Node buildTvIcon() {
        Path tv = new Path();
        tv.getElements().addAll(
                new MoveTo(10, 10),
                new LineTo(40, 10),
                new LineTo(45, 35),
                new LineTo(5, 35),
                new LineTo(10, 10),
                new MoveTo(15, 35),
                new LineTo(15, 40),
                new LineTo(35, 40),
                new LineTo(35, 35)
        );
        tv.setStroke(LinearGradient.valueOf("linear-gradient(to right, #9d4edd, #5a189a)"));
        tv.setStrokeWidth(2);
        tv.setStrokeLineJoin(StrokeLineJoin.ROUND);
        tv.setFill(Color.TRANSPARENT);
        tv.setEffect(new Glow(0.8));
        return tv;
    }
    private Node buldRoboIcon(){
        Path robot = new Path();
        robot.getElements().add(new MoveTo(50, 50));
        robot.getElements().add(new LineTo(90, 50));
        robot.getElements().add(new LineTo(90, 90));
        robot.getElements().add(new LineTo(50, 90));
        robot.getElements().add(new LineTo(50, 50));

        robot.getElements().add(new MoveTo(60, 90));
        robot.getElements().add(new LineTo(80, 90));
        robot.getElements().add(new LineTo(80, 150));
        robot.getElements().add(new LineTo(60, 150));
        robot.getElements().add(new LineTo(60, 90));


        robot.getElements().add(new MoveTo(50, 100));
        robot.getElements().add(new LineTo(30, 100));
        robot.getElements().add(new LineTo(30, 130));
        robot.getElements().add(new LineTo(50, 130));
        robot.getElements().add(new LineTo(50, 100));


        robot.getElements().add(new MoveTo(90, 100));
        robot.getElements().add(new LineTo(110, 100));
        robot.getElements().add(new LineTo(110, 130));
        robot.getElements().add(new LineTo(90, 130));
        robot.getElements().add(new LineTo(90, 100));


        robot.getElements().add(new MoveTo(60, 150));
        robot.getElements().add(new LineTo(60, 180));
        robot.getElements().add(new LineTo(70, 180));
        robot.getElements().add(new LineTo(70, 150));
        robot.getElements().add(new LineTo(60, 150));


        robot.getElements().add(new MoveTo(70, 150));
        robot.getElements().add(new LineTo(70, 180));
        robot.getElements().add(new LineTo(80, 180));
        robot.getElements().add(new LineTo(80, 150));
        robot.getElements().add(new LineTo(70, 150));

        robot.setStroke(Color.BLACK);
        robot.setFill(Color.LIGHTGRAY);
        robot.setEffect(new Glow(2.0));
        return robot;

    }
    private Node buildConsoleIcon() {
        Path console = new Path();
        console.getElements().addAll(
                new MoveTo(5, 25),
                new LineTo(45, 25),
                new LineTo(45, 30),
                new LineTo(5, 30),
                new LineTo(5, 25),
                new MoveTo(15, 15),
                new LineTo(35, 15),
                new LineTo(35, 20),
                new LineTo(15, 20),
                new LineTo(15, 15)
        );
        console.setStroke(LinearGradient.valueOf("linear-gradient(to right, #ff6d00, #ff9e00)"));
        console.setStrokeWidth(2);
        console.setStrokeLineJoin(StrokeLineJoin.ROUND);
        console.setFill(Color.TRANSPARENT);
        console.setEffect(new Glow(0.8));
        return console;
    }
    private Node buildExitIcon() {
        Line l1 = new Line(0, 0, 20, 20);
        Line l2 = new Line(20, 0, 0, 20);
        l1.setStroke(LinearGradient.valueOf("linear-gradient(to right, #ff0054, #ff477e)"));
        l2.setStroke(LinearGradient.valueOf("linear-gradient(to right, #ff0054, #ff477e)"));
        l1.setStrokeWidth(2.5);
        l2.setStrokeWidth(2.5);
        l1.setEffect(new Glow(0.8));
        l2.setEffect(new Glow(0.8));
        return new Group(l1, l2);
    }
    private Node buildPowerIcon() {
        Circle outer = new Circle(15);
        outer.setFill(Color.TRANSPARENT);
        outer.setStroke(LinearGradient.valueOf("linear-gradient(to right, #FF4500, #FF6B6B)"));
        outer.setStrokeWidth(2);

        Line line = new Line(0, -10, 0, 10);
        line.setStroke(Color.WHITE);
        line.setStrokeWidth(2);
        line.setEffect(new Glow(0.8));

        return new Group(outer, line);
    }

    private Node buildVolumeIcon(boolean up) {
        Path base = new Path();
        base.getElements().addAll(
                new MoveTo(5, 10),
                new LineTo(15, 5),
                new LineTo(15, 25),
                new LineTo(5, 20),
                new ClosePath()
        );
        base.setFill(Color.TRANSPARENT);
        base.setStroke(LinearGradient.valueOf("linear-gradient(to right, #1E90FF, #70A1FF)"));
        base.setStrokeWidth(2);

        Path waves = new Path();
        if (up) {
            waves.getElements().addAll(
                    new MoveTo(20, 15),
                    new QuadCurveTo(25, 10, 30, 15),
                    new MoveTo(25, 15),
                    new QuadCurveTo(30, 5, 35, 15)
            );
        } else {
            waves.getElements().addAll(
                    new MoveTo(20, 15),
                    new QuadCurveTo(25, 20, 30, 15)
            );
        }
        waves.setStroke(LinearGradient.valueOf("linear-gradient(to right, #1E90FF, #70A1FF)"));
        waves.setStrokeWidth(2);
        waves.setStrokeLineCap(StrokeLineCap.ROUND);

        return new Group(base, waves);
    }

    private Node buildInputSourceIcon() {
        Path path = new Path();
        path.getElements().addAll(
                new MoveTo(5, 5),
                new LineTo(20, 5),
                new LineTo(20, 20),
                new LineTo(5, 20),
                new ClosePath(),
                new MoveTo(25, 10),
                new LineTo(35, 10),
                new LineTo(35, 25),
                new LineTo(25, 25),
                new ClosePath(),
                new MoveTo(10, 25),
                new LineTo(15, 30),
                new LineTo(25, 30),
                new LineTo(30, 25)
        );
        path.setFill(Color.TRANSPARENT);
        path.setStroke(LinearGradient.valueOf("linear-gradient(to right, #32CD32, #7CFC00)"));
        path.setStrokeWidth(2);
        path.setEffect(new Glow(0.8));
        return path;
    }

    private Node buildGameControllerIcon() {
        Path controller = new Path();
        controller.getElements().addAll(
                new MoveTo(5, 15),
                new LineTo(35, 15),
                new LineTo(35, 25),
                new LineTo(5, 25),
                new ClosePath(),
                new MoveTo(10, 18),
                new LineTo(15, 18),
                new LineTo(15, 22),
                new LineTo(10, 22),
                new ClosePath(),
                new MoveTo(25, 18),
                new LineTo(30, 18),
                new LineTo(30, 22),
                new LineTo(25, 22),
                new ClosePath(),
                new MoveTo(18, 5),
                new LineTo(22, 5),
                new LineTo(22, 12),
                new LineTo(18, 12),
                new ClosePath()
        );
        controller.setFill(Color.TRANSPARENT);
        controller.setStroke(LinearGradient.valueOf("linear-gradient(to right, #9370DB, #B19CD9)"));
        controller.setStrokeWidth(2);
        controller.setEffect(new Glow(0.8));
        return controller;
    }

    private Node buildGearIcon() {
        Circle outerCircle = new Circle(15);
        outerCircle.setFill(Color.TRANSPARENT);
        outerCircle.setStroke(LinearGradient.valueOf("linear-gradient(to right, #20B2AA, #40E0D0)"));
        outerCircle.setStrokeWidth(2);

        Circle innerCircle = new Circle(8);
        innerCircle.setFill(Color.TRANSPARENT);
        innerCircle.setStroke(LinearGradient.valueOf("linear-gradient(to right, #20B2AA, #40E0D0)"));
        innerCircle.setStrokeWidth(1.5);

        Group gearGroup = new Group();
        gearGroup.getChildren().add(outerCircle);

        for (int i = 0; i < 8; i++) {
            Line tooth = new Line(0, -18, 0, -12);
            tooth.setStroke(LinearGradient.valueOf("linear-gradient(to right, #20B2AA, #40E0D0)"));
            tooth.setStrokeWidth(2);
            tooth.setRotate(i * 45);
            gearGroup.getChildren().add(tooth);
        }

        gearGroup.getChildren().add(innerCircle);
        return gearGroup;
    }

    private Node buildEjectIcon() {
        Polygon triangle = new Polygon(0, 20, 10, 0, 20, 20);
        triangle.setFill(Color.TRANSPARENT);
        triangle.setStroke(LinearGradient.valueOf("linear-gradient(to right, #FF6347, #FFA07A)"));
        triangle.setStrokeWidth(2);
        triangle.setEffect(new Glow(0.8));

        Rectangle rect = new Rectangle(5, 25, 10, 5);
        rect.setFill(Color.TRANSPARENT);
        rect.setStroke(LinearGradient.valueOf("linear-gradient(to right, #FF6347, #FFA07A)"));
        rect.setStrokeWidth(2);

        return new Group(triangle, rect);
    }

    private Node buildArrowIcon(boolean up) {
        Polygon arrow = up ?
                new Polygon(0, 20, 10, 0, 20, 20) :
                new Polygon(0, 0, 10, 20, 20, 0);
        arrow.setFill(Color.TRANSPARENT);
        arrow.setStroke(Color.WHITE);
        arrow.setStrokeWidth(2);
        arrow.setEffect(new Glow(0.8));
        return arrow;
    }

    private Node buildSaveIcon() {
        Path path = new Path();
        path.getElements().addAll(
                new MoveTo(5, 5),
                new LineTo(15, 5),
                new LineTo(15, 15),
                new LineTo(20, 15),
                new LineTo(20, 25),
                new LineTo(5, 25),
                new ClosePath(),
                new MoveTo(8, 15),
                new LineTo(12, 15),
                new LineTo(12, 20),
                new LineTo(8, 20),
                new ClosePath()
        );
        path.setFill(Color.TRANSPARENT);
        path.setStroke(LinearGradient.valueOf("linear-gradient(to right, #7CFC00, #ADFF2F)"));
        path.setStrokeWidth(2);
        path.setEffect(new Glow(0.8));
        return path;
    }
    private Node buildBackIcon() {
        Polygon arrow = new Polygon(20, 0, 0, 10, 20, 20);
        arrow.setFill(Color.TRANSPARENT);
        arrow.setStroke(LinearGradient.valueOf("linear-gradient(to right, #00e6ff, #00b4d8)"));
        arrow.setStrokeWidth(2);
        arrow.setEffect(new Glow(0.8));
        return arrow;
    }

    private Button createFuturisticButton(String label, Node icon) {
        return createFuturisticButton(label, icon, Color.web("#00e6ff"));
    }

    private Button createFuturisticButton(String label, Node icon, Color glowColor) {
        Button btn = new Button(label, icon);
        btn.setContentDisplay(ContentDisplay.TOP);
        btn.setStyle("-fx-background-color: rgba(15, 30, 45, 0.6); " +
                "-fx-text-fill: white; " +
                "-fx-font-weight: bold; " +
                "-fx-background-radius: 20; " +
                "-fx-border-radius: 20; ");
        btn.setFont(Font.font("Segoe UI", FontWeight.BOLD, 16));

        LinearGradient borderGradient = new LinearGradient(0, 0, 1, 0, true, CycleMethod.NO_CYCLE,
                new Stop(0, Color.TRANSPARENT),
                new Stop(0.3, glowColor),
                new Stop(0.7, glowColor),
                new Stop(1, Color.TRANSPARENT)
        );

        btn.setBorder(new Border(new BorderStroke(
                borderGradient,
                BorderStrokeStyle.SOLID,
                new CornerRadii(20),
                new BorderWidths(2))
        ));

        DropShadow glow = new DropShadow(BlurType.GAUSSIAN, glowColor, 20, 0.5, 0, 0);
        btn.setEffect(glow);

        btn.setOnMouseEntered(e -> {
            btn.setEffect(new DropShadow(BlurType.GAUSSIAN, glowColor, 30, 0.7, 0, 0));
            btn.setStyle(btn.getStyle() + "-fx-background-color: rgba(20, 40, 60, 0.8);");
        });

        btn.setOnMouseExited(e -> {
            btn.setEffect(glow);
            btn.setStyle(btn.getStyle().replace("-fx-background-color: rgba(20, 40, 60, 0.8);",
                    "-fx-background-color: rgba(15, 30, 45, 0.6);"));
        });

        return btn;
    }

    private Group buildOrbitRings() {
        Group group = new Group();

        for (int i = 1; i <= 50; i++) {
            double radius = 600 * i;
            Circle orbit = new Circle(radius, Color.TRANSPARENT);
            RadialGradient gradient = new RadialGradient(
                    0, 0, 0.5, 0.5, 0.5, true, CycleMethod.NO_CYCLE,
                    new Stop(0, Color.web("#00e6ff")),
                    new Stop(0.5, Color.web("#00b4d8")),
                    new Stop(1, Color.TRANSPARENT)
            );

            orbit.setStroke(gradient);
            orbit.setStrokeWidth(1.5);
            orbit.setEffect(new Glow(0.4));
            group.getChildren().add(orbit);
        }

        return group;
    }

    private void startOrbitAnimation(Group orbits) {
        RotateTransition rt = new RotateTransition(Duration.seconds(40), orbits);
        rt.setByAngle(360);
        rt.setCycleCount(Animation.INDEFINITE);
        rt.setInterpolator(Interpolator.LINEAR);
        rt.play();
    }

    public static void main() {
        launch();
    }
}