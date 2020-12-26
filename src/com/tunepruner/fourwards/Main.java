package com.tunepruner.fourwards;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;


import java.awt.*;

public class Main extends Application {
    private Rectangle rectBackground;

    @Override
    public void start(Stage primaryStage) throws Exception {
        Pane listAreaPane = new Pane();
        listAreaPane.toFront();

        rectBackground = new Rectangle();
        rectBackground.setFill(new Color(0.1019608f, 0.3519608f, 0.58039216f, 1));

        ListArea listArea = new ListArea(
                "PlanList",
                listAreaPane,
                new ListFromFile(),
                new Point(350, 150),//topLeft
                new Point(850, 150),//topRight
                new Point(350, 650),//bottomLeft
                500,
                500,
                25,
                200,
                10,
                primaryStage,
                new Rectangle()
        );
        listAreaPane = listArea.drawListArea(listArea);

        Group root = new Group();
        root.getChildren().addAll(listAreaPane, rectBackground);
        rectBackground.toBack();

        Scene scene = new Scene(root, 1300, 700);
        this.rectBackground.widthProperty().bind(scene.widthProperty());
        this.rectBackground.heightProperty().bind(scene.heightProperty());

        primaryStage.setScene(scene);
        primaryStage.show();

    }

    public static void main(String[] args) {
        launch(args);
    }

}