package com.tunepruner.fourwards.gui;

import javafx.collections.FXCollections;
import javafx.collections.ObservableMap;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

import java.awt.*;

public class Grid {
    ObservableMap<Integer, Point> gridMap = FXCollections.observableHashMap();
    public int currentDraggedFromIndex;
    double currentScrollDirectionX = 0;
    double currentScrollDirectionY = 0;

    public void setGridMap(ObservableMap<Integer, Point> gridMap) {
        this.gridMap = gridMap;
    }

    public ObservableMap<Integer, Point> getGridMap() {return gridMap;}

    public Grid(ListArea listArea) {
        /*Calculate the angle of the left edge of the ListArea.*/
        double xDiff = 1;
        double yDiff = 1;

        /*Calculate the y difference and the x difference between one cell and the next.*/
        double xGridFactor = ((listArea.getCellHeight() + listArea.getCellPadding()) * xDiff)/yDiff;
        double yGridFactor = -(listArea.getCellHeight() + listArea.getCellPadding());

        /*Distribute points on that line.*/
        for (int i = 0; i < listArea.getList().size(); i++) {
            Point startingPoint = new Point(
                    listArea.getTopLeft().x - listArea.getCellWidth()*2 - listArea.getCellHeight(),
                    listArea.getTopLeft().y + listArea.getAreaHeight());
            Point iteratedPoint = new Point(
                    (int) startingPoint.x + (int) xGridFactor * i,
                    (int) startingPoint.y + (int) yGridFactor * i);
            gridMap.put(i, iteratedPoint);
        }

    }

    public int getIndexOfXY(ListArea listArea, Point currentPoint) {
        int resultingIndex = currentDraggedFromIndex;
        for ( int i = 0; i < listArea.getGrid().getGridMap().size(); i++) {
            if (
                    Math.abs(currentPoint.y - (listArea.getGrid().getGridMap().get(i).y)) < listArea.getCellHeight() &&
                    Math.abs(currentPoint.x - (listArea.getGrid().getGridMap().get(i).x)) < listArea.getCellWidth())
            {
                resultingIndex =  i;
            }
        }
        return resultingIndex;
    }


    public boolean animationPermitted(ListArea listArea, HBox hBox, Cell cell) {
        String hBoxContent = (((Label) hBox.getChildren().get(1)).getText());
        boolean isInList = listArea.getList().contains(hBoxContent);
        boolean isOnGrid = false;
        boolean isAtCorrectIndex;
        boolean needsAnUpdate = false;
        boolean isCurrentlyDragging;
        boolean justGotDropped;
        boolean animationPermitted;
        for ( int i = 0; i < listArea.getGrid().getGridMap().size(); i++ ) {
            if (cell.currentPosition.equals(listArea.getGrid().getGridMap().get(i))) {
                isOnGrid = true;
            }
        }
        if (isOnGrid) {
            cell.isInListArea = true;
        }

        if (isOnGrid && isInList) {
            if (listArea.getList().indexOf(hBoxContent) != listArea.getGrid().getIndexOfXY(listArea, cell.currentPosition)) {
                needsAnUpdate = true;

            }
        }

        isCurrentlyDragging = !isInList;

        justGotDropped = false;
        if (isInList) {
            if (!isOnGrid) {
                if (!cell.isInListArea) {
                    justGotDropped = true;
                }
            }
        }

        if (needsAnUpdate || justGotDropped) {
            animationPermitted = true;
        } else if (isCurrentlyDragging) {
            animationPermitted = false;
        } else {
            animationPermitted = false;
        }
        cell.isInListArea = false;

        return animationPermitted;
    }

}
