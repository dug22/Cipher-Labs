package io.github.dug22.cipherlabs.ui.forms.cipher.impl;

import io.github.dug22.cipherlabs.core.ciphers.CipherRegistry;
import io.github.dug22.cipherlabs.core.ciphers.algorithm.classic.symmetric.AffineCipher;
import io.github.dug22.cipherlabs.ui.builder.LabelBuilder;
import io.github.dug22.cipherlabs.ui.forms.cipher.CenteredBottomBorderPane;
import io.github.dug22.cipherlabs.ui.forms.cipher.CipherForm;
import io.github.dug22.cipherlabs.ui.controllers.WorkStationController;
import javafx.geometry.Pos;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TabPane;
import javafx.scene.layout.HBox;

import java.util.List;
import java.util.stream.IntStream;

public class AffineCipherForm extends CipherForm {

    private final AffineCipher affineCipher = (AffineCipher) CipherRegistry.getCipher("Affine Cipher");
    private ComboBox<Integer> aKeyComboBox;
    private ComboBox<Integer> bKeyComboBox;


    public AffineCipherForm(WorkStationController workStationController, TabPane tabPane){
        super(workStationController, tabPane, "affine-cipher-form-description.txt", new String[]{"Affine"});
        setTitle("Affine Cipher Form");
    }

    @Override
    protected void initDimensions(){
        setResizable(true);
        getDialogPane().setPrefSize(500, 350);
        setResizable(false);
    }

    @Override
    protected void initOptions() {
        Label aKeyLabel = new LabelBuilder.Builder()
                .setText("Key a")
                .setFontSize(16)
                .setBold(true)
                .setAlignment(Pos.CENTER)
                .setMaxWidth(Double.MAX_VALUE)
                .build();
        aKeyComboBox = new ComboBox<>();
        aKeyComboBox.getItems().setAll(List.of(1, 3, 5, 7, 9, 11, 15, 17, 19, 21, 23, 25));
        aKeyComboBox.setValue(3);
        Label bKeyValue = new LabelBuilder.Builder()
                .setText("Key b")
                .setBold(true)
                .setFontSize(16)
                .setAlignment(Pos.CENTER)
                .setMaxWidth(Double.MAX_VALUE)
                .build();
        bKeyComboBox = new ComboBox<>();
        bKeyComboBox.getItems().setAll(IntStream.rangeClosed(1, 25).boxed().toList());
        bKeyComboBox.setValue(8);
        HBox keyOptionsRow = new HBox(10, aKeyLabel, aKeyComboBox, bKeyValue, bKeyComboBox);
        keyOptionsRow.setAlignment(Pos.CENTER);
        CenteredBottomBorderPane centeredBottomBorderPane = new CenteredBottomBorderPane();
        centeredBottomBorderPane.addCenteredContent(keyOptionsRow);
        centeredBottomBorderPane.addBottomContent(getActionButtonsRow());
        getDialogPane().setContent(centeredBottomBorderPane);
    }

    @Override
    protected void initListeners() {
        getEncryptButton().setOnAction((_) -> initEncryptDecryptAction(affineCipher, aKeyComboBox.getValue() + " " + bKeyComboBox.getValue(), true, "Affine Encrypted Result"));
        getDecryptButton().setOnAction((_) -> initEncryptDecryptAction(affineCipher,aKeyComboBox.getValue() + " " + bKeyComboBox.getValue(), false, "Affine Decrypted Result" ));
    }
}
