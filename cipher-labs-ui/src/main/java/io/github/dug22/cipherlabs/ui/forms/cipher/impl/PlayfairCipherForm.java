package io.github.dug22.cipherlabs.ui.forms.cipher.impl;


import io.github.dug22.cipherlabs.core.CipherLabsCore;
import io.github.dug22.cipherlabs.core.ciphers.CipherRegistry;
import io.github.dug22.cipherlabs.core.ciphers.algorithm.classic.symmetric.PlayfairCipher;
import io.github.dug22.cipherlabs.core.config.ConfigurationManager;
import io.github.dug22.cipherlabs.core.config.ConfigurationRegistry;
import io.github.dug22.cipherlabs.core.config.impl.EncryptionDecryptionConfig;
import io.github.dug22.cipherlabs.core.utils.Alphabets;
import io.github.dug22.cipherlabs.ui.builder.LabelBuilder;
import io.github.dug22.cipherlabs.ui.controllers.WorkStationController;
import io.github.dug22.cipherlabs.ui.dialog.Alerts;
import io.github.dug22.cipherlabs.ui.forms.cipher.CenteredBottomBorderPane;
import io.github.dug22.cipherlabs.ui.forms.cipher.CipherForm;
import io.github.dug22.cipherlabs.ui.node.SettingComboBox;
import io.github.dug22.cipherlabs.ui.visuals.VisualAnimationManager;
import io.github.dug22.cipherlabs.ui.visuals.impl.PlayfairCipherVisual;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

import java.io.File;

public class PlayfairCipherForm extends CipherForm {

    private CenteredBottomBorderPane centeredBottomBorderPane;
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
    private GridPane playfairVisualPane;

    public PlayfairCipherForm(WorkStationController workStationController, TabPane tabPane) {
        super(workStationController, tabPane, "playfair-cipher-form-description.txt", new String[]{"Playfair"});
        this.workStationController = workStationController;
        setTitle("Playfair Cipher Form");
    }

    @Override
    protected void initOptions() {
        playfairVisualPane = new GridPane();
        playfairVisualPane.setPrefHeight(0);
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
        centeredBottomBorderPane = new CenteredBottomBorderPane();
        centeredBottomBorderPane.addCenteredContent(playfairCipherOptions);
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
                    Alerts.VIGENERE_KEY_ERROR.show();
                }
            }
        });

        primaryFillerCharacterComboBox.getSelectionModel().selectedItemProperty().addListener((_, oldValue, newValue) -> {

            if (newValue.equals(secondaryFillerCharacterComboBox.getValue())) {
                primaryFillerCharacterComboBox.setValue(oldValue);
                Alerts.PLAYFAIR_FILLER_CHARACTER_ERROR.show();
                return;
            }

            if (newValue != null) {
                primaryFillerCharacterComboBox.saveSettingValue(encryptionDecryptionConfig, "play-fair-cipher-primary-filler-character", newValue);
            }
        });

        secondaryFillerCharacterComboBox.getSelectionModel().selectedItemProperty().addListener((_, oldValue, newValue) -> {
            if (newValue.equals(primaryFillerCharacterComboBox.getValue())) {
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
            initEncryptDecryptAction(playfairCipher, keyTextField.getText(), true, "Playfair Encrypted Result");
            executeVisual();
        });
        getDecryptButton().setOnAction((_) -> {
            String currentKey = keyTextField.getText();
            if (keyTextField.getText().isEmpty() || !currentKey.matches("[A-Za-z]+") || currentKey.length() > workStationController.getActiveTextArea().getText().length()) {
                Alerts.VIGENERE_KEY_ERROR.show();
                return;
            }
            initEncryptDecryptAction(playfairCipher, keyTextField.getText(), false, "Playfair Decrypted Result");
            executeVisual();
        });
    }

    private void executeVisual(){
        playfairVisualPane.setPrefHeight(250);
        PlayfairCipherVisual visual = new PlayfairCipherVisual(this, playfairVisualPane, playfairCipher.getPolybiusSquare(), playfairCipher.getSteps());
        VisualAnimationManager.terminate();
        visual.clear(this);
        visual.play();
        if (!centeredBottomBorderPane.getBottomContainer().getChildren().contains(playfairVisualPane)) {
            centeredBottomBorderPane.getBottomContainer().getChildren().add(playfairVisualPane);
        }
        Platform.runLater(() -> {
            getDialogPane().getScene().getWindow().sizeToScene();
        });
    }
}
