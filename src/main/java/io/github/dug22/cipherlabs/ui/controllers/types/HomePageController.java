package io.github.dug22.cipherlabs.ui.controllers.types;

import io.github.dug22.cipherlabs.ui.animation.CodeRainEffect;
import io.github.dug22.cipherlabs.ui.builder.GridPaneConstraintsBuilder;
import io.github.dug22.cipherlabs.ui.builder.LabelBuilder;
import io.github.dug22.cipherlabs.ui.controllers.AbstractController;
import io.github.dug22.cipherlabs.ui.task.general.CloseApplicationTask;
import io.github.dug22.cipherlabs.ui.task.general.SceneLoaderTask;
import io.github.dug22.cipherlabs.ui.utils.SceneManger;
import io.github.dug22.cipherlabs.ui.utils.StageProperties;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.TextAlignment;
import javafx.stage.FileChooser;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

public class HomePageController extends AbstractController {

    @FXML
    private AnchorPane homePageContainer;
    private final Label loadDocumentLabel = createNavigationLabel("Load Document");
    private final Label lockSpotLabel = createNavigationLabel("Lock Spot");
    private final Label resourcesLabel = createNavigationLabel("Resources");
    private final Label closeApplicationLabel = createNavigationLabel("Close Application");
    private final VBox loadDocumentCard = createCard("📁", "Load Document", "Load a document you wish to work with, with this tool (.txt)");
    private final VBox lockSpotCard = createCard("\uD83E\uDD16", "Lock Spot", "Use Cipher Labs builtin machine learning tool Lock Spot. Find more information from dug22's GitHub");
    private final VBox resourcesCard = createCard("\uD83D\uDCD6", "Resources", "Resources for how CipherLabs works and educational material about ciphers.");
    private final VBox closeApplicationCard = createCard("❌", "Close Application", "Exit the program safely.");
    private final GridPane gridPane = getGridPane();
    public static File selectedDocument;

    public HomePageController() {

    }

    @FXML
    private void initialize() {
        defineAnchorPaneProperties();
        defineGridPaneConstraints();
        defineNavigationHeader();
        defineMainContentBackgroundImage();
        CodeRainEffect codeRainEffect = new CodeRainEffect(gridPane);
        codeRainEffect.execute();
        defineMainContentOptions();
        gridPane.getStyleClass().add("grid-pane");
        homePageContainer.getChildren().setAll(gridPane);
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
        navigationBox.setSpacing(40);
        List<Label> navigationLabels = Arrays.asList(loadDocumentLabel, lockSpotLabel, resourcesLabel, closeApplicationLabel);
        for (Label navigationLabel : navigationLabels) {
            HBox.setHgrow(navigationLabel, Priority.ALWAYS);
        }
        navigationBox.getStyleClass().add("navigation-box");
        navigationBox.getChildren().addAll(navigationLabels);
        GridPane.setHgrow(navigationBox, Priority.ALWAYS);
        gridPane.add(navigationBox, 0, 0);
    }

    @Override
    protected void defineControlListeners() {
        Stream.of(loadDocumentLabel, lockSpotLabel, resourcesLabel, closeApplicationLabel).forEach(label -> label.setOnMouseEntered((_) -> label.getStyleClass().setAll("home-page-navigation-label-hover")));
        Stream.of(loadDocumentLabel, lockSpotLabel, resourcesLabel, closeApplicationLabel).forEach(label -> label.setOnMouseExited((_) -> label.getStyleClass().setAll("home-page-navigation-label")));
        Stream.of(loadDocumentCard, lockSpotCard, resourcesCard, closeApplicationCard).forEach(card -> card.setOnMouseEntered((_) -> {
            card.getStyleClass().setAll("card-layout-hover");
            card.setTranslateY(-5);
        }));
        Stream.of(loadDocumentCard, lockSpotCard, resourcesCard, closeApplicationCard).forEach(card -> card.setOnMouseExited((_) -> {
            card.getStyleClass().setAll("card-layout");
            card.setTranslateY(0);
        }));
        Stream.of(loadDocumentLabel, loadDocumentCard).forEach(control -> control.setOnMouseClicked((_) -> showFileChooser()));
        Stream.of(lockSpotLabel, lockSpotCard).forEach(control -> control.setOnMouseClicked((_) -> new SceneLoaderTask("lock-spot-page.fxml", new StageProperties("Lock Spot Page")).run()));
        Stream.of(resourcesLabel, resourcesCard).forEach(control -> control.setOnMouseClicked((_) -> new SceneLoaderTask("resource-page.fxml", new StageProperties("Cipher Labs Resources")).run()));
        Stream.of(closeApplicationLabel, closeApplicationCard).forEach(control -> control.setOnMouseClicked((_) -> new CloseApplicationTask().run()));
    }

    private Label createNavigationLabel(String text) {
        Label navigationLabel = new LabelBuilder.Builder()
                .setText(text)
                .setBold(true)
                .setFontSize(18)
                .setTextColor(Color.WHITE)
                .setAlignment(Pos.CENTER)
                .setMaxWidth(Double.MAX_VALUE)
                .setCursor(Cursor.HAND)
                .build();
        String baseStyle = "home-page-navigation-label";
        navigationLabel.getStyleClass().setAll(baseStyle);
        return navigationLabel;
    }

    private void defineMainContentBackgroundImage() {
        String backgroundImagePath = getClass().getResource("/resource-material/imgs/cipher-labs-home-page-background.png").toExternalForm();
        Image image = new Image(backgroundImagePath);
        int imageWidth = 100;
        int imageHeight = 100;
        boolean widthAsPercentage = true;
        boolean heightAsPercentage = true;
        boolean contain = true;
        boolean cover = false;
        BackgroundSize imageBackgroundSize = new BackgroundSize(
                imageWidth,
                imageHeight,
                widthAsPercentage,
                heightAsPercentage,
                contain,
                cover
        );
        BackgroundImage backgroundImage = new BackgroundImage(
                image,
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER,
                imageBackgroundSize
        );
        Region backgroundImageRegion = new Region();

        backgroundImageRegion.setBackground(new Background(backgroundImage));
        backgroundImageRegion.setMaxWidth(Double.MAX_VALUE);
        backgroundImageRegion.setMaxHeight(Double.MAX_VALUE);
        gridPane.add(backgroundImageRegion, 0, 1);
        GridPane.setHgrow(backgroundImageRegion, Priority.ALWAYS);
        GridPane.setVgrow(backgroundImageRegion, Priority.ALWAYS);
    }

    private void defineMainContentOptions() {
        HBox optionsContainer = new HBox(40);
        optionsContainer.setTranslateY(270);
        optionsContainer.setAlignment(Pos.CENTER);
        optionsContainer.getChildren().addAll(loadDocumentCard, lockSpotCard, resourcesCard, closeApplicationCard);
        gridPane.add(optionsContainer, 0, 1);
        optionsContainer.setPickOnBounds(false);
    }


    private VBox createCard(String emoji, String title, String description) {
        VBox card = new VBox(10);
        card.setAlignment(Pos.CENTER);
        card.setPadding(new Insets(20));
        card.setPrefSize(250, 200);
        card.setMaxSize(Region.USE_PREF_SIZE, Region.USE_PREF_SIZE);
        String baseStyle = "card-layout";
        card.getStyleClass().setAll(baseStyle);
        card.setCursor(Cursor.HAND);
        Label avatarLabel =  new LabelBuilder.Builder().setText(emoji)
                .setFontSize(40)
                .build();
        Label titleLabel = new LabelBuilder.Builder()
                .setText(title)
                .setFontSize(16)
                .setBold(true)
                .build();
        Label descriptionLabel = new LabelBuilder.Builder()
                .setText(description)
                .setFontSize(12)
                .setWrapText(true)
                .setTextAlignment(TextAlignment.CENTER)
                .build();
        card.getChildren().addAll(avatarLabel, titleLabel, descriptionLabel);
        return card;
    }


    private void showFileChooser() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Load Document");
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Text Files", "*.txt"));
        File selectedFile = fileChooser.showOpenDialog(SceneManger.getCurrentStage());
        if (selectedFile != null) {
            selectedDocument = selectedFile;
            new SceneLoaderTask("work-station.fxml", new StageProperties("Cipher Labs Workstation " + selectedFile.getName())).run();
        }
    }

    public static File getSelectedDocument() {
        return selectedDocument;
    }
}