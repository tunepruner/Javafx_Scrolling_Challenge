package com.tunepruner.fourwards;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.scene.Group;
import javafx.scene.layout.HBox;
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
//        point.setLocation(275, 300);
        return point;
    }

    @Override
    public void addWithFadeEffect(ListArea listArea, Group cellGroup) {
        cellGroup.setOpacity(0);
        listArea.getStartAreaPane().getChildren().add(cellGroup);
        Timeline timeline = new Timeline();
        KeyFrame keyFrame = new KeyFrame(
                new Duration(300),
                new KeyValue(cellGroup.opacityProperty(), 1));
        timeline.getKeyFrames().add(keyFrame);
        timeline.play();
    }

    public static AdderCell getInstance(ListArea listArea) {
        if (adderCell == null) adderCell = new AdderCell();
        Cell cell = new Cell();
        adderCell.drawCell(listArea, "Drag to add new", cell);
        return adderCell;
    }

    @Override
    public void cueReposition(ListArea listArea, HBox hBox, Cell cell) {
        //do nothing
    }

    @Override
    public void handleDragAndDrop(ListArea listArea, Group cellGroup, SVGPath svgPath, HBox hBox, VBox vBox, int currentDraggedFromInt, Cell cell) {
        cellGroup.setOnMouseClicked(event -> {
            listArea.getList().add("testing");
            super.drawCell(listArea, "testing", cell);

        });
    }

}
