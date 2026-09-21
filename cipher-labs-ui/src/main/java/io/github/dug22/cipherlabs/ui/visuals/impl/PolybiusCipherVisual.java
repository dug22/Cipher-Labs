package io.github.dug22.cipherlabs.ui.visuals.impl;

import io.github.dug22.cipherlabs.core.ciphers.steps.PolybiusCipherStep;
import io.github.dug22.cipherlabs.ui.builder.LabelBuilder;
import io.github.dug22.cipherlabs.ui.forms.cipher.impl.PolybiusCipherForm;
import io.github.dug22.cipherlabs.ui.visuals.CipherVisual;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Pos;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.util.Duration;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class PolybiusCipherVisual extends CipherVisual {

    private final Dialog<String> form;
    private final Map<String, Label> letterLabelMap;
    private final Label[][] gridLabels;
    private final GridPane visualPane;
    private final Timeline animationTimeline;
    private final char[][] polybiusSquare;
    private final List<PolybiusCipherStep> steps;
    private static final Background ROW_BG = new Background(new BackgroundFill(Color.web("#E0F7FA"), CornerRadii.EMPTY, null)); // Light Cyan
    private static final Background COL_BG = new Background(new BackgroundFill(Color.web("#FFE0B2"), CornerRadii.EMPTY, null)); // Light Orange
    private static final Background TARGET_BG = new Background(new BackgroundFill(Color.YELLOW, CornerRadii.EMPTY, null));

    public PolybiusCipherVisual(PolybiusCipherForm form, GridPane visualPane, char[][] polybiusSquare, List<PolybiusCipherStep> steps) {
        super(visualPane);
        this.visualPane = visualPane;
        this.form = form;
        this.letterLabelMap = new ConcurrentHashMap<>();
        this.gridLabels = new Label[5][5];
        this.animationTimeline = new Timeline();
        this.polybiusSquare = polybiusSquare;
        this.steps = steps;
    }

    public void play() {
        buildVisualLayout();
        startAnimation(form,animationTimeline, steps.size());
    }

    private void buildVisualLayout() {
        visualPane.getChildren().clear();
        letterLabelMap.clear();

        Border cellBorder = new Border(new BorderStroke(
                Color.BLACK,
                BorderStrokeStyle.SOLID,
                CornerRadii.EMPTY,
                new BorderWidths(1)
        ));

        for (int row = 0; row < 5; row++) {
            for (int col = 0; col < 5; col++) {
                char letter = polybiusSquare[row][col];
                Label letterLabel = new LabelBuilder.Builder()
                        .setText(String.valueOf(letter))
                        .setAlignment(Pos.CENTER)
                        .build();

                visualPane.add(letterLabel, col, row);
                letterLabelMap.put(letterLabel.getText(), letterLabel);
                gridLabels[row][col] = letterLabel;

                letterLabel.setMinSize(30, 30);
                letterLabel.setBorder(cellBorder);
            }
        }

        visualPane.setAlignment(Pos.CENTER);
    }

    @Override
    protected KeyFrame createKeyFrame() {
        return new KeyFrame(Duration.seconds(2), (_) -> {
            if (steps.isEmpty()) {
                visualPane.getChildren().clear();
                return;
            }
            for (int r = 0; r < 5; r++) {
                for (int c = 0; c < 5; c++) {
                    gridLabels[r][c].setBackground(null);
                }
            }
            PolybiusCipherStep step = steps.removeFirst();
            int targetRow = -1;
            int targetCol = -1;
            if (step.from().length() == 1) {
                String targetChar = step.from();
                if ("J".equalsIgnoreCase(targetChar) && !letterLabelMap.containsKey("J")) {
                    targetChar = "I";
                }
                Label targetLabel = letterLabelMap.get(targetChar);
                if (targetLabel != null) {
                    targetRow = GridPane.getRowIndex(targetLabel);
                    targetCol = GridPane.getColumnIndex(targetLabel);
                }
            } else if (step.from().length() == 2 && Character.isDigit(step.from().charAt(0))) {
                targetRow = Character.getNumericValue(step.from().charAt(0)) - 1;
                targetCol = Character.getNumericValue(step.from().charAt(1)) - 1;
            }

            if (targetRow >= 0 && targetRow < 5 && targetCol >= 0 && targetCol < 5) {
                for (int c = 0; c < 5; c++) {
                    gridLabels[targetRow][c].setBackground(ROW_BG);
                }
                for (int r = 0; r < 5; r++) {
                    gridLabels[r][targetCol].setBackground(COL_BG);
                }
                gridLabels[targetRow][targetCol].setBackground(TARGET_BG);
            }
        });
    }
}