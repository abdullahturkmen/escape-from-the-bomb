package com.example.demo;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Game extends Application {


    static int TILE_SIZE = 50; // 10x10 tahta için

    static int WIDTH = 500;
    static int HEIGHT = 500;

    static int X_TILES = WIDTH / TILE_SIZE;
    static int Y_TILES = HEIGHT / TILE_SIZE;

    private Scene startScene, gameScene;


    private Game.Tile[][] grid = new Game.Tile[X_TILES][Y_TILES];


    Alert a = new Alert(Alert.AlertType.NONE);


    private Pane createContent() {

        Pane root = new Pane();
        root.setPrefSize(WIDTH, HEIGHT);

        for (int y = 0; y < Y_TILES; y++) {
            for (int x = 0; x < X_TILES; x++) {
                Game.Tile tile = new Game.Tile(x, y, Math.random() < 0.2);

                grid[x][y] = tile;
                root.getChildren().add(tile);
            }
        }

        for (int y = 0; y < Y_TILES; y++) {
            for (int x = 0; x < X_TILES; x++) {
                Game.Tile tile = grid[x][y];

                if (tile.hasBomb)
                    continue;

                long bombs = getNeighbors(tile).stream().filter(t -> t.hasBomb).count();
                //System.out.println(bombs);

                if (bombs > 0) {
                    tile.text.setText(String.valueOf(bombs));
                }
            }
        }


        return root;
    }

    private List<Game.Tile> getNeighbors(Game.Tile tile) {

        List<Game.Tile> neighbors = new ArrayList<>();

        int[] points = new int[]{
                -1, -1,
                -1, 0,
                -1, 1,
                0, -1,
                0, 1,
                1, -1,
                1, 0,
                1, 1
        };

        for (int i = 0; i < points.length; i++) {
            int dx = points[i];
            int dy = points[++i];

            int newX = tile.x + dx;
            int newY = tile.y + dy;

            if (newX >= 0 && newX < X_TILES && newY >= 0 && newY < Y_TILES) {
                neighbors.add(grid[newX][newY]);
            }
        }

        return neighbors;

    }

    private class Tile extends StackPane {
        private int x, y;
        private boolean hasBomb;
        private boolean isOpen = false;


        private Rectangle border = new Rectangle(TILE_SIZE - 2, TILE_SIZE - 2);
        private Text text = new Text();

        public Tile(int x, int y, boolean hasBomb) {
            this.x = x;
            this.y = y;
            this.hasBomb = hasBomb;

            border.setStroke(Color.LIGHTGRAY);

            text.setFill(Color.WHITE);
            text.setFont(Font.font(18));

            if (hasBomb) {
                border.setFill(Color.BLACK);

            } else {
                text.setText("");
            }

            text.setVisible(false);

            getChildren().addAll(border, text);

            setTranslateX(x * TILE_SIZE);
            setTranslateY(y * TILE_SIZE);

            setOnMouseClicked(e -> open());
        }

        public void open() {
            if (isOpen)
                return;

            if (hasBomb) { //game over

                Image i = new Image("https://img.freepik.com/free-vector/round-black-bomb-realistic-style_52683-16086.jpg?size=338&ext=jpg");
                for (int y = 0; y < Y_TILES; y++) {
                    for (int x = 0; x < X_TILES; x++) {
                        Game.Tile tile = grid[x][y];

                        if (tile.hasBomb) {
                            tile.isOpen = true;
                            tile.text.setVisible(true);
                            tile.border.setFill(new ImagePattern(i));
                        }

                    }
                }


                a.setAlertType(Alert.AlertType.CONFIRMATION);
                a.setHeaderText("Game Over");
                a.setContentText("Press 'OK' button to play again 'Cancel' to finish");
                // show the dialog
                //a.showAndWait();


                Optional<ButtonType> option = a.showAndWait();

                if (option.get() == ButtonType.OK) {
                    gameScene.setRoot(createContent());
                } else if (option.get() == ButtonType.CANCEL) {
                    gameScene.getWindow().hide();
                    gameScene.setRoot(createContent());
                }

                return;
            }

            isOpen = true;
            text.setVisible(true);
            text.setFill(Color.RED);
            border.setFill(null);

            if (text.getText().isEmpty()) {
                getNeighbors(this).forEach(Game.Tile::open);
            }
        }
    }

    @Override
    public void start(Stage startStage) throws Exception {


        gameScene = new Scene(createContent());

        Stage gameStage = new Stage();

        gameStage.setScene(gameScene);

        Pane pane = new Pane();

        Label label = new Label();
        label.setText("Welcome to Escape from the Bomb");
        label.setFont(Font.font("Times New Roman", FontPosture.REGULAR, 20));
        label.setTextFill(Color.WHITE);
        Label label2 = new Label();
        label2.setFont(Font.font("Times New Roman", FontPosture.ITALIC, 10));
        label2.setText("(Please select a game board)");
        label2.setTextFill(Color.WHITE);


        Button ten = new Button("10x10");
        Button fifteen = new Button("15x15");
        Button twenty = new Button("20x20");


        ten.setFont(Font.font("Helvetica  ", FontWeight.BOLD, 15));
        fifteen.setFont(Font.font("Helvetica  ", FontWeight.BOLD, 15));
        twenty.setFont(Font.font("Helvetica", FontWeight.BOLD, 15));


        pane.getChildren().addAll(label, label2, ten, fifteen, twenty);


        label.relocate(75, 25);
        label2.relocate(160, 150);
        ten.relocate(35, 175);
        fifteen.relocate(190, 175);
        twenty.relocate(345, 175);


        Image image = new Image("https://mpng.subpng.com/20180306/ljw/kisspng-bomb-weapon-cartoon-bomb-vector-hand-painted-5a9e7906dc2fd7.4838742115203351109019.jpg");

        pane.setStyle("-fx-background-image :url(https://thumbs.dreamstime.com/b/minefield-mines-explosives-bombs-background-illustration-full-fuse-lit-each-bomb-going-to-go-boom-87753649.jpg)");

        ImageView imageview = new ImageView(image);


        ten.addEventHandler(ActionEvent.ACTION, e -> {
            gameStage.show();
        });

        fifteen.addEventHandler(ActionEvent.ACTION, e -> {
            gameStage.show();
        });

        twenty.addEventHandler(ActionEvent.ACTION, e -> {
            gameStage.show();
        });

        Scene startScene = new Scene(pane, 500, 500);

        startStage.setResizable(false);
        startStage.getIcons().add(image);
        startStage.setTitle("Escape From the Bomb");
        gameStage.getIcons().add(image);
        gameStage.setTitle("Escape From the Bomb");
        startStage.setScene(startScene);
        startStage.show();


    }


    public static void main(String[] args) {
        launch(args);
    }

}
