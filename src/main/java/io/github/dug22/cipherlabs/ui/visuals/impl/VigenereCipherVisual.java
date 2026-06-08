package io.github.dug22.cipherlabs.ui.visuals.impl;

import io.github.dug22.cipherlabs.ciphers.steps.VigenereCipherStep;
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

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Stream;

import static io.github.dug22.cipherlabs.ui.utils.VisualUtils.centerTextIntoRectangle;

public class VigenereCipherVisual extends CipherVisual {

    private final Dialog<String> form;
    private final Pane visualPane;
    private final List<VigenereCipherStep> steps;
    private final Timeline animationTimeline;
    private final Map<String, Label[]> alphabetLabelMap;

    public VigenereCipherVisual(Dialog<String> form, Pane visualPane, List<VigenereCipherStep> steps) {
        this.form = form;
        this.visualPane = visualPane;
        this.steps = steps;
        this.animationTimeline = new Timeline();
        this.alphabetLabelMap = new ConcurrentHashMap<>();
    }

    public void play() {
        buildVisualLayout();
        startAnimation(animationTimeline, steps.size());
    }

    public void clear() {
        clearAfterDelay(0);
    }

    public void buildVisualLayout() {
        visualPane.getChildren().clear();
        HBox visualHBox = new HBox(5);
        Rectangle messageAlphabetRectangle = VisualUtils.createRectangle(-1, -1, 85, 225);
        Rectangle keyAlphabetRectangle = VisualUtils.createRectangle(-1, -1, 85, 225);
        Rectangle resultAlphabetRectangle = VisualUtils.createRectangle(-1, -1, 85, 225);
        Stream.of(messageAlphabetRectangle, keyAlphabetRectangle, resultAlphabetRectangle).forEach(rectangle -> {
            rectangle.setArcHeight(15);
            rectangle.setArcWidth(15);
        });
        visualHBox.setAlignment(Pos.CENTER);
        visualHBox.getChildren().addAll(messageAlphabetRectangle, keyAlphabetRectangle, resultAlphabetRectangle);
        visualPane.getChildren().add(visualHBox);
        visualHBox.layoutXProperty().bind(visualPane.widthProperty().subtract(visualHBox.widthProperty()).divide(2));
        visualHBox.layoutYProperty().bind(visualPane.heightProperty().subtract(visualHBox.heightProperty()).divide(2));
        List<VBox> containers = Arrays.asList(new VBox(-1.6), new VBox(-1.6), new VBox(-1.6));
        containers.forEach(container -> {
            container.setAlignment(Pos.CENTER);
            container.setPrefWidth(100);
            container.setPrefHeight(225);
        });

        String[] headers = new String[]{"From Alphabet", "Key Alphabet", "To Alphabet"};
        for (int i = 0; i < containers.size(); i++) {
            Label headerLabel = new LabelBuilder.Builder()
                    .setFontSize(8)
                    .setText(headers[i])
                    .setBold(true)
                    .build();
            VBox container = containers.get(i);
            container.getChildren().add(headerLabel);
        }

        for (int i = 0; i < Alphabets.ALPHABET.length(); i++) {
            String letter = String.valueOf(Alphabets.ALPHABET_CHAR_ARRAY[i]);
            Label fromLetterLabel = new LabelBuilder.Builder().setText(letter).setBold(true).setFontSize(8).setAlignment(Pos.CENTER).build();
            Label keyLetterLabel = new LabelBuilder.Builder().setText(letter).setBold(true).setFontSize(8).setAlignment(Pos.CENTER).build();
            Label toLetterLabel = new LabelBuilder.Builder().setText(letter).setBold(true).setFontSize(8).setAlignment(Pos.CENTER).build();
            alphabetLabelMap.put(letter, new Label[]{fromLetterLabel, keyLetterLabel, toLetterLabel});
            containers.getFirst().getChildren().add(fromLetterLabel);
            containers.get(1).getChildren().add(keyLetterLabel);
            containers.getLast().getChildren().add(toLetterLabel);
        }

        centerTextIntoRectangle(containers.getFirst(), visualHBox, messageAlphabetRectangle);
        centerTextIntoRectangle(containers.get(1), visualHBox, keyAlphabetRectangle);
        centerTextIntoRectangle(containers.getLast(), visualHBox, resultAlphabetRectangle);
        visualPane.getChildren().addAll(containers);
    }

    @Override
    protected KeyFrame createKeyFrame() {
        return new KeyFrame(Duration.seconds(2), (_) -> {
            if (steps.isEmpty()) {
                visualPane.getChildren().clear();
            }

            VigenereCipherStep step = steps.removeFirst();
            String from = String.valueOf(step.fromChar());
            String key = String.valueOf(step.keyChar());
            String to = String.valueOf(step.toChar());
            Label fromLabel = alphabetLabelMap.get(from)[0];
            Label keyLabel = alphabetLabelMap.get(key)[1];
            Label toLabel = alphabetLabelMap.get(to)[2];
            Stream.of(fromLabel, keyLabel, toLabel).forEach(label -> {
                label.setBackground(new Background(new BackgroundFill(
                        Color.YELLOW,
                        new CornerRadii(1),
                        new Insets(0)
                )));
            });
            PauseTransition removeHighlightPauseTransition = new PauseTransition(Duration.seconds(2));
            removeHighlightPauseTransition.setOnFinished((_) -> {
                Stream.of(fromLabel, keyLabel, toLabel).forEach(label -> {
                    label.setBackground(null);
                });
            });
            AnimationManager.addAnimation(removeHighlightPauseTransition);
            removeHighlightPauseTransition.play();
        });
    }

    @Override
    protected void clearAfterDelay(int delay) {
        Timeline clearAnimationTimeline = new Timeline(new KeyFrame(Duration.seconds(delay), (_) -> {
            visualPane.getChildren().clear();
            new ResizeFormTask(form, 430).run();
        }));
        AnimationManager.addAnimation(clearAnimationTimeline);
        clearAnimationTimeline.play();
    }
}
