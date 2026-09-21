package io.github.dug22.cipherlabs.core.ciphers.algorithm.classic.symmetric.enigma;

import io.github.dug22.cipherlabs.core.utils.Alphabets;

public class Keyboard {

    public int forward(char letter){
        return Alphabets.ALPHABET.indexOf(letter);
    }

    public char backward(int index){
        return Alphabets.ALPHABET.charAt(index);
    }
}
