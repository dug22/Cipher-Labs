package io.github.dug22.cipherlabs.config.impl;

import io.github.dug22.cipherlabs.config.AbstractConfigFile;

import java.io.File;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class EncryptionDecryptionConfig extends AbstractConfigFile {

    private File encryptionDecryptionConfigFile;

    @Override
    public void init() {
        encryptionDecryptionConfigFile = configurationManager.createFile("encryption-decryption-settings.properties");
        if(configurationManager.isFileEmpty(encryptionDecryptionConfigFile)){
            final List<Map<String, Object>> properties = new LinkedList<>();
           properties.add(Map.of("baconian-cipher-use-early-era-encoding", true));
            properties.add(Map.of("baconian-cipher-use-binary-encoding", false));
            properties.add(Map.of("play-fair-cipher-primary-filler-character", "X"));
            properties.add(Map.of("play-fair-cipher-secondary-filler-character", "Z"));
            configurationManager.setData(encryptionDecryptionConfigFile, properties);
        }
    }

    @Override
    public File getFile() {
        return encryptionDecryptionConfigFile;
    }
}
