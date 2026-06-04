package io.github.dug22.cipherlabs.ui.forms.cipher.impl;

import io.github.dug22.cipherlabs.CipherLabsCore;
import io.github.dug22.cipherlabs.cipheralgorithms.CipherRegistry;
import io.github.dug22.cipherlabs.cipheralgorithms.impl.classic.steganography.BaconianCipher;
import io.github.dug22.cipherlabs.config.ConfigurationManager;
import io.github.dug22.cipherlabs.config.ConfigurationRegistry;
import io.github.dug22.cipherlabs.config.impl.EncryptionDecryptionConfig;
import io.github.dug22.cipherlabs.ui.builder.LabelBuilder;
import io.github.dug22.cipherlabs.ui.controllers.types.WorkStationController;
import io.github.dug22.cipherlabs.ui.forms.cipher.CipherForm;
import io.github.dug22.cipherlabs.ui.forms.cipher.CipherFormLayoutBuilder;
import io.github.dug22.cipherlabs.ui.node.SettingComboBox;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TabPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

import java.io.File;
import java.util.List;

public class BaconianCipherForm extends CipherForm {

    private final CipherLabsCore instance = CipherLabsCore.getInstance();
    private final ConfigurationManager configurationManager = instance.getConfigManager();
    private final ConfigurationRegistry configurationRegistry = instance.getConfigRegistry();
    private final EncryptionDecryptionConfig encryptionDecryptionConfig = (EncryptionDecryptionConfig) configurationRegistry.getConfigFile("encryption-decryption-config");
    private final File encryptionDecryptionConfigFileInstance = encryptionDecryptionConfig.getFile();
    private final BaconianCipher baconianCipher = (BaconianCipher) CipherRegistry.getCipher("Baconian Cipher");
    private SettingComboBox<Boolean> useEarlyEraEncodingComboBox;
    private SettingComboBox<Boolean> useBinaryEncodingComboBox;

    public BaconianCipherForm(WorkStationController workStationController, TabPane tabPane) {
        super(workStationController, tabPane, "baconian-cipher-form-description.txt", new String[]{"Baconian", "Bacon"});
        setTitle("Baconian Cipher Form");
    }

    @Override
    protected void initOptions() {
        Label earlyEraEncodingLabel = new LabelBuilder.Builder()
                .setText("Use Early Era Baconian Cipher Encoding")
                .setFontSize(16)
                .setAlignment(Pos.CENTER)
                .setMaxWidth(Double.MAX_VALUE)
                .setBold(true)
                .build();
        useEarlyEraEncodingComboBox = new SettingComboBox<>();
        useEarlyEraEncodingComboBox.setMaxWidth(Double.MAX_VALUE);
        useEarlyEraEncodingComboBox.setItemsAndMainValue(
                List.of(true, false),
                configurationManager.getBooleanProperty(encryptionDecryptionConfigFileInstance, "baconian-cipher-use-early-era-encoding")
        );

        Label binaryEncodingLabel = new LabelBuilder.Builder()
                .setText("Use Binary Encoding")
                .setFontSize(16)
                .setAlignment(Pos.CENTER)
                .setMaxWidth(Double.MAX_VALUE)
                .setBold(true)
                .build();
        useBinaryEncodingComboBox = new SettingComboBox<>();
        useBinaryEncodingComboBox.setMaxWidth(Double.MAX_VALUE);
        useBinaryEncodingComboBox.setItemsAndMainValue(
                List.of(true, false),
                configurationManager.getBooleanProperty(encryptionDecryptionConfigFileInstance, "baconian-cipher-use-binary-encoding")
        );
        VBox encodingOptionsRow = new VBox(10, earlyEraEncodingLabel, useEarlyEraEncodingComboBox, binaryEncodingLabel, useBinaryEncodingComboBox);
        VBox.setVgrow(useBinaryEncodingComboBox, Priority.ALWAYS);

        BorderPane formRoot = new CipherFormLayoutBuilder()
                .addMainContent(encodingOptionsRow)
                .addBottomContent(getActionButtonsRow())
                .build();
        getDialogPane().setContent(formRoot);
    }

    @Override
    protected void initListeners() {
        useEarlyEraEncodingComboBox.getSelectionModel().selectedItemProperty().addListener((_, _, newValue) -> {
            if (newValue != null) {
                useEarlyEraEncodingComboBox.saveSettingValue(encryptionDecryptionConfig, "baconian-cipher-use-early-era-encoding", newValue);
            }
        });
        useBinaryEncodingComboBox.getSelectionModel().selectedItemProperty().addListener((_, _, newValue) -> {
            if (newValue != null) {
                useBinaryEncodingComboBox.saveSettingValue(encryptionDecryptionConfig, "baconian-cipher-use-binary-encoding", newValue);
            }
        });
        getEncryptButton().setOnAction((_) -> initEncryptDecryptAction(baconianCipher, true, "Baconian Cipher Encrypted Result"));
        getDecryptButton().setOnAction((_) -> initEncryptDecryptAction(baconianCipher, false, "Baconian Cipher Decrypted Result"));
    }
}
