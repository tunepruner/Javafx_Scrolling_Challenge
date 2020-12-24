package com.tunepruner.draglistfx;

import  javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import org.w3c.dom.css.Rect;
import javafx.scene.shape.Rectangle;

import java.awt.*;
import java.io.IOException;

public class ListArea {
    String uniqueID;
    Pane pane;
    Pane parentPane;
    ListFromFile listFromFile;
    Grid grid;
    Point topLeft;
    int areaHeight, areaWidth, cellHeight, cellWidth, cellPadding;
    ObservableList<String> list = FXCollections.observableArrayList();
    Stage stage;

    public ListArea (
            String uniqueID,
            Pane pane,
            ListFromFile listFromFile,
            Point topLeft,
            Point topRight,
            Point bottomLeft,
            int areaHeight,
            int areaWidth,
            int cellHeight,
            int cellWidth,
            int cellPadding,
            Stage stage,
            Rectangle clip
    ){
        this.uniqueID = uniqueID;
//        this.pane = pane;
        this.listFromFile = listFromFile;
        this.topLeft = topLeft;
        this.areaHeight = areaHeight;
        this.areaWidth = areaWidth;
        this.cellHeight = cellHeight;
        this.cellWidth = cellWidth;
        this.cellPadding = cellPadding;
        this.stage = stage;
        this.parentPane = pane;
        this.pane = new Pane();
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
    public int getAreaHeight() {
        return areaHeight;
    }
    public int getAreaWidth() {
        return areaWidth;
    }
    public Point getTopLeft() {
        return topLeft;
    }
    public Pane getParentPane() {
        return parentPane;
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
    public int getCellWidth() {
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
        Cell cell = new Cell();
        cell.displayAllCells(listArea);
        listArea.handleScrolling();

        Rectangle clip = new Rectangle();
        clip.setHeight(listArea.getAreaHeight());
        clip.setWidth(listArea.getAreaWidth());
        clip.setLayoutX(listArea.topLeft.x);
        clip.setLayoutY(listArea.topLeft.y);

        listArea.parentPane.getChildren().add(listArea.getPane());

        listArea.parentPane.setClip(clip);
        listArea.parentPane.toFront();

        return listArea.parentPane;
    }

    public void handleScrolling(){
        pane.setOnScroll(event -> {
            double deltaX = event.getDeltaX();
            double deltaY = event.getDeltaY();
            grid.currentScrollDirectionX = deltaX;
            grid.currentScrollDirectionY = deltaY;

            pane.setLayoutX(pane.getLayoutX() + grid.currentScrollDirectionY / 8);
            pane.setLayoutY(pane.getLayoutY() - grid.currentScrollDirectionY / 8);
        });
    }
}