package io.github.dug22.cipherlabs.ui.controllers.types;

import io.github.dug22.cipherlabs.ui.builder.GridPaneConstraintsBuilder;
import io.github.dug22.cipherlabs.ui.builder.LabelBuilder;
import io.github.dug22.cipherlabs.ui.builder.MenuBuilder;
import io.github.dug22.cipherlabs.ui.controllers.AbstractController;
import io.github.dug22.cipherlabs.ui.task.general.CloseApplicationTask;
import io.github.dug22.cipherlabs.ui.task.general.SceneLoaderTask;
import io.github.dug22.cipherlabs.ui.task.menu.OpenGitHubRepoTask;
import io.github.dug22.cipherlabs.ui.task.menu.SaveDocumentTask;
import io.github.dug22.cipherlabs.ui.task.menu.form.*;
import io.github.dug22.cipherlabs.ui.utils.StageProperties;
import io.github.dug22.cipherlabs.utils.Alphabets;
import io.github.dug22.cipherlabs.utils.TextAnalysisUtils;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

public class WorkStationController extends AbstractController {

    @FXML
    private AnchorPane workstationContainer;
    private TabPane tabPane;
    private TextArea textArea;
    private final GridPane gridPane = getGridPane();
    private ScrollPane sideDashboard;

    public WorkStationController() {
    }

    @FXML
    private void initialize() {
        textArea = new TextArea();
        defineAnchorPaneProperties();
        defineGridPaneConstraints();
        defineMainContent();
        defineNavigationHeader();
        defineControlListeners();
        textArea.setText(textArea.getText().toUpperCase());
        tabPane.getTabs().getFirst().setClosable(false);
        workstationContainer.getChildren().add(gridPane);
    }

    @Override
    protected void defineGridPaneConstraints() {
        new GridPaneConstraintsBuilder.Builder().getGridPane(gridPane)
                .addColumnConstraints(new ColumnConstraints(), new GridPaneConstraintsBuilder.ColumnConstraintsProperties(65))
                .addColumnConstraints(new ColumnConstraints(), new GridPaneConstraintsBuilder.ColumnConstraintsProperties(35))
                .addRowConstraints(new RowConstraints(), new GridPaneConstraintsBuilder.RowConstraintsProperties(5))
                .addRowConstraints(new RowConstraints(), new GridPaneConstraintsBuilder.RowConstraintsProperties(95))
                .setConstraints();
    }

    @Override
    protected void defineNavigationHeader() {
        Menu fileMenu = new MenuBuilder.Builder().createMenu("File")
                .addMenuItem("Home", new SceneLoaderTask("home-page.fxml", new StageProperties("Cipher Labs Home Page")))
                .addMenuItem("Lock Spot", new SceneLoaderTask("lock-spot-page.fxml", new StageProperties("Lock Spot Page")))
                .addMenuItem("Save", new SaveDocumentTask(this))
                .addMenuItem("Close", new CloseApplicationTask())
                .build();

        Menu encryptDecryptMenu = new MenuBuilder.Builder().createMenu("Encrypt/Decrypt")
                .addSubMenu(new MenuBuilder.Builder()
                        .createMenu("Symmetric (Classic)")
                        .addMenuItem("Caesar/ROT-13", new OpenCaesarCipherFormTask(this, tabPane))
                        .addMenuItem("Vigenere", new OpenVigenereCipherFormTask(this, tabPane))
                        .addMenuItem("Playfair", new OpenPlayfairCipherFormTask(this, tabPane))
                        .addMenuItem("Enigma", new OpenEnigmaFormTask(this, tabPane))
                        .build())
                .addSubMenu(new MenuBuilder.Builder().createMenu("Steganography (Classic)")
                        .addMenuItem("Baconian", new OpenBaconianCipherFormTask(this, tabPane))
                        .build())
                .addSubMenu(new MenuBuilder.Builder().createMenu("Asymmetric ")
                        .addMenuItem("RSA", new OpenRSAFormTask(this, tabPane))
                        .build())
                .build();

        Menu helpMenu = new MenuBuilder.Builder().createMenu("Help")
                .addMenuItem("Resources", new SceneLoaderTask("resource-page.fxml", new StageProperties("Cipher Labs Resources")))
                .addMenuItem("Visit GitHub Repository", new OpenGitHubRepoTask())
                .build();
        MenuBar menuBar = new MenuBar();
        menuBar.setPadding(new Insets(10));
        menuBar.setMaxHeight(Double.MAX_VALUE);
        menuBar.getMenus().addAll(fileMenu, encryptDecryptMenu, helpMenu);
        gridPane.add(menuBar, 0, 0, 2, 1);
    }

    private void defineMainContent() {
        tabPane = new TabPane();
        tabPane.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        tabPane.getStyleClass().add("workstation-tab-pane");
        TextArea editor = new TextArea();
        editor.setWrapText(true);
        editor.setFont(Font.font("Arial", 14));
        editor.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        editor.getStyleClass().add("workstation-text-area");
        Tab tab = new Tab("Untitled");
        tab.setContent(editor);
        tabPane.getTabs().add(tab);

        textArea = editor;
        tabPane.getSelectionModel().selectedItemProperty().addListener((obs, oldTab, newTab) -> {
            if (newTab != null) {
                textArea = (TextArea) newTab.getContent();
            }
        });

        StringBuilder fileContent = new StringBuilder();
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(HomePageController.getSelectedDocument()))) {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                fileContent.append(line).append("\n");
            }
        } catch (Exception _) {
        }

        textArea.setText(fileContent.toString());
        textArea.setFont(Font.font("Arial", 14));
        tab.setText(HomePageController.getSelectedDocument().getName());
        gridPane.add(tabPane, 0, 1);
        sideDashboard = createAnalysisDashboard(textArea, true);
        gridPane.add(sideDashboard, 1, 1);
    }

    public ScrollPane createAnalysisDashboard(TextArea textArea, boolean isOriginalDocument) {
        VBox dashboardContainer = new VBox(16);
        dashboardContainer.setPadding(new Insets(16));
        dashboardContainer.setAlignment(Pos.TOP_CENTER);
        dashboardContainer.setStyle("-fx-background-color: transparent;");
        Label documentInformationLabel = new LabelBuilder.Builder()
                .setText("Document Information")
                .setFontSize(22)
                .setBold(true)
                .setTextColor(Color.WHITE)
                .setAlignment(Pos.CENTER)
                .build();
        VBox metricsContainer = new VBox(10);
        metricsContainer.setAlignment(Pos.TOP_CENTER);
        metricsContainer.setMaxWidth(Double.MAX_VALUE);
        VBox tabTitleCard = createMetricCard("Tab Title", tabPane.getSelectionModel().getSelectedItem().getText());
        VBox tabTypeCard = createMetricCard("Document Type", getDocumentType(tabPane.getSelectionModel().getSelectedItem(), isOriginalDocument));
        VBox textLengthCard = createMetricCard("Text Length", String.valueOf(textArea.getText().length()));
        VBox uniqueCharactersCard = createMetricCard("Unique Characters", String.valueOf(TextAnalysisUtils.getUniqueCharacterCount(textArea.getText())));
        VBox mostFrequentCharacterCard = createMetricCard("Most Frequent Character", String.valueOf(TextAnalysisUtils.getMostFrequentCharacter(textArea.getText())));
        VBox hasDigitsCard = createMetricCard("Contains Digits?", TextAnalysisUtils.hasDigits(textArea.getText()) == 1D ? "True" : "False");
        VBox hasDoubleLettersOrDoubleNumbersCard = createMetricCard("Has Double Letters or Double Numbers?", TextAnalysisUtils.hasDigits(textArea.getText()) == 1D ? "True" : "False");
        metricsContainer.getChildren().addAll(tabTitleCard, tabTypeCard, textLengthCard, uniqueCharactersCard, mostFrequentCharacterCard, hasDigitsCard, hasDoubleLettersOrDoubleNumbersCard);
        BarChart<String, Number> frequencyChart = getFrequencyChart(textArea);
        VBox.setVgrow(frequencyChart, Priority.ALWAYS);
        dashboardContainer.getChildren().addAll(documentInformationLabel, metricsContainer, frequencyChart);
        return getScrollPane(dashboardContainer);
    }

    private static ScrollPane getScrollPane(VBox dashboardContainer) {
        ScrollPane scrollPane = new ScrollPane(dashboardContainer);
        scrollPane.setFitToWidth(true);
        scrollPane.setFitToHeight(true);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        scrollPane.getStyleClass().add("document-information-side-panel");
        return scrollPane;
    }

    public BarChart<String, Number> getFrequencyChart(TextArea textArea) {
        int minValue = 0;
        int maxValue = 100;
        int stepValue = 20;
        CategoryAxis xAxis = new CategoryAxis();
        NumberAxis yAxis = new NumberAxis(minValue, maxValue, stepValue);
        xAxis.setLabel("Characters");
        yAxis.setLabel("%");
        xAxis.setTickLabelFill(Color.WHITE);
        yAxis.setTickLabelFill(Color.WHITE);
        xAxis.setTickLabelGap(4);
        BarChart<String, Number> frequencyChart = new BarChart<>(xAxis, yAxis);
        frequencyChart.setTitle("Character Frequency");
        frequencyChart.setLegendVisible(false);
        frequencyChart.setMinHeight(250);
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        String currentText = textArea.getText();
        String cleanText = currentText.toUpperCase().replaceAll("[^A-Z0-9]", "");
        Map<Character, Double> frequencyMap = TextAnalysisUtils.getFrequencies(cleanText);
        List<Character> appropriateCharacters = Alphabets.ALNUM_CHAR_LIST;
        appropriateCharacters.forEach(appropriateCharacter -> {
            double percentage = frequencyMap.getOrDefault(appropriateCharacter, 0.0) * 100;
            if (percentage != 0) {
                series.getData().add(new XYChart.Data<>(String.valueOf(appropriateCharacter), percentage));
            }
        });

        series.getData().sort(Comparator.comparingDouble((XYChart.Data<String, Number> d) -> d.getYValue().doubleValue()).reversed());
        frequencyChart.getData().add(series);

        for (XYChart.Data<String, Number> data : series.getData()) {
            Tooltip tooltip = new Tooltip(data.getXValue() + " : " + String.format("%.4f%%", data.getYValue().doubleValue()));
            Tooltip.install(data.getNode(), tooltip);
        }

        return frequencyChart;
    }

    private VBox createMetricCard(String title, String value) {
        VBox card = new VBox(4);
        card.setAlignment(Pos.CENTER_LEFT);
        card.setPadding(new Insets(10));
        card.setMinHeight(60);
        card.setMaxWidth(Double.MAX_VALUE);
        card.getStyleClass().add("metric-card");
        Label titleLabel = new LabelBuilder.Builder().setText(title).setTextColor(Color.web("#8b949e")).setFontSize(12).setBold(true).setAlignment(Pos.CENTER_LEFT).build();
        Label valueLabel = new LabelBuilder.Builder().setText(value).setTextColor(Color.WHITE).setFontSize(16).setBold(true).setAlignment(Pos.CENTER_LEFT).build();
        valueLabel.setWrapText(true);
        VBox.setVgrow(valueLabel, Priority.ALWAYS);
        card.getChildren().addAll(titleLabel, valueLabel);
        return card;
    }

    public TextArea getActiveTextArea() {
        Tab tab = tabPane.getSelectionModel().getSelectedItem();
        return tab == null ? null : (TextArea) tab.getContent();
    }

    @Override
    protected void defineControlListeners() {

        tabPane.getSelectionModel().selectedItemProperty().addListener((_, _, newTab) -> {
            boolean isOriginalDocument = tabPane.getSelectionModel().getSelectedIndex() == 0;
            if (newTab != null) {
                gridPane.getChildren().remove(getSideDashboard());
                sideDashboard = createAnalysisDashboard(getActiveTextArea(), isOriginalDocument);
                gridPane.add(sideDashboard, 1, 1);
            }
        });
    }

    public ScrollPane getSideDashboard() {
        return sideDashboard;
    }

    private String getDocumentType(Tab tab, boolean isOriginalDocument) {
        String documentType;
        if (isOriginalDocument) {
            documentType = "Original Document";
        } else {
            if (tab.getText().contains("Encrypted Result")) {
                documentType = "Encrypted Document";
            } else if (tab.getText().contains("Decrypted Result")) {
                documentType = "Decrypted Document";
            }else{
                documentType = "Unknown";
            }
        }

        return documentType;
    }
}