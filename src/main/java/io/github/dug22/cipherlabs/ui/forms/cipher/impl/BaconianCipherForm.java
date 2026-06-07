package io.github.dug22.cipherlabs.ui.forms.cipher.impl;

import io.github.dug22.cipherlabs.CipherLabsCore;
import io.github.dug22.cipherlabs.ciphers.CipherRegistry;
import io.github.dug22.cipherlabs.ciphers.algorithm.classic.steganography.BaconianCipher;
import io.github.dug22.cipherlabs.config.ConfigurationManager;
import io.github.dug22.cipherlabs.config.ConfigurationRegistry;
import io.github.dug22.cipherlabs.config.impl.EncryptionDecryptionConfig;
import io.github.dug22.cipherlabs.ui.animation.AnimationManager;
import io.github.dug22.cipherlabs.ui.builder.LabelBuilder;
import io.github.dug22.cipherlabs.ui.controllers.types.WorkStationController;
import io.github.dug22.cipherlabs.ui.dialog.Alerts;
import io.github.dug22.cipherlabs.ui.forms.cipher.CipherForm;
import io.github.dug22.cipherlabs.ui.forms.cipher.CipherFormPane;
import io.github.dug22.cipherlabs.ui.node.SettingComboBox;
import io.github.dug22.cipherlabs.ui.visuals.BaconianCipherVisual;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TabPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Window;

import java.io.File;
import java.util.List;
import java.util.Map;

public class BaconianCipherForm extends CipherForm {

    private CipherFormPane cipherFormPane;
    private final CipherLabsCore instance = CipherLabsCore.getInstance();
    private final ConfigurationManager configurationManager = instance.getConfigManager();
    private final ConfigurationRegistry configurationRegistry = instance.getConfigRegistry();
    private final EncryptionDecryptionConfig encryptionDecryptionConfig = (EncryptionDecryptionConfig) configurationRegistry.getConfigFile("encryption-decryption-config");
    private final File encryptionDecryptionConfigFileInstance = encryptionDecryptionConfig.getFile();
    private final BaconianCipher baconianCipher = (BaconianCipher) CipherRegistry.getCipher("Baconian Cipher");
    private SettingComboBox<Boolean> useEarlyEraEncodingComboBox;
    private SettingComboBox<Boolean> useBinaryEncodingComboBox;
    private Pane baconianVisualPane;

    public BaconianCipherForm(WorkStationController workStationController, TabPane tabPane) {
        super(workStationController, tabPane, "baconian-cipher-form-description.txt", new String[]{"Baconian", "Bacon"});
        setTitle("Baconian Cipher Form");
    }


    @Override
    protected void initOptions() {
        baconianVisualPane = new Pane();
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

        cipherFormPane = new CipherFormPane();
        cipherFormPane.addCenteredContent(encodingOptionsRow);
        cipherFormPane.addBottomContent(getActionButtonsRow());
        getDialogPane().setContent(cipherFormPane);
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
        getEncryptButton().setOnAction((_) -> initEncryptDecryptAction(true));
        getDecryptButton().setOnAction((_) -> {
            Map<String, String> decryptionMap = baconianCipher.getAppropriateDecryptionMap();
            boolean hasInvalidTextToDecrypt = false;
            if (decryptionMap != null) {
                for (int i = 0; i < getWorkStationController().getActiveTextArea().getText().length() - 5; i += 5) {
                    String code = getWorkStationController().getActiveTextArea().getText(i, i + 5);
                    if (!decryptionMap.containsKey(code)) {
                        hasInvalidTextToDecrypt = true;
                        break;
                    }
                }
            }

            if (decryptionMap == null || hasInvalidTextToDecrypt) {
                Alerts.BACON_DECRYPTION_ERROR.show();
                return;
            }

            initEncryptDecryptAction(false);
        });
    }

    private void initEncryptDecryptAction(boolean encrypt) {
        resize(700);
        initEncryptDecryptAction(baconianCipher, encrypt, encrypt ? "Baconian Cipher Encrypted Result" : "Baconian Cipher Decrypted Result");
        Platform.runLater(() -> {
            BaconianCipherVisual visual = new BaconianCipherVisual(baconianVisualPane, baconianCipher);
            AnimationManager.terminate();
            visual.clear();
            visual.setOnFinished(() -> {
                Platform.runLater(() -> resize(400));
            });
            visual.play(encrypt);
            if (!cipherFormPane.getBottomContainer().getChildren().contains(baconianVisualPane)) {
                cipherFormPane.getBottomContainer().getChildren().add(baconianVisualPane);
            }
        });
    }

    private void resize(int height) {
        setResizable(true);
        getDialogPane().setPrefSize(500, height);
        getDialogPane().setMinSize(500, height);
        getDialogPane().setMaxSize(500, height);
        if (getDialogPane().getScene() != null && getDialogPane().getScene().getWindow() != null) {
            Window window = getDialogPane().getScene().getWindow();
            window.setHeight(height);
            window.sizeToScene();
        }

        Platform.runLater(() -> setResizable(false));
    }
}
