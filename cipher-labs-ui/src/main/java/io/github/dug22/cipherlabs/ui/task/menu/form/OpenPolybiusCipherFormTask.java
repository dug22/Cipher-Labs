package io.github.dug22.cipherlabs.ui.task.menu.form;

import io.github.dug22.cipherlabs.ui.controllers.WorkStationController;
import io.github.dug22.cipherlabs.ui.forms.cipher.impl.PolybiusCipherForm;
import io.github.dug22.cipherlabs.ui.task.CipherLabsRunnable;
import javafx.scene.control.TabPane;

public class OpenPolybiusCipherFormTask implements CipherLabsRunnable {

    private final WorkStationController workStationController;
    private final TabPane tabPane;

    public OpenPolybiusCipherFormTask(WorkStationController workStationController, TabPane tabPane) {
        this.workStationController = workStationController;
        this.tabPane = tabPane;
    }

    @Override
    public void run() {
        new PolybiusCipherForm(workStationController, tabPane).init();
    }

}