package io.github.dug22.cipherlabs.ui.task.other;

import io.github.dug22.cipherlabs.ui.task.CipherLabsRunnable;
import io.github.dug22.cipherlabs.ui.utils.FormUtils;
import javafx.scene.control.Dialog;

public class ResizeFormListener implements CipherLabsRunnable {

    private final Dialog<String> form;
    private final int height;

    public ResizeFormListener(Dialog<String> form, int height) {
        this.form = form;
        this.height = height;
    }

    @Override
    public void run() {
        FormUtils.resize(form, height);
    }
}
