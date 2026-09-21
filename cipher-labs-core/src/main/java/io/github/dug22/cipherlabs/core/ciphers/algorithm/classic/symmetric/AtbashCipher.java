package io.github.dug22.cipherlabs.core.ciphers.algorithm.classic.symmetric;

import io.github.dug22.cipherlabs.core.ciphers.algorithm.classic.ClassicSymmetricCipher;

public class AtbashCipher extends ClassicSymmetricCipher {

    @Override
    public String name() {
        return "Atbash Cipher";
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
        return -1;
    }

    @Override
    public String encrypt(String plaintext, String key) {
        //Key is not needed
        StringBuilder ciphertext = new StringBuilder();
        plaintext.chars().forEach(character -> {
            char plaintextCharacter = (char) character;
            boolean isUpperCaseLetter = plaintextCharacter >= 'A' && plaintextCharacter <= 'Z';
            boolean isLowerCaseLetter = plaintextCharacter >= 'a' && plaintextCharacter <= 'z';
            if(isUpperCaseLetter){
                ciphertext.append((char) ('A' + 'Z' - plaintextCharacter));
            } else if (isLowerCaseLetter) {
                ciphertext.append((char) ('a' + 'z' - plaintextCharacter));
            }else{
                ciphertext.append(plaintextCharacter);
            }
        });

        return ciphertext.toString();
    }

    @Override
    public String decrypt(String ciphertext, String key) {
        //Key is not needed
        return encrypt(ciphertext, key);
    }

    public String encrypt(String plaintext){
        return encrypt(plaintext, "");
    }

    public String decrypt(String ciphertext){
        return decrypt(ciphertext, "");
    }
}
