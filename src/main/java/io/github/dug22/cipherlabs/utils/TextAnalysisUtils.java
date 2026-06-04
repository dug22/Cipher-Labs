package io.github.dug22.cipherlabs.utils;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

public class TextAnalysisUtils {

    public static Map<Character, Double> getFrequencies(String text) {
        Map<Character, Double> frequencies = new HashMap<>();
        int textLength = text.length();

        if (textLength == 0) {
            return frequencies;
        }

        Map<Character, Integer> counts = new HashMap<>();
        for (char character : text.toCharArray()) {
            counts.put(character, counts.getOrDefault(character, 0) + 1);
        }
        for (Map.Entry<Character, Integer> entry : counts.entrySet()) {
            frequencies.put(entry.getKey(), (double) entry.getValue() / textLength);
        }
        return frequencies;
    }

    public static double findIndexOfCoincidence(String text) {
        int ciphertextLength = text.length();
        HashMap<Character, Integer> characterFrequencyMap = new HashMap<>();
        for (char character : text.toCharArray()) {
            if (Character.isLetter(character)) {
                characterFrequencyMap.merge(character, 1, Integer::sum);
            }
        }

        long numerator = 0;
        for (int frequency : characterFrequencyMap.values()) {
            numerator += (long) frequency * (frequency - 1);
        }

        double denominator = (double) ciphertextLength * (ciphertextLength - 1);
        return numerator / denominator;
    }

    public static double hasLetterJ(String text) {
        return text.contains("J") ? 1D : 0D;
    }

    public static double hasDigits(String text) {
        return text.matches(".*\\d.*") ? 1 : 0;
    }

    public static double hasDoubleLettersOrNumbers(String text) {
        for (int i = 0; i < text.length() - 1; i++) {
            if (text.charAt(i) == text.charAt(i + 1)) {
                return 1D;
            }
        }

        return 0D;
    }

    public static long getUniqueCharacterCount(String text) {
        Set<Character> characterSet = new HashSet<>();
        for (char character : text.toCharArray()) {
                characterSet.add(character);
        }

        return characterSet.size();
    }

    public static Character getMostFrequentCharacter(String text){
        return text.chars()
                .mapToObj(c -> (char) c)
                .filter(c -> c != ' ')
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()))
                .entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse('?');
    }
}
