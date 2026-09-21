package io.github.dug22.cipherlabs.ui.forms.cipher.impl;

import io.github.dug22.cipherlabs.core.ciphers.CipherRegistry;
import io.github.dug22.cipherlabs.core.ciphers.algorithm.classic.symmetric.AtbashCipher;
import io.github.dug22.cipherlabs.ui.forms.cipher.CenteredBottomBorderPane;
import io.github.dug22.cipherlabs.ui.forms.cipher.CipherForm;
import io.github.dug22.cipherlabs.ui.controllers.WorkStationController;
import javafx.scene.control.TabPane;

public class AtbashCipherForm extends CipherForm {

    private final AtbashCipher atbashCipher = (AtbashCipher) CipherRegistry.getCipher("Atbash Cipher");
    public AtbashCipherForm(WorkStationController workStationController, TabPane tabPane){
        super(workStationController, tabPane, "atbash-cipher-form-description.txt", new String[]{"Atbash"});
        setTitle("Atbash Cipher Form");
    }

    @Override
    protected void initDimensions(){
        setResizable(true);
        getDialogPane().setPrefSize(500, 200);
        setResizable(false);
    }

    @Override
    protected void initOptions() {
        CenteredBottomBorderPane centeredBottomBorderPane = new CenteredBottomBorderPane();
        centeredBottomBorderPane.addCenteredContent(getActionButtonsRow());
        getDialogPane().setContent(centeredBottomBorderPane);
    }

    @Override
    protected void initListeners() {
        getEncryptButton().setOnAction((_) -> initEncryptDecryptAction(atbashCipher, true, "Atbash Encrypted Result"));
        getDecryptButton().setOnAction((_) -> initEncryptDecryptAction(atbashCipher, false, "Atbash Decrypted Result"));
    }
}