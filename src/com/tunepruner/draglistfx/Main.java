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

        ListArea planListArea = new PlanListArea(
                "PlanList",
                planPane,
                new PlanListFromFile(),
                new Point(400, 50),//topLeft
                new Point(600, 100),//topRight
                new Point(100, 350),//bottomLeft
                25,
                10,
                primaryStage
        );
        planPane = planListArea.drawListArea(planListArea);

        Group root = new Group();
        root.getChildren().addAll(planPane, rectBackground);
        rectBackground.toBack();

//        animateBackground(planPane);

        Scene scene = new Scene(root, 1300, 700);
        this.rectBackground.widthProperty().bind(scene.widthProperty());
        this.rectBackground.heightProperty().bind(scene.heightProperty());

        primaryStage.setScene(scene);
        primaryStage.show();

    }

    public static void main(String[] args) {
        launch(args);
    }


    public static void animateBackground (Pane pane){
        for (int i = 0; i < 40; i++) {
            Line line = new Line();
            line.setEndX(i * 100 - 2000);
            line.setEndY(-200);
            line.setStartX(i * 100 + 500);
            line.setStartY(2000);
            line.setStrokeWidth(20);
            line.setStroke(new Color(0, 0, .2, .2));


            line.toBack();
            pane.getChildren().add(line);

            Timeline timeline = new Timeline();
            KeyFrame end =
                    new KeyFrame(Duration.seconds(15),
                            new KeyValue(line.startXProperty(), line.getStartX() - 500),
                            new KeyValue(line.endXProperty(), line.getEndX() - 500));
            timeline.getKeyFrames().add(end);
            timeline.setCycleCount(Animation.INDEFINITE);
            timeline.play();
        }
    }
}