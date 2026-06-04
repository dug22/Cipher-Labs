package io.github.dug22.cipherlabs.cipheralgorithms.impl.classic.symmetric.enigma;

import io.github.dug22.cipherlabs.cipheralgorithms.impl.classic.symmetric.enigma.reflector.ReflectorA;
import io.github.dug22.cipherlabs.cipheralgorithms.impl.classic.symmetric.enigma.reflector.ReflectorB;
import io.github.dug22.cipherlabs.cipheralgorithms.impl.classic.symmetric.enigma.reflector.ReflectorC;
import io.github.dug22.cipherlabs.cipheralgorithms.impl.classic.symmetric.enigma.rotors.*;

import java.util.List;

public class EnigmaTest {

    public static void main(String[] args) {
        RotorI rotorI = new RotorI();
        RotorII rotorII = new RotorII();
        RotorIII rotorIII = new RotorIII();
        RotorIV rotorIV = new RotorIV();
        RotorV rotorV = new RotorV();

        ReflectorA reflectorA = new ReflectorA();
        ReflectorB reflectorB = new ReflectorB();
        ReflectorC reflectorC = new ReflectorC();
        Keyboard keyboard = new Keyboard();
        Plugboard plugboard = new Plugboard(List.of("AB", "CD", "EF"));

        Enigma enigma = new Enigma(reflectorB, plugboard, keyboard, List.of(rotorIV, rotorII, rotorI));

        // --- ENCRYPTION PHASE ---
        enigma.setRings(new Rings(5, 26, 2));
        enigma.setKey("CAT");

        String message = "TESTINGTESTINGTESTINGTESTING";
        StringBuilder ciphertext = new StringBuilder();
        for (char letter : message.toCharArray()) {
            ciphertext.append(enigma.encipher(letter));
        }
        System.out.println("Ciphertext: " + ciphertext);

        // --- DECRYPTION PHASE ---
        // CRITICAL: Reset the machine to the exact same starting configuration
        enigma.setRings(new Rings(5, 26, 2));
        enigma.setKey("CAT");

        String ct = "IHNZGVPHIYBRFNUFQMXHROHXELBN"; // Or use ciphertext.toString()
        StringBuilder plaintext = new StringBuilder();
        for (char letter : ct.toCharArray()) {
            // Calling encipher here works perfectly because of Enigma's symmetry
            plaintext.append(enigma.decipher(letter));
        }
        System.out.println("Plaintext:  " + plaintext);
    }
}