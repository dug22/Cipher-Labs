package io.github.dug22.cipherlabs.ui.utils;

import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

import java.util.Arrays;

public class TextUtils {

    public static TextFlow createTextWithBoldedKeywords(String fullText, String[] keywords) {
        TextFlow textFlow = new TextFlow();
        String[] words = fullText.split(" ");
        int count = 0;
        for (String word : words) {
            Text wordNode = new Text(word);
            if (Arrays.stream(keywords).anyMatch(keyword -> keyword.equalsIgnoreCase(word))) {
                wordNode.setFont(Font.font("Arial", FontWeight.BOLD, 14));
            } else {
                wordNode.setFont(Font.font("Arial", FontWeight.NORMAL, 14));
            }
            textFlow.getChildren().add(wordNode);
            if (count < words.length - 1) {
                textFlow.getChildren().add(new Text(" "));
            }
            count++;
        }
        return textFlow;
    }
}