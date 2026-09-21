package io.github.dug22.cipherlabs.core.ciphers.algorithm.classic.symmetric;


import io.github.dug22.cipherlabs.core.ciphers.algorithm.classic.ClassicSymmetricCipher;
import io.github.dug22.cipherlabs.core.ciphers.steps.PolybiusCipherStep;
import io.github.dug22.cipherlabs.core.utils.PolybiusSquare;

import java.util.ArrayList;
import java.util.List;

public class PolybiusCipher extends ClassicSymmetricCipher {

    private char[][] polybiusSquare;
    private List<PolybiusCipherStep> steps;

    @Override
    public String name() {
        return "Polybius Cipher";
    }

    @Override
    public String versionIntroduced() {
        return "1.0.1";
    }

    @Override
    public boolean isClassic() {
        return true;
    }

    @Override
    public boolean isModern() {
        return false;
    }

    @Override
    public int maxKeyLength() {
        return 0;
    }

    @Override
    public String encrypt(String plaintext, String key) {
        steps = new ArrayList<>();
        plaintext = plaintext.toUpperCase();
        key = key.toUpperCase();
        if (key.isEmpty()) {
            polybiusSquare = PolybiusSquare.createPolybiusSquare();
        } else {
            polybiusSquare = PolybiusSquare.createPolybiusSquareWithKey(key);
        }
        int rowSize = 5;
        int colSize = 5;
        char[] plaintextCharArray = plaintext.toCharArray();
        StringBuilder ciphertext = new StringBuilder();
        for (int i = 0; i < plaintext.length(); i++) {
            boolean found = false;
            for (int row = 0; row < rowSize; row++) {
                for (int col = 0; col < colSize; col++) {
                    if (polybiusSquare[row][col] == plaintextCharArray[i]) {
                        ciphertext.append(row + 1).append(col + 1);
                        steps.add(
                                new PolybiusCipherStep(
                                        String.valueOf(polybiusSquare[row][col]),
                                        String.valueOf(row + 1) +
                                        (col + 1)));
                        found = true;
                        break;
                    } else if (plaintextCharArray[i] == 'J' && polybiusSquare[row][col] == 'I') {
                        ciphertext.append(row + 1).append(col + 1);
                        steps.add(new PolybiusCipherStep("J", String.valueOf(row + 1) + (col + 1)));
                        found = true;
                        break;
                    }
                }

                if (found) break;
            }

            if (!found) {
                ciphertext.append(plaintextCharArray[i]);
            }
        }
        return ciphertext.toString();
    }

    @Override
    public String decrypt(String ciphertext, String key) {
        steps = new ArrayList<>();
        ciphertext = ciphertext.toUpperCase();
        key = key.toUpperCase();
        if (key.isEmpty()) {
            polybiusSquare = PolybiusSquare.createPolybiusSquare();
        } else {
            polybiusSquare = PolybiusSquare.createPolybiusSquareWithKey(key);
        }

        StringBuilder plaintext = new StringBuilder();
        char[] ciphertextCharacters = ciphertext.toCharArray();
        for (int i = 0; i < ciphertext.length(); i++) {
            if (Character.isDigit(ciphertextCharacters[i]) && i + 1 < ciphertext.length() && Character.isDigit(ciphertextCharacters[i + 1])) {
                int row = Character.getNumericValue(ciphertextCharacters[i]);
                int col = Character.getNumericValue(ciphertextCharacters[i + 1]);
                plaintext.append(polybiusSquare[row - 1][col - 1]);
                steps.add(
                        new PolybiusCipherStep(
                                String.valueOf(row) + col,
                                String.valueOf(polybiusSquare[row - 1][col - 1])));
                i++;
            } else {
                plaintext.append(ciphertextCharacters[i]);
            }
        }

        return plaintext.toString();
    }

    public char[][] getPolybiusSquare() {
        return polybiusSquare;
    }

    public List<PolybiusCipherStep> getSteps() {
        return steps;
    }
}
