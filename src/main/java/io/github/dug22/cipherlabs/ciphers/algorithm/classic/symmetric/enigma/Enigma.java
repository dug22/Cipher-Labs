package io.github.dug22.cipherlabs.ciphers.algorithm.classic.symmetric.enigma;

import io.github.dug22.cipherlabs.ciphers.Cipher;
import io.github.dug22.cipherlabs.utils.Alphabets;

import java.util.List;

public class Enigma implements Cipher {

    private final Reflector reflector;
    private final Plugboard plugboard;
    private final Keyboard keyboard;
    private final List<Rotor> rotors;


    public Enigma(Reflector reflector, Plugboard plugboard, Keyboard keyboard, List<Rotor> rotors) {
        this.reflector = reflector;
        this.plugboard = plugboard;
        this.keyboard = keyboard;
        this.rotors = rotors;
    }

    @Override
    public String name() {
        return "Enigma";
    }

    @Override
    public String versionIntroduced() {
        return "1.0.0";
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

    public String encrypt(String plaintext, int[] rings, String key) {
        setRings(new Rings(rings));
        if (!key.isEmpty()) {
            setKey(key);
        }
        StringBuilder ciphertext = new StringBuilder();
        for (char letter : plaintext.toCharArray()) {
            ciphertext.append(encipher(letter));
        }
        return ciphertext.toString();
    }

    public String decrypt(String ciphertext, int[] rings, String key) {
        return encrypt(ciphertext, rings, key);
    }

    public void setRings(Rings rings) {
        for (int i = 0; i < rings.rings().length; i++) {
            rotors.get(i).setRing(rings.rings()[i]);
        }
    }

    public void setKey(String key) {
        for (int i = 0; i < rotors.size(); i++) {
            rotors.get(i).rotateToLetter(key.charAt(i));
        }
    }

    public char encipher(char letter) {
        if (!Alphabets.ALPHABET.contains(String.valueOf(letter))) {
            return letter;
        }
        rotateRotors(rotors);
        int signal = keyboard.forward(letter);
        signal = plugboard.forward(signal);
        for (int i = rotors.size() - 1; i >= 0; i--) {
            signal = rotors.get(i).forward(signal);
        }
        signal = reflector.reflect(signal);
        for (Rotor rotor : rotors) {
            signal = rotor.backward(signal);
        }
        signal = plugboard.backward(signal);
        return keyboard.backward(signal);
    }

    public char decipher(char letter) {
        return encipher(letter);
    }

    public void encipherWithTrace(char letter, List<String> row) {
        if (!Alphabets.ALPHABET.contains(String.valueOf(letter))) {
            for (int i = 0; i < 11; i++) {
                row.add(String.valueOf(letter));
            }
            return;
        }

        rotateRotors(rotors);

        char[] alphabetCharArray = Alphabets.ALPHABET_CHAR_ARRAY;
        //1 Keyboard
        int signal = keyboard.forward(letter);

        row.add(String.valueOf(alphabetCharArray[signal]));
        //2 Plugboard
        signal = plugboard.forward(signal);
        row.add(String.valueOf(alphabetCharArray[signal]));
        //3 4 5 Rotors
        for (int i = rotors.size() - 1; i >= 0; i--) {
            signal = rotors.get(i).forward(signal);
            row.add(String.valueOf(alphabetCharArray[signal]));
        }


        //Reflector 6
        signal = reflector.reflect(signal);
        row.add(String.valueOf(alphabetCharArray[signal]));
        //Rotor 7 8 9
        for (Rotor rotor : rotors) {
            signal = rotor.backward(signal);
            row.add(String.valueOf(alphabetCharArray[signal]));
        }
        //Plugboard
        signal = plugboard.backward(signal);
        row.add(String.valueOf(alphabetCharArray[signal]));

        //Lamboard
        char outputCharacter = keyboard.backward(signal);
        row.add(String.valueOf(outputCharacter));
    }

    private void rotateRotors(List<Rotor> rotors) {
        Rotor rotorA = rotors.getFirst();
        Rotor rotorB = rotors.get(1);
        Rotor rotorC = rotors.getLast();
        if (rotorB.getLeft().charAt(0) == rotorB.getNotch() && rotorC.getLeft().charAt(0) == rotorC.getNotch()) {
            rotorA.rotate();
            rotorB.rotate();
            rotorC.rotate();
        } else if (rotorB.getLeft().charAt(0) == rotorB.getNotch()) {
            rotorA.rotate();
            rotorB.rotate();
            rotorC.rotate();
        } else if (rotorC.getLeft().charAt(0) == rotorC.getNotch()) {
            rotorB.rotate();
            rotorC.rotate();
        } else {
            rotorC.rotate();
        }
    }
}