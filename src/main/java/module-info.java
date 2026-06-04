module io.github.dug22.cipherlabs {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;
    requires java.desktop;
    requires com.google.gson;
    opens io.github.dug22.cipherlabs.lockspot to com.google.gson;


    opens io.github.dug22.cipherlabs.ui.controllers to javafx.fxml;
    exports io.github.dug22.cipherlabs;
    exports io.github.dug22.cipherlabs.config;
    opens io.github.dug22.cipherlabs.ui.controllers.types to javafx.fxml;
    opens io.github.dug22.cipherlabs.lockspot.ffn to com.google.gson;
    opens io.github.dug22.cipherlabs.lockspot.lr to com.google.gson;
}