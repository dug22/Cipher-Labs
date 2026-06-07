package io.github.dug22.cipherlabs.ciphers;

import io.github.dug22.cipherlabs.ciphers.algorithm.classic.steganography.BaconianCipher;
import io.github.dug22.cipherlabs.ciphers.algorithm.classic.symmetric.CaesarCipher;
import io.github.dug22.cipherlabs.ciphers.algorithm.classic.symmetric.PlayfairCipher;
import io.github.dug22.cipherlabs.ciphers.algorithm.classic.symmetric.VigenereCipher;
import io.github.dug22.cipherlabs.ciphers.algorithm.classic.symmetric.enigma.*;
import io.github.dug22.cipherlabs.ciphers.algorithm.modern.asymmetric.RSA;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CipherRegistry {

    private static final Map<String, Cipher> cipherAlgorithmsMap = new HashMap<>();


    static {
        cipherAlgorithmsMap.put("Baconian Cipher", new BaconianCipher());
        cipherAlgorithmsMap.put("Caesar Cipher", new CaesarCipher());
        cipherAlgorithmsMap.put("RSA", new RSA());
        cipherAlgorithmsMap.put("Playfair Cipher", new PlayfairCipher());
        cipherAlgorithmsMap.put("Vigenère Cipher", new VigenereCipher());
    }

    public static Map<String, Cipher> getCipherAlgorithmsMap() {
        return cipherAlgorithmsMap;
    }

    public static Cipher getCipher(String cipherAlgorithm) {
        return cipherAlgorithmsMap.get(cipherAlgorithm);
    }

    public static Enigma getEnigma(Reflector reflector, Plugboard plugboard, Keyboard keyboard, List<Rotor> rotors) {
        return new Enigma(reflector, plugboard, keyboard, rotors);
    }
}
