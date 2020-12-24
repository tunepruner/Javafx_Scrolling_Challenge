package com.tunepruner.draglistfx;

import  javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.awt.*;
import java.io.IOException;

public class ListArea {
    String uniqueID;
    Pane pane;
    ListFromFile listFromFile;
    Grid grid;
    Point topLeft, topRight, bottomLeft;
    int cellHeight, cellPadding;
    ObservableList<String> list = FXCollections.observableArrayList();
    Stage stage;

    public ListArea (
            String uniqueID,
            Pane pane,
            ListFromFile listFromFile,
            Point topLeft,
            Point topRight,
            Point bottomLeft,
            int cellHeight,
            int cellPadding,
            Stage stage
    ){
        this.uniqueID = uniqueID;
        this.pane = pane;
        this.listFromFile = listFromFile;
        this.topLeft = topLeft;
        this.topRight = topRight;
        this.bottomLeft = bottomLeft;
        this.cellHeight = cellHeight;
        this.cellPadding = cellPadding;
        this.stage = stage;

    }


    public void setGrid(Grid grid) {
        this.grid = grid;
    }
    public String getUniqueID() {
        return uniqueID;
    }
    public Pane getPane() {
        return pane;
    }
    public ListFromFile getListFromFile() {
        return listFromFile;
    }
    public Grid getGrid() {
        return grid;
    }
    public Point getTopLeft() {
        return topLeft;
    }
    public Point getTopRight() {
        return topRight;
    }
    public Point getBottomLeft() {
        return bottomLeft;
    }
    public ObservableList<String> getList() {
        return list;
    }
    public int getCellHeight() {
        return cellHeight;
    }
    public int getCellPadding() {
        return cellPadding;
    }
    public int getCellWidth(){
        int cellWidth = topRight.x - topLeft.x;
        return cellWidth;
    }
    public Stage getStage() {
        return stage;
    }

    public static Pane drawListArea(ListArea listArea) {
        try {
            listArea.listFromFile.syncFromFile(listArea);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Cell cell = new Cell();//just so I can use an instance method
        cell.displayAllCells(listArea);
        return listArea.getPane();
    }




}