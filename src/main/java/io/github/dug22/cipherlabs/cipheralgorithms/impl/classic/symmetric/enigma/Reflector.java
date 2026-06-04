package io.github.dug22.cipherlabs.cipheralgorithms.impl.classic.symmetric.enigma;

import io.github.dug22.cipherlabs.utils.Alphabets;

public class Reflector {

    private final String right;
    private final String left;

    public Reflector(String wiring) {
        this.left = Alphabets.ALPHABET;
        this.right = wiring;
    }

    public int reflect(int signal) {
        char letter = this.right.charAt(signal);
        return left.indexOf(letter);
    }
}