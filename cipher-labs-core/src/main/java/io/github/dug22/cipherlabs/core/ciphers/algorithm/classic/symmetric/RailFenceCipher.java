package io.github.dug22.cipherlabs.core.ciphers.algorithm.classic.symmetric;

import io.github.dug22.cipherlabs.core.ciphers.algorithm.classic.ClassicSymmetricCipher;

import java.util.Arrays;

public class RailFenceCipher extends ClassicSymmetricCipher {

    private char[][] matrix;

    @Override
    public String name() {
        return "RailFence Cipher";
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
        String ciphertext;
        int keyAsInt = Integer.parseInt(key);
        int plaintextLength = plaintext.length();
        matrix = buildMatrix(keyAsInt, plaintextLength);
        int matrixLength = matrix.length;
        int rowIncrement = 1;
        for (int row = 0, col = 0; col < matrix[row].length; col++) {
            if (row + rowIncrement == matrixLength || row + rowIncrement == -1) {
                rowIncrement *= -1;
            }

            matrix[row][col] = plaintext.charAt(col);
            row += rowIncrement;
        }
        ciphertext = buildStringFromMatrix(matrix);
        return ciphertext;
    }

    @Override
    public String decrypt(String ciphertext, String key) {
        String plaintext;
        int ciphertextLength = ciphertext.length();
        int keyAsInt = Integer.parseInt(key);
        matrix = buildMatrix(keyAsInt, ciphertextLength);
        int matrixLength = matrix.length;
        int rowIncrement = 1;
        int textIndex = 0;
        for (int selectedRow = 0; selectedRow < matrixLength; selectedRow++) {
            for (int row = 0, col = 0; col < matrix[row].length; col++) {
                if (row + rowIncrement == matrixLength || row + rowIncrement == -1) {
                    rowIncrement *= -1;
                }

                if (row == selectedRow) {
                    matrix[row][col] = ciphertext.charAt(textIndex++);
                }

                row += rowIncrement;
            }
        }

        matrix = transposeMatrix(matrix);
        plaintext = buildStringFromMatrix(matrix);
        return plaintext;
    }

    private char[][] transposeMatrix(char[][] matrix) {
        char[][] result = buildMatrix(matrix[0].length, matrix.length);
        for (int row = 0; row < matrix.length; row++) {
            for (int col = 0; col < matrix[row].length; col++) {
                result[col][row] = matrix[row][col];
            }
        }

        return result;
    }

    private char[][] buildMatrix(int rows, int cols) {
        char[][] matrix = new char[rows][];
        for (int row = 0; row < matrix.length; row++) {
            matrix[row] = new char[cols];
        }
        return matrix;
    }

    private String buildStringFromMatrix(char[][] matrix) {
        StringBuilder result = new StringBuilder();
        for (char[] row : matrix) {
            for (char current : row) {
                if (current != '\0') {
                    result.append(current);
                }
            }
        }

        return result.toString();
    }

    public String getCleanMatrix(char separator) {
        StringBuilder matrixBuilder = new StringBuilder();
        if (matrix != null) {
            for (char[] matrixChars : matrix) {
                for (int i = 0; i < matrixChars.length; i++) {
                    if (matrixChars[i] == '\0') {
                        matrixChars[i] = separator;
                    }
                }

                matrixBuilder.append(Arrays.toString(matrixChars)).append("\n");
            }
        }

        return matrixBuilder.toString()
                .replace("[", "")
                .replace("]", "")
                .replace(",", "");
    }
}
