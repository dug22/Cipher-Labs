package io.github.dug22.cipherlabs.ui.forms.cipher;

import io.github.dug22.cipherlabs.ciphers.Cipher;
import io.github.dug22.cipherlabs.ui.animation.AnimationManager;
import io.github.dug22.cipherlabs.ui.builder.LabelBuilder;
import io.github.dug22.cipherlabs.ui.controllers.types.WorkStationController;
import io.github.dug22.cipherlabs.ui.forms.AbstractForm;
import io.github.dug22.cipherlabs.ui.utils.FormUtils;
import io.github.dug22.cipherlabs.ui.utils.TextUtils;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextAlignment;
import javafx.scene.text.TextFlow;

public abstract class CipherForm extends AbstractForm<String> {

    private final WorkStationController workStationController;
    private final TabPane tabPane;
    private final String descriptionFormFileName;
    private final String[] descriptionKeywords;
    private Button encryptButton, decryptButton, cancelButton;
    private HBox actionButtonsRow;

    public CipherForm(WorkStationController workStationController, TabPane tabPane, String descriptionFormFileName, String[] descriptionKeywords) {
        this.workStationController = workStationController;
        this.tabPane = tabPane;
        this.descriptionFormFileName = descriptionFormFileName;
        this.descriptionKeywords = descriptionKeywords;
        this.encryptButton = new Button("Encrypt");
        this.decryptButton = new Button("Decrypt");
        this.cancelButton = new Button("Cancel");
        this.actionButtonsRow = new HBox(5, encryptButton, decryptButton, cancelButton);
        actionButtonsRow.setAlignment(Pos.CENTER);
        initCloseFormListener();
    }

    @Override
    protected void initDescription() {
        Label descriptionLabel = new LabelBuilder.Builder()
                .setText("Description")
                .setFontSize(18)
                .setBold(true)
                .build();
        TextFlow descriptionTextFlow = TextUtils.createTextWithBoldedKeywords(FormUtils.getHeaderFormContent("/form-descriptions/" + descriptionFormFileName), descriptionKeywords);
        VBox descriptionContainer = new VBox(descriptionLabel, new Label("\n"), descriptionTextFlow);
        descriptionContainer.setAlignment(Pos.TOP_LEFT);
        descriptionContainer.setPadding(new Insets(10));
        descriptionTextFlow.setMaxWidth(800);
        descriptionTextFlow.setTextAlignment(TextAlignment.JUSTIFY);
        getDialogPane().setHeader(descriptionContainer);
    }

    protected void initCloseFormListener() {
        cancelButton.setOnAction((_) -> {
            getDialogPane().getButtonTypes().add(ButtonType.CANCEL);
            close();
            AnimationManager.terminate();
        });
    }

    public WorkStationController getWorkStationController() {
        return workStationController;
    }

    public TabPane getTabPane() {
        return tabPane;
    }

    protected Button getEncryptButton() {
        return encryptButton;
    }

    protected Button getDecryptButton() {
        return decryptButton;
    }

    protected Button getCancelButton() {
        return cancelButton;
    }

    public HBox getActionButtonsRow() {
        return actionButtonsRow;
    }

    protected void initEncryptDecryptAction(Cipher cipher, boolean encrypt, String tabTitle) {
        initEncryptDecryptAction(cipher, null, encrypt, tabTitle);
    }

    protected void initEncryptDecryptAction(Cipher cipher, TextField keyTextfield, boolean encrypt, String tabTitle) {
        CipherFormActions.initEncryptDecryptAction(cipher, workStationController, tabPane, keyTextfield, encrypt, tabTitle);
    }
}
