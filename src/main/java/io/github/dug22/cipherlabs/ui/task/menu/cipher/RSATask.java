package io.github.dug22.cipherlabs.ui.task.menu.cipher;

import io.github.dug22.cipherlabs.ui.controllers.types.WorkStationController;
import io.github.dug22.cipherlabs.ui.forms.cipher.impl.RSAForm;
import io.github.dug22.cipherlabs.ui.task.CipherLabsRunnable;
import javafx.scene.control.TabPane;

public class RSATask implements CipherLabsRunnable {

    private WorkStationController workStationController;
    private TabPane tabPane;

    public RSATask(WorkStationController workStationController, TabPane tabPane){
        this.workStationController = workStationController;
        this.tabPane = tabPane;
    }

    @Override
    public void run() {
        new RSAForm(workStationController, tabPane).init();
    }
}
