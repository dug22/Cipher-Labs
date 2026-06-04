package io.github.dug22.cipherlabs;

import io.github.dug22.cipherlabs.config.ConfigurationManager;
import io.github.dug22.cipherlabs.config.ConfigurationRegistry;

public class CipherLabsCore extends AbstractCipherLabsCore implements PlatformInformation {

    private static CipherLabsCore instance;
    private ConfigurationManager configManager;
    private ConfigurationRegistry configRegistry;

    public CipherLabsCore() {
        instance = this;
        registerInstantiations();
        registerConfigs();
    }

    @Override
    public void onEnable() {
        System.out.println(name());
        System.out.println(description());
        System.out.println(version());
        System.out.println(repositoryInformation());
    }

    @Override
    public void onDisable() {
        System.exit(1);
    }

    @Override
    void registerInstantiations() {
        this.configManager = new ConfigurationManager();
        this.configRegistry = new ConfigurationRegistry();
    }

    @Override
    void registerConfigs() {
        configManager.createCipherLabsFolder();
        configRegistry.registerConfigFiles();
    }

    public ConfigurationManager getConfigManager() {
        return configManager;
    }

    public ConfigurationRegistry getConfigRegistry() {
        return configRegistry;
    }

    public static CipherLabsCore getInstance() {
        return instance;
    }
}
