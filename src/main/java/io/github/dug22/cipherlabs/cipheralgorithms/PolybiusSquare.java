package io.github.dug22.cipherlabs.cipheralgorithms;

import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Set;

public class PolybiusSquare {

    private static final int matrixRows = 5;
    private static final int matrixColumns = 5;
    private static final char disallowedCharacter = 'J';

    public static char[][] createPolybiusSquare() {
        char[][] polybiusSquare = new char[matrixRows][matrixColumns];
        final char[] letter = new char[]{'A'};

        for (int i = 0; i < matrixRows; i++) {
            for (int j = 0; j < matrixColumns; j++) {
                if (letter[0] == disallowedCharacter) {
                    letter[0]++;
                }
                polybiusSquare[i][j] = letter[0];
                letter[0]++;
            }
        }

        return polybiusSquare;
    }

    public static char[][] createPolybiusSquareWithKey(String key) {
        char[][] polybiusSquare = new char[matrixRows][matrixColumns];
        Set<Character> uniqueCharacters = new LinkedHashSet<>();
        for (char keyCharacter : key.toCharArray()) {
            if (Character.isLetter(keyCharacter) && keyCharacter != disallowedCharacter) {
                uniqueCharacters.add(keyCharacter);
            }
        }

        String alphabet = "ABCEDEFGHIJKLMOPQRSTUVWXYZ";
        for (char character : alphabet.toCharArray()) {
            if (Character.isLetter(character) && character != disallowedCharacter) {
                uniqueCharacters.add(character);
            }
        }

        Iterator<Character> uniqueCharactersIterator = uniqueCharacters.iterator();
        for (int i = 0; i < matrixRows; i++) {
            for (int j = 0; j < matrixColumns; j++) {
                if (uniqueCharactersIterator.hasNext()) {
                    polybiusSquare[i][j] = uniqueCharactersIterator.next();
                }
            }
        }

        return polybiusSquare;
    }
}
