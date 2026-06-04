package io.github.dug22.cipherlabs.ui.forms.cipher.impl;

import io.github.dug22.cipherlabs.cipheralgorithms.CipherRegistry;
import io.github.dug22.cipherlabs.cipheralgorithms.impl.classic.symmetric.VigenereCipher;
import io.github.dug22.cipherlabs.ui.controllers.types.WorkStationController;
import io.github.dug22.cipherlabs.ui.dialog.Alerts;
import io.github.dug22.cipherlabs.ui.forms.cipher.CipherForm;
import io.github.dug22.cipherlabs.ui.forms.cipher.CipherFormLayoutBuilder;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.scene.text.TextFlow;


public class VigenereCipherForm extends CipherForm {

    private final WorkStationController workStationController;
    private TextField keyTextField;
    private TextFlow keyCombinationsTextFlow;
    private Text keyCombinationsHeaderText;
    private Text keyCombinationsResultText;
    private Text crackTimePerMillionResultText;
    private final VigenereCipher vigenereCipher = (VigenereCipher) CipherRegistry.getCipher("Vigenère Cipher");

    public VigenereCipherForm(WorkStationController workStationController, TabPane tabPane) {
        super(workStationController, tabPane, "vigenere-cipher-form-description.txt", new String[]{"Vigenère"});
        this.workStationController = workStationController;
        setTitle("Vigenère Cipher Form");
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
        keyCombinationsTextFlow = new TextFlow();
        keyCombinationsHeaderText = new Text("Key Combinations: ");
        keyCombinationsHeaderText.setStyle("-fx-font-weight: bold");
        keyCombinationsResultText = new Text("26^n");
        keyCombinationsTextFlow.getChildren().setAll(keyCombinationsHeaderText, keyCombinationsResultText);
        TextFlow crackTimePerMillionTextFlow = new TextFlow();
        Text crackTimePerMillionHeaderText = new Text("Time to Crack Per Million Guesses: ");
        crackTimePerMillionHeaderText.setStyle("-fx-font-weight: bold");
        crackTimePerMillionResultText = new Text("?");
        crackTimePerMillionTextFlow.getChildren().addAll(crackTimePerMillionHeaderText, crackTimePerMillionResultText);
        keyCombinationsTextFlow.setTextAlignment(TextAlignment.CENTER);
        crackTimePerMillionTextFlow.setTextAlignment(TextAlignment.CENTER);
        BorderPane formRoot = new CipherFormLayoutBuilder()
                .addMainContent(keyOptionsRow)
                .addBottomContent(getActionButtonsRow())
                .addBottomContent(keyCombinationsTextFlow)
                .addBottomContent(crackTimePerMillionTextFlow)
                .build();
        getDialogPane().setContent(formRoot);
    }

    @Override
    protected void initListeners() {
        keyTextField.focusedProperty().addListener((_, _, hasFocus) -> {
            if (!hasFocus && !keyTextField.getText().isEmpty()) {
                boolean isKeyValid;
                String currentText = keyTextField.getText();
                isKeyValid = currentText.matches("[A-Za-z]+");
                if (!isKeyValid) {
                    Alerts.VIGENERE_KEY_ERROR.show();
                }
            }
        });
        getEncryptButton().setOnAction((_) -> {
            String currentKey = keyTextField.getText();
            if (keyTextField.getText().isEmpty() || !currentKey.matches("[A-Za-z]+") || currentKey.length() > workStationController.getActiveTextArea().getText().length()) {
                Alerts.VIGENERE_KEY_ERROR.show();
                return;
            }
            initEncryptDecryptAction(vigenereCipher,keyTextField,true, "Vigenère Encrypted Result");
            keyCombinationsResultText.setText("26^" + currentKey.length());
            keyCombinationsTextFlow.getChildren().setAll(keyCombinationsHeaderText, keyCombinationsResultText);
            crackTimePerMillionResultText.setText(String.format("%.2f seconds", Math.pow(26, currentKey.length()) / 1_000_000));
        });

        getDecryptButton().setOnAction((_) -> {
            String currentKey = keyTextField.getText();
            if (keyTextField.getText().isEmpty() || !currentKey.matches("[A-Za-z]+") || currentKey.length() > workStationController.getActiveTextArea().getText().length()) {
                Alerts.VIGENERE_KEY_ERROR.show();
                return;
            }
            initEncryptDecryptAction(vigenereCipher,keyTextField,false, "Vigenère Decrypted Result");
            keyCombinationsResultText.setText("26^" + currentKey.length());
            crackTimePerMillionResultText.setText(String.format("%.2f seconds", Math.pow(26, currentKey.length()) / 1_000_000));
        });
    }
}
