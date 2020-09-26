import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.awt.*;

public class PlanListArea extends ListArea {

    public PlanListArea (
            String uniqueID,
            Pane pane,
            PlanListFromFile planListFromFile,
            Point topLeft,
            Point topRight,
            Point bottomLeft,
            int cellHeight,
            int cellPadding,
            Stage stage
    ){
        this.uniqueID = uniqueID;
        this.pane = pane;
        this.listFromFile = planListFromFile;
        this.topLeft = topLeft;
        this.topRight = topRight;
        this.bottomLeft = bottomLeft;
        this.cellHeight = cellHeight;
        this.cellPadding = cellPadding;
        this.stage = stage;

    }

}
