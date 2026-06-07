package io.github.dug22.cipherlabs.ui.forms.cipher.impl;

import io.github.dug22.cipherlabs.ciphers.CipherRegistry;
import io.github.dug22.cipherlabs.ciphers.algorithm.classic.symmetric.CaesarCipher;
import io.github.dug22.cipherlabs.ui.animation.AnimationManager;
import io.github.dug22.cipherlabs.ui.builder.LabelBuilder;
import io.github.dug22.cipherlabs.ui.controllers.types.WorkStationController;
import io.github.dug22.cipherlabs.ui.dialog.Alerts;
import io.github.dug22.cipherlabs.ui.forms.cipher.CipherForm;
import io.github.dug22.cipherlabs.ui.forms.cipher.CipherFormPane;
import io.github.dug22.cipherlabs.ui.visuals.CaesarCipherVisual;
import io.github.dug22.cipherlabs.utils.Alphabets;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;

import java.util.stream.Stream;

public class CaesarCipherForm extends CipherForm {

    private CipherFormPane cipherFormPane;
    private TextField keyTextField;
    private RadioButton useROT13RadioButton;
    private RadioButton chooseValueRadioButton;
    private TextField fromAlphabetTextField;
    private TextField toAlphabetTextField;
    private Pane caesarVisualPane;
    private final CaesarCipher caesarCipher = (CaesarCipher) CipherRegistry.getCipher("Caesar Cipher");


    public CaesarCipherForm(WorkStationController workStationController, TabPane tabPane) {
        super(workStationController, tabPane, "caesar-cipher-form-description.txt", new String[]{"Caesar", "ROT-13"});
        setTitle("Caesar Cipher Form");
    }

    @Override
    protected void initOptions() {
        caesarVisualPane = new Pane();
        keyTextField = new TextField() {

            @Override
            public void paste() {

            }
        };
        keyTextField.setPromptText("Type a key between 1-25");
        keyTextField.setFocusTraversable(false);
        keyTextField.setMaxWidth(300);
        ToggleGroup group = new ToggleGroup();
        useROT13RadioButton = new RadioButton("Use ROT-13");
        chooseValueRadioButton = new RadioButton("Choose a value");
        chooseValueRadioButton.setSelected(true);
        Stream.of(useROT13RadioButton, chooseValueRadioButton).forEach(r -> r.setToggleGroup(group));
        fromAlphabetTextField = createAlphabetTextField();
        toAlphabetTextField = createAlphabetTextField();
        HBox keyOptionsRow = new HBox(5, keyTextField, chooseValueRadioButton, useROT13RadioButton);
        keyOptionsRow.setAlignment(Pos.CENTER);
        BorderPane.setMargin(getActionButtonsRow(), new Insets(20, 0, 0, 0));
        HBox topAlphabetRow = createAlphabetRow("From Alphabet", fromAlphabetTextField);
        HBox bottomAlphabetRow = createAlphabetRow("To Alphabet\t  ", toAlphabetTextField);
        cipherFormPane = new CipherFormPane();
        cipherFormPane.addCenteredContent(keyOptionsRow);
        cipherFormPane.addBottomContent(getActionButtonsRow());
        cipherFormPane.addBottomContent(topAlphabetRow);
        cipherFormPane.addBottomContent(bottomAlphabetRow);
        getDialogPane().setContent(cipherFormPane);
    }

    @Override
    protected void initListeners() {
        keyTextField.focusedProperty().addListener((_, _, hasFocus) -> {
            if (!hasFocus && !keyTextField.getText().isEmpty()) {
                boolean canParseKey;
                String currentText = keyTextField.getText();
                try {
                    Integer.parseInt(currentText);
                    canParseKey = true;
                } catch (NumberFormatException e) {
                    canParseKey = false;
                }

                if (canParseKey) {
                    String key = currentText;
                    if (Integer.parseInt(key) > 0 && Integer.parseInt(key) < 25) {
                        String toTextFieldCurrentText = toAlphabetTextField.getText();
                        String shiftedAlphabet = caesarCipher.encrypt(fromAlphabetTextField.getText(), key);
                        toAlphabetTextField.replaceText(0, toTextFieldCurrentText.length(), shiftedAlphabet);

                    } else {
                        Alerts.CAESAR_KEY_ERROR.show();
                    }
                } else {
                    Alerts.CAESAR_KEY_ERROR.show();
                }
            }

            chooseValueRadioButton.setSelected(true);
        });
        chooseValueRadioButton.setOnAction((_) -> keyTextField.clear());
        useROT13RadioButton.setOnAction((_) -> {
            String key = "13";
            keyTextField.setText(key);
            String toTextFieldCurrentText = toAlphabetTextField.getText();
            String shiftedAlphabet = caesarCipher.encrypt(fromAlphabetTextField.getText(), key);
            toAlphabetTextField.replaceText(0, toTextFieldCurrentText.length(), shiftedAlphabet);
        });
        getEncryptButton().setOnAction((e) -> initEncryptDecryptAction(true));
        getDecryptButton().setOnAction((e) -> initEncryptDecryptAction(false));
    }

    private void initEncryptDecryptAction(boolean encrypt) {
        String currentKey = keyTextField.getText();
        if (currentKey.isEmpty() || !currentKey.matches("[0-9]+")) {
            Alerts.CAESAR_KEY_ERROR.show();
            return;
        }
        initEncryptDecryptAction(caesarCipher, keyTextField, encrypt, encrypt ? "Caesar Cipher Encrypted Result" : "Caesar Cipher Decrypted Result");
        CaesarCipherVisual visual = new CaesarCipherVisual(caesarVisualPane, caesarCipher.getSteps(), fromAlphabetTextField, toAlphabetTextField);
        AnimationManager.terminate();
        visual.clear();
        visual.play(encrypt);
        if (!cipherFormPane.getBottomContainer().getChildren().contains(caesarVisualPane)) {
            cipherFormPane.getBottomContainer().getChildren().add(caesarVisualPane);
        }
    }

    private TextField createAlphabetTextField() {
        TextField alphabetTextField = new TextField(Alphabets.ALPHABET);
        alphabetTextField.setEditable(false);
        alphabetTextField.setMaxWidth(Double.MAX_VALUE);
        return alphabetTextField;
    }

    private HBox createAlphabetRow(String labelText, TextField textField) {
        Label label = new LabelBuilder.Builder()
                .setText(labelText)
                .setFontSize(16)
                .setBold(true)
                .build();
        HBox row = new HBox(10, label, textField);
        HBox.setHgrow(textField, Priority.ALWAYS);
        row.setAlignment(Pos.CENTER);
        return row;
    }
}
