package io.github.dug22.cipherlabs.lockspot;



import io.github.dug22.cipherlabs.core.utils.Alphabets;
import io.github.dug22.cipherlabs.core.utils.TextUtils;

import java.util.Map;
import java.util.stream.IntStream;

public class LockSpotFeatureUtils {

    private static final int ALNUM_CHARACTERS_LENGTH = Alphabets.ALNUM.length();

    public static double[] getFeatures(String text) {
        text = text.toUpperCase().replaceAll("[^A-Z0-9]", "");
        double ioc = TextUtils.findIndexOfCoincidence(text);
        double haveLetterJ = TextUtils.hasLetterJ(text);
        double containsDigits = TextUtils.hasDigits(text);
        double containsDoubleLettersOrNumbers = TextUtils.hasDoubleLettersOrNumbers(text);
        Map<Character, Double> frequencyPercentages = TextUtils.getFrequencies(text);
        double[] features = new double[40];
        features[0] = ioc;
        features[1] = haveLetterJ;
        features[2] = containsDigits;
        features[3] = containsDoubleLettersOrNumbers;
        IntStream.range(0, ALNUM_CHARACTERS_LENGTH).forEach(i -> features[4 + i] = frequencyPercentages.getOrDefault(Alphabets.ALNUM.charAt(i), 0.0));
        return features;
    }
}


