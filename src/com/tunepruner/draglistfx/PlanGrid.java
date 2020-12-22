package com.tunepruner.draglistfx;

import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.awt.*;

public class PlanGrid extends Grid {

    public PlanGrid(ListArea listArea) {
        /*Calculate the angle of the left edge of the ListArea.*/
        double xDiff = Math.abs(listArea.getTopLeft().x - listArea.getBottomLeft().x);
        double yDiff = Math.abs(listArea.getTopLeft().y - listArea.getBottomLeft().y);
        double angleInRadians = Math.toDegrees(Math.atan2(xDiff, yDiff));

        /*Calculate the y difference and the x difference between one cell and the next.*/
        double xGridFactor = ((listArea.getCellHeight() + listArea.getCellPadding()) * xDiff)/yDiff;
        double yGridFactor = (listArea.getCellHeight() + listArea.getCellPadding());

        /*Distribute points on that line.*/
        for (int i = 0; i < listArea.getList().size(); i++) {
            int size = listArea.getList().size();
            Point topLeft = listArea.getTopLeft();
            Point iteratedPoint = new Point(((int) (topLeft.getX())) + ((int) (-xGridFactor) * i), (int)(topLeft.getY()) + ((int) (yGridFactor) * i));
            gridMap.put(i, iteratedPoint);
        }

    }



    @Override
    public int getIndexOfXY(ListArea listArea, Point currentPoint) {
        int resultingIndex = currentDraggedFromIndex;
        for (int i = 0; i < listArea.getGrid().getGridMap().size(); i++) {
            if (
                    Math.abs(currentPoint.y - (listArea.getGrid().getGridMap().get(i).y)) < listArea.getCellHeight() &&
                    Math.abs(currentPoint.x - (listArea.getGrid().getGridMap().get(i).x)) < listArea.getCellWidth())
            {
                resultingIndex =  i;
            }
        }
        return resultingIndex;
    }

    @Override
    public boolean animationPermitted(ListArea listArea, HBox hBox, Cell cell) {
        String hBoxContent = (((Label) hBox.getChildren().get(1)).getText());
        boolean isInList = listArea.getList().contains(hBoxContent);
        boolean isOnGrid = false;
        boolean isAtCorrectIndex;
        boolean needsAnUpdate = false;
        boolean isCurrentlyDragging;
        boolean justGotDropped;
        boolean animationPermitted;
        for (int i = 0; i < listArea.getGrid().getGridMap().size(); i++) {
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
        hBox.getLayoutX();

        int topDistance = Math.abs((int) (listArea.getTopLeft().getY() - hBox.getLayoutY()));
        int bottomDistance = Math.abs((int) (listArea.getBottomLeft().getY() - hBox.getLayoutY()));
        int opaqueEdgeSize = (listArea.getCellHeight() + listArea.getCellPadding()) * 3;

        if (hBox.getLayoutY() > listArea.getTopLeft().getY()
            &&
            hBox.getLayoutY() < listArea.getBottomLeft().getY()
            ){
            int distance = Math.min(topDistance, bottomDistance);
            if (distance < opaqueEdgeSize) {
                hBox.setOpacity((double) distance/ (double) opaqueEdgeSize);
                vBox.setOpacity((double) distance/ (double) opaqueEdgeSize);
            }
        }else{
            hBox.setOpacity(0);
            vBox.setOpacity(0);
        }
    }
}
