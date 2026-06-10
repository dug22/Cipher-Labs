package io.github.dug22.cipherlabs.ciphers.algorithm.classic.symmetric;

import io.github.dug22.cipherlabs.CipherLabsCore;
import io.github.dug22.cipherlabs.ciphers.PolybiusSquare;
import io.github.dug22.cipherlabs.ciphers.algorithm.classic.ClassicSymmetricCipher;
import io.github.dug22.cipherlabs.ciphers.steps.PlayFairCipherStep;
import io.github.dug22.cipherlabs.config.ConfigurationManager;
import io.github.dug22.cipherlabs.config.ConfigurationRegistry;
import io.github.dug22.cipherlabs.config.impl.EncryptionDecryptionConfig;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PlayfairCipher extends ClassicSymmetricCipher {

    private final CipherLabsCore instance = CipherLabsCore.getInstance();
    private final ConfigurationManager configurationManager = instance.getConfigManager();
    private final ConfigurationRegistry configurationRegistry = instance.getConfigRegistry();
    private final EncryptionDecryptionConfig encryptionDecryptionConfig = (EncryptionDecryptionConfig) configurationRegistry.getConfigFile("encryption-decryption-config");
    private final File encryptionDecryptionFileInstance = encryptionDecryptionConfig.getFile();
    private List<PlayFairCipherStep> steps;
    private char[][] polybiusSquare;

    @Override
    public String name() {
        return "Playfair Cipher";
    }

    @Override
    public String versionIntroduced() {
        return "1.0.0";
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
        plaintext = getPair(plaintext);
        return encryptOrDecrypt(plaintext, key, true);
    }

    @Override
    public String decrypt(String ciphertext, String key) {
        ciphertext = getPair(ciphertext);
        return encryptOrDecrypt(ciphertext, key, false);
    }

    private String getPair(String text) {
        text = text.toUpperCase().replaceAll("[^A-Z]", "");
        StringBuilder foundPair = new StringBuilder();
        int textLength = text.length();
        for (int i = 0; i < textLength; i++) {
            char firstCharacter = text.charAt(i);
            char secondCharacter;
            char primaryFiller = configurationManager.getCharacterProperty(encryptionDecryptionFileInstance, "play-fair-cipher-primary-filler-character");
            if (i + 1 < textLength) {
                secondCharacter = text.charAt(i + 1);
            } else {
                secondCharacter = primaryFiller;
            }

            if (firstCharacter != secondCharacter) {
                foundPair.append(firstCharacter);
                foundPair.append(secondCharacter);
                i++;
            } else {
                char secondaryFiller = configurationManager.getCharacterProperty(encryptionDecryptionFileInstance, "play-fair-cipher-secondary-filler-character");
                char filler = (firstCharacter == primaryFiller) ? secondaryFiller : primaryFiller;
                foundPair.append(firstCharacter);
                foundPair.append(filler);
            }
        }

        return foundPair.toString();
    }

    private String encryptOrDecrypt(String pair, String key, boolean isEncrypt) {
        steps = new ArrayList<>();
        polybiusSquare = PolybiusSquare.createPolybiusSquareWithKey(key);
        int firstRow = 0, firstColumn = 0, secondRow = 0, secondColumn = 0;
        StringBuilder result = new StringBuilder();
        for (int i = 0; i + 1 < pair.length(); i += 2) {
            boolean foundX = false;
            boolean foundY = false;
            for (int j = 0; j < 5; j++) {
                for (int k = 0; k < 5; k++) {
                    if (pair.charAt(i) == polybiusSquare[j][k]) {
                        firstRow = j;
                        firstColumn = k;
                        foundX = true;
                    }
                    if (pair.charAt(i + 1) == polybiusSquare[j][k]) {
                        secondRow = j;
                        secondColumn = k;
                        foundY = true;
                    }
                }
            }

            if (foundX && foundY) {
                int shift = isEncrypt ? 1 : 4;
                if (firstRow == secondRow) {
                    result.append(polybiusSquare[firstRow][(firstColumn + shift) % 5]);
                    result.append(polybiusSquare[secondRow][(secondColumn + shift) % 5]);
                    steps.add(new PlayFairCipherStep(
                            pair.charAt(i), polybiusSquare[firstRow][(firstColumn + shift) % 5],
                            pair.charAt(i + 1), polybiusSquare[secondRow][(secondColumn + shift) % 5]
                    ));
                } else if (firstColumn == secondColumn) {
                    result.append(polybiusSquare[(firstRow + shift) % 5][firstColumn]);
                    result.append(polybiusSquare[(secondRow + shift) % 5][secondColumn]);
                    steps.add(new PlayFairCipherStep(
                            pair.charAt(i), polybiusSquare[(firstRow + shift) % 5][firstColumn],
                            pair.charAt(i + 1), polybiusSquare[(secondRow + shift) % 5][secondColumn]
                    ));
                } else {
                    result.append(polybiusSquare[firstRow][secondColumn]);
                    result.append(polybiusSquare[secondRow][firstColumn]);
                    steps.add(new PlayFairCipherStep(
                            pair.charAt(i), polybiusSquare[firstRow][secondColumn],
                            pair.charAt(i + 1), polybiusSquare[secondRow][firstColumn]
                    ));
                }
            }
        }
        return result.toString();
    }

    public List<PlayFairCipherStep> getSteps(){
        return steps;
    }

    public char[][] getPolybiusSquare() {
        return polybiusSquare;
    }
}
