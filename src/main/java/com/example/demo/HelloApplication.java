package com.example.demo;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
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

    Alert a = new Alert(Alert.AlertType.NONE);


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
                a.setAlertType(Alert.AlertType.CONFIRMATION);
                a.setHeaderText("Game Over");
                //a.setContentText("Game Over");
                // show the dialog
                a.show();
                System.out.println("Game Over");

                isOpen = true;
                text.setVisible(true);
                text.setFill(Color.YELLOW);
                border.setFill(null);

                //scene.setRoot(createContent());
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

        scene2 = new Scene(createContent());

        Stage stage2 = new Stage();

         stage2.setScene(scene2);




        /////



            Pane pane = new Pane();
			/*String x="Welcome to Escape from the Bomb";
			Label []enter =new Label[31];

			for(int i=0;i<31;i++) {
				enter[i].setText(String.valueOf(x.charAt(i)));
				pane.add(e, i, i, i, i);
			}
			*/
            Label label = new Label();
            label.setText("Welcome to Escape from the Bomb");
            label.setFont(Font.font("Times New Roman", FontPosture.REGULAR, 20));
            label.setTextFill(Color.WHITE);
            Label label2 = new Label();
            label2.setFont(Font.font("Times New Roman", FontPosture.ITALIC, 10));
            label2.setText("(Please select a game board)");
            label2.setTextFill(Color.WHITE);


            //pane.getChildren().add(label);
            //label.setAlignment(Pos.TOP_CENTER);


            //enter.setPrefSize(20, 20);
            Button ten = new Button("10x10");
            Button fifteen = new Button("15x15");
            Button twenty = new Button("20x20");


            //enter.setPadding(new Insets(10,10,10,10));

            //pane.addRow(5, enter);
            //pane.getChildren().add(enter);


            ten.setFont(Font.font("Helvetica  ", FontWeight.BOLD, 15));
            fifteen.setFont(Font.font("Helvetica  ", FontWeight.BOLD, 15));
            twenty.setFont(Font.font("Helvetica", FontWeight.BOLD, 15));

			/*ten.setStyle("-fx-background-color: lightgreen");
			fifteen.setStyle("-fx-background-color: yellow");
			twenty.setStyle("-fx-background-color: red");*/


			/*ten.setStyle("-fx-border-color: black");
			fifteen.setStyle("-fx-border-color: black");
			twenty.setStyle("-fx-border-color: black");
			*/
            pane.getChildren().addAll(label, label2, ten, fifteen, twenty);
			/*pane.setPadding(new Insets(30, 30, 30, 30));
			pane.setVgap(60);
			pane.setHgap(81); //yanlamasına aralık
			*/


            label.relocate(75, 25);
            label2.relocate(160, 150);
            ten.relocate(35, 175);
            fifteen.relocate(190, 175);
            twenty.relocate(345, 175);

			/*pane.setTop(label);
			pane.setCenter(ten);
			pane.setCenter(fifteen);
			pane.setCenter(twenty);

			ten.setAlignment(Pos.CENTER_LEFT);
			fifteen.setAlignment(Pos.CENTER);
			twenty.setAlignment(Pos.CENTER_RIGHT);
			*/


			/*pane.setVgap(10);
			pane.setHgap(10);
			//pane.add(enter, 3, 1);
			pane.add(ten, 5, 20);
			pane.add(fifteen, 10, 20);
			pane.add(twenty,15,20);*/
            Image image = new Image("https://mpng.subpng.com/20180306/ljw/kisspng-bomb-weapon-cartoon-bomb-vector-hand-painted-5a9e7906dc2fd7.4838742115203351109019.jpg");

            pane.setStyle("-fx-background-image :url(https://thumbs.dreamstime.com/b/minefield-mines-explosives-bombs-background-illustration-full-fuse-lit-each-bomb-going-to-go-boom-87753649.jpg)");


            //pane.getChildren().addAll(ten,fifteen,twenty);

            //Image image=new Image("https://mpng.subpng.com/20180306/ljw/kisspng-bomb-weapon-cartoon-bomb-vector-hand-painted-5a9e7906dc2fd7.4838742115203351109019.jpg");
            ImageView imageview = new ImageView(image);



            ten.addEventHandler(ActionEvent.ACTION, e -> {
                stage2.show();
            });

            Scene scene1 = new Scene(pane, 430, 400);

            stage.setResizable(false);
            stage.getIcons().add(image);
            stage.setTitle("Escape From the Bomb");
            stage.setScene(scene1);
            stage.show();



    }



    public static void main(String[] args) {
        launch(args);
    }
}