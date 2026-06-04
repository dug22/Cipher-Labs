package io.github.dug22.cipherlabs.cipheralgorithms.impl.classic;

import io.github.dug22.cipherlabs.cipheralgorithms.Cipher;

public abstract class ClassicSteganographicCipher implements Cipher {

    public abstract String encrypt(String plaintext);

    public abstract String decrypt(String ciphertext);
}
