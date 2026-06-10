package io.github.dug22.cipherlabs.ui.visuals.impl;

import io.github.dug22.cipherlabs.ciphers.steps.PlayFairCipherStep;
import io.github.dug22.cipherlabs.ui.builder.LabelBuilder;
import io.github.dug22.cipherlabs.ui.forms.cipher.impl.PlayfairCipherForm;
import io.github.dug22.cipherlabs.ui.task.other.ResizeFormTask;
import io.github.dug22.cipherlabs.ui.visuals.CipherVisual;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.util.Duration;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class PlayfairCipherVisual extends CipherVisual {

    private final Dialog<String> form;
    private final Map<String, Label> letterLabelMap;
    private final GridPane visualPane;
    private final char[][] polybiusSquare;
    private final List<PlayFairCipherStep> steps;
    private final Timeline animationTimeline;

    public PlayfairCipherVisual(PlayfairCipherForm form, GridPane visualPane, char[][] polybiusSquare, List<PlayFairCipherStep> steps) {
        super(visualPane);
        this.form = form;
        this.visualPane = visualPane;
        this.polybiusSquare = polybiusSquare;
        this.steps = steps;
        letterLabelMap = new ConcurrentHashMap<>();
        this.animationTimeline = new Timeline();
    }

    public void play() {
        buildVisualLayout();
        startAnimation(animationTimeline, steps.size());
    }

    private void buildVisualLayout() {
        visualPane.getChildren().clear();
        Border cellBorder = new Border(new BorderStroke(
                Color.BLACK,
                BorderStrokeStyle.SOLID,
                CornerRadii.EMPTY,
                new BorderWidths(1)
        ));
        for (int row = 0; row < 5; row++) {
            for (int col = 0; col < 5; col++) {
                char letter = polybiusSquare[row][col];
                Label letterLabel = new LabelBuilder.Builder()
                        .setText(String.valueOf(letter))
                        .setAlignment(Pos.CENTER)
                        .build();
                visualPane.add(letterLabel, col, row);
                letterLabelMap.put(letterLabel.getText(), letterLabel);
                letterLabel.setMinSize(30, 30);
                letterLabel.setBorder(cellBorder);
            }
        }

        visualPane.setAlignment(Pos.CENTER);
    }

    @Override
    protected KeyFrame createKeyFrame() {
        return new KeyFrame(Duration.seconds(2), (_) -> {
            if (steps.isEmpty()) {
                visualPane.getChildren().clear();
            }
            letterLabelMap.values().forEach(label -> label.setBackground(null));
            PlayFairCipherStep step = steps.removeFirst();
            String fromFirstLetter = String.valueOf(step.fromFirstLetter());
            String toFirstLetter = String.valueOf(step.toFirstLetter());
            String fromSecondLetter = String.valueOf(step.fromSecondLetter());
            String toSecondLetter = String.valueOf(step.toSecondLetter());
            Label fromFirstLetterLabel = letterLabelMap.get(fromFirstLetter);
            Label toFirstLetterLabel = letterLabelMap.get(toFirstLetter);
            Label fromSecondLetterLabel = letterLabelMap.get(fromSecondLetter);
            Label toSecondLetterLabel = letterLabelMap.get(toSecondLetter);
            Background fromBackground = new Background(new BackgroundFill(Color.RED, new CornerRadii(1), Insets.EMPTY));
            Background toBackground = new Background(new BackgroundFill(Color.LIME, new CornerRadii(1), Insets.EMPTY));
            Background overlapBackround = new Background(new BackgroundFill(Color.ORANGE, new CornerRadii(1), Insets.EMPTY));
            if (fromFirstLetterLabel != null) fromFirstLetterLabel.setBackground(fromBackground);
            if (fromSecondLetterLabel != null) fromSecondLetterLabel.setBackground(fromBackground);
            if (toFirstLetterLabel != null) {
                toFirstLetterLabel.setBackground(toFirstLetterLabel.getBackground() == fromBackground ? overlapBackround : toBackground);
            }
            if (toSecondLetterLabel != null) {
                toSecondLetterLabel.setBackground(toSecondLetterLabel.getBackground() == fromBackground ? overlapBackround : toBackground);
            }
        });
    }

    @Override
    protected Runnable getPostClearAction() {
        return () -> new ResizeFormTask(form, 490).run();
    }
}
