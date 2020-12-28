package com.tunepruner.fourwards.gui;

import com.tunepruner.fourwards.data.ListFromTextFile;
import com.tunepruner.fourwards.data.TimeContainer;
import com.tunepruner.fourwards.data.TimeContainers;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.scene.shape.Rectangle;

import java.awt.*;
import java.io.IOException;

public class ListArea {
    public final int LIST_BOTTOM_X_VALUE = -145;
    public final int LIST_TOP_X_VALUE = 386;
    public final Color COLOR_OF_INNER_PANE = new Color(0, .2, .3, 1);
    public final Color COLOR_OF_CELLS = new Color(0.3084314f, .4, .5, 1);
    public final String uniqueID;
    public Pane pane;
    private Pane listAreaPane;
    private Pane clipPane;
    private Pane startAreaPane;
    private ListFromTextFile listFromTextFile;
    private TimeContainers timeContainers = new TimeContainers(this);
    private Grid grid;
    private Point topLeft;
    private int areaHeight, areaWidth, cellHeight, cellWidth, cellPadding;
    private AdderCell adderCell;
    private Stage stage;

    public ListArea (
            String uniqueID,
            Pane listAreaPane,
            ListFromTextFile listFromTextFile,
            Point topLeft,
            int areaHeight,
            int areaWidth,
            int cellHeight,
            int cellWidth,
            int cellPadding,
            Stage stage

    ){
        this.uniqueID = uniqueID;
        this.listFromTextFile = listFromTextFile;
        this.topLeft = topLeft;
        this.areaHeight = areaHeight;
        this.areaWidth = areaWidth;
        this.cellHeight = cellHeight;
        this.cellWidth = cellWidth;
        this.cellPadding = cellPadding;
        this.stage = stage;
        this.listAreaPane = listAreaPane;
        this.pane = new Pane();
        this.startAreaPane = new Pane();
        this.clipPane = new Pane();
        this.adderCell = AdderCell.getInstance(this);
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

    public Grid getGrid() {
        return grid;
    }

    public Pane getClipPane() {
        return clipPane;
    }
    public Pane getStartAreaPane() {
        return startAreaPane;
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
    public Pane getListAreaPane() {
        return listAreaPane;
    }
    public ObservableList<String> getList() {
        return ListFromTextFile.list;
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

    public Pane drawListArea(ListArea listArea) {
        try {
            listArea.listFromTextFile.syncFromFile(listArea);
        } catch (IOException e) {
            e.printStackTrace();
        }
        displayAllCells(listArea);
        listArea.handleScrolling();

        Rectangle clip = new Rectangle();
        clip.setHeight(listArea.getAreaHeight());
        clip.setWidth(listArea.getAreaWidth());
        clip.setLayoutX(listArea.topLeft.x);
        clip.setLayoutY(listArea.topLeft.y);


        listArea.getListAreaPane().getChildren().addAll(listArea.getClipPane(), listArea.getStartAreaPane());
        listArea.getStartAreaPane().toFront();
        listArea.getClipPane().getChildren().add(listArea.getPane());

        listArea.getClipPane().setClip(clip);
        listArea.getListAreaPane().toFront();

        listArea.getClipPane().setBackground(new Background(new BackgroundFill(listArea.COLOR_OF_INNER_PANE, CornerRadii.EMPTY, Insets.EMPTY)));

        listArea.startAreaPane.relocate(350, 400);

        listArea.getStartAreaPane().setBackground(new Background(new BackgroundFill(listArea.COLOR_OF_CELLS, new CornerRadii(0, 0, 0, 60, false), Insets.EMPTY)));

        return listArea.listAreaPane;
    }

    public void handleScrolling(){
        pane.setOnScroll(event -> {
            if (this.pane.getLayoutX() > LIST_TOP_X_VALUE) {
                this.pane.setLayoutX(LIST_TOP_X_VALUE-1);
                this.pane.setLayoutY(-LIST_TOP_X_VALUE+1);
            } else if(this.pane.getLayoutX() < LIST_BOTTOM_X_VALUE) {
                this.pane.setLayoutX(LIST_BOTTOM_X_VALUE + 1);
                this.pane.setLayoutY(-LIST_BOTTOM_X_VALUE-1);
            } else {
                double deltaX = event.getDeltaX();
                double deltaY = event.getDeltaY();
                grid.currentScrollDirectionX = deltaX;
                grid.currentScrollDirectionY = deltaY;

                pane.setLayoutX(pane.getLayoutX() + grid.currentScrollDirectionY / 8);
                pane.setLayoutY(pane.getLayoutY() - grid.currentScrollDirectionY / 8);
            }
        });
    }

    public void updateList(){

    }

    public void displayAllCells(ListArea listArea) {
        listArea.setGrid(new Grid(listArea));
        ObservableList<TimeContainer> listOfTC = timeContainers.getListOfTimeContainers();
        for ( int i = 0; i < listOfTC.size(); i++ ) {
            listOfTC.get(i).getCell().designCell(listOfTC.get(i).getTopic().getName());
            listOfTC.get(i).getCell().revealCell(pane);
        }
//        for ( int i = 0; i < listArea.getList().size(); i++ ) {
//            String string = listArea.getList().get(i);
//            listArea.getListFromFile().handleSyncToFile(listArea);
//            Cell cell = new Cell(this, string);
//            cell.designCell(string);
//            cell.revealCell(listArea.getPane());
//        }
    }
}