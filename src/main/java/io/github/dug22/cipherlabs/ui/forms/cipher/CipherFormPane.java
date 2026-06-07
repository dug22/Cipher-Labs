package io.github.dug22.cipherlabs.ui.forms.cipher;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

public class CipherFormPane extends BorderPane {

    private final VBox centeredContainer = new VBox(5);
    private final VBox bottomContainer = new VBox(5);

    public CipherFormPane() {
        centeredContainer.setAlignment(Pos.CENTER);
        setCenter(centeredContainer);

        bottomContainer.setPadding(new Insets(5));
        bottomContainer.setAlignment(Pos.CENTER);
        setBottom(bottomContainer);
    }

    public void addCenteredContent(Node node) {
        centeredContainer.getChildren().add(node);
    }

    public void addBottomContent(Node node) {
        bottomContainer.getChildren().add(node);
    }

    public VBox getCenteredContainer() {
        return centeredContainer;
    }

    public VBox getBottomContainer() {
        return bottomContainer;
    }
}