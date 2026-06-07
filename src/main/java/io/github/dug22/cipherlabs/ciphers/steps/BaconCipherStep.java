package io.github.dug22.cipherlabs.ciphers.steps;

public class BaconCipherStep {

    private final String letter;
    private final String code;

    public BaconCipherStep(String from, String to){
        this.letter = from;
        this.code = to;
    }

    public String getLetter() {
        return letter;
    }

    public String getCode() {
        return code;
    }
}
