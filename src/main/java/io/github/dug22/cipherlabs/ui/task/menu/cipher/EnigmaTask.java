package io.github.dug22.cipherlabs.ui.task.menu.cipher;

import io.github.dug22.cipherlabs.ui.controllers.types.WorkStationController;
import io.github.dug22.cipherlabs.ui.forms.cipher.impl.EnigmaForm;
import io.github.dug22.cipherlabs.ui.task.CipherLabsRunnable;
import javafx.scene.control.TabPane;

public class EnigmaTask implements CipherLabsRunnable {

    private WorkStationController workStationController;
    private TabPane tabPane;

    public EnigmaTask(WorkStationController workStationController, TabPane tabPane) {
        this.workStationController = workStationController;
        this.tabPane = tabPane;
    }

    @Override
    public void run() {
        new EnigmaForm(workStationController, tabPane).init();
    }
}
