import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
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

    public PlanCell(){
    }

    public PlanCell(HBox hBox, VBox vBox, Label label) {
        cellCount++;
        cellIdentifier = cellCount;
        this.hBox = hBox;
        this.vBox = vBox;
        this.label = label;
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
        Cell cell = new PlanCell(hBox, vBox, label);



        hBox.setMinWidth(listArea.getCellWidth());
        hBox.setMaxWidth(listArea.getCellWidth());
        hBox.setMinHeight(listArea.getCellHeight());
        hBox.setMaxHeight(listArea.getCellHeight());
        hBox.setBackground(new Background(new BackgroundFill(new Color(0.5784314f, .7, 1, .6), new CornerRadii((2)), Insets.EMPTY)));
        hBox.setManaged(true);
        hBox.setBorder(new Border(new BorderStroke(new Color(0.1584314f, 0.18705883f, 0.5019608f, .5),
                BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));


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
            popup.setAnchorX(hBox.getLayoutX() + listArea.getCellWidth()/2);
            popup.setAnchorY(hBox.getLayoutY() + listArea.getCellHeight() / 2);
            popup.setHeight(300);
            popup.show(listArea.getStage());
//            hBox.getChildren().remove(1);
//            TextField textField = new TextField();
//            textField.toFront();
//            hBox.getChildren().add(1, textField);
//            textField.setScaleY(.6);
//            textField.setScaleX(.6);
//            textField.setOnAction(event1 -> {
//               String text = textField.getText();
//                int indexOf = listArea.getList().indexOf(string);
//                listArea.getList().remove(indexOf);
//                listArea.getList().add(indexOf, text);
//               hBox.getChildren().remove(1);
//               hBox.getChildren().add(1, label);
//               label.setText(text);
//            });
//            textField.setText(string);
//            textField.selectAll();
        });

        javafx.beans.value.ChangeListener vBoxXListener = (observable, oldValue, newValue) -> vBox.setLayoutX((int) cell.followableX.get() + listArea.getCellWidth());

        javafx.beans.value.ChangeListener vBoxYListener = (observable, oldValue, newValue) -> {
            vBox.setLayoutY((int) cell.followableY.get());
        };

        listArea.getPane().setOnScroll(event -> {
            double deltaX = event.getDeltaX();
            double deltaY = event.getDeltaY();
            grid.currentScrollDirectionX = deltaX;
            grid.currentScrollDirectionY = deltaY;
            listArea.getGrid().scrollActivity.setValue(listArea.getGrid().scrollActivity.get() + grid.currentScrollDirectionY);
            grid.scrollGrid(listArea, deltaX, deltaY, hBox, vBox, cell);
        });

        javafx.beans.value.ChangeListener scrollLevelListener = (observable, oldValue, newValue) -> {
            hBox.setLayoutX(hBox.getLayoutX() + grid.currentScrollDirectionY/8);
            hBox.setLayoutY(hBox.getLayoutY() - grid.currentScrollDirectionY/8);


            listArea.getGrid().getGridMap().get(listArea.getList().indexOf(string)).x = (int) hBox.getLayoutX();
            listArea.getGrid().getGridMap().get(listArea.getList().indexOf(string)).y = (int) hBox.getLayoutY();

            cell.followableX.setValue(hBox.getLayoutX());
            cell.followableY.setValue(hBox.getLayoutY());

            listArea.getGrid().setCellOpacity(listArea, hBox, vBox, cell);
        };
//        javafx.beans.value.ChangeListener hBoxYListenerForOpacity = new ChangeListener(){
//            @Override
//            public void changed(ObservableValue observable, Object oldValue, Object newValue) {
//                System.out.println("cell.followableY.get()" + cell.followableY.get());
//                System.out.println("listArea.getTopLeft().getY()" + listArea.getTopLeft().getY());
//                System.out.println("cell.followableY.get()" + cell.followableY.get());
//                System.out.println("listArea.getBottomLeft().getY()" + listArea.getBottomLeft().getY());
//
//                if (cell.followableY.get() >= listArea.getTopLeft().getY()
//                        ||
//                        cell.followableY.get() <= listArea.getBottomLeft().getY()) {
//                    hBox.setBackground(new Background(new BackgroundFill(new Color(0.5784314f, .7, 1, 0), new CornerRadii((2)), Insets.EMPTY)));
//                    hBox.setBorder(new Border(new BorderStroke(new Color(0.1584314f, 0.18705883f, 0.5019608f, 0),
//                            BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
//                    vBox.setBackground(new Background(new BackgroundFill(new Color(0.5784314f, .7, 1, 0), new CornerRadii((2)), Insets.EMPTY)));
//                    vBox.setBorder(new Border(new BorderStroke(new Color(0.1584314f, 0.18705883f, 0.5019608f, 0),
//                            BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
//                }
//            }
//        };

        cell.followableX.addListener(vBoxXListener);
        cell.followableY.addListener(vBoxYListener);
        listArea.getGrid().scrollActivity.addListener(scrollLevelListener);
//        cell.followableY.addListener(hBoxYListenerForOpacity);


        listArea.getPane().getChildren().add(hBox);
        listArea.getPane().getChildren().add(vBox);

        hBox.toFront();

        vBox.toFront();

        handleDragAndDrop(listArea, hBox, vBox, currentDraggedFromInt, cell);
        cueReposition(listArea, hBox, vBox, cell);
        listArea.getGrid().setCellOpacity(listArea, hBox, vBox, cell);
    }

    public void handleDragAndDrop(ListArea listArea, HBox hBox, VBox vBox, int currentDraggedFromInt, Cell cell){
        hBox.setOnMousePressed(event -> {
            listArea.getGrid().currentDraggedFromIndex =  listArea.getList().indexOf(((Label) hBox.getChildren().get(1)).getText());
            preCalcSceneX = event.getSceneX();
            preCalcSceneY = event.getSceneY();
            originalSceneX = event.getSceneX();
            originalSceneY = event.getSceneY();
            HBox d = (HBox) (event.getSource());
            d.toFront();
            cell.isInListArea = false;
        });

        vBox.setOnMousePressed(event -> {
            listArea.getGrid().currentDraggedFromIndex =  listArea.getList().indexOf(((Label) hBox.getChildren().get(1)).getText());
            preCalcSceneX = event.getSceneX();
            preCalcSceneY = event.getSceneY();

            originalSceneX = event.getSceneX();
            originalSceneY = event.getSceneY();
            VBox d = (VBox) (event.getSource());
            d.toFront();
            cell.isInListArea = false;
        });

        hBox.setOnDragDetected(event -> {
            hBox.startFullDrag();
        });

        vBox.setOnDragDetected(event -> {
            vBox.startFullDrag();
        });

        hBox.setOnMouseDragged(event -> {

            double offsetX = event.getSceneX() - preCalcSceneX;
            double offsetY = event.getSceneY() - preCalcSceneY;

            HBox d = (HBox) (event.getSource());

            d.toFront();

            d.setLayoutX(d.getLayoutX() + offsetX);
            d.setLayoutY(d.getLayoutY() + offsetY);
            /*Report the current position in SimpleDoubleProperty format.*/
            cell.followableX.setValue(d.getLayoutX() + offsetX);
            cell.followableY.setValue(d.getLayoutY() + offsetY);

            /*Report the current position in Point format,
            * for use by the animation algorithm.*/
            Point newPoint;
            int x = (int) (d.getLayoutX() + offsetX);
            int y= (int) (d.getLayoutY() + offsetY);
            newPoint = new Point(x, y);
            cell.currentPosition = newPoint;


            preCalcSceneX = event.getSceneX();
            preCalcSceneY = event.getSceneY();

            originalSceneOffsetX = originalSceneX-preCalcSceneX;
            originalSceneOffsetY = originalSceneY-preCalcSceneY;

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

//            hBox.setMouseTransparent(true);
            listArea.getGrid().setCellOpacity(listArea, hBox, vBox, cell);
        });

        vBox.setOnMouseDragged(event -> {

            double offsetX = event.getSceneX() - preCalcSceneX;
            double offsetY = event.getSceneY() - preCalcSceneY;

            VBox d = (VBox) (event.getSource());

            d.toFront();
            hBox.toFront();

            hBox.setLayoutX(hBox.getLayoutX() + offsetX);
            hBox.setLayoutY(hBox.getLayoutY() + offsetY);
            /*Report the current position in SimpleDoubleProperty format.*/
            cell.followableX.setValue(hBox.getLayoutX());
            cell.followableY.setValue(hBox.getLayoutY());


            /*Report the current position in Point format,
            * for use by the animation algorithm.*/
            Point newPoint;
            int x = (int) (hBox.getLayoutX());
            int y= (int) (hBox.getLayoutY());
            newPoint = new Point(x, y);
            cell.currentPosition = newPoint;


            preCalcSceneX = event.getSceneX();
            preCalcSceneY = event.getSceneY();

            originalSceneOffsetX = originalSceneX-preCalcSceneX;
            originalSceneOffsetY = originalSceneY-preCalcSceneY;

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
//            hBox.setMouseTransparent(true);
        });

//        hBox.setOnDragEntered(event -> {
//        });
//        hBox.setOnMouseDragOver(event -> {
//        });
//        hBox.setOnMouseDragExited(event -> {
//        });

        hBox.setOnMouseReleased(event -> {
            HBox d = (HBox) (event.getSource());
            Label lbl = new Label();
            lbl = ((Label) hBox.getChildren().get(1));
            String stringToAdd = (String) lbl.getText();
            int indexToInsert = 0;

            if (listArea.getList().contains("")) {
//                System.out.println("List area contains: \"\"");
                if (!listArea.getList().contains(stringToAdd)){
//                    System.out.println("List area doesn't contain: " + stringToAdd);
                    indexToInsert = listArea.getList().indexOf("");
//                    System.out.println("Index to insert: " + indexToInsert);
                    listArea.getList().remove("");
                    listArea.getList().add(indexToInsert, stringToAdd);
//                    System.out.println(listArea.getList());
                }
                cell.isInListArea = false;
            }

//            hBox.setMouseTransparent(false);
        });

        vBox.setOnMouseReleased(event -> {
            VBox d = (VBox) (event.getSource());
            Label lbl = new Label();
            lbl = ((Label) hBox.getChildren().get(1));
            String stringToAdd = (String) lbl.getText();
            int indexToInsert = 0;

            if (listArea.getList().contains("")) {
//                System.out.println("List area contains: \"\"");
                if (!listArea.getList().contains(stringToAdd)){
//                    System.out.println("List area doesn't contain: " + stringToAdd);
                    indexToInsert = listArea.getList().indexOf("");
//                    System.out.println("Index to insert: " + indexToInsert);
                    listArea.getList().remove("");
                    listArea.getList().add(indexToInsert, stringToAdd);
//                    System.out.println(listArea.getList());
                }

                cell.isInListArea = false;
            }

//            hBox.setMouseTransparent(false);
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
        System.out.println("repositionActivated();");
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
                new KeyValue(hBox.layoutXProperty(), listArea.getGrid().getGridMap().get(targetIndex).x),
                new KeyValue(hBox.layoutYProperty(), listArea.getGrid().getGridMap().get(targetIndex).y),
                new KeyValue(vBox.layoutXProperty(), listArea.getGrid().getGridMap().get(targetIndex).x + listArea.getCellWidth()),
                new KeyValue(vBox.layoutYProperty(), listArea.getGrid().getGridMap().get(targetIndex).y));
                /*new KeyValue(hBox.opacityProperty(), listArea.getGrid().setCellOpacity(listArea, hBox, vBox, cell))*/;

        timeline.getKeyFrames().add(end);
        timeline.play();
        cell.currentPosition.x = listArea.getGrid().getGridMap().get(targetIndex).x;
        cell.currentPosition.y = listArea.getGrid().getGridMap().get(targetIndex).y;
    }


}
