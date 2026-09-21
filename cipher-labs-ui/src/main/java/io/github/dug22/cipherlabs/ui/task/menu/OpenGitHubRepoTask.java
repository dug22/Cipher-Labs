package io.github.dug22.cipherlabs.ui.task.menu;

import io.github.dug22.cipherlabs.ui.task.CipherLabsRunnable;
import java.awt.*;
import java.net.URI;

public class OpenGitHubRepoTask implements CipherLabsRunnable {


    public OpenGitHubRepoTask() {
    }

    @Override
    public void run() {
        try {
            if (isDesktopSupported()) {
                openGitHubRepository();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private boolean isDesktopSupported() {
        return Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.BROWSE);
    }

    private void openGitHubRepository() throws Exception {
        String url = "https://github.com/dug22/Cipher-Labs";
        Desktop.getDesktop().browse(new URI(url));
    }
}
