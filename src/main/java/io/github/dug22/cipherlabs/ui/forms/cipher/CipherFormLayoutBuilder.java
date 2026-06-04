package io.github.dug22.cipherlabs.ui.forms.cipher;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

public class CipherFormLayoutBuilder {

    private final BorderPane root;
    private final VBox mainContentContainer;
    private final VBox bottomContainer;

    public CipherFormLayoutBuilder() {
        this.root = new BorderPane();

        this.mainContentContainer = new VBox(5);
        this.mainContentContainer.setAlignment(Pos.CENTER);
        this.root.setCenter(mainContentContainer);

        this.bottomContainer = new VBox(5);
        this.bottomContainer.setPadding(new Insets(5));
        this.bottomContainer.setAlignment(Pos.CENTER);
        this.root.setBottom(bottomContainer);
    }


    public CipherFormLayoutBuilder addMainContent(Node node) {
        this.mainContentContainer.getChildren().add(node);
        return this;
    }

    public CipherFormLayoutBuilder addBottomContent(Node node) {
        this.bottomContainer.getChildren().add(node);
        return this;
    }

    public BorderPane build() {
        return this.root;
    }
}
