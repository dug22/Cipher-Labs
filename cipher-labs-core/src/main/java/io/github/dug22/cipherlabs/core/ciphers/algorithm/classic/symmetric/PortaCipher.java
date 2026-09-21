package io.github.dug22.cipherlabs.core.ciphers.algorithm.classic.symmetric;


import io.github.dug22.cipherlabs.core.ciphers.algorithm.classic.ClassicSymmetricCipher;

public class PortaCipher extends ClassicSymmetricCipher {

    @Override
    public String name() {
        return "Porta Cipher";
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
        return encryptOrDecrypt(plaintext, key);
    }

    @Override
    public String decrypt(String ciphertext, String key) {
        return encryptOrDecrypt(ciphertext, key);
    }

    private String encryptOrDecrypt(String text, String key) {
        text = text.toUpperCase();
        key = key.toUpperCase();
        StringBuilder resultingText = new StringBuilder();
        int keyIndex = 0;
        for (int i = 0; i < text.length(); i++) {
            char currentTextCharacter = text.charAt(i);
            if (currentTextCharacter >= 'A' && currentTextCharacter <= 'Z') {
                char currentKeyCharacter = key.charAt(keyIndex % key.length());
                keyIndex++;
                int keyNumber = (currentKeyCharacter - 'A') / 2;
                if (currentTextCharacter <= 'M') {
                    currentTextCharacter = (char) ('N' + (currentTextCharacter - 'A' + keyNumber) % 13);
                } else {
                    currentTextCharacter = (char) ('A' + (currentTextCharacter - 'N' - keyNumber + 13) % 13);
                }
                resultingText.append(currentTextCharacter);
            } else {
                resultingText.append(currentTextCharacter);
            }
        }

        return resultingText.toString();
    }
}
