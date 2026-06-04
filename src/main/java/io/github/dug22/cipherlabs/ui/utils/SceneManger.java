package io.github.dug22.cipherlabs.ui.utils;

import io.github.dug22.cipherlabs.CipherLabsApplication;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.net.URL;

public class SceneManger {

    private static Stage stage;

    public static void setStage(Stage stage) {
        SceneManger.stage = stage;
    }

    public static void show(String fxmlFile, StageProperties stageProperties) {
        try {
            URL resource = CipherLabsApplication.class.getResource(fxmlFile);
            if (resource == null) {
                throw new IllegalArgumentException("The following FXML file does not exist!");
            }
            Parent root = FXMLLoader.load(resource);
            ;
            Stage newStage = new Stage();
            if (stage != null) {
                stage.close();
            }
            Screen screen = Screen.getPrimary();
            double width = screen.getVisualBounds().getWidth();
            double height = screen.getVisualBounds().getHeight();
            stage = newStage;
            stage.sizeToScene();
            stage.setResizable(false);
            stage.setScene(new Scene(root, width, height));
            stage.setTitle(stageProperties.title());
            stage.setMaximized(true);
            stage.setMinWidth(width);
            stage.setMaxWidth(width);
            stage.setMinHeight(height);
            stage.setMaxHeight(height);
            stage.getIcons().add(new Image(SceneManger.class.getResourceAsStream("/images/cipher-labs-desktop-icon.png"), 64, 64, true, false));

            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Stage getCurrentStage(){
        return stage;
    }
}
