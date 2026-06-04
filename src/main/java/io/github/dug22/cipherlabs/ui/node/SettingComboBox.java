package io.github.dug22.cipherlabs.ui.node;

import io.github.dug22.cipherlabs.CipherLabsCore;
import io.github.dug22.cipherlabs.config.AbstractConfigFile;
import io.github.dug22.cipherlabs.config.ConfigurationManager;
import javafx.scene.control.ComboBox;

import java.util.List;

public class SettingComboBox<T> extends ComboBox<T> {

    public SettingComboBox() {

    }

    public void setItemsAndMainValue(List<T> items, T value) {
        this.getItems().setAll(items);
        this.setValue(value);
    }

    public void saveSettingValue(AbstractConfigFile configFile, String key, Object value) {
        ConfigurationManager configurationManager = CipherLabsCore.getInstance().getConfigManager();
        configurationManager.setProperty(configFile.getFile(), key, value);
    }

    public void saveSettingValue(AbstractConfigFile configFile, String key){
      saveSettingValue(configFile, key, getValue());
    }
}
