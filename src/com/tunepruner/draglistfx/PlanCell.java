package com.tunepruner.draglistfx;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.SVGPath;
import javafx.stage.Popup;
import javafx.util.Duration;
import javafx.scene.control.Button;

import java.awt.*;

import javafx.scene.control.ProgressBar;

public class PlanCell extends Cell {
    private static int cellCount = 0;
    private static double preCalcSceneX, preCalcSceneY, originalSceneX, originalSceneY;
    private static double originalSceneOffsetX, originalSceneOffsetY;
    private static boolean currentlyAnimating;
    static int currentDraggedFromInt;

    private static int currentDraggedThroughInt;
    //this value is assigned in the ListArea method call, because the Grid also needs it.
    private int cellIdentifier;

    public PlanCell() {
    }

    public PlanCell(HBox hBox, VBox vBox, Label label, Group cellGroup) {
        cellCount++;
        cellIdentifier = cellCount;
        this.hBox = hBox;
        this.vBox = vBox;
        this.label = label;
        this.cellGroup = cellGroup;
    }

    public void displayCell(ListArea listArea, String string, Grid grid) {
        ObservableList<String> list = listArea.getList();
        ObservableMap gridMap = listArea.getGrid().getGridMap();

        HBox hBox = new HBox();
        VBox vBox = new VBox();
        Pane paneInsideHBox1 = new Pane();
        Pane paneInsideHBox2 = new Pane();
        Pane paneInsideVBox1 = new Pane();
        Pane paneInsideVBox2 = new Pane();
        javafx.scene.control.Label label = new Label(string);
        Button btn = new Button();
        ProgressBar progressBar = new ProgressBar(1);
        Group cellGroup = new Group();
        cellGroup.getChildren().addAll(hBox, vBox);

        Cell cell = new PlanCell(hBox, vBox, label, cellGroup);

        SVGPath svgPath = new SVGPath();
        svgPath.setContent("M4,10h24c1.104,0,2-0.896,2-2s-0.896-2-2-2H4C2.896,6,2,6.896,2,8S2.896,10,4,10z M28,14H4c-1.104,0-2,0.896-2,2  s0.896,2,2,2h24c1.104,0,2-0.896,2-2S29.104,14,28,14z M28,22H4c-1.104,0-2,0.896-2,2s0.896,2,2,2h24c1.104,0,2-0.896,2-2  S29.104,22,28,22z");
        svgPath.setRotate(90);
        svgPath.setScaleX(.5);
        svgPath.setScaleY(.8);

        hBox.setMinWidth(listArea.getCellWidth());
        hBox.setMaxWidth(listArea.getCellWidth());
        hBox.setMinHeight(listArea.getCellHeight());
        hBox.setMaxHeight(listArea.getCellHeight());
        hBox.setBackground(new Background(new BackgroundFill(new Color(0.5784314f, .7, 1, .6), new CornerRadii((2)), Insets.EMPTY)));
        hBox.setManaged(true);
//        cellGroup.setB
//
//                (new Border(new BorderStroke(new Color(0.1584314f, 0.18705883f, 0.5019608f, .5),
//                BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));

        progressBar.setRotate(-90);
        progressBar.setScaleX(3.5);
//        progressBar.setBackground(new Background(new BackgroundFill(new Color(0.9584314f, 0.98705883f, 0.5019608f, .5), new CornerRadii((0)), Insets.EMPTY)));
        progressBar.setOpacity(1);
//        progressBar.setMinWidth(listArea.getCellWidth() * .8);
//        progressBar.setMaxWidth(listArea.getCellWidth() * .8);
        progressBar.setManaged(true);
//        progressBar.setStyle("-fx-background: black");
//        progressBar.setStyle("-fx-accent: black");
//        progressBar.setStyle("-fx-text-box-border: forestgreen");
        progressBar.setStyle("-fx-control-inner-background: rgb(1, 500, 500); -fx-text-box-border: rgb(100, 200, 250); -fx-background: rgb(1, 500, 500); -fx-accent: rgb(100, 200, 250); ");
//        progressBar.setBackground(new Background(new BackgroundFill(new Color(50,30,100, 50), new CornerRadii(0), new Insets(0))));

        btn.setOpaqueInsets(new Insets(0));
        btn.setScaleX(.6);
        btn.setScaleY(.6);
        btn.setAlignment(Pos.CENTER);
        btn.setStyle("-fx-background-color: rgb(1, 200, 500);");


        vBox.setMinWidth(listArea.getCellHeight());
        vBox.setMaxWidth(listArea.getCellHeight());
        vBox.setMinHeight(listArea.getCellWidth());
        vBox.setMaxHeight(listArea.getCellWidth());
        vBox.setBackground(hBox.getBackground());
        vBox.setManaged(true);
        vBox.setBorder(hBox.getBorder());

        label.setAlignment(Pos.BOTTOM_CENTER);
        label.setTextFill(Color.WHITE);
//        label.setOpaqueInsets(new Insets(10));

        hBox.getChildren().add(paneInsideHBox1);
        hBox.setHgrow(paneInsideHBox1, Priority.ALWAYS);
        hBox.getChildren().add(label);
        hBox.getChildren().add(paneInsideHBox2);
        hBox.setHgrow(paneInsideHBox2, Priority.ALWAYS);
        hBox.getChildren().add(btn);
        hBox.getChildren().add(svgPath);

        vBox.getChildren().add(paneInsideVBox1);
        vBox.setVgrow(paneInsideVBox1, Priority.ALWAYS);
        vBox.getChildren().add(progressBar);
//        vBox.setVgrow(progressBar, Priority.ALWAYS);
        vBox.getChildren().add(paneInsideVBox2);
        vBox.setVgrow(paneInsideVBox2, Priority.ALWAYS);

        Point point = listArea.getGrid().getGridMap().get(list.indexOf(string));
        hBox.setLayoutX(point.x);
        hBox.setLayoutY(point.y);


        cell.followableX = new SimpleDoubleProperty();
        cell.followableY = new SimpleDoubleProperty();
        cell.followableX.set(point.x);
        cell.followableY.set(point.y);

        vBox.setLayoutX((int) cell.followableX.get() + listArea.getCellWidth());
        vBox.setLayoutY((int) cell.followableY.get());

        btn.setOnAction(event -> {
            Popup popup = new Popup();
            Label lbl1 = new Label("this");
            lbl1.setTextFill(Color.WHITE);
            Label lbl2 = new Label("that");
            lbl2.setTextFill(Color.WHITE);
            Label lbl3 = new Label("the other thing");
            lbl3.setTextFill(Color.WHITE);
            VBox vBox1 = new VBox();
            vBox1.setBackground(new Background(new BackgroundFill(new Color(.1, .5, .7, .5), new CornerRadii(10), Insets.EMPTY)));
            vBox1.setPadding(new Insets(5, 10, 5, 10));
            vBox1.getChildren().addAll(lbl1, lbl2, lbl3);
            popup.getContent().addAll(vBox1);
            popup.setAnchorX(hBox.getLayoutX() + listArea.getCellWidth() / 2);
            popup.setAnchorY(hBox.getLayoutY() + listArea.getCellHeight() / 2);
            popup.setHeight(300);
            popup.show(listArea.getStage());
        });

        listArea.getPane().setOnScroll(event -> {
            double deltaX = event.getDeltaX();
            double deltaY = event.getDeltaY();
            grid.currentScrollDirectionX = deltaX;
            grid.currentScrollDirectionY = deltaY;
            listArea.getGrid().scrollActivity.setValue(listArea.getGrid().scrollActivity.get() + grid.currentScrollDirectionY);
            grid.scrollGrid(listArea, deltaX, deltaY, hBox, vBox, cell);
        });

        javafx.beans.value.ChangeListener scrollLevelListener = (observable, oldValue, newValue) -> {
            cellGroup.setLayoutX(cellGroup.getLayoutX() + grid.currentScrollDirectionY / 8);
            cellGroup.setLayoutY(cellGroup.getLayoutY() - grid.currentScrollDirectionY / 8);

            listArea.getGrid().getGridMap().get(listArea.getList().indexOf(string)).x = (int) cellGroup.getLayoutX();
            listArea.getGrid().getGridMap().get(listArea.getList().indexOf(string)).y = (int) cellGroup.getLayoutY();

            System.out.println(cellGroup.getLayoutY());
//            cell.followableX.setValue(hBox.getLayoutX());
//            cell.followableY.setValue(hBox.getLayoutY());

            listArea.getGrid().setCellOpacity(listArea, hBox, vBox, cell);
        };

        listArea.getGrid().scrollActivity.addListener(scrollLevelListener);

        listArea.getPane().getChildren().add(cellGroup);
;

        handleDragAndDrop(listArea, cellGroup, svgPath, hBox, vBox, currentDraggedFromInt, cell);
        cueReposition(listArea, hBox, vBox, cell);
        listArea.getGrid().setCellOpacity(listArea, hBox, vBox, cell);
    }

    public void handleDragAndDrop(ListArea listArea, Group cellGroup, SVGPath svgPath, HBox hBox, VBox vBox, int currentDraggedFromInt, Cell cell) {
        cellGroup.setOnMouseEntered(event -> {
            System.out.println(listArea.getGrid().currentDraggedFromIndex);
            System.out.println("Hover");
        });
        cellGroup.setOnMousePressed(event -> {
            listArea.getGrid().currentDraggedFromIndex = listArea.getList().indexOf(((Label) hBox.getChildren().get(1)).getText());

            preCalcSceneX = event.getSceneX();
            preCalcSceneY = event.getSceneY();
            originalSceneX = event.getSceneX();
            originalSceneY = event.getSceneY();
            Group d = (Group) (event.getSource());
            cell.isInListArea = false;

        });
        cellGroup.setOnMouseExited(event -> cellGroup.toBack());

        cellGroup.setOnDragDetected(event -> {
        });

        cellGroup.setOnMouseDragged(event -> {
            double offsetX = event.getSceneX() - preCalcSceneX;
            double offsetY = event.getSceneY() - preCalcSceneY;

            Group d = (Group) (event.getSource());


            cellGroup.setLayoutX(cellGroup.getLayoutX() + offsetX);
            cellGroup.setLayoutY(cellGroup.getLayoutY() + offsetY);
            /*Report the current position in SimpleDoubleProperty format.*/
            cell.followableX.setValue(cellGroup.getLayoutX() + offsetX);
            cell.followableY.setValue(cellGroup.getLayoutY() + offsetY);

            /*Report the current position in Point format,
             * for use by the animation algorithm.*/
            Point newPoint;
            int x = (int) (d.getLayoutX() + offsetX);
            int y = (int) (d.getLayoutY() + offsetY);
            newPoint = new Point(x, y);
            cell.currentPosition = newPoint;


            preCalcSceneX = event.getSceneX();
            preCalcSceneY = event.getSceneY();

            originalSceneOffsetX = originalSceneX - preCalcSceneX;
            originalSceneOffsetY = originalSceneY - preCalcSceneY;

            Label lbl = ((Label) hBox.getChildren().get(1));
            String itemToRemove = lbl.getText();
            int localCurrentDraggedFromInt = listArea.getGrid().currentDraggedFromIndex;
            int updatedInsertionInt = listArea.getGrid().currentDraggedFromIndex;

            if (listArea.getList().contains(itemToRemove)) {
                listArea.getList().remove(itemToRemove);
                listArea.getList().add(localCurrentDraggedFromInt, "");
            }

            if (listArea.getList().contains("")) {
                updatedInsertionInt = listArea.getGrid().getIndexOfXY(listArea, cell.currentPosition);
                listArea.getList().remove("");
            }

            if (!listArea.getList().contains("")) {
                listArea.getList().add(updatedInsertionInt, "");
            }

            listArea.getGrid().setCellOpacity(listArea, hBox, vBox, cell);
        });

        cellGroup.setOnMouseReleased(event -> {
            Group d = (Group) (event.getSource());
            Label lbl = new Label();
            lbl = ((Label) hBox.getChildren().get(1));
            String stringToAdd = (String) lbl.getText();
            int indexToInsert = 0;

            if (listArea.getList().contains("")) {
                if (!listArea.getList().contains(stringToAdd)) {
                    indexToInsert = listArea.getList().indexOf("");
                    listArea.getList().remove("");
                    listArea.getList().add(indexToInsert, stringToAdd);
                }
                cell.isInListArea = false;
            }

        });
    }


    @Override
    public void cueReposition(ListArea listArea, HBox hBox, VBox vBox, Cell cell) {
        /*(Everything enclosed in listener to the ObservableList)
         * Animate a timeline transform for HBox, then for VBox.
         */
        //TODO I need to make this compatible with scrolling somehow.
        // While making them mutually exclusive would be easier,
        // the product will feel much more complete if they
        // can both happen at once.
        listArea.getList().addListener((ListChangeListener.Change<? extends String> c) -> {
            while (c.next()) {

                if (c.wasAdded()) {
//                    Point currentPoint = new Point((int) hBox.getLayoutX(), (int) hBox.getLayoutY());
                    boolean animationPermitted = listArea.getGrid().animationPermitted(listArea,/*maybe add point here*/hBox, cell);

                    if (animationPermitted == true) {
                        cell.executeReposition(listArea, hBox, vBox, cell);
                    }
                }
            }
        });

    }

    @Override
    public void executeReposition(ListArea listArea, HBox hBox, VBox vBox, Cell cell) {
        final Duration SEC_2 = Duration.millis(200);
        Timeline timeline = new Timeline();
        Label lbl = ((Label) hBox.getChildren().get(1));
        String string = lbl.getText();
        int targetIndex;

        if (listArea.getList().contains(string)) {
            targetIndex = listArea.getList().indexOf(string);
        } else {
            targetIndex = listArea.getList().indexOf("");
        }



        KeyFrame end = new KeyFrame(SEC_2,
                new KeyValue(cell.cellGroup.layoutXProperty(), listArea.getGrid().getGridMap().get(targetIndex).x),
                new KeyValue(cell.cellGroup.layoutYProperty(), listArea.getGrid().getGridMap().get(targetIndex).y));


        timeline.getKeyFrames().add(end);
        timeline.play();
        cell.currentPosition.x = listArea.getGrid().getGridMap().get(targetIndex).x;
        cell.currentPosition.y = listArea.getGrid().getGridMap().get(targetIndex).y;
    }


}
