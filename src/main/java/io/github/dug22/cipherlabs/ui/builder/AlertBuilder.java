package io.github.dug22.cipherlabs.ui.builder;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DialogPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.paint.Color;

import java.io.InputStream;
import java.util.Collection;

public class AlertBuilder {

    public static class Builder {

        private Alert.AlertType alertType;
        private String title;
        private String headerText;
        private String contentText;
        private ImageView imageView;
        private Collection<ButtonType> buttonTypes;

        public Builder setAlertType(Alert.AlertType alertType) {
            this.alertType = alertType;
            return this;
        }

        public Builder setTitle(String title) {
            this.title = title;
            return this;
        }

        public Builder setHeaderText(String headerText) {
            this.headerText = headerText;
            return this;
        }

        public Builder setContentText(String contentText) {
            this.contentText = contentText;
            return this;
        }

        public Builder setImageView(InputStream imageResourcePath, int width, int height) {
            Image image = new Image(imageResourcePath);
            this.imageView = new ImageView(image);
            this.imageView.setFitWidth(width);
            this.imageView.setFitHeight(height);
            return this;
        }

        public Builder setButtonTypes(Collection<ButtonType> buttonTypes) {
            this.buttonTypes = buttonTypes;
            return this;
        }

        public Alert build() {
            Alert alert = new Alert(alertType);
            if (title != null) {
                alert.setTitle(title);
            }

            if(alertType != null){
                alert.setAlertType(alertType);
            }

            if (headerText != null) {
                alert.setHeaderText(headerText);
            }

            if (contentText != null) {
                alert.setContentText(contentText);
            }

            if (buttonTypes != null) {
                alert.getButtonTypes().setAll(buttonTypes);
            }

            DialogPane dialogPane = alert.getDialogPane();

            if(imageView != null){
                dialogPane.setGraphic(imageView);
            }

            dialogPane.setBackground(Background.fill(Color.WHITE));
            dialogPane.lookup(".header-panel .label").setStyle("-fx-font-family: Arial; -fx-font-size: 16px;");
            dialogPane.lookup(".content.label").setStyle("-fx-font-family: Arial; -fx-font-size: 16px;");
            return alert;
        }
    }
}
