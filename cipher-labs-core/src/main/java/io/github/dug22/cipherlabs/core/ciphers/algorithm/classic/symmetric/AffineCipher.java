package io.github.dug22.cipherlabs.core.ciphers.algorithm.classic.symmetric;


import io.github.dug22.cipherlabs.core.ciphers.algorithm.classic.ClassicSymmetricCipher;
import io.github.dug22.cipherlabs.core.utils.Alphabets;
import io.github.dug22.cipherlabs.core.utils.MathUtils;

public class AffineCipher extends ClassicSymmetricCipher {

    @Override
    public String name() {
        return "Affine Cipher";
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
        String[] keyArray = key.split(" ");
        int a = Integer.parseInt(keyArray[0]);
        int b = Integer.parseInt(keyArray[1]);
        StringBuilder ciphertext = new StringBuilder();
        for (int i = 0; i < plaintext.length(); i++) {
            char plaintextCharacter = plaintext.charAt(i);
            if (plaintextCharacter >= 'A' && plaintextCharacter <= 'Z') {
                int ciphertextNumericValue = ((a * (plaintextCharacter - 'A') + b) % 26 + 26) % 26;
                ciphertext.append((char) (ciphertextNumericValue + 'A'));
            } else if (plaintextCharacter >= 'a' && plaintextCharacter <= 'z') {
                int ciphertextNumericValue = ((a * (plaintextCharacter - 'a') + b) % 26 + 26) % 26;
                ciphertext.append((char) (ciphertextNumericValue + 'a'));
            } else {
                ciphertext.append(plaintextCharacter);
            }
        }
        return ciphertext.toString();
    }

    @Override
    public String decrypt(String ciphertext, String key) {
        String[] keyArray = key.split(" ");
        int a = Integer.parseInt(keyArray[0]);
        int b = Integer.parseInt(keyArray[1]);
        StringBuilder plaintext = new StringBuilder();
        int modInverse = MathUtils.modInverse(a, Alphabets.ALPHABET.length());
        for (int i = 0; i < ciphertext.length(); i++) {
            char ciphertextCharacter = ciphertext.charAt(i);
            if (ciphertextCharacter >= 'A' && ciphertextCharacter <= 'Z') {
                int plaintextNumericValue = ((modInverse * ((ciphertextCharacter - 'A') - b)) % 26 + 26) % 26;
                plaintext.append((char) (plaintextNumericValue + 'A'));
            } else if (ciphertextCharacter >= 'a' && ciphertextCharacter <= 'z') {
                int plaintextNumericValue = ((modInverse * ((ciphertextCharacter - 'a') - b)) % 26 + 26) % 26;
                plaintext.append((char) (plaintextNumericValue + 'a'));
            } else {
                plaintext.append(ciphertextCharacter);
            }
        }
        return plaintext.toString();
    }
}
