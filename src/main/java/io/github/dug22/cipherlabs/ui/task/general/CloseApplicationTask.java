package io.github.dug22.cipherlabs.ui.task.general;

import io.github.dug22.cipherlabs.CipherLabsCore;
import io.github.dug22.cipherlabs.ui.task.CipherLabsRunnable;

public class CloseApplicationTask implements CipherLabsRunnable {

    @Override
    public void run() {
        CipherLabsCore.getInstance().onDisable();
    }
}
