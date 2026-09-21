package io.github.dug22.cipherlabs.ui.forms.cipher.impl;

import io.github.dug22.cipherlabs.core.ciphers.CipherRegistry;
import io.github.dug22.cipherlabs.core.ciphers.algorithm.classic.symmetric.PolybiusCipher;
import io.github.dug22.cipherlabs.ui.builder.LabelBuilder;
import io.github.dug22.cipherlabs.ui.controllers.WorkStationController;
import io.github.dug22.cipherlabs.ui.dialog.Alerts;
import io.github.dug22.cipherlabs.ui.forms.cipher.CenteredBottomBorderPane;
import io.github.dug22.cipherlabs.ui.forms.cipher.CipherForm;
import io.github.dug22.cipherlabs.ui.visuals.VisualAnimationManager;
import io.github.dug22.cipherlabs.ui.visuals.impl.PolybiusCipherVisual;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextAlignment;

public class PolybiusCipherForm extends CipherForm {

    private final PolybiusCipher polybiusCipher = (PolybiusCipher) CipherRegistry.getCipher("Polybius Cipher");
    private CenteredBottomBorderPane centeredBottomBorderPane;
    private TextField optionalKeyTextField;
    private GridPane polybiusCipherVisualPane;

    public PolybiusCipherForm(WorkStationController workStationController, TabPane tabPane) {
        super(workStationController, tabPane, "polybius-cipher-form-description.txt", new String[]{"Polybius", "Square", "polybius", "square"});
    }

    @Override
    protected void initOptions() {
        polybiusCipherVisualPane = new GridPane();
        polybiusCipherVisualPane.setPrefHeight(0);
        Label optionalKeyLabel = new LabelBuilder.Builder()
                .setText("Optional Grid Key")
                .setBold(true)
                .setFontSize(16)
                .setTextAlignment(TextAlignment.CENTER)
                .build();
        optionalKeyTextField = new TextField();
        optionalKeyTextField.setAlignment(Pos.CENTER);
        optionalKeyTextField.setPromptText("Type out your key here (optional)");
        optionalKeyTextField.setFocusTraversable(false);
        VBox keyOptionsVBox = new VBox(5, optionalKeyLabel, optionalKeyTextField);
        keyOptionsVBox.setAlignment(Pos.CENTER);
        centeredBottomBorderPane = new CenteredBottomBorderPane();
        centeredBottomBorderPane.addCenteredContent(keyOptionsVBox);
        centeredBottomBorderPane.addBottomContent(getActionButtonsRow());
        getDialogPane().setContent(centeredBottomBorderPane);
    }

    @Override
    protected void initListeners() {
        getEncryptButton().setOnAction((_) -> {
            String optionalKey = optionalKeyTextField.getText();
            if (!optionalKey.isEmpty() && !optionalKey.chars().allMatch(Character::isLetter)) {
                Alerts.POLYBIUS_KEY_ERROR.show();
                return;
            }

            initEncryptDecryptAction(polybiusCipher, optionalKey, true, "Polybius Encrypted Result");
            executeVisual();
        });

        getDecryptButton().setOnAction((_) -> {
            String optionalKey = optionalKeyTextField.getText();
            if (!optionalKey.isEmpty() && !optionalKey.chars().allMatch(Character::isLetter) || optionalKey.length() > getWorkStationController().getActiveTextArea().getText().length()) {
                Alerts.POLYBIUS_KEY_ERROR.show();
                return;
            }

            initEncryptDecryptAction(polybiusCipher, optionalKey, false, "Polybius Decrypted Result");
            executeVisual();
        });
    }

    private void executeVisual(){
        //resize(this, 700);
        polybiusCipherVisualPane.setPrefHeight(250);
        PolybiusCipherVisual visual = new PolybiusCipherVisual(this, polybiusCipherVisualPane, polybiusCipher.getPolybiusSquare(), polybiusCipher.getSteps());
        VisualAnimationManager.terminate();
        visual.clear(this);
        visual.play();
        if (!centeredBottomBorderPane.getBottomContainer().getChildren().contains(polybiusCipherVisualPane)) {
            centeredBottomBorderPane.getBottomContainer().getChildren().add(polybiusCipherVisualPane);
        }
        Platform.runLater(() -> {
            getDialogPane().getScene().getWindow().sizeToScene();
        });
    }
}