package io.github.dug22.cipherlabs.cipheralgorithms.parameters;

import io.github.dug22.cipherlabs.utils.Alphabets;

public interface AlphabeticParameters {

    default String getAlphabet() {
        return Alphabets.ALPHABET;
    }

    default boolean isInAlphabet(char value) {
        return getAlphabet().contains(String.valueOf(value));
    }
}
