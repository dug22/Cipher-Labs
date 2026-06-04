package io.github.dug22.cipherlabs.cipheralgorithms.impl.classic.symmetric;

import io.github.dug22.cipherlabs.cipheralgorithms.impl.classic.ClassicSymmetricCipher;
import io.github.dug22.cipherlabs.cipheralgorithms.parameters.AlphabeticParameters;

public class CaesarCipher extends ClassicSymmetricCipher implements AlphabeticParameters {

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
        plaintext = plaintext.toUpperCase();
        int shift = Integer.parseInt(key);
        StringBuilder ciphertext = new StringBuilder();
        for (int i = 0; i < plaintext.length(); i++) {
            char character = plaintext.charAt(i);
            if (!isInAlphabet(character)) {
                ciphertext.append(character);
            } else {
                ciphertext.append((char) ((character - 'A' + shift) % 26 + 'A'));
            }
        }

        return ciphertext.toString();
    }

    @Override
    public String decrypt(String ciphertext, String key) {
        ciphertext = ciphertext.toUpperCase();
        int shift = Integer.parseInt(key);
        StringBuilder plaintext = new StringBuilder();
        for (int i = 0; i < ciphertext.length(); i++) {
            char character = ciphertext.charAt(i);
            if (!isInAlphabet(character)) {
                plaintext.append(character);
            } else {
                plaintext.append((char) ((character - 'A' - shift + 26) % 26 + 'A'));
            }
        }

        return plaintext.toString();
    }
}