package io.github.dug22.cipherlabs;

import io.github.dug22.cipherlabs.ui.utils.SceneManger;
import io.github.dug22.cipherlabs.ui.utils.StageProperties;
import javafx.application.Application;
import javafx.stage.Stage;

public class CipherLabsApplication extends Application {

    private static CipherLabsApplication instance;

    public CipherLabsApplication() {
        instance = this;
    }

    public static CipherLabsApplication getInstance() {
        return instance;
    }

    @Override
    public void start(Stage stage){
        SceneManger.setStage(stage);
        SceneManger.show("home-page.fxml", new StageProperties("Cipher Labs Home Page"));
    }

    public static void main(String[] args) {
        CipherLabsCore cipherLabsCore = new CipherLabsCore();
        cipherLabsCore.onEnable();
        launch();
    }
}
