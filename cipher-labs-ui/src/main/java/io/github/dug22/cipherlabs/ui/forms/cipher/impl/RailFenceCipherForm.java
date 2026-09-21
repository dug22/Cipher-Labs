package io.github.dug22.cipherlabs.ui.forms.cipher.impl;


import io.github.dug22.cipherlabs.core.CipherLabsCore;
import io.github.dug22.cipherlabs.core.ciphers.CipherRegistry;
import io.github.dug22.cipherlabs.core.ciphers.algorithm.classic.symmetric.RailFenceCipher;
import io.github.dug22.cipherlabs.core.config.ConfigurationManager;
import io.github.dug22.cipherlabs.core.config.ConfigurationRegistry;
import io.github.dug22.cipherlabs.core.config.impl.EncryptionDecryptionConfig;
import io.github.dug22.cipherlabs.ui.builder.LabelBuilder;
import io.github.dug22.cipherlabs.ui.controllers.WorkStationController;
import io.github.dug22.cipherlabs.ui.dialog.Alerts;
import io.github.dug22.cipherlabs.ui.forms.cipher.CenteredBottomBorderPane;
import io.github.dug22.cipherlabs.ui.forms.cipher.CipherForm;
import io.github.dug22.cipherlabs.ui.node.SettingComboBox;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextArea;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextAlignment;

import java.io.File;
import java.util.List;

public class RailFenceCipherForm extends CipherForm {

    private final CipherLabsCore instance = CipherLabsCore.getInstance();
    private final ConfigurationManager configurationManager = instance.getConfigManager();
    private final ConfigurationRegistry configurationRegistry = instance.getConfigRegistry();
    private final EncryptionDecryptionConfig encryptionDecryptionConfig = (EncryptionDecryptionConfig) configurationRegistry.getConfigFile("encryption-decryption-config");
    private final File encryptionDecryptionConfigFileInstance = encryptionDecryptionConfig.getFile();
    private final RailFenceCipher railFenceCipher = (RailFenceCipher) CipherRegistry.getCipher("Rail Fence Cipher");
    private Spinner<Integer> railsSpinner;
    private SettingComboBox<String> separatorComboBox;
    private TextArea railFenceTextArea;

    public RailFenceCipherForm(WorkStationController workStationController, TabPane tabPane) {
        super(workStationController, tabPane, "rail-fence-cipher-form-description.txt", new String[]{"Rail", "rail", "Fence", "fence"});
    }

    @Override
    protected void initDimensions() {
        setResizable(true);
        getDialogPane().setPrefSize(500, 500);
        setResizable(false);
    }

    @Override
    protected void initOptions() {
        Label railsLabel = new LabelBuilder.Builder()
                .setText("Rails")
                .setFontSize(16)
                .setBold(true)
                .setTextAlignment(TextAlignment.CENTER)
                .build();
        railsSpinner = new Spinner<>(1, 1000, 3);
        separatorComboBox = new SettingComboBox<>();
        separatorComboBox.setItemsAndMainValue(
                List.of("-", ".", " ", "|", ":", "*", "~"),
                configurationManager.getStringProperty(encryptionDecryptionConfigFileInstance, "rail-fence-cipher-separator"));
        Label railfenceSeparatorOptionsLabel = new LabelBuilder.Builder()
                .setText("Separator Options")
                .setFontSize(16)
                .setBold(true)
                .setTextAlignment(TextAlignment.CENTER)
                .build();

        VBox railfenceSettingsVBox = new VBox(5, railsLabel, railsSpinner, railfenceSeparatorOptionsLabel, separatorComboBox);
        railfenceSettingsVBox.setAlignment(Pos.CENTER);
        Label matrixLabel = new LabelBuilder.Builder()
                .setText("Rail Fence Matrix Layout")
                .setFontSize(16)
                .setBold(true)
                .setTextAlignment(TextAlignment.CENTER)
                .build();
        railFenceTextArea = new TextArea();
        railFenceTextArea.setEditable(false);
        VBox matrixVBox = new VBox(5, matrixLabel, railFenceTextArea);
        matrixVBox.setAlignment(Pos.CENTER);
        StackPane railfenceTextAreaStackPane = new StackPane(matrixVBox);
        railfenceTextAreaStackPane.setAlignment(Pos.CENTER);
        CenteredBottomBorderPane centeredBottomBorderPane = new CenteredBottomBorderPane();
        centeredBottomBorderPane.addCenteredContent(railfenceSettingsVBox);
        centeredBottomBorderPane.addCenteredContent(getActionButtonsRow());
        centeredBottomBorderPane.addBottomContent(railfenceTextAreaStackPane);
        getDialogPane().setContent(centeredBottomBorderPane);
    }

    @Override
    protected void initListeners() {
        getEncryptButton().setOnAction((_) -> {
            int rails = railsSpinner.getValue();
            if (rails > getWorkStationController().getActiveTextArea().getText().length()) {
                Alerts.RAILFENCE_RAIL_AMOUNT_ERROR.show();
                return;
            }

            initEncryptDecryptAction(railFenceCipher, String.valueOf(rails), true, "Rail Fence Encrypted Result");
            railFenceTextArea.setText(railFenceCipher.getCleanMatrix(separatorComboBox.getValue().charAt(0)));
        });

        getDecryptButton().setOnAction((_) -> {
            int rails = railsSpinner.getValue();
            if (rails > getWorkStationController().getActiveTextArea().getText().length()) {
                Alerts.RAILFENCE_RAIL_AMOUNT_ERROR.show();
                return;
            }

            initEncryptDecryptAction(railFenceCipher, String.valueOf(rails), false, "Rail Fence Decrypted Result");
            railFenceTextArea.setText(railFenceCipher.getCleanMatrix(separatorComboBox.getValue().charAt(0)));
        });

        separatorComboBox.getSelectionModel().selectedItemProperty().addListener((_, _, newValue) -> {
            if (newValue != null) {
                separatorComboBox.saveSettingValue(encryptionDecryptionConfig, "rail-fence-cipher-separator", newValue);
            }
        });
    }
}
