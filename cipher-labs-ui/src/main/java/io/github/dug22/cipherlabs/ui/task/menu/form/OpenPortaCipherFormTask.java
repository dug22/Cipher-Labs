package io.github.dug22.cipherlabs.ui.task.menu.form;

import io.github.dug22.cipherlabs.ui.controllers.WorkStationController;
import io.github.dug22.cipherlabs.ui.forms.cipher.impl.PortaCipherForm;
import io.github.dug22.cipherlabs.ui.task.CipherLabsRunnable;
import javafx.scene.control.TabPane;

public class OpenPortaCipherFormTask implements CipherLabsRunnable {

    private final WorkStationController workStationController;
    private final TabPane tabPane;

    public OpenPortaCipherFormTask(WorkStationController workStationController, TabPane tabPane) {
        this.workStationController = workStationController;
        this.tabPane = tabPane;
    }

    @Override
    public void run() {
        new PortaCipherForm(workStationController, tabPane).init();
    }

}
