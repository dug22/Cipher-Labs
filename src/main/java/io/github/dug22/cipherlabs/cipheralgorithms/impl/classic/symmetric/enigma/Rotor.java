package io.github.dug22.cipherlabs.cipheralgorithms.impl.classic.symmetric.enigma;

import io.github.dug22.cipherlabs.utils.Alphabets;

public class Rotor {

    private final String wiring;
    private String left;
    private char notch;

    public Rotor(String wiring, char notch) {
        this.left = Alphabets.ALPHABET;
        this.wiring = wiring;
        this.notch = notch;
    }

    public int forward(int signal) {
        char inputLetter = left.charAt(signal);
        int wiringIndex = Alphabets.ALPHABET.indexOf(inputLetter);
        char outputLetter = wiring.charAt(wiringIndex);
        return left.indexOf(outputLetter);
    }

    public int backward(int signal) {
        char inputLetter = left.charAt(signal);
        int wiringIndex = wiring.indexOf(inputLetter);
        char outputLetter = Alphabets.ALPHABET.charAt(wiringIndex);
        return left.indexOf(outputLetter);
    }

    public void rotate() {
        rotate(1, true);
    }

    public void rotate(int n, boolean forward) {
        for (int i = 0; i < n; i++) {
            left = forward ? left.substring(1) + left.charAt(0) : left.substring(25) + left.substring(0, 25);
        }
    }

    public void rotateToLetter(char letter) {
        int index = Alphabets.ALPHABET.indexOf(letter);
        rotate(index, true);
    }

    public String getLeft() {
        return left;
    }

    public String getRight() {
        return wiring;
    }

    private void reset() {
        this.left = Alphabets.ALPHABET;
    }

    public char getNotch() {
        return notch;
    }

    public void setRing(int n) {
        reset();
        rotate(n - 1, false);
        int notchIndex = Alphabets.ALPHABET.indexOf(notch);
        int shiftedNotchIndex = ((notchIndex - (n - 1)) % 26 + 26) % 26;
        notch = Alphabets.ALPHABET.charAt(shiftedNotchIndex);
    }
}