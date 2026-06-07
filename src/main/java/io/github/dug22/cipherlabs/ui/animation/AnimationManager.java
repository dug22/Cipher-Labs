package io.github.dug22.cipherlabs.ui.animation;

import javafx.animation.Animation;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class AnimationManager {

    private static final List<Animation> animations = new CopyOnWriteArrayList<>();

    private AnimationManager() {

    }

    public static void clear() {
        animations.clear();
    }

    public static void addAnimation(Animation animation) {
        animations.add(animation);
    }

    public static void terminate() {
        if (animations.isEmpty()) return;
        for (Animation animation : animations) {
            if (animation != null) {
                animation.stop();
            }
        }

        clear();
    }
}
