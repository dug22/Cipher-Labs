package io.github.dug22.cipherlabs.ui.controllers;

import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;

public abstract class AbstractController {

    private final GridPane gridPane;

    public AbstractController(){
        this.gridPane = new GridPane();
    }

    protected void defineAnchorPaneProperties(){
        AnchorPane.setTopAnchor(gridPane, 0.0);
        AnchorPane.setBottomAnchor(gridPane, 0.0);
        AnchorPane.setLeftAnchor(gridPane, 0.0);
        AnchorPane.setRightAnchor(gridPane, 0.0);
    }

    protected abstract void defineGridPaneConstraints();

    protected abstract void defineNavigationHeader();

    protected abstract void defineControlListeners();

    public GridPane getGridPane() {
        return gridPane;
    }
}
