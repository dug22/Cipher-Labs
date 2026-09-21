package io.github.dug22.cipherlabs.ui.controllers;

import io.github.dug22.cipherlabs.resources.Resource;
import io.github.dug22.cipherlabs.resources.ResourceManager;
import io.github.dug22.cipherlabs.ui.builder.GridPaneConstraintsBuilder;
import io.github.dug22.cipherlabs.ui.builder.LabelBuilder;
import io.github.dug22.cipherlabs.ui.task.general.SceneLoaderTask;
import io.github.dug22.cipherlabs.ui.utils.StageProperties;
import javafx.animation.Interpolator;
import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.util.Duration;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ResourcesController extends AbstractController {

    @FXML
    private AnchorPane resourcesContainer;

    private final GridPane gridPane = getGridPane();
    private final VBox sidePanel = new VBox();
    private final Label backArrow = new LabelBuilder.Builder()
            .setText(" ◀ Go Back")
            .setTextColor(Color.WHITE)
            .setFontSize(24)
            .setBold(true)
            .setCursor(Cursor.HAND)
            .build();
    private final Label cipherLabsResourcesLabel = new LabelBuilder.Builder()
            .setText("Cipher Labs Resources")
            .setTextColor(Color.WHITE)
            .setFontSize(36)
            .setBold(true)
            .build();
    private final Label collapsibleButton = new LabelBuilder.Builder()
            .setText("▶")
            .setTextColor(Color.WHITE)
            .setCursor(Cursor.HAND)
            .build();
    private final WebView webView = new WebView();
    private final WebEngine webEngine = webView.getEngine();


    private final List<Label> resourceLabels = new ArrayList<>();


    @FXML
    private void initialize() {
        defineAnchorPaneProperties();
        defineGridPaneConstraints();
        defineNavigationHeader();
        gridPane.add(webView, 0, 1);
        defineSidePanel();
        defineControlListeners();
        gridPane.getStyleClass().add("grid-pane");
        resourcesContainer.getChildren().add(gridPane);
        loadDefaultResource();
    }


    @Override
    protected void defineGridPaneConstraints() {
        new GridPaneConstraintsBuilder.Builder().getGridPane(gridPane)
                .addColumnConstraints(new ColumnConstraints())
                .addRowConstraints(new RowConstraints(), new GridPaneConstraintsBuilder.RowConstraintsProperties(10))
                .addRowConstraints(new RowConstraints(), new GridPaneConstraintsBuilder.RowConstraintsProperties(90))
                .setConstraints();
    }

    @Override
    protected void defineNavigationHeader() {
        HBox navigationBox = new HBox();
        navigationBox.setAlignment(Pos.CENTER);
        HBox.setHgrow(cipherLabsResourcesLabel, Priority.ALWAYS);
        cipherLabsResourcesLabel.setMaxWidth(Double.MAX_VALUE);
        cipherLabsResourcesLabel.setAlignment(Pos.CENTER);
        HBox.setHgrow(backArrow, Priority.ALWAYS);
        backArrow.setAlignment(Pos.TOP_LEFT);
        navigationBox.getStyleClass().add("navigation-box");
        navigationBox.getChildren().addAll(backArrow, cipherLabsResourcesLabel);
        GridPane.setHgrow(navigationBox, Priority.ALWAYS);
        gridPane.add(navigationBox, 0, 0);
    }

    @Override
    protected void defineControlListeners() {
        for (Label resourceLabel : resourceLabels) {
            resourceLabel.setOnMouseClicked((e) -> {
                Label clickedResource = (Label) e.getSource();
                String clickedText = clickedResource.getText();
                String path = ResourceManager.findResource(clickedText);
                if (path != null) {
                    URL resourceUrl = getClass().getResource(path);
                    if (resourceUrl != null) {
                        webEngine.load(resourceUrl.toExternalForm());
                        minimizeSidePanel();
                    } else {
                        URL errorPage = getClass().getResource(ResourceManager.findResource("/resource-material/error-page.html"));
                        webEngine.load(errorPage.toExternalForm());
                        minimizeSidePanel();
                    }
                }
            });
        }

        collapsibleButton.setOnMouseClicked(_ -> {
            if (sidePanel.getMinWidth() >= 250) {
                minimizeSidePanel();
            } else {
                maximizeSidePanel();
            }
        });

        backArrow.setOnMouseClicked((_) -> new SceneLoaderTask("fxml/home-page.fxml", new StageProperties("Cipher Labs Home Page")).run());
    }

    private void defineSidePanel() {
        maximizeSidePanel();
        sidePanel.setSpacing(20);
        for (Map.Entry<String, List<Resource>> categoryEntry : ResourceManager.getCategorizedResourceMap().entrySet()) {
            sidePanel.getChildren().add(new LabelBuilder.Builder()
                    .setText(categoryEntry.getKey())
                    .setBold(true)
                    .setFontSize(14)
                    .setAlignment(Pos.CENTER)
                    .setMaxWidth(360D)
                    .setTextColor(Color.WHITE)
                    .build());
            for (Resource resource : categoryEntry.getValue()) {
                Label clickableResourceLabel = new LabelBuilder.Builder()
                        .setText(resource.getResourceName())
                        .setFontSize(14)
                        .setTextColor(Color.WHITE)
                        .setCursor(Cursor.HAND)
                        .build();
                sidePanel.getChildren().add(clickableResourceLabel);
                clickableResourceLabel.setAlignment(Pos.CENTER);
                clickableResourceLabel.setMaxWidth(360);
                resourceLabels.add(clickableResourceLabel);
            }
        }
        sidePanel.setBackground(Background.fill(Color.web("#1a1c2c")));
        collapsibleButton.getStyleClass().add("resources-side-panel-collapsible-button");
        HBox sidePanelWrapper = new HBox(sidePanel, collapsibleButton);
        sidePanelWrapper.setAlignment(Pos.CENTER_LEFT);
        sidePanelWrapper.setPickOnBounds(false);
        gridPane.add(sidePanelWrapper, 0, 1);
    }

    private void minimizeSidePanel() {
        collapsibleButton.setVisible(false);
        TranslateTransition translateTransition = new TranslateTransition(Duration.millis(500), sidePanel);
        translateTransition.setFromX(0);
        translateTransition.setToX(-sidePanel.getWidth());
        translateTransition.setInterpolator(Interpolator.EASE_BOTH);
        translateTransition.setOnFinished((e) -> {
            sidePanel.setMinWidth(0);
            sidePanel.setPrefWidth(0);
            sidePanel.setMaxWidth(0);
            sidePanel.setVisible(false);
            collapsibleButton.setVisible(true);
        });
        collapsibleButton.setText("▶");
        translateTransition.play();
    }

    private void maximizeSidePanel() {
        sidePanel.setMinWidth(250);
        sidePanel.setPrefWidth(250);
        sidePanel.setMaxWidth(250);
        sidePanel.setVisible(true);
        TranslateTransition translateTransition = new TranslateTransition(Duration.millis(300), sidePanel);
        translateTransition.setFromX(-250);
        translateTransition.setToX(0);
        translateTransition.setInterpolator(Interpolator.EASE_BOTH);
        translateTransition.setOnFinished((_) -> sidePanel.setTranslateX(0));
        collapsibleButton.setText("◀");
        translateTransition.play();
    }


    @SuppressWarnings("all")
    private void loadDefaultResource() {
        URL defaultResource = ResourceManager.class.getResource("/resource-material/about-cipher-labs-article.html");
        webEngine.load(defaultResource.toExternalForm());
    }
}
