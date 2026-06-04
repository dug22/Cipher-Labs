package io.github.dug22.cipherlabs.cipheralgorithms.impl.classic.symmetric.enigma;

import io.github.dug22.cipherlabs.utils.Alphabets;

import java.util.List;

public class Plugboard {

    private String left;
    private String right;

    public Plugboard(List<String> pairs) {
        this.left = Alphabets.ALPHABET;
        this.right = Alphabets.ALPHABET;
        char[] leftCharacters = left.toCharArray();
        pairs.forEach(pair -> {
            char a = pair.charAt(0);
            char b = pair.charAt(1);
            int positionA = left.indexOf(a);
            int positionB = right.indexOf(b);
            leftCharacters[positionA] = b;
            leftCharacters[positionB] = a;
            left = new String(leftCharacters);
        });
    }

    public int forward(int signal) {
        char letter = this.right.charAt(signal);
        return left.indexOf(letter);
    }


    public int backward(int signal) {
        char letter = this.left.charAt(signal);
        return right.indexOf(letter);
    }
}
