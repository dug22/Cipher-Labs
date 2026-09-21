package io.github.dug22.cipherlabs.ui.forms;


import javafx.scene.control.Dialog;
import javafx.scene.layout.Region;

public abstract class AbstractForm<R> extends Dialog<R> {

    public AbstractForm(){

    }

    public void init(){
        initDimensions();
        initDescription();
        initOptions();
        initListeners();
        show();
    }

    protected void initDimensions() {
        setResizable(true);
        getDialogPane().setPrefSize(500, Region.USE_PREF_SIZE);
        setResizable(false);
    }

    protected abstract void initDescription();

    protected abstract void initOptions();

    protected abstract void initListeners();
}
