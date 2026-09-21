package io.github.dug22.cipherlabs.core.ciphers;



import io.github.dug22.cipherlabs.core.ciphers.algorithm.classic.steganography.BaconianCipher;
import io.github.dug22.cipherlabs.core.ciphers.algorithm.classic.symmetric.*;
import io.github.dug22.cipherlabs.core.ciphers.algorithm.classic.symmetric.enigma.*;
import io.github.dug22.cipherlabs.core.ciphers.algorithm.modern.asymmetric.RSA;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CipherRegistry {

    private static final Map<String, Cipher> cipherAlgorithmsMap = new HashMap<>();


    static {
        cipherAlgorithmsMap.put("Affine Cipher", new AffineCipher());
        cipherAlgorithmsMap.put("Atbash Cipher", new AtbashCipher());
        cipherAlgorithmsMap.put("Baconian Cipher", new BaconianCipher());
        cipherAlgorithmsMap.put("Caesar Cipher", new CaesarCipher());
        cipherAlgorithmsMap.put("Playfair Cipher", new PlayfairCipher());
        cipherAlgorithmsMap.put("Polybius Cipher", new PolybiusCipher());
        cipherAlgorithmsMap.put("Porta Cipher", new PortaCipher());
        cipherAlgorithmsMap.put("Rail Fence Cipher", new RailFenceCipher());
        cipherAlgorithmsMap.put("RSA", new RSA());
        cipherAlgorithmsMap.put("Vigenère Cipher", new VigenereCipher());
    }

    public static Cipher getCipher(String cipherAlgorithm) {
        return cipherAlgorithmsMap.get(cipherAlgorithm);
    }

    public static Enigma createEnigma(Reflector reflector, Plugboard plugboard, Keyboard keyboard, List<Rotor> rotors) {
        return new Enigma(reflector, plugboard, keyboard, rotors);
    }
}
