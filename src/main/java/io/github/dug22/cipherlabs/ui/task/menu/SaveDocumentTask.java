package io.github.dug22.cipherlabs.ui.task.menu;

import io.github.dug22.cipherlabs.ui.builder.AlertBuilder;
import io.github.dug22.cipherlabs.ui.controllers.types.HomePageController;
import io.github.dug22.cipherlabs.ui.controllers.types.WorkStationController;
import io.github.dug22.cipherlabs.ui.dialog.Alerts;
import io.github.dug22.cipherlabs.ui.task.CipherLabsRunnable;
import io.github.dug22.cipherlabs.ui.utils.SceneManger;
import javafx.scene.control.Alert;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextArea;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.FileWriter;

public class SaveDocumentTask implements CipherLabsRunnable {

    private final WorkStationController workStationController;


    public SaveDocumentTask(WorkStationController workStationController) {
        this.workStationController = workStationController;
    }

    @Override
    public void run() {
        TextArea currentTextArea = workStationController.getActiveTextArea();
        String textContent = currentTextArea.getText();
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save Document");
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Text Files", "*.txt"));
        File file = fileChooser.showSaveDialog(SceneManger.getCurrentStage());
        try (FileWriter fileWriter = new FileWriter(file)) {
            fileWriter.write(textContent);
            Alerts.SAVE_DOCUMENT.show(file.getAbsolutePath());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
