package io.github.dug22.cipherlabs.ui.forms.cipher.impl;

import io.github.dug22.cipherlabs.ciphers.CipherRegistry;
import io.github.dug22.cipherlabs.ciphers.algorithm.classic.symmetric.enigma.*;
import io.github.dug22.cipherlabs.ciphers.algorithm.classic.symmetric.enigma.reflector.ReflectorA;
import io.github.dug22.cipherlabs.ciphers.algorithm.classic.symmetric.enigma.reflector.ReflectorB;
import io.github.dug22.cipherlabs.ciphers.algorithm.classic.symmetric.enigma.reflector.ReflectorC;
import io.github.dug22.cipherlabs.ciphers.algorithm.classic.symmetric.enigma.rotors.*;
import io.github.dug22.cipherlabs.ui.builder.LabelBuilder;
import io.github.dug22.cipherlabs.ui.controllers.types.WorkStationController;
import io.github.dug22.cipherlabs.ui.dialog.Alerts;
import io.github.dug22.cipherlabs.ui.forms.cipher.CipherForm;
import io.github.dug22.cipherlabs.ui.forms.cipher.CipherFormActions;
import io.github.dug22.cipherlabs.ui.forms.cipher.CipherFormPane;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.skin.TableHeaderRow;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.text.TextAlignment;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;

public class EnigmaForm extends CipherForm {

    private Enigma enigma;
    private WorkStationController workStationController;
    private TabPane tabPane;
    private TextField plugboardTextfield;
    private ComboBox<String> reflectorComboBox;
    private Spinner<Integer> ringSpinnerI;
    private Spinner<Integer> ringSpinnerII;
    private Spinner<Integer> ringSpinnerIII;
    private Spinner<String> rotorSpinnerI;
    private Spinner<String> rotorSpinnerII;
    private Spinner<String> rotorSpinnerIII;
    private TextField keyTextfield;
    private TableView<List<String>> traceTable;
    private ObservableList<List<String>> traceData = FXCollections.observableArrayList();

    public EnigmaForm(WorkStationController workStationController, TabPane tabPane) {
        super(workStationController, tabPane, "enigma-form-description.txt", new String[]{"Enigma"});
        this.workStationController = workStationController;
        this.tabPane = tabPane;
        setTitle("Enigma Form");
    }

    @Override
    protected void initDimensions() {
        setResizable(true);
        getDialogPane().setPrefSize(800, 800);
        Platform.runLater(() -> setResizable(false));

    }

    @Override
    protected void initOptions() {
        Label plugboardHeaderLabel = new LabelBuilder.Builder()
                .setText("Plugboard")
                .setFontSize(16)
                .setAlignment(Pos.CENTER)
                .setTextAlignment(TextAlignment.CENTER)
                .setBold(true)
                .setMaxWidth(Double.MAX_VALUE)
                .build();
        plugboardTextfield = new TextField();
        plugboardTextfield.setPromptText("Type out your plugboard pairs like this: XX XX XX");
        plugboardTextfield.setFocusTraversable(false);
        Label reflectorHeaderLabel = new LabelBuilder.Builder()
                .setText("Reflector")
                .setFontSize(16)
                .setAlignment(Pos.CENTER)
                .setTextAlignment(TextAlignment.CENTER)
                .setBold(true)
                .setMaxWidth(Double.MAX_VALUE)
                .build();
        reflectorComboBox = new ComboBox<>();
        reflectorComboBox.getItems().setAll("Reflector A", "Reflector B", "Reflector C");
        reflectorComboBox.setValue("Reflector A");
        Label ringHeaderLabel = new LabelBuilder.Builder()
                .setText("Rings")
                .setFontSize(16)
                .setAlignment(Pos.CENTER)
                .setTextAlignment(TextAlignment.CENTER)
                .setBold(true)
                .setMaxWidth(Double.MAX_VALUE)
                .build();
        ringSpinnerI = new Spinner<>(1, 26, 1);
        ringSpinnerII = new Spinner<>(1, 26, 1);
        ringSpinnerIII = new Spinner<>(1, 26, 1);
        HBox ringSpinnerRow = new HBox(10, ringSpinnerI, ringSpinnerII, ringSpinnerIII);
        ringSpinnerRow.setAlignment(Pos.CENTER);
        HBox.setHgrow(ringSpinnerRow, Priority.ALWAYS);
        Label rotorHeaderLabel = new LabelBuilder.Builder()
                .setText("Rotors")
                .setFontSize(16)
                .setAlignment(Pos.CENTER)
                .setTextAlignment(TextAlignment.CENTER)
                .setBold(true)
                .setMaxWidth(Double.MAX_VALUE)
                .build();
        ObservableList<String> rotorOptions = FXCollections.observableArrayList("I", "II", "III", "IV", "V");
        rotorSpinnerI = new Spinner<>(rotorOptions);
        rotorSpinnerII = new Spinner<>(rotorOptions);
        rotorSpinnerIII = new Spinner<>(rotorOptions);
        AtomicInteger i = new AtomicInteger(0);
        Stream.of(rotorSpinnerI, rotorSpinnerII, rotorSpinnerIII).forEach(rotor -> {
            int index = i.getAndIncrement();
            if (index == 0) {
                rotor.getValueFactory().setValue("I");
            } else if (index == 1) {
                rotor.getValueFactory().setValue("II");
            } else {
                rotor.getValueFactory().setValue("III");
            }

        });

        HBox rotorOptionsRow = new HBox(10, rotorSpinnerI, rotorSpinnerII, rotorSpinnerIII);
        rotorOptionsRow.setAlignment(Pos.CENTER);
        HBox.setHgrow(rotorOptionsRow, Priority.ALWAYS);
        Label keyHeaderLabel = new LabelBuilder.Builder()
                .setText("Key")
                .setFontSize(16)
                .setAlignment(Pos.CENTER)
                .setTextAlignment(TextAlignment.CENTER)
                .setBold(true)
                .setMaxWidth(Double.MAX_VALUE)
                .build();
        keyTextfield = new TextField();
        keyTextfield.setPromptText("Type your key here, and make sure the key length is 3! (It's completely optional to type a key)");
        keyTextfield.setFocusTraversable(false);
        traceTable = new TableView<>(traceData);
        traceTable.setMaxHeight(220);
        traceTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_FLEX_LAST_COLUMN);
        String[] headers = {"Keyboard", "Plugboard", "Rotor III", "Rotor II", "Rotor I", "Reflector",
                "Rotor I B", "Rotor II B", "Rotor III B", "Plugboard", "Lampboard"};
        for (int colIndex = 0; colIndex < headers.length; colIndex++) {
            final int finalIdx = colIndex;
            TableColumn<List<String>, String> column = new TableColumn<>(headers[colIndex]);
            column.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().get(finalIdx)));
            traceTable.getColumns().add(column);
        }
        CipherFormPane cipherFormPane = new CipherFormPane();
        cipherFormPane.addCenteredContent(plugboardHeaderLabel);
        cipherFormPane.addCenteredContent(plugboardTextfield);
        cipherFormPane.addCenteredContent(reflectorHeaderLabel);
        cipherFormPane.addCenteredContent(reflectorComboBox);
        cipherFormPane.addCenteredContent(ringHeaderLabel);
        cipherFormPane.addCenteredContent(ringSpinnerRow);
        cipherFormPane.addCenteredContent(rotorHeaderLabel);
        cipherFormPane.addCenteredContent(rotorOptionsRow);
        cipherFormPane.addCenteredContent(keyHeaderLabel);
        cipherFormPane.addCenteredContent(keyTextfield);
        cipherFormPane.addCenteredContent(traceTable);
        cipherFormPane.addBottomContent(getActionButtonsRow());
        getDialogPane().setContent(cipherFormPane);
    }

    @Override
    protected void initListeners() {
        getEncryptButton().setOnAction((_) -> {
            if (isKeyAppropriate() && isPlugboardAppropriate()) {
                encryptDecrypt(true);
            }
        });

        getDecryptButton().setOnAction((_) -> {
            if (isKeyAppropriate() && isPlugboardAppropriate()) {
                encryptDecrypt(false);
            }
        });

        traceTable.widthProperty().addListener((_, _, _) -> {
            TableHeaderRow header = (TableHeaderRow) traceTable.lookup("TableHeaderRow");
            header.reorderingProperty().addListener((_, _, _) -> header.setReordering(false));
        });
    }

    private boolean isKeyAppropriate() {
        String key = keyTextfield.getText();
        if (key.isEmpty()) {
            return true;
        }
        if (!key.matches("([^A-Z])") && key.length() != 3) {
            Alerts.ENIGMA_KEY_ERROR.show();
            return false;
        }

        return true;
    }

    private boolean isPlugboardAppropriate() {
        String plugboardText = plugboardTextfield.getText();
        String regex = "^[A-Z]{2} [A-Z]{2} [A-Z]{2}$";

        if (!plugboardText.matches(regex)) {
            Alerts.ENIGMA_PLUGBOARD_ERROR.show();
            return false;
        }

        return true;
    }

    private Reflector chooseReflector(String reflector) {
        switch (reflector) {
            case "Reflector A" -> {
                return new ReflectorA();
            }

            case "Reflector B" -> {
                return new ReflectorB();
            }

            case "Reflector C" -> {
                return new ReflectorC();
            }
        }

        return null;
    }

    private Rotor chooseRotor(String rotor) {
        switch (rotor) {
            case "I" -> {
                return new RotorI();
            }
            case "II" -> {
                return new RotorII();
            }

            case "III" -> {
                return new RotorIII();
            }

            case "IV" -> {
                return new RotorIV();
            }

            case "V" -> {
                return new RotorV();
            }
        }
        return null;
    }

    private void encryptDecrypt(boolean encrypt) {
        Reflector reflector = chooseReflector(reflectorComboBox.getValue());
        String[] plugboardPairs = plugboardTextfield.getText().split(" ");
        List<String> plugboardPairList = new ArrayList<>(Arrays.asList(plugboardPairs));
        Plugboard plugboard = new Plugboard(plugboardPairList);
        Keyboard keyboard = new Keyboard();
        List<Rotor> rotors = new ArrayList<>();
        rotors.add(chooseRotor(rotorSpinnerI.getValue()));
        rotors.add(chooseRotor(rotorSpinnerII.getValue()));
        rotors.add(chooseRotor(rotorSpinnerIII.getValue()));
        enigma = CipherRegistry.getEnigma(reflector, plugboard, keyboard, rotors);
        String currentText = getWorkStationController().getActiveTextArea().getText().toUpperCase();
        String key = keyTextfield.getText().toUpperCase();
        int[] rings = new int[]{ringSpinnerI.getValue(), ringSpinnerII.getValue(), ringSpinnerIII.getValue()};
        String resultingText;
        if (encrypt) {
            resultingText = enigma.encrypt(currentText, rings, key);
        } else {
            resultingText = enigma.decrypt(currentText, rings, key);
        }
        CipherFormActions.initEncryptDecryptAction(resultingText, encrypt ? "Enigma Encrypted Result" : "Enigma Decrypted Result", workStationController, tabPane);
        runTableAnimation(currentText, rings, key);
    }

    private void runTableAnimation(String text, int[] rings, String key) {
        Reflector animReflector = chooseReflector(reflectorComboBox.getValue());
        String[] plugboardPairs = plugboardTextfield.getText().split(" ");
        Plugboard animPlugboard = new Plugboard(new ArrayList<>(Arrays.asList(plugboardPairs)));
        Keyboard animKeyboard = new Keyboard();
        List<Rotor> animRotors = Arrays.asList(chooseRotor(rotorSpinnerI.getValue()), chooseRotor(rotorSpinnerII.getValue()), chooseRotor(rotorSpinnerIII.getValue()));
        Enigma animationEnigma = CipherRegistry.getEnigma(animReflector, animPlugboard, animKeyboard, animRotors);
        animationEnigma.setRings(new Rings(rings));
        if (!key.isEmpty()) {
            animationEnigma.setKey(key);
        }
        traceData.clear();
        AtomicInteger charIndex = new AtomicInteger(0);
        Timeline animationTimeline = new Timeline();
        KeyFrame stepFrame = new KeyFrame(Duration.millis(250), (_) -> {
            if (charIndex.get() < text.length()) {
                char nextChar = text.charAt(charIndex.getAndIncrement());
                List<String> rowTrace = new ArrayList<>();
                animationEnigma.encipherWithTrace(nextChar, rowTrace);
                traceData.add(rowTrace);
                traceTable.scrollTo(rowTrace);
            } else {
                animationTimeline.stop();
            }
        });

        animationTimeline.getKeyFrames().add(stepFrame);
        animationTimeline.setCycleCount(text.length() + 1);
        animationTimeline.play();
    }
}
