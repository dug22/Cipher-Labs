package io.github.dug22.cipherlabs.cipheralgorithms.impl.classic.symmetric.enigma;

import io.github.dug22.cipherlabs.utils.Alphabets;

public class Keyboard {

    public int forward(char letter){
        return Alphabets.ALPHABET.indexOf(letter);
    }

    public char backward(int index){
        return Alphabets.ALPHABET.charAt(index);
    }
}
