package io.github.dug22.cipherlabs.ui.forms.cipher;

import io.github.dug22.cipherlabs.ciphers.Cipher;
import io.github.dug22.cipherlabs.ciphers.algorithm.classic.ClassicSteganographicCipher;
import io.github.dug22.cipherlabs.ciphers.algorithm.classic.ClassicSymmetricCipher;
import io.github.dug22.cipherlabs.ciphers.algorithm.modern.asymmetric.RSA;
import io.github.dug22.cipherlabs.ui.controllers.types.WorkStationController;
import javafx.application.Platform;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.math.BigInteger;

public class CipherFormActions {

    public static void initEncryptDecryptAction(String resultingText, String tabTitle, WorkStationController workStationController, TabPane tabPane){
        TextArea newTextArea = new TextArea(resultingText);
        Tab tab = new Tab(tabTitle);
        tab.setContent(newTextArea);
        tabPane.getTabs().add(tab);
        Platform.runLater(() -> {
            tabPane.getSelectionModel().select(tab);
            TextArea area = (TextArea) tab.getContent();
            area.requestFocus();
            refreshTextAnalysisDashboard(workStationController, newTextArea);
        });
    }


    public static void initEncryptDecryptAction(Cipher classicCipher, WorkStationController workStationController, TabPane tabPane, TextField keyTextField, boolean encrypt, String tabTitle) {
        TextArea newTextArea = new TextArea(getResultingText(classicCipher, workStationController, keyTextField, encrypt));
        Tab tab = new Tab(tabTitle);
        tab.setContent(newTextArea);
        tabPane.getTabs().add(tab);
        Platform.runLater(() -> {
            tabPane.getSelectionModel().select(tab);
            TextArea area = (TextArea) tab.getContent();
            area.requestFocus();
            refreshTextAnalysisDashboard(workStationController, newTextArea);
        });
    }

    private static String getResultingText(Cipher cipher, WorkStationController workStationController, TextField keyTextField, boolean encrypt) {
        String currentText = workStationController.getActiveTextArea().getText();
        if (cipher instanceof ClassicSymmetricCipher || cipher instanceof ClassicSteganographicCipher) {
            currentText = currentText.toUpperCase();
        }

        String key = "";

        if (keyTextField != null) {
            key = keyTextField.getText().toUpperCase();
        }

        String resultingText = "";

        if (cipher instanceof ClassicSymmetricCipher classicSymmetricCipher) {
            if (encrypt) {
                resultingText = classicSymmetricCipher.encrypt(currentText, key);
            } else {
                resultingText = classicSymmetricCipher.decrypt(currentText, key);
            }
        } else if (cipher instanceof ClassicSteganographicCipher classicSteganographicCipher) {
            if (encrypt) {
                resultingText = classicSteganographicCipher.encrypt(currentText);
            } else {
                resultingText = classicSteganographicCipher.decrypt(currentText);
            }
        } else if (cipher instanceof RSA rsa) {
            String[] keyParts = key.split(" ");
            if (encrypt) {
                resultingText = rsa.encrypt(
                        currentText,
                        BigInteger.valueOf(Integer.parseInt(keyParts[0])),
                        BigInteger.valueOf(Integer.parseInt(keyParts[1])),
                        BigInteger.valueOf(Integer.parseInt(keyParts[2])));
            } else {
                resultingText = rsa.decrypt(
                        currentText,
                        BigInteger.valueOf(Integer.parseInt(keyParts[0])),
                        BigInteger.valueOf(Integer.parseInt(keyParts[1])),
                        BigInteger.valueOf(Integer.parseInt(keyParts[2])));
            }
        }

        return resultingText;
    }

    private static void refreshTextAnalysisDashboard(WorkStationController workStationController, TextArea textArea){
        workStationController.getGridPane().getChildren().remove(workStationController.getSideDashboard());
        workStationController.getGridPane().add( workStationController.createAnalysisDashboard(textArea, false), 1, 1);
    }
}
