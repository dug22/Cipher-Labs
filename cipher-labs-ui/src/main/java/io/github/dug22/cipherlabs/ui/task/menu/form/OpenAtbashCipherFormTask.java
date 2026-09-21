package io.github.dug22.cipherlabs.ui.task.menu.form;

import io.github.dug22.cipherlabs.ui.controllers.WorkStationController;
import io.github.dug22.cipherlabs.ui.forms.cipher.impl.AtbashCipherForm;
import io.github.dug22.cipherlabs.ui.task.CipherLabsRunnable;
import javafx.scene.control.TabPane;

public class OpenAtbashCipherFormTask implements CipherLabsRunnable {

    private final WorkStationController workStationController;
    private final TabPane tabPane;

    public OpenAtbashCipherFormTask(WorkStationController workStationController, TabPane tabPane) {
        this.workStationController = workStationController;
        this.tabPane = tabPane;
    }

    @Override
    public void run() {
        new AtbashCipherForm(workStationController, tabPane).init();
    }

}