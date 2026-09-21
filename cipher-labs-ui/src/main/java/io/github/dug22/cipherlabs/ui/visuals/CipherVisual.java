package io.github.dug22.cipherlabs.ui.visuals;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.scene.control.Dialog;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

public abstract class CipherVisual {

    private final Pane visualPane;

    public CipherVisual(Pane visualPane) {
        this.visualPane = visualPane;
    }

    protected void startAnimation(Dialog<?> form, Timeline animationTimeline, int stepSize) {
        VisualAnimationManager.addAnimation(animationTimeline);
        KeyFrame keyFrame = createKeyFrame();
        animationTimeline.getKeyFrames().add(keyFrame);
        animationTimeline.setCycleCount(stepSize);
        animationTimeline.setOnFinished((_) -> {
            clearAfterDelay(form, 2);
        });
        animationTimeline.play();
    }

    public void clear(Dialog<?> form) {
        clearAfterDelay(form, 0);
    }

    protected abstract KeyFrame createKeyFrame();

    protected void clearAfterDelay(Dialog<?> form, int delay) {
        Timeline clearAnimationTimeline = new Timeline(new KeyFrame(Duration.seconds(delay), (_) -> {
            visualPane.getChildren().clear();
            getPostClearAction(form).run();
        }));
        VisualAnimationManager.addAnimation(clearAnimationTimeline);
        clearAnimationTimeline.play();
    }

    protected Runnable getPostClearAction(Dialog<?> form) {
        return () -> {
            visualPane.setPrefHeight(0);
            Platform.runLater(() -> {
                if (form != null && form.getDialogPane().getScene() != null) {
                    form.getDialogPane().getScene().getWindow().sizeToScene();
                }
            });
        };
    }
}