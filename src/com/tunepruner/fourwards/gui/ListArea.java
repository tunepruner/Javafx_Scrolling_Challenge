package com.tunepruner.fourwards.gui;

import com.tunepruner.fourwards.data.TimeContainer;
import com.tunepruner.fourwards.data.Data;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.scene.shape.Rectangle;

import javax.xml.bind.JAXBException;
import java.awt.*;

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
    private Grid grid;
    private Point topLeft;
    private int areaHeight, areaWidth, cellHeight, cellWidth, cellPadding;
    private AdderCell adderCell;
    private Stage stage;

    public ListArea (
            String uniqueID,
            Pane listAreaPane,
            Point topLeft,
            int areaHeight,
            int areaWidth,
            int cellHeight,
            int cellWidth,
            int cellPadding,
            Stage stage

    ) throws JAXBException {
        this.uniqueID = uniqueID;
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
        Data.readFromFile();
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

    public Pane drawListArea() {
        displayAllCells();
        handleScrolling();

        Rectangle clip = new Rectangle();
        clip.setHeight(areaHeight);
        clip.setWidth(areaWidth);
        clip.setLayoutX(topLeft.x);
        clip.setLayoutY(topLeft.y);


        listAreaPane.getChildren().addAll(clipPane, startAreaPane);
        startAreaPane.toFront();
        clipPane.getChildren().add(pane);

        clipPane.setClip(clip);
        listAreaPane.toFront();

        clipPane.setBackground(new Background(new BackgroundFill(COLOR_OF_INNER_PANE, CornerRadii.EMPTY, Insets.EMPTY)));

        startAreaPane.relocate(350, 400);

        startAreaPane.setBackground(new Background(new BackgroundFill(COLOR_OF_CELLS, new CornerRadii(0, 0, 0, 60, false), Insets.EMPTY)));

        return listAreaPane;
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

    public void displayAllCells() {
        setGrid(new Grid(this));
        ObservableList<TimeContainer> listOfTC = Data.getList();
        for ( int i = 0; i < listOfTC.size(); i++ ) {
            String stringAtIndex = listOfTC.get(i).getTopic().getName();
            Cell cell = new Cell(this, stringAtIndex);
            cell.designCell(stringAtIndex);
            cell.revealCell(pane);
        }
    }
}