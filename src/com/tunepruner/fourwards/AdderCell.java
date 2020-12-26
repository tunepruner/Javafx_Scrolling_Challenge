package com.tunepruner.fourwards;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.scene.Group;
import javafx.util.Duration;

import java.awt.*;

public class AdderCell extends Cell {
    private static AdderCell adderCell = new AdderCell();

    private AdderCell() {
//        this.drawCell(listArea, "Drag to add new", listArea.getGrid());
    }

    @Override
    public Point determineCellPosition(ListArea listArea, String string) {
        Point point = new Point();
        point.setLocation(275, 300);
        //Will do something similar to the following:
//        Point point = new Point();
//        point.setLocation(
//                listArea.getTopLeft().x,
//                listArea.getTopLeft().y + listArea.getStartArea().getHeight() - listArea.getStartAreaHeight);
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
        cellGroup.toFront();
    }

    public static AdderCell getInstance(ListArea listArea) {
        if (adderCell == null) adderCell = new AdderCell();
        return adderCell;
    }
}
