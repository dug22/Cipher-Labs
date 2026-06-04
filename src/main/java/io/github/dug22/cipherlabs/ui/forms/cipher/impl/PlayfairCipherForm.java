package io.github.dug22.cipherlabs.ui.forms.cipher.impl;

import io.github.dug22.cipherlabs.CipherLabsCore;
import io.github.dug22.cipherlabs.cipheralgorithms.CipherRegistry;
import io.github.dug22.cipherlabs.cipheralgorithms.impl.classic.symmetric.PlayfairCipher;
import io.github.dug22.cipherlabs.config.ConfigurationManager;
import io.github.dug22.cipherlabs.config.ConfigurationRegistry;
import io.github.dug22.cipherlabs.config.impl.EncryptionDecryptionConfig;
import io.github.dug22.cipherlabs.ui.builder.LabelBuilder;
import io.github.dug22.cipherlabs.ui.controllers.types.WorkStationController;
import io.github.dug22.cipherlabs.ui.dialog.Alerts;
import io.github.dug22.cipherlabs.ui.forms.cipher.CipherForm;
import io.github.dug22.cipherlabs.ui.forms.cipher.CipherFormLayoutBuilder;
import io.github.dug22.cipherlabs.ui.node.SettingComboBox;
import io.github.dug22.cipherlabs.utils.Alphabets;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

import java.io.File;

public class PlayfairCipherForm extends CipherForm {


    private final CipherLabsCore instance = CipherLabsCore.getInstance();
    private final ConfigurationManager configurationManager = instance.getConfigManager();
    private final ConfigurationRegistry configurationRegistry = instance.getConfigRegistry();
    private final EncryptionDecryptionConfig encryptionDecryptionConfig = (EncryptionDecryptionConfig) configurationRegistry.getConfigFile("encryption-decryption-config");
    private final File encryptionDecryptionConfigFileInstance = encryptionDecryptionConfig.getFile();
    private final PlayfairCipher playfairCipher = (PlayfairCipher) CipherRegistry.getCipher("Playfair Cipher");
    private final WorkStationController workStationController;
    private SettingComboBox<Character> primaryFillerCharacterComboBox;
    private SettingComboBox<Character> secondaryFillerCharacterComboBox;
    private TextField keyTextField;

    public PlayfairCipherForm(WorkStationController workStationController, TabPane tabPane) {
        super(workStationController, tabPane, "playfair-cipher-form-description.txt", new String[]{"Playfair"});
        this.workStationController = workStationController;
        setTitle("Playfair Cipher Form");
    }

    @Override
    protected void initOptions() {
        Label primaryFillerCharacterLabel = new LabelBuilder.Builder()
                .setText("Primary Filler Character")
                .setFontSize(16)
                .setAlignment(Pos.CENTER)
                .setMaxWidth(Double.MAX_VALUE)
                .setBold(true)
                .build();
        primaryFillerCharacterComboBox = new SettingComboBox<>();
        primaryFillerCharacterComboBox.setMaxWidth(Double.MAX_VALUE);
        primaryFillerCharacterComboBox.setItemsAndMainValue(
                Alphabets.ALPHABET_CHAR_LIST,
                configurationManager.getCharacterProperty(encryptionDecryptionConfigFileInstance, "play-fair-cipher-primary-filler-character")
        );

        Label secondaryFillerCharacterLabel = new LabelBuilder.Builder()
                .setText("Secondary Filler Character")
                .setFontSize(16)
                .setAlignment(Pos.CENTER)
                .setMaxWidth(Double.MAX_VALUE)
                .setBold(true)
                .build();
        secondaryFillerCharacterComboBox = new SettingComboBox<>();
        secondaryFillerCharacterComboBox.setMaxWidth(Double.MAX_VALUE);
        secondaryFillerCharacterComboBox.setItemsAndMainValue(
                Alphabets.ALPHABET_CHAR_LIST,
                configurationManager.getCharacterProperty(encryptionDecryptionConfigFileInstance, "play-fair-cipher-secondary-filler-character")
        );

        keyTextField = new TextField();
        keyTextField.setFocusTraversable(false);
        keyTextField.setMaxWidth(Double.MAX_VALUE);
        keyTextField.setPadding(new Insets(10));
        keyTextField.setPromptText("Type your key here! Keys can only contain alphabetic values only!");
        VBox playfairCipherOptions = new VBox(10, primaryFillerCharacterLabel,
                primaryFillerCharacterComboBox,
                secondaryFillerCharacterLabel,
                secondaryFillerCharacterComboBox,
                keyTextField,
                getActionButtonsRow());
        BorderPane formRoot = new CipherFormLayoutBuilder()
                .addMainContent(playfairCipherOptions)
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

        primaryFillerCharacterComboBox.getSelectionModel().selectedItemProperty().addListener((_, oldValue, newValue) -> {

            if(newValue.equals(secondaryFillerCharacterComboBox.getValue())){
                primaryFillerCharacterComboBox.setValue(oldValue);
                Alerts.PLAYFAIR_FILLER_CHARACTER_ERROR.show();
                return;
            }

            if (newValue != null) {
                primaryFillerCharacterComboBox.saveSettingValue(encryptionDecryptionConfig, "play-fair-cipher-primary-filler-character", newValue);
            }
        });

        secondaryFillerCharacterComboBox.getSelectionModel().selectedItemProperty().addListener((_, oldValue, newValue) -> {
            if(newValue.equals(primaryFillerCharacterComboBox.getValue())){
                secondaryFillerCharacterComboBox.setValue(oldValue);
                Alerts.PLAYFAIR_FILLER_CHARACTER_ERROR.show();
                return;
            }

            if (newValue != null) {
                secondaryFillerCharacterComboBox.saveSettingValue(encryptionDecryptionConfig, "play-fair-cipher-secondary-filler-character", newValue);
            }
        });

        getEncryptButton().setOnAction((_) -> {
            String currentKey = keyTextField.getText();
            if (keyTextField.getText().isEmpty() || !currentKey.matches("[A-Za-z]+") || currentKey.length() > workStationController.getActiveTextArea().getText().length()) {
                Alerts.VIGENERE_KEY_ERROR.show();
                return;
            }
            initEncryptDecryptAction(playfairCipher, keyTextField, true, "Playfair Encrypted Result");
        });
        getDecryptButton().setOnAction((_) -> {
            String currentKey = keyTextField.getText();
            if (keyTextField.getText().isEmpty() || !currentKey.matches("[A-Za-z]+") || currentKey.length() > workStationController.getActiveTextArea().getText().length()) {
                Alerts.VIGENERE_KEY_ERROR.show();
                return;
            }
            initEncryptDecryptAction(playfairCipher, keyTextField, false, "Playfair Decrypted Result");
        });
    }
}
