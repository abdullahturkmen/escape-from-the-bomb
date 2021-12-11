package com.example.demo;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class Switching extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {

        primaryStage.setTitle("Multiple Page");

        Group group1 = new Group();
        Scene scene1 = new Scene(group1, 300, 250);

        Group group2 = new Group();
        Scene scene2 = new Scene(group2, 350, 500);

        Button btn = new Button();
        btn.setLayoutX(100);
        btn.setLayoutY(80);
        btn.setText("go 2");
        btn.setOnAction(e -> primaryStage.setScene(scene2));
        group1.getChildren().add(btn);

        Button btn2 = new Button();
        btn2.setLayoutX(50);
        btn2.setLayoutY(250);
        btn2.setText("go 1");
        btn2.setOnAction(e -> primaryStage.setScene(scene1));
        group2.getChildren().add(btn2);

        primaryStage.setScene(scene1);
        primaryStage.show();

    }
}
