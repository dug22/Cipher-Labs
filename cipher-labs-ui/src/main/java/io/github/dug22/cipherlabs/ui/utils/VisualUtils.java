package io.github.dug22.cipherlabs.ui.utils;

import javafx.beans.binding.Bindings;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class VisualUtils {

    public static Rectangle createRectangle(double x, double y, double width, double height) {
        Rectangle rectangle = new Rectangle();
        if (x != -1) {
            rectangle.setX(x);
        }

        if (y != -1) {
            rectangle.setY(y);
        }
        rectangle.setWidth(width);
        rectangle.setHeight(height);
        rectangle.setFill(Color.WHITE);
        rectangle.setStroke(Color.BLACK);
        return rectangle;
    }

    public static void centerTextInRectangle(Rectangle rectangle, Label label) {
        label.layoutXProperty().bind(rectangle.xProperty().add(rectangle.widthProperty().divide(2)).subtract(label.widthProperty().divide(2)));
        label.layoutYProperty().bind(rectangle.yProperty().add(rectangle.heightProperty().divide(2)).subtract(label.heightProperty().divide(2)));
    }

    public static void centerTextIntoRectangle(VBox container, HBox parentHBox, Rectangle rectangle) {
        container.layoutXProperty().bind(Bindings.createDoubleBinding(
                () -> parentHBox.getLayoutX() + rectangle.getBoundsInParent().getMinX(),
                parentHBox.layoutXProperty(), rectangle.boundsInParentProperty()
        ));
        container.layoutYProperty().bind(Bindings.createDoubleBinding(
                () -> parentHBox.getLayoutY() + rectangle.getBoundsInParent().getMinY(),
                parentHBox.layoutYProperty(), rectangle.boundsInParentProperty()
        ));
    }
}
