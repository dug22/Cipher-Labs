package io.github.dug22.cipherlabs.cipheralgorithms;

import java.util.Map;

public interface Cipher {

    String name();

    String versionIntroduced();

    boolean isClassic();

    boolean isModern();

    int maxKeyLength();


}
