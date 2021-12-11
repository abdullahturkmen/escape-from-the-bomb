package com.example.demo;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class HelloApplication extends Application {

    private static final int TILE_SIZE = 50; // 10x10 tahta için
    private static final int WIDTH = 500;
    private static final int HEIGHT = 500;

    private Scene scene;

    private static final int X_TILES = WIDTH / TILE_SIZE;
    private static final int Y_TILES = HEIGHT / TILE_SIZE;

    private Tile[][] grid = new Tile[X_TILES][Y_TILES];

    Group group1 = new Group();
    Scene scene2 = new Scene(group1, 350, 500);


    private Pane createContent(){
        Pane root = new Pane();
        root.setPrefSize(WIDTH, HEIGHT);

        for (int y = 0; y < Y_TILES; y++){
            for (int x = 0; x < X_TILES; x++){
                Tile tile = new Tile(x, y, Math.random() < 0.2);

                grid[x][y] = tile;
                root.getChildren().add(tile);
            }
        }

        for (int y = 0; y < Y_TILES; y++){
            for (int x = 0; x < X_TILES; x++){
                Tile tile = grid[x][y];

                if(tile.hasBomb)
                    continue;

                long bombs = getNeighbors(tile).stream().filter(t -> t.hasBomb).count();
                System.out.println(bombs);

                if(bombs > 0)
                {
                    tile.text.setText(String.valueOf(bombs));
                }
            }
        }


        return root;
    }

    private List<Tile> getNeighbors(Tile tile) {

        List<Tile> neighbors = new ArrayList<>();

        int[] points = new int[] {
                -1, -1,
                -1, 0,
                -1, 1,
                0, -1,
                0, 1,
                1, -1,
                1, 0,
                1, 1
        };

        for(int i = 0; i < points.length; i++){
            int dx = points[i];
            int dy = points[++i];

            int newX = tile.x + dx;
            int newY = tile.y + dy;

            if(newX >= 0 && newX < X_TILES && newY >= 0 && newY < Y_TILES){
                neighbors.add(grid[newX][newY]);
            }
        }

        return neighbors;

    }

    private class Tile extends StackPane{
        private int x, y;
        private boolean hasBomb;
        private boolean isOpen = false;


        private Rectangle border = new Rectangle(TILE_SIZE - 2, TILE_SIZE - 2);
        private Text text = new Text();

        public Tile(int x, int y, boolean hasBomb){
            this.x = x;
            this.y = y;
            this.hasBomb = hasBomb;

            border.setStroke(Color.LIGHTGRAY);

            text.setFill(Color.WHITE);
            text.setFont(Font.font(18));
            text.setText(hasBomb ? "x" : "");
            text.setVisible(false);

            getChildren().addAll(border, text);

            setTranslateX(x * TILE_SIZE);
            setTranslateY(y * TILE_SIZE);

            setOnMouseClicked(e -> open());
        }

        public void open() {
            if(isOpen)
                return;

            if(hasBomb){ //game over
                System.out.println("Game Over");
                scene.setRoot(createContent());
                return;
            }

            isOpen = true;
            text.setVisible(true);
            text.setFill(Color.RED);
            border.setFill(null);

            if(text.getText().isEmpty()){
                getNeighbors(this).forEach(Tile::open);
            }
        }
    }

    @Override
    public void start(Stage stage) throws Exception {

        scene = new Scene(createContent());

        stage.setScene(scene);
        stage.show();

    }



    public static void main(String[] args) {
        launch(args);
    }
}