package io.github.dug22.cipherlabs.ui.animation;

import io.github.dug22.cipherlabs.utils.Alphabets;
import javafx.animation.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.util.Duration;

import java.util.Random;

public class CodeRainEffect extends AbstractAnimation {


    private final String characters = Alphabets.ALPHABET + Alphabets.ALPHABET.toLowerCase() + "0123456789!@#$%^&*()";
    private final Random random = new Random();
    private Timeline spawnTimeline;
    private final GridPane gridPane = getGridPane();
    private final Pane canvas = getCanvas();

    public CodeRainEffect(GridPane gridPane) {
        super(gridPane);
        init();
    }


    @Override
    public void init() {
        spawnTimeline = new Timeline(new KeyFrame(Duration.millis(100), e -> spawnRandomWord()));
        spawnTimeline.setCycleCount(Timeline.INDEFINITE);
        gridPane.add(canvas, 0, 1);
    }

    @Override
    public void execute() {
        spawnTimeline.play();
    }


    private void spawnRandomWord() {
        Text word = new Text(generateRandomWord());
        word.setFont(Font.font("Arial", 12));
        word.setFill(Color.BLACK);
        double randomX = random.nextDouble(0D, canvas.getWidth());
        word.setLayoutX(randomX);
        canvas.getChildren().add(word);
        TranslateTransition fallTransition = new TranslateTransition(Duration.seconds(random.nextInt(3, 6)), word);
        fallTransition.setToY(canvas.getHeight() + 100);
        FadeTransition fade = new FadeTransition(Duration.seconds(random.nextInt(3, 6)), word);
        fade.setToValue(0);
        ParallelTransition parallelTransition = new ParallelTransition(fallTransition, fade);
        parallelTransition.setOnFinished(_ -> {
            canvas.getChildren().remove(word);
            parallelTransition.setOnFinished(null);
        });
        parallelTransition.play();
    }

    private String generateRandomWord() {
        StringBuilder randomWord = new StringBuilder();
        int charactersLength = characters.length();
        int length = random.nextInt(4, 6);
        for (int i = 0; i < length; i++) {
            randomWord.append(characters.charAt(random.nextInt(charactersLength))).append("\n");
        }

        return randomWord.toString();
    }
}
