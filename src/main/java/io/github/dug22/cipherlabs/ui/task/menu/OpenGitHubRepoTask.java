package io.github.dug22.cipherlabs.ui.task.menu;

import io.github.dug22.cipherlabs.CipherLabsApplication;
import io.github.dug22.cipherlabs.ui.task.CipherLabsRunnable;

public class OpenGitHubRepoTask implements CipherLabsRunnable {


    public OpenGitHubRepoTask() {
    }

    @Override
    public void run() {
        try {
            String url = "https://github.com/dug22/Cipher-Labs";
            CipherLabsApplication.getInstance().getHostServices().showDocument(url);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
