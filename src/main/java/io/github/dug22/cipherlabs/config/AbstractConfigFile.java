package io.github.dug22.cipherlabs.config;

import io.github.dug22.cipherlabs.CipherLabsCore;

import java.io.File;

public abstract class AbstractConfigFile {

    protected final ConfigurationManager configurationManager = CipherLabsCore.getInstance().getConfigManager();

    public abstract void init();


    public abstract File getFile();
}
