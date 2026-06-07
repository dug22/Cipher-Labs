package io.github.dug22.cipherlabs.ciphers.algorithm.classic;

import io.github.dug22.cipherlabs.ciphers.Cipher;

public abstract class ClassicSymmetricCipher implements Cipher {

    public abstract String encrypt(String plaintext, String key);

    public abstract String decrypt(String ciphertext, String key);
}
