package io.github.dug22.cipherlabs.ui.visuals.impl;

import io.github.dug22.cipherlabs.ciphers.algorithm.classic.steganography.BaconianCipher;
import io.github.dug22.cipherlabs.ciphers.steps.BaconCipherStep;
import io.github.dug22.cipherlabs.ui.animation.AnimationManager;
import io.github.dug22.cipherlabs.ui.builder.LabelBuilder;
import io.github.dug22.cipherlabs.ui.task.other.ResizeFormTask;
import io.github.dug22.cipherlabs.ui.utils.VisualUtils;
import io.github.dug22.cipherlabs.ui.visuals.CipherVisual;
import io.github.dug22.cipherlabs.utils.Alphabets;
import javafx.animation.KeyFrame;
import javafx.animation.PauseTransition;
import javafx.animation.Timeline;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Stream;

import static io.github.dug22.cipherlabs.ui.utils.VisualUtils.centerTextIntoRectangle;

public class BaconianCipherVisual extends CipherVisual {

    private final Dialog<String> form;
    private final Pane visualPane;
    private final BaconianCipher baconianCipher;
    private final Map<String, Label> alphabetLabelMap;
    private final Map<String, Label> baconsCodeLabelMap;
    private Timeline animationTimeline;

    public BaconianCipherVisual(Dialog<String> form, Pane visualPane, BaconianCipher baconianCipher) {
        super(visualPane);
        this.form = form;
        this.visualPane = visualPane;
        this.baconianCipher = baconianCipher;
        this.alphabetLabelMap = new ConcurrentHashMap<>();
        this.baconsCodeLabelMap = new ConcurrentHashMap<>();
        animationTimeline = new Timeline();
    }

    public void play(boolean encrypt) {
        buildVisualLayout(encrypt);
        startAnimation(animationTimeline, baconianCipher.getSteps().size());
    }

    private void buildVisualLayout(boolean encrypt) {
        visualPane.getChildren().clear();
        HBox visualHBox = new HBox(10);
        Rectangle alphabetRectangle = VisualUtils.createRectangle(-1, -1, 85, 225);
        Rectangle baconianCodeRectangle = VisualUtils.createRectangle(-1, -1, 85, 225);
        Stream.of(alphabetRectangle, baconianCodeRectangle).forEach(rectangle -> {
            rectangle.setArcHeight(15);
            rectangle.setArcWidth(15);
        });
        visualHBox.setAlignment(Pos.CENTER);
        if (encrypt) {
            visualHBox.getChildren().addAll(alphabetRectangle, baconianCodeRectangle);
        } else {
            visualHBox.getChildren().addAll(baconianCodeRectangle, alphabetRectangle);
        }
        visualPane.getChildren().add(visualHBox);
        visualHBox.layoutXProperty().bind(visualPane.widthProperty().subtract(visualHBox.widthProperty()).divide(2));
        visualHBox.layoutYProperty().bind(visualPane.heightProperty().subtract(visualHBox.heightProperty()).divide(2));

        VBox letterContainer = new VBox(-1.6);
        letterContainer.setAlignment(Pos.CENTER);
        letterContainer.setPrefWidth(85);
        letterContainer.setPrefHeight(225);
        for (int i = 0; i < 26; i++) {
            Label letterLabel = new LabelBuilder.Builder()
                    .setText(String.valueOf(Alphabets.ALPHABET_CHAR_ARRAY[i]))
                    .setBold(true)
                    .setFontSize(8)
                    .setAlignment(Pos.CENTER)
                    .build();
            alphabetLabelMap.put(letterLabel.getText(), letterLabel);
            letterContainer.getChildren().add(letterLabel);
        }

        VBox baconCodeContainer = new VBox(-1.6);
        baconCodeContainer.setAlignment(Pos.CENTER);
        baconCodeContainer.setPrefWidth(85);
        baconCodeContainer.setPrefHeight(225);
        for (int i = 0; i < 26; i++) {
            char letterFromAlphabet = Alphabets.ALPHABET_CHAR_ARRAY[i];
            LabelBuilder.Builder baconCodeLabelBuilder = new LabelBuilder.Builder()
                    .setBold(true)
                    .setFontSize(8);
            Map<String, String> encryptionMap = baconianCipher.getAppropriateEncryptionMap();
            baconCodeLabelBuilder.setText(encryptionMap.get(String.valueOf(letterFromAlphabet)));
            Label baconCodeLabel = baconCodeLabelBuilder.build();
            baconsCodeLabelMap.put(baconCodeLabel.getText(), baconCodeLabel);
            baconCodeLabel.setAlignment(Pos.CENTER);
            baconCodeContainer.getChildren().add(baconCodeLabel);
        }

        centerTextIntoRectangle(letterContainer, visualHBox, alphabetRectangle);
        centerTextIntoRectangle(baconCodeContainer, visualHBox, baconianCodeRectangle);
        if (encrypt) {
            visualPane.getChildren().addAll(letterContainer, baconCodeContainer);
        } else {
            visualPane.getChildren().addAll(baconCodeContainer, letterContainer);
        }
    }

    @Override
    protected KeyFrame createKeyFrame() {
        return new KeyFrame(Duration.seconds(2), (_) -> {
            if (baconianCipher.getSteps().isEmpty()) {
                visualPane.getChildren().clear();
            }
            BaconCipherStep step = baconianCipher.getSteps().removeFirst();
            String letter = step.letter();
            String code = step.code();
            Label letterLabel = alphabetLabelMap.get(letter);
            Label baconCodeLabel = baconsCodeLabelMap.get(code);
            Stream.of(letterLabel, baconCodeLabel).forEach(label -> {
                label.setBackground(
                        new Background(new BackgroundFill(
                                Color.YELLOW,
                                new CornerRadii(1),
                                new Insets(0)
                        )));
            });
            PauseTransition removeHighlightPauseTransition = new PauseTransition(Duration.seconds(2));
            removeHighlightPauseTransition.setOnFinished((_) -> {
                letterLabel.setBackground(null);
                baconCodeLabel.setBackground(null);
            });
            AnimationManager.addAnimation(removeHighlightPauseTransition);
            removeHighlightPauseTransition.play();
        });

    }

    @Override
    protected Runnable getPostClearAction() {
        return () -> new ResizeFormTask(form, 400).run();
    }
}