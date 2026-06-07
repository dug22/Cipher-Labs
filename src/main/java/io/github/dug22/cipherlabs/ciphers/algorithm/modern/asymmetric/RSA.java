package io.github.dug22.cipherlabs.ciphers.algorithm.modern.asymmetric;

import io.github.dug22.cipherlabs.ciphers.Cipher;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

public class RSA implements Cipher {

    @Override
    public String name() {
        return "RSA";
    }

    @Override
    public String versionIntroduced() {
        return "1.0.0";
    }

    @Override
    public boolean isClassic() {
        return false;
    }

    @Override
    public boolean isModern() {
        return true;
    }

    @Override
    public int maxKeyLength() {
        return 0;
    }

    public String encrypt(String message, BigInteger p, BigInteger q, BigInteger exponent) {
        BigInteger n = p.multiply(q);
        byte[] messageBytes = message.getBytes(StandardCharsets.UTF_8);
        BigInteger m = new BigInteger(1, messageBytes);
        return m.modPow(exponent, n).toString();
    }

    public String decrypt(String encryptedMessage, BigInteger p, BigInteger q, BigInteger exponent) {
        BigInteger n = p.multiply(q);
        BigInteger phi = p.subtract(BigInteger.ONE).multiply(q.subtract(BigInteger.ONE));
        BigInteger d = exponent.modInverse(phi);
        BigInteger c = new BigInteger(encryptedMessage);
        BigInteger m = c.modPow(d, n);
        byte[] decryptedBytes = m.toByteArray();
        if (decryptedBytes.length > 0 && decryptedBytes[0] == 0) {
            decryptedBytes = Arrays.copyOfRange(decryptedBytes, 1, decryptedBytes.length);
        }

        return new String(decryptedBytes, StandardCharsets.UTF_8);
    }
}