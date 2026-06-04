package io.github.dug22.cipherlabs.ui.builder;

import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraints;

import java.util.ArrayList;
import java.util.List;

public class GridPaneConstraintsBuilder {

    public static class Builder {

        private GridPane gridPane;
        private final List<ColumnConstraints> columnConstraintsList = new ArrayList<>();
        private final List<RowConstraints> rowConstraintsList = new ArrayList<>();


        public Builder getGridPane(GridPane gridPane) {
            this.gridPane = gridPane;
            return this;
        }

        public Builder addColumnConstraints(ColumnConstraints columnConstraints) {
            addColumnConstraints(columnConstraints, new ColumnConstraintsProperties(100));
            return this;
        }

        public Builder addColumnConstraints(ColumnConstraints columnConstraints, ColumnConstraintsProperties columnConstraintsProperties) {
            columnConstraints.setPercentWidth(columnConstraintsProperties.widthPercentage());
            columnConstraints.setHgrow(Priority.ALWAYS);
            columnConstraintsList.add(columnConstraints);
            return this;
        }

        public Builder addRowConstraints(RowConstraints rowConstraints, RowConstraintsProperties rowConstraintsProperties) {
            rowConstraints.setPercentHeight(rowConstraintsProperties.heightPercentage());
            rowConstraints.setVgrow(Priority.ALWAYS);
            rowConstraintsList.add(rowConstraints);
            return this;
        }

        public void setConstraints() {
            gridPane.getColumnConstraints().addAll(columnConstraintsList);
            gridPane.getRowConstraints().addAll(rowConstraintsList);
        }
    }

    public record ColumnConstraintsProperties(int widthPercentage) {
    }

    public record RowConstraintsProperties(int heightPercentage) {

    }
}
