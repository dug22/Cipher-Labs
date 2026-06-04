package io.github.dug22.cipherlabs.ui.task.general;

import io.github.dug22.cipherlabs.ui.task.CipherLabsRunnable;
import io.github.dug22.cipherlabs.ui.utils.SceneManger;
import io.github.dug22.cipherlabs.ui.utils.StageProperties;

public class SceneLoaderTask implements CipherLabsRunnable {

    private final String fxmlFile;
    private final StageProperties stageProperties;

    public SceneLoaderTask(String fxmlFile, StageProperties stageProperties) {
        this.fxmlFile = fxmlFile;
        this.stageProperties = stageProperties;
    }

    @Override
    public void run() {
        SceneManger.show(fxmlFile, stageProperties);
    }
}
