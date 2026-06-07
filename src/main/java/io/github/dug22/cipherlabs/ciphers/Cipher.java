package io.github.dug22.cipherlabs.ciphers;

public interface Cipher {

    String name();

    String versionIntroduced();

    boolean isClassic();

    boolean isModern();

    int maxKeyLength();


}
