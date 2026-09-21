package io.github.dug22.cipherlabs.core.config;

import io.github.dug22.cipherlabs.core.CipherLabsCore;

import java.io.File;

public abstract class AbstractConfigFile {

    protected final ConfigurationManager configurationManager = CipherLabsCore.getInstance().getConfigManager();

    public abstract void init();

    public abstract File getFile();
}
