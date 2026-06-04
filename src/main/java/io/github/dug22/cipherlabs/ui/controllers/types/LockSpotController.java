package io.github.dug22.cipherlabs.ui.controllers.types;

import com.google.gson.reflect.TypeToken;
import io.github.dug22.cipherlabs.lockspot.LockSpotFeatureUtils;
import io.github.dug22.cipherlabs.lockspot.LockSpotModel;
import io.github.dug22.cipherlabs.lockspot.LockSpotModelLoader;
import io.github.dug22.cipherlabs.lockspot.ffn.LockSpotFFN;
import io.github.dug22.cipherlabs.lockspot.ffn.LockSpotFFNModelData;
import io.github.dug22.cipherlabs.lockspot.lr.LockSpotLogisticRegression;
import io.github.dug22.cipherlabs.lockspot.lr.LockSpotLogisticRegressionModelData;
import io.github.dug22.cipherlabs.ui.builder.GridPaneConstraintsBuilder;
import io.github.dug22.cipherlabs.ui.builder.LabelBuilder;
import io.github.dug22.cipherlabs.ui.controllers.AbstractController;
import io.github.dug22.cipherlabs.ui.dialog.Alerts;
import io.github.dug22.cipherlabs.ui.node.SettingComboBox;
import io.github.dug22.cipherlabs.ui.task.general.SceneLoaderTask;
import io.github.dug22.cipherlabs.ui.utils.StageProperties;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.control.TextArea;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

import java.util.*;
import java.util.stream.Collectors;

public class LockSpotController extends AbstractController {

    @FXML
    private AnchorPane lockSpotContainer;

    private final GridPane gridPane = getGridPane();

    private final Label lockSpotLabel = new LabelBuilder.Builder()
            .setText("Lock Spot (LS)")
            .setAlignment(Pos.CENTER)
            .setBold(true)
            .setFontSize(36)
            .setTextColor(Color.WHITE)
            .build();

    private final Label backArrowLabel = new LabelBuilder.Builder()
            .setText(" ◀ Go Back")
            .setBold(true)
            .setFontSize(24)
            .setCursor(Cursor.HAND)
            .setAlignment(Pos.TOP_LEFT)
            .setTextColor(Color.WHITE)
            .build();

    private final Label lockSpotDescriptionLabel = new LabelBuilder.Builder()
            .setText("Lock Spot is a simple machine learning tool that can determine the cipher algorithm and cipher category the ciphertext belongs to!")
            .setFontSize(12)
            .setWrapText(true)
            .setAlignment(Pos.CENTER)
            .setTextColor(Color.WHITE)
            .build();

    private final TextArea cipherTextArea = new TextArea();
    private final Button analyzeButton = new Button("📊 Analyze");
    private final Label ciphertextLengthLabel = new LabelBuilder.Builder()
            .setText("Ciphertext length: 0")
            .setFontSize(12)
            .setAlignment(Pos.TOP_RIGHT)
            .build();

    private final SettingComboBox<String> modelArchitectureComboBox = new SettingComboBox<>();
    private final VBox resultsBox = new VBox(10);
    private LockSpotModelLoader<?> lockSpotModelContainer;
    private LockSpotModel cipherTypeModel;
    private LockSpotModel cipherAlgorithmModel;

    @FXML
    public void initialize() {
        //Default Model Architecture
        loadModelArchitecture("/model/lock-spot-small-ffn-model.json", new LockSpotFFNModelData());
        defineAnchorPaneProperties();
        defineGridPaneConstraints();
        defineNavigationHeader();
        defineMainContent();
        gridPane.getStyleClass().add("grid-pane");
        lockSpotContainer.getChildren().setAll(gridPane);
        defineControlListeners();
    }

    @Override
    protected void defineGridPaneConstraints() {
        new GridPaneConstraintsBuilder.Builder().getGridPane(gridPane)
                .addColumnConstraints(new ColumnConstraints())
                .addRowConstraints(new RowConstraints(), new GridPaneConstraintsBuilder.RowConstraintsProperties(15))
                .addRowConstraints(new RowConstraints(), new GridPaneConstraintsBuilder.RowConstraintsProperties(85))
                .setConstraints();
    }

    @Override
    protected void defineNavigationHeader() {
        HBox navigationBox = new HBox();
        navigationBox.setAlignment(Pos.CENTER);
        HBox.setHgrow(backArrowLabel, Priority.ALWAYS);
        VBox centerTextGroup = new VBox(15, lockSpotLabel, lockSpotDescriptionLabel);
        centerTextGroup.setAlignment(Pos.CENTER);
        HBox.setHgrow(centerTextGroup, Priority.ALWAYS);
        navigationBox.getStyleClass().add("navigation-box");
        navigationBox.getChildren().addAll(backArrowLabel, centerTextGroup);
        GridPane.setHgrow(navigationBox, Priority.ALWAYS);
        gridPane.add(navigationBox, 0, 0);
    }

    private void defineMainContent() {
        VBox mainContentBox = new VBox(20);
        mainContentBox.setPadding(new Insets(20));
        mainContentBox.setAlignment(Pos.TOP_CENTER);
        GridPane.setHgrow(mainContentBox, Priority.ALWAYS);
        GridPane.setVgrow(mainContentBox, Priority.ALWAYS);
        mainContentBox.setMaxWidth(Double.MAX_VALUE);
        mainContentBox.setMaxHeight(Double.MAX_VALUE);
        HBox textHeaderBox = new HBox();
        textHeaderBox.getChildren().addAll(ciphertextLengthLabel);
        HBox.setHgrow(ciphertextLengthLabel, Priority.ALWAYS);
        textHeaderBox.setAlignment(Pos.TOP_RIGHT);
        cipherTextArea.setPromptText("Enter ciphertext here...");
        cipherTextArea.setWrapText(true);
        VBox.setVgrow(cipherTextArea, Priority.ALWAYS);
        cipherTextArea.setMaxHeight(Double.MAX_VALUE);
        VBox cipherInputGroup = new VBox(5, textHeaderBox, cipherTextArea);
        VBox.setVgrow(cipherInputGroup, Priority.ALWAYS);
        HBox configBox = new HBox(10);
        configBox.setAlignment(Pos.CENTER_LEFT);
        HBox architectureGroup = new HBox(10);
        architectureGroup.setAlignment(Pos.CENTER_LEFT);
        Label modelArchitectureLabel = new LabelBuilder.Builder().setText("Architecture:").build();
        modelArchitectureComboBox.setItemsAndMainValue(List.of("Small FFN Model", "Small Logistic Regression Model"), "Small FFN Model");
        architectureGroup.setAlignment(Pos.CENTER);
        architectureGroup.getChildren().addAll(modelArchitectureLabel, modelArchitectureComboBox);
        configBox.getChildren().addAll(architectureGroup);
        analyzeButton.setMaxWidth(Double.MAX_VALUE);
        analyzeButton.getStyleClass().add("analyze-button");
        resultsBox.setAlignment(Pos.TOP_LEFT);
        Label resultsHeaderLabel = new LabelBuilder.Builder().setText("Results")
                .setFontSize(24)
                .setBold(true)
                .setTextColor(Color.WHITE)
                .build();
        resultsHeaderLabel.setFont(Font.font("Arial", FontWeight.BOLD, 24));
        resultsHeaderLabel.setTextFill(Color.WHITE);
        Label resultsPlaceholderLabel = new LabelBuilder.Builder().setText("Click on \"Analyze\" to get the results...")
                .setFontSize(14)
                .build();
        Separator separator = new Separator();
        resultsBox.getChildren().addAll(resultsHeaderLabel, resultsPlaceholderLabel, separator);
        mainContentBox.getChildren().addAll(cipherInputGroup, configBox, analyzeButton, resultsBox);
        gridPane.add(mainContentBox, 0, 1);
    }

    @Override
    protected void defineControlListeners() {
        cipherTextArea.textProperty().addListener((_, _, newValue) -> {
            if (newValue != null) {
                ciphertextLengthLabel.setText("Ciphertext length: " + newValue.length());
            }
        });
        analyzeButton.setOnAction(_ -> {
            String analyzedText = cipherTextArea.getText();
            if (analyzedText.length() < 100) {
                Alerts.LOCK_SPOT_ERROR.show();
                return;
            }

            double[] features = LockSpotFeatureUtils.getFeatures(analyzedText);
            resultsBox.getChildren().clear();
            Label resultsHeaderLabel = new LabelBuilder.Builder()
                    .setText("Results")
                    .setFontSize(24)
                    .setBold(true)
                    .setTextColor(Color.WHITE)
                    .build();
            Separator separator = new Separator();
            resultsBox.getChildren().addAll(resultsHeaderLabel, separator);
            generateReport("Cipher Type", cipherTypeModel, features);
            generateReport("Cipher Algorithm", cipherAlgorithmModel, features);

        });
        modelArchitectureComboBox.getSelectionModel().selectedItemProperty().addListener((_, _, newValue) -> {
            if (newValue.equalsIgnoreCase("Small FFN Model")) {
                loadModelArchitecture("/model/lock-spot-small-ffn-model.json", new LockSpotFFNModelData());
            } else if (newValue.equalsIgnoreCase("Small Logistic Regression Model")) {
                loadModelArchitecture("/model/lock-spot-small-logistic-regression-model.json", new LockSpotLogisticRegressionModelData());
            }
        });

        backArrowLabel.setOnMouseClicked((_) -> new SceneLoaderTask("home-page.fxml", new StageProperties("Cipher Labs Home Page")).run());
    }


    private void generateReport(String labelTitle, LockSpotModel lockSpotModel, double[] features) {
        Label sectionLabel = new LabelBuilder.Builder()
                .setText(labelTitle)
                .setFontSize(14)
                .setBold(true)
                .setAlignment(Pos.CENTER)
                .setMaxWidth(Double.MAX_VALUE)
                .build();
        resultsBox.getChildren().add(sectionLabel);
        Map<String, Double> predictionsMap = getModelPredictions(lockSpotModel, features);
        double appropriateMinValue = 0.01;
        for (Map.Entry<String, Double> entry : predictionsMap.entrySet()) {
            if (entry.getValue() > appropriateMinValue) {
                HBox row = new HBox(15);
                row.setAlignment(Pos.CENTER);
                row.setPadding(new Insets(0, 0, 0, 15));
                Label nameLabel = createAppropriateReportLabel(String.format("%-20s", entry.getKey()), Color.BLACK);
                int filled = (int) Math.round(entry.getValue() * 10);
                Label barLabel = createAppropriateReportLabel("●".repeat(filled) + "○".repeat(10 - filled), Color.GREEN);
                Label percentLabel = createAppropriateReportLabel(String.format("%5.1f%%", entry.getValue() * 100), Color.BLACK);
                row.getChildren().addAll(nameLabel, barLabel, percentLabel);
                resultsBox.getChildren().add(row);
            }
        }

        Region spacer = new Region();
        spacer.setPrefHeight(10);
        resultsBox.getChildren().add(spacer);
    }

    private Label createAppropriateReportLabel(String text, Color color) {
        return new LabelBuilder.Builder().setText(text)
                .setTextColor(color)
                .setFontSize(14)
                .build();
    }


    private <T> void loadModelArchitecture(String modelFilePath, T modelData) {
        if (modelData instanceof LockSpotFFNModelData) {
            lockSpotModelContainer = LockSpotModelLoader.loadModel(modelFilePath, new TypeToken<LockSpotModelLoader<LockSpotFFNModelData>>() {
            });
            cipherTypeModel = new LockSpotFFN((LockSpotFFNModelData) lockSpotModelContainer.cipherTypeModel);
            cipherAlgorithmModel = new LockSpotFFN((LockSpotFFNModelData) lockSpotModelContainer.cipherAlgorithmModel);
        } else if (modelData instanceof LockSpotLogisticRegressionModelData) {
            lockSpotModelContainer = LockSpotModelLoader.loadModel(modelFilePath, new TypeToken<LockSpotModelLoader<LockSpotLogisticRegressionModelData>>() {
            });
            cipherTypeModel = new LockSpotLogisticRegression((LockSpotLogisticRegressionModelData) lockSpotModelContainer.cipherTypeModel);
            cipherAlgorithmModel = new LockSpotLogisticRegression((LockSpotLogisticRegressionModelData) lockSpotModelContainer.cipherAlgorithmModel);
        }
    }

    private Map<String, Double> getModelPredictions(LockSpotModel lockSpotModel, double[] features) {
        double[] probabilities = lockSpotModel.getProbabilities(features);
        String[] classes = lockSpotModel.getClasses();
        Map<String, Double> predictionResultsMap = new HashMap<>();
        for (int i = 0; i < classes.length; i++) {
            predictionResultsMap.put(classes[i], probabilities[i]);
        }
        return predictionResultsMap.entrySet().stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (oldValue, _) -> oldValue,
                        LinkedHashMap::new
                ));
    }
}
