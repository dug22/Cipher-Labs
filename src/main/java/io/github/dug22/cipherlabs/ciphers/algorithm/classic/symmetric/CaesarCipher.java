package io.github.dug22.cipherlabs.ciphers.algorithm.classic.symmetric;

import io.github.dug22.cipherlabs.ciphers.algorithm.classic.ClassicSymmetricCipher;
import io.github.dug22.cipherlabs.ciphers.steps.CaesarCipherStep;
import io.github.dug22.cipherlabs.utils.Alphabets;

import java.util.ArrayList;
import java.util.List;

public class CaesarCipher extends ClassicSymmetricCipher {

    private List<CaesarCipherStep> steps;

    public CaesarCipher() {
    }

    @Override
    public String name() {
        return "Caesar Cipher";
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
        return 25;
    }

    @Override
    public String encrypt(String plaintext, String key) {
        steps = new ArrayList<>();
        plaintext = plaintext.toUpperCase();
        int shift = Integer.parseInt(key);
        StringBuilder ciphertext = new StringBuilder();
        for (int i = 0; i < plaintext.length(); i++) {
            char plaintextCharacter = plaintext.charAt(i);
            if (!Alphabets.isInAlphabet(plaintextCharacter)) {
                ciphertext.append(plaintextCharacter);
            } else {
                char encryptedCharacter = (char) ((plaintextCharacter - 'A' + shift) % 26 + 'A');
                ciphertext.append(encryptedCharacter);
                steps.add(new CaesarCipherStep(plaintextCharacter, shift, encryptedCharacter));
            }
        }

        return ciphertext.toString();
    }

    @Override
    public String decrypt(String ciphertext, String key) {
        steps = new ArrayList<>();
        ciphertext = ciphertext.toUpperCase();
        int shift = Integer.parseInt(key);
        StringBuilder plaintext = new StringBuilder();
        for (int i = 0; i < ciphertext.length(); i++) {
            char cipherCharacter = ciphertext.charAt(i);
            if (!Alphabets.isInAlphabet(cipherCharacter)) {
                plaintext.append(cipherCharacter);
            } else {
                char decryptedCharacter = (char) ((cipherCharacter - 'A' - shift + 26) % 26 + 'A');
                plaintext.append(decryptedCharacter);
                steps.add(new CaesarCipherStep(cipherCharacter, shift, decryptedCharacter));
            }
        }

        return plaintext.toString();
    }

    public List<CaesarCipherStep> getSteps() {
        return steps;
    }
}