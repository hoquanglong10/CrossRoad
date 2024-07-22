package com.demo.mainapp.crossyroaddemo;

import javafx.animation.FillTransition;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.net.URL;

public class HelloApplication extends Application {
    private HelloController game;
    private Stage primaryStage;
    private final int WIDTH = 1920;
    private final int HEIGHT = 1080;
    private MediaPlayer mediaPlayer;
    private boolean isMuted = false;
    private boolean isSFXMuted = false;
    private double sfxVolume = 0.5; // Default SFX volume

    @Override
    public void start(Stage stage) {
        this.primaryStage = stage;
        primaryStage.setTitle("Crossy Road - Version: 1.0.0 - Team thằng nào mạnh, thằng đấy làm!");
        playBackgroundMusic();
        showMainMenu();
        primaryStage.show();
    }

    private void playBackgroundMusic() {
        String musicFile = getClass().getResource("/assets/backgroundmusic.mp3").toExternalForm();
        Media media = new Media(musicFile);
        mediaPlayer = new MediaPlayer(media);
        mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE); // Loop the music
        mediaPlayer.play();
    }

    private Parent createContent() {
        Pane root = new Pane();
        root.setPrefSize(WIDTH, HEIGHT);

        // Background Image
        Image bgImage = new Image(getClass().getResource("/assets/minecraft-crossy-road.png").toExternalForm(), WIDTH, HEIGHT, false, true);
        ImageView bgImageView = new ImageView(bgImage);
        URL resource = getClass().getResource("/assets/backgroundmusic.mp3");

        if (resource == null) {
            System.out.println("Audio file not found!");
        }
        Media media = new Media(resource.toString());
        MediaPlayer a =new MediaPlayer(media);
//        a.setOnEndOfMedia( () -> a.seek(Duration.ZERO));
        a.setCycleCount(MediaPlayer.INDEFINITE);
        a.play();
        // Logo Image
        Image logoImage = new Image(getClass().getResource("/assets/logo-with-firebase.png").toExternalForm(), 400, 460, false, true);
        ImageView logoView = new ImageView(logoImage);

        VBox menuBox = createMenuBox();

        StackPane logoPane = new StackPane();
        logoPane.setPrefSize(WIDTH, 90);
        logoPane.getChildren().add(logoView);
        StackPane.setAlignment(logoView, Pos.CENTER);

        root.getChildren().addAll(bgImageView, logoPane, menuBox);
        return root;
    }

    private VBox createMenuBox() {
        VBox box = new VBox(10);
        box.setBackground(new Background(new BackgroundFill(Color.web("black", 0.6), null, null)));
        box.setTranslateX(WIDTH - 1700);
        box.setTranslateY(HEIGHT - 440);

        MenuItem playItem = new MenuItem("Bắt đầu", this::initGame);
        MenuItem settingsItem = new MenuItem("Cài đặt", this::initSettings);
        MenuItem quitItem = new MenuItem("Thoát", () -> {
            mediaPlayer.stop(); // Stop the music when quitting
            Platform.exit();
        });

        box.getChildren().addAll(playItem, settingsItem, quitItem);
        return box;
    }

    private static class MenuItem extends StackPane {
        MenuItem(String name, Runnable action) {
            LinearGradient gradient = new LinearGradient(0, 0.1, 1, 0.6, true, CycleMethod.NO_CYCLE,
                    new Stop(0.1, Color.web("white", 0.5)),
                    new Stop(1.0, Color.web("yellow", 0.5))
            );

            Rectangle bg0 = new Rectangle(250, 30, gradient);
            Rectangle bg1 = new Rectangle(250, 30, Color.web("black", 0.2));

            FillTransition ft = new FillTransition(Duration.seconds(0.6666), bg1,
                    Color.web("black", 0.2), Color.web("white", 0.3));
            ft.setAutoReverse(true);
            ft.setCycleCount(Integer.MAX_VALUE);

            hoverProperty().addListener((o, oldValue, isHovering) -> {
                if (isHovering) {
                    ft.playFromStart();
                } else {
                    ft.stop();
                    bg1.setFill(Color.web("black", 0.2));
                }
            });

            Rectangle line = new Rectangle(5, 30);
            line.widthProperty().bind(
                    Bindings.when(hoverProperty()).then(8).otherwise(5)
            );
            line.fillProperty().bind(
                    Bindings.when(hoverProperty()).then(Color.RED).otherwise(Color.GRAY)
            );

            Text text = new Text(name);
            text.setFont(Font.font(22.0));
            text.fillProperty().bind(
                    Bindings.when(hoverProperty()).then(Color.WHITE).otherwise(Color.BLACK)
            );

            setOnMouseClicked(e -> action.run());

            setOnMousePressed(e -> bg0.setFill(Color.LIGHTGREEN));
            setOnMouseReleased(e -> bg0.setFill(gradient));
            setAlignment(Pos.CENTER_LEFT);

            HBox box = new HBox(15, line, text);
            box.setAlignment(Pos.CENTER_LEFT);

            getChildren().addAll(bg0, bg1, box);
        }
    }

    private void showMainMenu() {
        Parent mainMenuContent = createContent();
        Scene mainMenuScene = new Scene(mainMenuContent, WIDTH, HEIGHT);
        primaryStage.setScene(mainMenuScene);
        primaryStage.setTitle("Main Menu");
    }

    protected void initSettings() {
        // Background Image
        Image bgImage = new Image(getClass().getResource("/assets/minecraft-crossy-road.png").toExternalForm(), WIDTH, HEIGHT, false, true);
        ImageView bgImageView = new ImageView(bgImage);

        // Settings Box
        VBox settingsBox = createSettingsBox(); // Tạo hàm này để tạo các mục trong cài đặt

        // StackPane to hold background and settings box
        Image closeButtonImage = new Image(getClass().getResource("/assets/X-Png.png").toExternalForm());
        ImageView closeButton = new ImageView(closeButtonImage);
        closeButton.setFitHeight(30);
        closeButton.setFitWidth(30);
        closeButton.setTranslateX(-470);
        closeButton.setTranslateY(200);

        closeButton.setOnMouseClicked(event -> showMainMenu());
        StackPane.setAlignment(closeButton, Pos.TOP_RIGHT);

        StackPane root = new StackPane(bgImageView, settingsBox, closeButton);
        root.setAlignment(Pos.CENTER);

        Scene scene = new Scene(root, WIDTH, HEIGHT);

        primaryStage.setScene(scene);
        primaryStage.setTitle("Settings");
        primaryStage.show();
    }

    private VBox createSettingsBox() {
        VBox settingsBox = new VBox(20);
        settingsBox.setAlignment(Pos.CENTER);
        settingsBox.setPadding(new Insets(20));
        settingsBox.setMaxSize(1000, 700); // Đặt kích thước tối đa cho khung cài đặt
        settingsBox.setBackground(new Background(new BackgroundFill(Color.rgb(0, 0, 0, 0.8), new CornerRadii(10), Insets.EMPTY)));
        settingsBox.setBorder(new Border(new BorderStroke(Color.WHITE, BorderStrokeStyle.SOLID, new CornerRadii(10), BorderWidths.DEFAULT)));

        // Mute Music Label
        Label muteMusicLabel = new Label("Tắt âm nhạc");
        muteMusicLabel.setFont(new Font(20));
        muteMusicLabel.setTextFill(Color.WHITE);
        muteMusicLabel.setOnMouseClicked(event -> {
            isMuted = !isMuted;
            mediaPlayer.setMute(isMuted);
            muteMusicLabel.setText(isMuted ? "Bật âm nhạc" : "Tắt âm nhạc");
        });

        // Volume Slider for Music
        Slider volumeSlider = new Slider(0, 1, mediaPlayer.getVolume());
        volumeSlider.setShowTickLabels(true);
        volumeSlider.setShowTickMarks(true);
        volumeSlider.valueProperty().addListener((obs, oldVal, newVal) -> mediaPlayer.setVolume(newVal.doubleValue()));

        // Mute SFX Label
        Label muteSFXLabel = new Label("Tắt SFX");
        muteSFXLabel.setFont(new Font(20));
        muteSFXLabel.setTextFill(Color.WHITE);
        muteSFXLabel.setOnMouseClicked(event -> {
            isSFXMuted = !isSFXMuted;
            muteSFXLabel.setText(isSFXMuted ? "Bật SFX" : "Tắt SFX");
            // You might want to integrate the functionality to handle SFX mute state here
        });

        // Volume Slider for SFX
        Slider sfxVolumeSlider = new Slider(0, 1, sfxVolume); // Initial SFX volume
        sfxVolumeSlider.setShowTickLabels(true);
        sfxVolumeSlider.setShowTickMarks(true);
        sfxVolumeSlider.valueProperty().addListener((obs, oldVal, newVal) -> {
            sfxVolume = newVal.doubleValue();
            // Update SFX volume here
        });

        settingsBox.getChildren().addAll(muteMusicLabel, volumeSlider, muteSFXLabel, sfxVolumeSlider);

        return settingsBox;
    }

    protected void initGame() {
        startNewGame();
    }

    private void startNewGame() {
        if (game != null) {
            game.cleanup(); // Clean up previous game resources
        }

        game = new HelloController(); // Ensure HelloController has a no-argument constructor
        Scene gameScene = new Scene(game.getRoot(), WIDTH, HEIGHT);

        gameScene.setOnKeyPressed(event -> {
            switch (event.getCode()) {
                case UP:
                    game.movePlayerUp();
                    break;
                case DOWN:
                    game.movePlayerDown();
                    break;
                case LEFT:
                    game.movePlayerLeft();
                    break;
                case RIGHT:
                    game.movePlayerRight();
                    break;
                default:
                    break;
            }
        });

        game.getRoot().requestFocus();

        primaryStage.setScene(gameScene);
        primaryStage.setTitle("Game");

        game.setOnGameOver(() -> showGameOver(game.getScore()));
    }

    private void showGameOver(int score) {
        VBox gameOverMenu = new VBox(20);
        gameOverMenu.setAlignment(Pos.CENTER);
        gameOverMenu.setPadding(new Insets(20));
        gameOverMenu.setPrefWidth(300); // Set a fixed width for the menu
        gameOverMenu.setPrefHeight(250); // Adjusted height for additional buttons
        gameOverMenu.setBackground(new Background(new BackgroundFill(Color.rgb(0, 0, 0, 0.3), new CornerRadii(10), Insets.EMPTY)));
        gameOverMenu.setBorder(new Border(new BorderStroke(Color.WHITE, BorderStrokeStyle.SOLID, new CornerRadii(10), BorderWidths.DEFAULT)));

        Text gameOverText = new Text("Game Over");
        gameOverText.setFont(Font.font("Arial", FontWeight.BOLD, 30));
        gameOverText.setFill(Color.RED);

        Text scoreText = new Text("Your Score: " + score);
        scoreText.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        scoreText.setFill(Color.WHITE);

        Button playAgainButton = new Button("Play Again");
        playAgainButton.setFont(Font.font("Arial", FontWeight.BOLD, 15));
        playAgainButton.setStyle("-fx-background-color: #28a745; -fx-text-fill: white; -fx-padding: 10 20; -fx-background-radius: 5;");
        playAgainButton.setOnAction(event -> startNewGame());

        Button backButton = new Button("Back to Menu");
        backButton.setFont(Font.font("Arial", FontWeight.BOLD, 15));
        backButton.setStyle("-fx-background-color: #ff5733; -fx-text-fill: white; -fx-padding: 10 20; -fx-background-radius: 5;");
        backButton.setOnAction(event -> showMainMenu());

        Button quitButton = new Button("Quit Game");
        quitButton.setFont(Font.font("Arial", FontWeight.BOLD, 15));
        quitButton.setStyle("-fx-background-color: #dc3545; -fx-text-fill: white; -fx-padding: 10 20; -fx-background-radius: 5;");
        quitButton.setOnAction(event -> Platform.exit());

        gameOverMenu.getChildren().addAll(gameOverText, scoreText, playAgainButton, backButton, quitButton);

        Image bgImage = new Image(getClass().getResource("/assets/gameover.jpg").toExternalForm(), WIDTH, HEIGHT, false, true);
        ImageView bgImageView = new ImageView(bgImage);

        StackPane root = new StackPane(bgImageView, gameOverMenu);
        StackPane.setAlignment(gameOverMenu, Pos.CENTER);

        Scene scene = new Scene(root, WIDTH, HEIGHT);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Game Over");
    }

    public static void main(String[] args) {
        launch(args);
    }
}
