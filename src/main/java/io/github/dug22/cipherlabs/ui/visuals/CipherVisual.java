package io.github.dug22.cipherlabs.ui.visuals;

import io.github.dug22.cipherlabs.ui.animation.AnimationManager;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

public abstract class CipherVisual {

    private final Pane visualPane;

    public CipherVisual(Pane visualPane) {
        this.visualPane = visualPane;
    }

    protected void startAnimation(Timeline animationTimeline, int stepSize) {
        AnimationManager.addAnimation(animationTimeline);
        KeyFrame keyFrame = createKeyFrame();
        animationTimeline.getKeyFrames().add(keyFrame);
        animationTimeline.setCycleCount(stepSize);
        animationTimeline.setOnFinished((_) -> {
            clearAfterDelay(2);
        });
        animationTimeline.play();
    }

    public void clear() {
        clearAfterDelay(0);
    }

    protected abstract KeyFrame createKeyFrame();

    protected void clearAfterDelay(int delay) {
        Timeline clearAnimationTimeline = new Timeline(new KeyFrame(Duration.seconds(delay), (_) -> {
            visualPane.getChildren().clear();
            getPostClearAction().run();
        }));
        AnimationManager.addAnimation(clearAnimationTimeline);
        clearAnimationTimeline.play();
    }

    protected Runnable getPostClearAction() {
        return () -> {};
    }
}