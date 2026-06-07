package io.github.dug22.cipherlabs.ciphers.algorithm.classic;

import io.github.dug22.cipherlabs.ciphers.Cipher;

public abstract class ClassicSteganographicCipher implements Cipher {

    public abstract String encrypt(String plaintext);

    public abstract String decrypt(String ciphertext);
}
