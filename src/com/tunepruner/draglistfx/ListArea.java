package com.tunepruner.draglistfx;

import  javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import org.w3c.dom.css.Rect;
import javafx.scene.shape.Rectangle;

import java.awt.*;
import java.io.IOException;

public class ListArea {
    public final Color COLOR_OF_INNER_PANE = new Color(0, .2, .3, 1);
    public final String uniqueID;
    public Pane pane;
    private Pane parentPane;
    private ListFromFile listFromFile;
    private Grid grid;
    private Point topLeft;
    private int areaHeight, areaWidth, cellHeight, cellWidth, cellPadding;
    private ObservableList<String> list = FXCollections.observableArrayList();
    private Stage stage;

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

        listArea.getParentPane().getChildren().add(listArea.getPane());

        listArea.getParentPane().setClip(clip);
        listArea.getParentPane().toFront();

        listArea.getPane().setBackground(new Background(new BackgroundFill(listArea.COLOR_OF_INNER_PANE, CornerRadii.EMPTY, Insets.EMPTY)));

        return listArea.parentPane;
    }

    public void handleScrolling(){
        pane.setOnScroll(event -> {
            if (this.pane.getLayoutX() > 360) {
                this.pane.setLayoutX(359);
                this.pane.setLayoutY(-359);
                /*This number is a magic number unfortunately.
                * I can't figure out how to relate it to the properties of the listArea. */
            } else if(this.pane.getLayoutX() < -145) {
                this.pane.setLayoutX(-144);
                this.pane.setLayoutY(144);
                /*This is also a magic number,  found by trial and error.*/
            } else {
                double deltaX = event.getDeltaX();
                double deltaY = event.getDeltaY();
                grid.currentScrollDirectionX = deltaX;
                grid.currentScrollDirectionY = deltaY;

                pane.setLayoutX(pane.getLayoutX() + grid.currentScrollDirectionY / 8);
                pane.setLayoutY(pane.getLayoutY() - grid.currentScrollDirectionY / 8);
                System.out.println("this.pane.getLayoutX() = " + this.pane.getLayoutX());
                System.out.println("this.pane.getLayoutY() = " + this.pane.getLayoutY());
            }
        });
    }
}