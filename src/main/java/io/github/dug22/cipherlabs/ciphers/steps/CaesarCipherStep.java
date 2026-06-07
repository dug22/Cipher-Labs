package io.github.dug22.cipherlabs.ciphers.steps;

public class CaesarCipherStep {

    private final char fromCharacter;
    private final char toCharacter;
    private final int shift;

    public CaesarCipherStep(char fromCharacter, int shift, char newCharacter) {
        this.fromCharacter = fromCharacter;
        this.shift = shift;
        this.toCharacter = newCharacter;
    }

    public char getFromCharacter() {
        return fromCharacter;
    }

    public int getShift() {
        return shift;
    }

    public char getToCharacter() {
        return toCharacter;
    }
}
