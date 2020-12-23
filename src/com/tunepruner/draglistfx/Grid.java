package com.tunepruner.draglistfx;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableMap;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.awt.*;

public abstract class Grid {
    ObservableMap<Integer, Point> relativeGridMap = FXCollections.observableHashMap();


    ObservableMap<Integer, Point> absoluteGridMap = FXCollections.observableHashMap();
    SimpleDoubleProperty scrollActivity = new SimpleDoubleProperty(0);
    public int currentDraggedFromIndex;
    static double currentScrollDirectionX = 0;
    static double currentScrollDirectionY = 0;


    public void setRelativeGridMap(ObservableMap<Integer, Point> relativeGridMap) {
        this.relativeGridMap = relativeGridMap;
    }
    public ObservableMap<Integer, Point> getRelativeGridMap() {return relativeGridMap;}
    public ObservableMap<Integer, Point> getAbsoluteGridMap() {return absoluteGridMap;}
    public abstract int getIndexOfXY (ListArea listArea, Point currentPoint);
    public abstract boolean animationPermitted(ListArea listArea, HBox hBox, Cell cell);
    public abstract void setCellOpacity(ListArea listArea, HBox hBox, VBox vBox, Cell cell);
    public void scrollGrid(ListArea listArea, double deltaX, double deltaY, HBox hBox, VBox vBox, Cell cell) {
        double xDiff = Math.abs(listArea.getTopLeft().x - listArea.getBottomLeft().x);
        double yDiff = Math.abs(listArea.getTopLeft().y - listArea.getBottomLeft().y);

        Point topPoint = listArea.getGrid().getRelativeGridMap().get(0);
        Point bottomPoint = listArea.getGrid().getRelativeGridMap().get(listArea.getGrid().getRelativeGridMap().size()-1);

        boolean listAreaIsAtBottom = topPoint.y >= listArea.getTopLeft().getY();
        boolean listAreaIsAtTop = bottomPoint.y <= listArea.getBottomLeft().getY();

        listArea.getGrid().scrollActivity.setValue(listArea.getGrid().scrollActivity.get() + currentScrollDirectionY);

    }
}
