package com.tunepruner.draglistfx;

import com.tunepruner.draglistfx.*;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Duration;


import java.awt.*;

public class Main extends Application {
    private Rectangle rectBackground;

    @Override
    public void start(Stage primaryStage) throws Exception {
        Pane planPane = new Pane();
        planPane.toFront();

        rectBackground = new Rectangle();
        rectBackground.setFill(new Color(0.1019608f, 0.3519608f, 0.58039216f, 1));

        ListArea listArea = new ListArea(
                "PlanList",
                planPane,
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
        planPane = listArea.drawListArea(listArea);

        Group root = new Group();
        root.getChildren().addAll(planPane, rectBackground);
        rectBackground.toBack();

        Rectangle rectangle1 = new Rectangle();
        rectangle1.setLayoutX(350);
        rectangle1.setLayoutY(650 - listArea.getCellHeight() - listArea.getCellWidth());
        rectangle1.setHeight(listArea.getCellHeight() + listArea.getCellWidth());
        rectangle1.setWidth(rectangle1.getHeight());
        rectangle1.setFill(listArea.COLOR_OF_INNER_PANE);
        planPane.getChildren().add(rectangle1);

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