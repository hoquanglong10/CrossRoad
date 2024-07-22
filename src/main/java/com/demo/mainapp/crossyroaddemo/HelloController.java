package com.demo.mainapp.crossyroaddemo;

import com.demo.mainapp.crossyroaddemo.components.CarComponent;
import com.demo.mainapp.crossyroaddemo.components.PlayerCpn;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

import javafx.animation.AnimationTimer;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.scene.text.Font;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Random;

public class HelloController {
    private Pane root;
    private PlayerCpn player;
    private ArrayList<CarComponent> obstacles;
    private AnimationTimer timer;
    private Runnable onGameOver;
    private int score;
    private Text scoreText;

    public HelloController() {
        root = new Pane();
        player = new PlayerCpn(300, 440);
        root.getChildren().add(player.getShape());

        obstacles = new ArrayList<>();
        setupObstacles();
        //score
        score = 0;
        scoreText = new Text("Score: 0");
        scoreText.setFont(new Font(20));
        scoreText.setFill(Color.BLACK);
        scoreText.setX(500);
        scoreText.setY(30);
        root.getChildren().add(scoreText);

        // Thiết lập focus và sự kiện KeyEvent
        root.setFocusTraversable(true);
        root.requestFocus();

        // Thiết lập các phương thức di chuyển của player
        root.setOnKeyPressed(this::handleKeyPress);

        // Thiết lập vòng lặp AnimationTimer cho vòng lặp game
        timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                update();
            }
        };

        timer.start();
    }
    public Pane getRoot() {
        return root;
    }

    // Xử lý khi phím được nhấn
    private void handleKeyPress(KeyEvent event) {
        switch (event.getCode()) {
            case UP:
                movePlayerUp();
                break;
            case DOWN:
                movePlayerDown();
                break;
            case LEFT:
                movePlayerLeft();
                break;
            case RIGHT:
                movePlayerRight();
                break;
            default:
                break;
        }
    }

    public void movePlayerUp() {
        player.moveUp();
        if (player.getShape().getY() <= 0) {
            score++;
            scoreText.setText("Score: " + score);
            player.getShape().setY(440);
        }
    }

    public void movePlayerDown() {
        player.moveDown();
    }

    public void movePlayerLeft() {
        player.moveLeft();
    }

    public void movePlayerRight() {
        player.moveRight();
    }

    // Thiết lập các chướng ngại vật
    private void setupObstacles() {
        // Khởi tạo và thêm các chướng ngại vật vào root pane
        for (int i = 0; i < 5; i++) {
            CarComponent obstacle = createRandomObstacle();
            obstacles.add(obstacle);
            root.getChildren().add(obstacle);
        }
    }

    // Tạo ngẫu nhiên một chướng ngại vật
    private CarComponent createRandomObstacle() {
        Random random = new Random();
        double x = 640 + random.nextDouble() * 200; // Vị trí ngẫu nhiên ngoài màn hình bên phải
        double y = 50 + random.nextDouble() * 300; // Vị trí ngẫu nhiên trong khu vực hiển thị

        CarComponent obstacle = new CarComponent(x, y);
        return obstacle;
    }

    // Cập nhật logic game
//    private void update() {
//        // Cập nhật logic game, ví dụ: di chuyển chướng ngại vật, phát hiện va chạm, vv.
//        for (Obstacle obstacle : obstacles) {
//            obstacle.move();
//
//            // Kiểm tra va chạm với người chơi
//            if (player.getShape().getBoundsInParent().intersects(obstacle.getBoundsInParent())) {
//                timer.stop();
//                System.out.println("Game Over");
//            }
//        }
//    }

    public void setOnGameOver(Runnable onGameOver) {
        this.onGameOver = onGameOver;

    }
    public int getScore() {
        return score;
    }

    private void update() {
        // Cập nhật logic game, ví dụ: di chuyển chướng ngại vật, phát hiện va chạm, vv.
        for (CarComponent obstacle : obstacles) {
            obstacle.move();

            // Kiểm tra va chạm với người chơi
            if (player.getShape().getBoundsInParent().intersects(obstacle.getBoundsInParent())) {
                timer.stop();
                if (onGameOver != null) {
                    onGameOver.run();
                }
            }
        }
    }
    public void cleanup() {
        // Stop the animation timer
        if (timer != null) {
            timer.stop();
        }

        // Remove all obstacles from the root pane
        root.getChildren().removeAll(obstacles);
        obstacles.clear();

        // Remove the player from the root pane
        root.getChildren().remove(player.getShape());

        // Remove key event handler
        root.setOnKeyPressed(null);

        // Optionally: Perform additional cleanup if needed
    }
}