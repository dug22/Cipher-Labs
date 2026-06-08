package io.github.dug22.cipherlabs.ui.utils;

import javafx.application.Platform;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;
import javafx.stage.Window;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

public class FormUtils {

    public static String getHeaderFormContent(String file) {
        StringBuilder header = new StringBuilder();
        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(FormUtils.class.getResourceAsStream(file)))) {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                header.append(line);
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return header.toString();
    }

    public static void resize(Dialog<String> form, int height) {
        form.setResizable(true);
        DialogPane formDialogPane = form.getDialogPane();
        formDialogPane.setPrefSize(500, height);
        formDialogPane.setMinSize(500, height);
        formDialogPane.setMaxSize(500, height);
        if (formDialogPane.getScene() != null && formDialogPane.getScene().getWindow() != null) {
            Window window = formDialogPane.getScene().getWindow();
            window.setHeight(height);
            window.sizeToScene();
        }

        Platform.runLater(() -> form.setResizable(false));
    }
}
