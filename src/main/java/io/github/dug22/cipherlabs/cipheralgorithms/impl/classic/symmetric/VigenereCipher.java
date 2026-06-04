package io.github.dug22.cipherlabs.cipheralgorithms.impl.classic.symmetric;

import io.github.dug22.cipherlabs.cipheralgorithms.impl.classic.ClassicSymmetricCipher;
import io.github.dug22.cipherlabs.cipheralgorithms.parameters.AlphabeticParameters;

public class VigenereCipher extends ClassicSymmetricCipher implements AlphabeticParameters {


    @Override
    public String name() {
        return "Vigenère Cipher";
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
        return -1;
    }

    @Override
    public String encrypt(String plaintext, String key) {
        int plaintextLength = plaintext.length();
        int keyLength = key.length();
        StringBuilder ciphertext = new StringBuilder();
        int unknownCharacters = 0;
        for (int i = 0; i < plaintextLength; i++) {
            char plaintextCharacter = plaintext.charAt(i);
            if (!isInAlphabet(plaintextCharacter)) {
                ciphertext.append(plaintextCharacter);
                unknownCharacters++;
            } else {
                int keyIndex = (i - unknownCharacters) % keyLength;
                char cipherCharacter = (char) ('A' + ((plaintextCharacter - 'A') + (key.charAt(keyIndex) - 'A')) % 26);
                ciphertext.append(cipherCharacter);
            }
        }
        return ciphertext.toString();
    }


    @Override
    public String decrypt(String ciphertext, String key) {
        int ciphertextLength = ciphertext.length();
        int keyLength = key.length();
        StringBuilder plaintext = new StringBuilder();
        int unknownCharacters = 0;
        for (int i = 0; i < ciphertextLength; i++) {
            char ciphertextCharacter = ciphertext.charAt(i);
            if (!isInAlphabet(ciphertextCharacter)) {
                plaintext.append(ciphertextCharacter);
                unknownCharacters++;
            } else {
                int keyIndex = (i - unknownCharacters) % keyLength;
                char plaintextCharacter = (char) ('A' + (ciphertextCharacter - 'A' - (key.charAt(keyIndex) - 'A') + 26) % 26);
                plaintext.append(plaintextCharacter);
            }
        }
        return plaintext.toString();
    }

}