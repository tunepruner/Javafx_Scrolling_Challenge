package com.tunepruner.draglistfx;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.collections.ObservableList;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.SVGPath;

import java.awt.*;

public abstract class Cell {
    HBox hBox;
    VBox vBox;
    Label label;
    boolean isInListArea = false;
    Point currentPosition = new Point();
    SimpleDoubleProperty followableX;
    SimpleDoubleProperty followableY;

    //TODO I still need to set this up.
    private CellState cellState;
//    Property property = new Simple



    public abstract void displayCell(ListArea listArea, String string, Grid grid);


    public void handleDragAndDrop(ListArea listArea, Pane cellPane, SVGPath svgPath, HBox hBox, VBox vBox, int currentDraggedFromInt, Cell cell) {
        /*Default implementation goes here.
         * Store (lbl.getText) in String lblTxt "".
         * Store ObservableList.indexOf(lblTxt) in int lblTxtIndex.
         * ObservarbleList.remove(lblTxtIndex)
         * Drag handler moves hBox, calls getIndexOfXY,
         * and adds empty string back into ObservableList.
         * At drop, it removes that empty string and adds lblTxt.
         *
         * */
    }

    public abstract void cueReposition(ListArea listArea, HBox hBox, VBox vBox, Cell cell);
    public abstract void executeReposition(ListArea listArea, HBox hBox, VBox vBox, Cell cell);
    public void displayAllCells(ListArea listArea) {
        ObservableList list = listArea.getList();
        listArea.setGrid(new PlanGrid(listArea));
        for (int i = 0; i < listArea.getList().size(); i++) {
            String string = (String) listArea.getList().get(i);
            listArea.getListFromFile().handleSyncToFile(listArea);
            displayCell(listArea, string, listArea.getGrid());
        }
    }

}

