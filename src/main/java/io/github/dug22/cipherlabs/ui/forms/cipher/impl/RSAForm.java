package io.github.dug22.cipherlabs.ui.forms.cipher.impl;

import io.github.dug22.cipherlabs.cipheralgorithms.CipherRegistry;
import io.github.dug22.cipherlabs.cipheralgorithms.impl.modern.asymmetric.RSA;
import io.github.dug22.cipherlabs.ui.builder.LabelBuilder;
import io.github.dug22.cipherlabs.ui.controllers.types.WorkStationController;
import io.github.dug22.cipherlabs.ui.dialog.Alerts;
import io.github.dug22.cipherlabs.ui.forms.cipher.CipherForm;
import io.github.dug22.cipherlabs.ui.forms.cipher.CipherFormLayoutBuilder;
import io.github.dug22.cipherlabs.utils.MathUtils;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;

import java.math.BigInteger;
import java.util.stream.Stream;

public class RSAForm extends CipherForm {

    private final RSA rsa = (RSA) CipherRegistry.getCipher("RSA");
    private Spinner<Integer> pValueSpinner;
    private Spinner<Integer> qValueSpinner;
    private Spinner<Integer> exponentSpinner;
    private Label nBitLength;

    public RSAForm(WorkStationController workStationController, TabPane tabPane) {
        super(workStationController, tabPane, "rsa-form-description.txt", new String[]{"RSA"});
        setTitle("RSA Cipher Form");
    }

    @Override
    protected void initOptions() {
        Label pLabel = new LabelBuilder.Builder()
                .setText("p")
                .setFontSize(14)
                .setAlignment(Pos.TOP_LEFT)
                .setMaxWidth(Double.MAX_VALUE)
                .build();
        pValueSpinner = new Spinner<>(0, Integer.MAX_VALUE, 17);
        Label qLabel = new LabelBuilder.Builder()
                .setText("q")
                .setFontSize(14)
                .setAlignment(Pos.TOP_LEFT)
                .setMaxWidth(Double.MAX_VALUE)
                .build();
        qValueSpinner = new Spinner<>(0, Integer.MAX_VALUE, 19);
        Label exponentLabel = new LabelBuilder.Builder()
                .setText("e")
                .setFontSize(14)
                .setAlignment(Pos.TOP_LEFT)
                .setMaxWidth(Double.MAX_VALUE)
                .build();
        exponentSpinner = new Spinner<>(0, Integer.MAX_VALUE, 5);

        nBitLength = new LabelBuilder.Builder()
                .setText("Bit Length: " + BigInteger.valueOf((long) pValueSpinner.getValue() * qValueSpinner.getValue()).bitLength())
                .setFontSize(14)
                .setAlignment(Pos.CENTER)
                .build();

        HBox keyOptionsRow = new HBox(10,
                pLabel,
                pValueSpinner,
                qLabel,
                qValueSpinner,
                exponentLabel,
                exponentSpinner);
        Stream.of(pValueSpinner, qValueSpinner, exponentSpinner)
                .forEach(spinner -> {
                    spinner.setEditable(true);
                });


        BorderPane formRoot = new CipherFormLayoutBuilder()
                .addMainContent(keyOptionsRow)
                .addMainContent(nBitLength)
                .addBottomContent(getActionButtonsRow())
                .build();
        getDialogPane().setContent(formRoot);

    }

    @Override
    protected void initListeners() {
        getEncryptButton().setOnAction((_) -> {
            BigInteger p = BigInteger.valueOf(pValueSpinner.getValue());
            BigInteger q = BigInteger.valueOf(qValueSpinner.getValue());
            BigInteger exponent = BigInteger.valueOf(exponentSpinner.getValue());
            BigInteger n = p.multiply(q);
            BigInteger m = new BigInteger(1, getWorkStationController().getActiveTextArea().getText().getBytes());
            if (m.compareTo(n) >= 0) {
                Alerts.RSA_MESSAGE_ERROR.show();
                return;
            }
            if (isPrime() && isInvertible()) {
                initEncryptDecryptAction(rsa, new TextField(p + " " + q + " " + exponent), true, "RSA Encrypted Result");
            } else {
                Alerts.RSA_KEY_ERROR.show();
            }

            nBitLength.setText("Bit Length: " + n.bitLength());
        });

        getDecryptButton().setOnAction((_) -> {
            BigInteger p = BigInteger.valueOf(pValueSpinner.getValue());
            BigInteger q = BigInteger.valueOf(qValueSpinner.getValue());
            BigInteger exponent = BigInteger.valueOf(exponentSpinner.getValue());

            if (isPrime() && isInvertible()) {
                initEncryptDecryptAction(rsa, new TextField(p + " " + q + " " + exponent), false, "RSA Decrypted Result");
            } else {
                Alerts.RSA_KEY_ERROR.show();
            }
        });
    }

    private boolean isPrime() {
        int p = pValueSpinner.getValue();
        int q = qValueSpinner.getValue();
        return MathUtils.isPrime(p) && MathUtils.isPrime(q);
    }

    private boolean isInvertible() {
        BigInteger p = BigInteger.valueOf(pValueSpinner.getValue());
        BigInteger q = BigInteger.valueOf(qValueSpinner.getValue());
        BigInteger exponent = BigInteger.valueOf(exponentSpinner.getValue());
        BigInteger phi = p.subtract(BigInteger.ONE).multiply(q.subtract(BigInteger.ONE));
        return MathUtils.isInvertible(exponent, phi);
    }
}
