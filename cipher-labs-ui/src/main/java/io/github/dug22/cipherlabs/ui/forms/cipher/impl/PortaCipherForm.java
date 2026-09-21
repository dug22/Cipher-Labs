package io.github.dug22.cipherlabs.ui.forms.cipher.impl;

import io.github.dug22.cipherlabs.core.ciphers.CipherRegistry;
import io.github.dug22.cipherlabs.core.ciphers.algorithm.classic.symmetric.PortaCipher;
import io.github.dug22.cipherlabs.ui.controllers.WorkStationController;
import io.github.dug22.cipherlabs.ui.dialog.Alerts;
import io.github.dug22.cipherlabs.ui.forms.cipher.CenteredBottomBorderPane;
import io.github.dug22.cipherlabs.ui.forms.cipher.CipherForm;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;

public class PortaCipherForm extends CipherForm {

    private final WorkStationController workStationController;
    private TextField keyTextField;
    private final PortaCipher portaCipher = (PortaCipher) CipherRegistry.getCipher("Porta Cipher");


    public PortaCipherForm(WorkStationController workStationController, TabPane tabPane){
        super(workStationController, tabPane, "porta-cipher-form-description.txt", new String[]{"Porta"});
        this.workStationController = workStationController;
        setTitle("Porta Cipher Form");
    }

    @Override
    protected void initOptions() {
        keyTextField = new TextField();
        keyTextField.setFocusTraversable(false);
        keyTextField.setMaxWidth(Double.MAX_VALUE);
        keyTextField.setPadding(new Insets(5));
        keyTextField.setPromptText("Type your key here! Keys can only contain alphabetic values only!");
        HBox keyOptionsRow = new HBox(keyTextField);
        keyOptionsRow.setAlignment(Pos.CENTER);
        HBox.setHgrow(keyTextField, Priority.ALWAYS);
        CenteredBottomBorderPane centeredBottomBorderPane = new CenteredBottomBorderPane();
        centeredBottomBorderPane.addCenteredContent(keyOptionsRow);
        centeredBottomBorderPane.addBottomContent(getActionButtonsRow());
        getDialogPane().setContent(centeredBottomBorderPane);
    }

    @Override
    protected void initListeners() {
        keyTextField.focusedProperty().addListener((_, _, hasFocus) -> {
            if (!hasFocus && !keyTextField.getText().isEmpty()) {
                boolean isKeyValid;
                String currentText = keyTextField.getText();
                isKeyValid = currentText.matches("[A-Za-z]+");
                if (!isKeyValid) {
                    Alerts.PORTA_KEY_ERROR.show();
                }
            }
        });

        getEncryptButton().setOnAction((_) -> {
            String currentKey = keyTextField.getText();
            if (keyTextField.getText().isEmpty() || !currentKey.matches("[A-Za-z]+") || currentKey.length() > workStationController.getActiveTextArea().getText().length()) {
                Alerts.PORTA_KEY_ERROR.show();
                return;
            }
            initEncryptDecryptAction(portaCipher, keyTextField.getText(), true, "Porta Encrypted Result");
        });

        getDecryptButton().setOnAction((_) -> {
            String currentKey = keyTextField.getText();
            if (keyTextField.getText().isEmpty() || !currentKey.matches("[A-Za-z]+") || currentKey.length() > workStationController.getActiveTextArea().getText().length()) {
                Alerts.PORTA_KEY_ERROR.show();
                return;
            }
            initEncryptDecryptAction(portaCipher, keyTextField.getText(), false, "Porta Decrypted Result");
        });
    }
}