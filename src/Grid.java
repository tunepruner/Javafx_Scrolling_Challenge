import javafx.beans.property.SimpleDoubleProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableMap;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.awt.*;

public abstract class Grid {
    ObservableMap<Integer, Point> gridMap = FXCollections.observableHashMap();
    SimpleDoubleProperty scrollActivity = new SimpleDoubleProperty(0);
    public int currentDraggedFromInt;


    public void setGridMap(ObservableMap<Integer, Point> gridMap) {
        this.gridMap = gridMap;
    }
    public ObservableMap<Integer, Point> getGridMap() {
        return gridMap;
    }

    public void scrollGrid(ListArea listArea, double deltaX, double deltaY, HBox hBox, VBox vBox, Cell cell) {
        double xDiff = Math.abs(listArea.getTopLeft().x - listArea.getBottomLeft().x);
        double yDiff = Math.abs(listArea.getTopLeft().y - listArea.getBottomLeft().y);

        Point topPoint = listArea.getGrid().getGridMap().get(0);
        Point bottomPoint = listArea.getGrid().getGridMap().get(listArea.getGrid().getGridMap().size()-1);

        boolean listAreaIsAtBottom = topPoint.y >= listArea.getTopLeft().getY();
        boolean listAreaIsAtTop = bottomPoint.y <= listArea.getBottomLeft().getY();

        double scrollDirection = deltaY/10;

        scrollActivity.setValue(scrollActivity.get() + scrollDirection);
        System.out.println(scrollActivity);
//        hBox.setLayoutX(hBox.getLayoutX() + (int) (scrollDirection * xDiff/100));
//        hBox.setLayoutY(hBox.getLayoutY() + (int) (-scrollDirection * yDiff/100));
//        System.out.println("hBox.getLayoutX()" + hBox.getLayoutX());
//        System.out.println("hBox.getLayoutY()" + hBox.getLayoutY());
//
//        cell.followableX.setValue(hBox.getLayoutX());
//        cell.followableY.setValue(hBox.getLayoutY());
//
//        if (listAreaIsAtBottom) {
//            for (int i = 0; i < listArea.getGrid().getGridMap().size(); i++) {
//                double scrollDirection = deltaY/10;
//                hBox.setLayoutX(listArea.getGrid().getGridMap().get(i).x + (int) (xDiff/100));
//                hBox.setLayoutY(listArea.getGrid().getGridMap().get(i).y + (int) (-yDiff/100));
//
//                cell.followableX.setValue(hBox.getLayoutX());
//                cell.followableY.setValue(hBox.getLayoutY());
//
//                listArea.getGrid().getGridMap().get(i).x = (int) cell.followableX.get();
//                listArea.getGrid().getGridMap().get(i).y = (int) cell.followableY.get();
//
//
//            }
//
//        }else if(listAreaIsAtTop){
//            for (int i = 0; i < listArea.getGrid().getGridMap().size(); i++) {
//                hBox.setLayoutX(listArea.getGrid().getGridMap().get(i).x + (int) (-xDiff/100));
//                hBox.setLayoutY(listArea.getGrid().getGridMap().get(i).y + (int) (yDiff/100));
//                cell.followableX.setValue(hBox.getLayoutX());
//                cell.followableY.setValue(hBox.getLayoutY());
//
//                listArea.getGrid().getGridMap().get(i).x = (int) cell.followableX.get();
//                listArea.getGrid().getGridMap().get(i).y = (int) cell.followableY.get();
//            }
//        }else{
//            for (int i = 0; i < listArea.getGrid().getGridMap().size(); i++) {
//                double scrollDirection = deltaY/10;
//                hBox.setLayoutX(listArea.getGrid().getGridMap().get(i).x + (int) (scrollDirection * xDiff/100));
//                hBox.setLayoutY(listArea.getGrid().getGridMap().get(i).y + (int) (-scrollDirection * yDiff/100));
//
//                cell.followableX.setValue(hBox.getLayoutX());
//                cell.followableY.setValue(hBox.getLayoutY());
//
//                listArea.getGrid().getGridMap().get(i).x = (int) hBox.getLayoutX();
//                listArea.getGrid().getGridMap().get(i).y = (int) hBox.getLayoutY();
//                System.out.println("hBox.getLayoutX()" + hBox.getLayoutX());
//                System.out.println("hBox.getLayoutX()" + hBox.getLayoutX());
//            }
//        }
    }
    public abstract int getIndexOfXY (ListArea listArea, Point currentPoint);
    public abstract boolean animationPermitted(ListArea listArea, HBox hBox, Cell cell);

    public abstract void setCellOpacity(ListArea listArea, HBox hBox, VBox vBox, Cell cell);
}
