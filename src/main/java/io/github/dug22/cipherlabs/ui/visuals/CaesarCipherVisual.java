package io.github.dug22.cipherlabs.ui.visuals;

import io.github.dug22.cipherlabs.ciphers.steps.CaesarCipherStep;
import io.github.dug22.cipherlabs.ui.animation.AnimationManager;
import io.github.dug22.cipherlabs.ui.builder.LabelBuilder;
import io.github.dug22.cipherlabs.ui.utils.VisualUtils;
import javafx.animation.KeyFrame;
import javafx.animation.PauseTransition;
import javafx.animation.Timeline;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

import java.util.List;
import java.util.Map;

public class CaesarCipherVisual {

    private final List<CaesarCipherStep> steps;
    private final Pane visualPane;
    private final Label fromCharLabel = new LabelBuilder.Builder()
            .setText("")
            .setAlignment(Pos.CENTER)
            .build();
    private final Label shiftLabel = new LabelBuilder.Builder()
            .setText("")
            .setAlignment(Pos.CENTER)
            .build();
    private final Label toCharLabel = new LabelBuilder.Builder()
            .setText("")
            .setAlignment(Pos.CENTER)
            .build();
    private final TextField fromAlphabetTextField;
    private final TextField toAlphabetTextField;
    private Timeline animationTimeline;
    private Timeline clearAnimationTimeline;
    private PauseTransition toAlphabetHighlightPauseTransition;
    private PauseTransition clearFocusPauseTransition;

    public CaesarCipherVisual(Pane visualPane, List<CaesarCipherStep> steps, TextField fromAlphabetTextField, TextField toAlphabetTextField) {
        this.visualPane = visualPane;
        this.steps = steps;
        this.fromAlphabetTextField = fromAlphabetTextField;
        this.toAlphabetTextField = toAlphabetTextField;
    }

    public void play(boolean encrypt) {
        buildVisualLayout(encrypt);
        startAnimation();
    }

    public void clear() {
        clearAfterDelay(0);
    }

    private void buildVisualLayout(boolean encrypt) {
        visualPane.getChildren().clear();
        Rectangle fromRectangle = createRectangle(150, 10);
        Label operatorLabel = new LabelBuilder.Builder()
                .setText(encrypt ? "+" : "-")
                .setFontSize(25)
                .setLayoutX(225)
                .setLayoutY(15)
                .build();
        Rectangle shiftRectangle = createRectangle(250, 10);
        Label equalsLabel = new LabelBuilder.Builder()
                .setText("=")
                .setFontSize(25)
                .setLayoutX(325)
                .setLayoutY(15)
                .build();
        Rectangle toRectangle = createRectangle(350, 10);
        visualPane.getChildren().addAll(fromRectangle, fromCharLabel, operatorLabel, shiftRectangle, shiftLabel, equalsLabel, toRectangle, toCharLabel);
        Map<Rectangle, Label> rectangleLabelMap = Map.of(fromRectangle, fromCharLabel, shiftRectangle, shiftLabel, toRectangle, toCharLabel);
        rectangleLabelMap.forEach(VisualUtils::centerTextInRectangle);
    }

    private void startAnimation() {
        animationTimeline = new Timeline();
        AnimationManager.addAnimation(animationTimeline);
        KeyFrame keyFrame = createKeyFrame();
        animationTimeline.getKeyFrames().add(keyFrame);
        animationTimeline.setCycleCount(steps.size());
        animationTimeline.setOnFinished((_) -> {
            clearAfterDelay(2);
        });
        animationTimeline.play();
    }

    private KeyFrame createKeyFrame() {
        return new KeyFrame(Duration.seconds(2), _ -> {
            if (steps.isEmpty()) {
                visualPane.getChildren().clear();
                return;
            }
            CaesarCipherStep step = steps.removeFirst();
            fromCharLabel.setText(String.valueOf(step.getFromCharacter()));
            shiftLabel.setText(String.valueOf(step.getShift()));
            toCharLabel.setText(String.valueOf(step.getToCharacter()));
            int fromIndex = fromAlphabetTextField.getText().indexOf(step.getFromCharacter());
            int toIndex = toAlphabetTextField.getText().indexOf(step.getToCharacter());
            fromAlphabetTextField.requestFocus();
            fromAlphabetTextField.selectRange(fromIndex, fromIndex + 1);
            toAlphabetHighlightPauseTransition = new PauseTransition(javafx.util.Duration.seconds(1));
            AnimationManager.addAnimation(toAlphabetHighlightPauseTransition);
            toAlphabetHighlightPauseTransition.setOnFinished(_ -> {
                toAlphabetTextField.requestFocus();
                toAlphabetTextField.selectRange(toIndex, toIndex + 1);
                clearFocusPauseTransition = new PauseTransition(Duration.seconds(0.8));
                AnimationManager.addAnimation(clearFocusPauseTransition);
                clearFocusPauseTransition.setOnFinished((_) -> {
                    fromAlphabetTextField.deselect();
                    toAlphabetTextField.deselect();
                    visualPane.requestFocus();
                });
                clearFocusPauseTransition.play();
            });
            toAlphabetHighlightPauseTransition.play();
        });
    }

    private void clearAfterDelay(int delay) {
        clearAnimationTimeline = new Timeline(new KeyFrame(Duration.seconds(delay), _ -> visualPane.getChildren().clear()));
        AnimationManager.addAnimation(clearAnimationTimeline);
        clearAnimationTimeline.play();
    }

    private Rectangle createRectangle(double x, double y) {
        return VisualUtils.createRectangle(x, y, 50, 50);
    }
}
