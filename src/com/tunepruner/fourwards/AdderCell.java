package com.tunepruner.fourwards;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.SVGPath;
import javafx.util.Duration;

import java.awt.*;

public class AdderCell extends Cell {
    private static AdderCell adderCell = new AdderCell();

    @Override
    public Point determineCellPosition(ListArea listArea, String string) {
        Point point = new Point();
        point.setLocation(-75, -100);
        return point;
    }

    @Override
    public void revealCell(Pane pane) {
        cellGroup.setOpacity(0);
        pane.getChildren().add(cellGroup);
        Timeline timeline = new Timeline();
        KeyFrame keyFrame = new KeyFrame(
                new Duration(300),
                new KeyValue(cellGroup.opacityProperty(), 1));
        timeline.getKeyFrames().add(keyFrame);
        timeline.play();
    }

    public static AdderCell getInstance(ListArea listArea) {
        if (adderCell == null) adderCell = new AdderCell();
        adderCell.designCell(listArea, "Drag to add new");
        adderCell.revealCell(listArea.getStartAreaPane());
        return adderCell;
    }

    @Override
    public void cueReposition(ListArea listArea) {
        //do nothing
    }

    @Override
    public void handleDragAndDrop(ListArea listArea) {
        cellGroup.setOnMouseClicked(event -> {
            listArea.getList().add("testing");
            super.designCell(listArea, "testing");
        });

        cellGroup.setOnMousePressed(event -> {
            listArea.getGrid().currentDraggedFromIndex = listArea.getList().indexOf(((Label) hBox.getChildren().get(1)).getText());

            preCalcSceneX = event.getSceneX();
            preCalcSceneY = event.getSceneY();
            isInListArea = false;

        });
    }

}
