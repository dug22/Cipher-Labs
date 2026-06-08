package io.github.dug22.cipherlabs.ui.visuals;

import io.github.dug22.cipherlabs.ui.animation.AnimationManager;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;

public abstract class CipherVisual {

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

    protected abstract KeyFrame createKeyFrame();

    protected abstract void clearAfterDelay(int delay);
}
