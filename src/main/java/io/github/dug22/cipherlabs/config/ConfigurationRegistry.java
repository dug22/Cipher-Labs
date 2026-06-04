package io.github.dug22.cipherlabs.config;

import io.github.dug22.cipherlabs.config.impl.EncryptionDecryptionConfig;

import java.util.Map;

public class ConfigurationRegistry {

    private final Map<String, AbstractConfigFile> configFileMap = Map.ofEntries(
            Map.entry("encryption-decryption-config", new EncryptionDecryptionConfig())
    );

    public void registerConfigFiles() {
        for (AbstractConfigFile configFile : configFileMap.values()) {
            configFile.init();
        }
    }

    public AbstractConfigFile getConfigFile(String fileName) {
        return configFileMap.get(fileName);
    }
}
