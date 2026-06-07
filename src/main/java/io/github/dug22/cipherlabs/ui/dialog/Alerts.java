package io.github.dug22.cipherlabs.ui.dialog;

import io.github.dug22.cipherlabs.ui.builder.AlertBuilder;
import javafx.scene.control.Alert;

import java.io.InputStream;
import java.util.function.Function;

public enum Alerts {

    BACON_DECRYPTION_ERROR(new AlertBuilder.Builder()
            .setAlertType(Alert.AlertType.ERROR)
            .setTitle("Bacon Cipher Decryption Error")
            .setHeaderText("Decryption Error")
            .setContentText("An error has occurred! Please make sure your text contains appropriate 5-character biliteral alphabets.")
            .setImageView(getErrorLogo(), 64, 64)
            .build()),


    CAESAR_KEY_ERROR(new AlertBuilder.Builder()
            .setAlertType(Alert.AlertType.ERROR)
            .setTitle("Key Input Error")
            .setHeaderText("Key Input Error")
            .setContentText("An error has occurred! Please make sure the key text field contains a number that is between 1 and 25!")
            .setImageView(getErrorLogo(), 64, 64)
            .build()),

    ENIGMA_PLUGBOARD_ERROR(new AlertBuilder.Builder()
            .setAlertType(Alert.AlertType.ERROR)
            .setTitle("Plugboard Error")
            .setHeaderText("Plugboard Error")
            .setContentText("An error has occurred! Please make sure that your plugboard consists of 3 pairs of alphabetic characters! For example, AB CD EF!'")
            .setImageView(getErrorLogo(), 64, 64)
            .build()),

    ENIGMA_KEY_ERROR(new AlertBuilder.Builder()
            .setAlertType(Alert.AlertType.ERROR)
            .setTitle("Key Input Error")
            .setHeaderText("Key Input Error")
            .setContentText("An error has occurred! Please make sure that your key is 3 characters and that your key only contains alphabetic characters!")
            .setImageView(getErrorLogo(), 64, 64)
            .build()),

    LOCK_SPOT_ERROR(new AlertBuilder.Builder()
            .setAlertType(Alert.AlertType.ERROR)
            .setTitle("Lock Spot Error")
            .setHeaderText("Lock Spot Error")
            .setContentText("Make sure your ciphertext is 100 characters or longer!")
            .setImageView(getErrorLogo(), 64, 64)
            .build()),

    PLAYFAIR_FILLER_CHARACTER_ERROR(new AlertBuilder.Builder()
            .setAlertType(Alert.AlertType.ERROR)
            .setTitle("Character Filler Error")
            .setHeaderText("Character Filler Error")
            .setContentText("Your primary or secondary filler character cannot be the same!")
            .setImageView(getErrorLogo(), 64, 64)
            .build()),
    RSA_KEY_ERROR(new AlertBuilder.Builder()
            .setAlertType(Alert.AlertType.ERROR)
            .setTitle("RSA Key Error")
            .setHeaderText("RSA Key Error")
            .setContentText("An error has occurred with your key inputs. Make sure p and q are prime, and that e and phi are invertible!")
            .setImageView(getErrorLogo(), 64, 64)
            .build()),

    RSA_MESSAGE_ERROR(new AlertBuilder.Builder()
            .setAlertType(Alert.AlertType.ERROR)
            .setTitle("Message Error")
            .setHeaderText("RSA Message Error")
            .setContentText("The following message is too long for the given key size!")
            .setImageView(getErrorLogo(), 64, 64)
            .build()),

    SAVE_DOCUMENT(currentDocument -> new AlertBuilder.Builder()
            .setTitle("Save Notification")
            .setHeaderText("You have successfully saved the following text document! File location: " + currentDocument)
            .setAlertType(Alert.AlertType.INFORMATION)
            .build()),

    SAVE_SETTINGS(new AlertBuilder.Builder()
            .setTitle("Settings Saved")
            .setAlertType(Alert.AlertType.INFORMATION)
            .setHeaderText("Settings Saved")
            .setContentText("You successfully saved your configuration settings!")
            .build()),

    VIGENERE_KEY_ERROR(new AlertBuilder.Builder()
            .setAlertType(Alert.AlertType.ERROR)
            .setTitle("Key Input Error")
            .setHeaderText("Key Input Error")
            .setContentText("An error has occurred! Please make sure that the key is less than or equal to the length of your message and that your key only contains alphabetic characters!")
            .setImageView(getErrorLogo(), 64, 64)
            .build());


    private Alert staticAlert;

    private Function<String, Alert> dynamicAlert;

    Alerts(Alert alert) {
        this.staticAlert = alert;
    }

    Alerts(Function<String, Alert> dynamicAlert) {
        this.dynamicAlert = dynamicAlert;
    }

    public void show() {
        staticAlert.show();
    }

    public void showAndWait() {
        staticAlert.showAndWait();
    }

    public void show(String dynamicValue) {
        Alert alert = dynamicAlert.apply(dynamicValue);
        alert.show();
    }

    public void showAndWait(String dynamicValue) {
        Alert alert = dynamicAlert.apply(dynamicValue);
        alert.showAndWait();
    }


    private static InputStream getErrorLogo() {
        return Alerts.class.getResourceAsStream("/images/error-logo.png");
    }
}
