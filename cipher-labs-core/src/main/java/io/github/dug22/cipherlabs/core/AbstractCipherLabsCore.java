package io.github.dug22.cipherlabs.core;

public abstract class AbstractCipherLabsCore {

    abstract void onEnable();

    abstract void onDisable();

    abstract void registerInstantiations();

    abstract void registerConfigs();
}
