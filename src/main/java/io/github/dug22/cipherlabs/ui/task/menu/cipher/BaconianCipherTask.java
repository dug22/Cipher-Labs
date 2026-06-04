package io.github.dug22.cipherlabs.ui.task.menu.cipher;

import io.github.dug22.cipherlabs.ui.controllers.types.WorkStationController;
import io.github.dug22.cipherlabs.ui.forms.cipher.impl.BaconianCipherForm;
import io.github.dug22.cipherlabs.ui.task.CipherLabsRunnable;
import javafx.scene.control.TabPane;

public class BaconianCipherTask implements CipherLabsRunnable {

    private final WorkStationController workStationController;
    private final TabPane tabPane;

    public BaconianCipherTask(WorkStationController workStationController, TabPane tabPane) {
        this.workStationController = workStationController;
        this.tabPane = tabPane;
    }

    @Override
    public void run() {
        new BaconianCipherForm(workStationController, tabPane).init();
    }

}
