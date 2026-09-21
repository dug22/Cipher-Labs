package io.github.dug22.cipherlabs.core.ciphers;

public interface Cipher {

    String name();

    String versionIntroduced();

    boolean isClassic();

    boolean isModern();

    int maxKeyLength();


}
