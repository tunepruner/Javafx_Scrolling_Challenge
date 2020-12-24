package com.tunepruner.draglistfx;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableMap;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.awt.*;

public class Grid {
    ObservableMap<Integer, Point> gridMap = FXCollections.observableHashMap();

    SimpleDoubleProperty scrollActivity = new SimpleDoubleProperty(0);
    public int currentDraggedFromIndex;
    static double currentScrollDirectionX = 0;
    static double currentScrollDirectionY = 0;
    public void setGridMap(ObservableMap<Integer, Point> gridMap) {
        this.gridMap = gridMap;
    }
    public ObservableMap<Integer, Point> getGridMap() {return gridMap;}

    public Grid(ListArea listArea) {
        /*Calculate the angle of the left edge of the ListArea.*/
        double xDiff = Math.abs(listArea.getTopLeft().x - listArea.getBottomLeft().x);
        double yDiff = Math.abs(listArea.getTopLeft().y - listArea.getBottomLeft().y);
        double angleInRadians = Math.toDegrees(Math.atan2(xDiff, yDiff));

        /*Calculate the y difference and the x difference between one cell and the next.*/
        double xGridFactor = ((listArea.getCellHeight() + listArea.getCellPadding()) * xDiff)/yDiff;
        double yGridFactor = (listArea.getCellHeight() + listArea.getCellPadding());

        /*Distribute points on that line.*/
        for (int i = 0; i < listArea.getList().size(); i++) {
            Point topLeft = listArea.getTopLeft();
            Point iteratedPoint = new Point(((int) (topLeft.getX())) + ((int) (-xGridFactor) * i), (int)(topLeft.getY()) + ((int) (yGridFactor) * i));
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
        for ( int i = 0; i < listArea.getGrid().getGridMap().size(); i++) {
            if (cell.currentPosition.equals(listArea.getGrid().getGridMap().get(i))) {
                isOnGrid = true;
            }
        }
        if (isOnGrid){
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
    public String getListID(){

        //TODO the method getIndexOfXY will need the string that this returns
        // It will use it to return the list from the correct
        // listArea so that the D&D can add to the correct one.
        String listID = "";

        return listID;
    }

    public void setCellOpacity(ListArea listArea, HBox hBox, VBox vBox, Cell cell) {
        listArea.getTopLeft();
        listArea.getBottomLeft();
        cell.cellGroup.getLayoutX();

        int topDistance = Math.abs((int) (listArea.getTopLeft().getY() - cell.cellGroup.getLayoutY()));
        int bottomDistance = Math.abs((int) (listArea.getBottomLeft().getY() - cell.cellGroup.getLayoutY()));
        int opaqueEdgeSize = (listArea.getCellHeight() + listArea.getCellPadding()) * 3;

        if (cell.cellGroup.getLayoutY() > listArea.getTopLeft().getY()
            &&
            cell.cellGroup.getLayoutY() < listArea.getBottomLeft().getY()
            ){
            int distance = Math.min(topDistance, bottomDistance);
            if (distance < opaqueEdgeSize) {
                cell.cellGroup.setOpacity((double) distance/ (double) opaqueEdgeSize);
                cell.cellGroup.setOpacity((double) distance/ (double) opaqueEdgeSize);
            }
        }else{
            cell.cellGroup.setOpacity(0);
            cell.cellGroup.setOpacity(0);
        }
    }
    public void scrollGrid(ListArea listArea, double deltaX, double deltaY, HBox hBox, VBox vBox, Cell cell) {
        double xDiff = Math.abs(listArea.getTopLeft().x - listArea.getBottomLeft().x);
        double yDiff = Math.abs(listArea.getTopLeft().y - listArea.getBottomLeft().y);

        Point topPoint = listArea.getGrid().getGridMap().get(0);
        Point bottomPoint = listArea.getGrid().getGridMap().get(listArea.getGrid().getGridMap().size()-1);

        boolean listAreaIsAtBottom = topPoint.y >= listArea.getTopLeft().getY();
        boolean listAreaIsAtTop = bottomPoint.y <= listArea.getBottomLeft().getY();

        listArea.getGrid().scrollActivity.setValue(listArea.getGrid().scrollActivity.get() + currentScrollDirectionY);

    }
    
}
