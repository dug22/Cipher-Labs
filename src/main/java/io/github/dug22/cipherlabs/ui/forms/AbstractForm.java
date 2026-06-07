package io.github.dug22.cipherlabs.ui.forms;


import javafx.application.Platform;
import javafx.scene.control.Dialog;

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
        getDialogPane().setPrefSize(500, 400);
        setResizable(false);
    }

    protected abstract void initDescription();

    protected abstract void initOptions();

    protected abstract void initListeners();
}
