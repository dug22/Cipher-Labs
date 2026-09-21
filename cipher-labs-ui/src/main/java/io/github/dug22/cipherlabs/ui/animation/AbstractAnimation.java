package io.github.dug22.cipherlabs.ui.animation;

import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

public abstract class AbstractAnimation {

    private final GridPane gridPane;
    private final Pane canvas;

    public AbstractAnimation(GridPane gridPane){
        this.canvas = new Pane();
        this.gridPane = gridPane;
    }

    protected abstract void init();

    public abstract void execute();

    public Pane getCanvas() {
        return canvas;
    }

    public GridPane getGridPane(){
        return gridPane;
    }
}
