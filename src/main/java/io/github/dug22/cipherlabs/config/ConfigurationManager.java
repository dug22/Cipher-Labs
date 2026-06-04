package io.github.dug22.cipherlabs.config;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;

public class ConfigurationManager {

    public void createCipherLabsFolder() {
        try {
            Files.createDirectories(Path.of(getCipherLabsFolder()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean isFileEmpty(File file) {
        return file.length() == 0;
    }

    public String getCipherLabsFolder() {
        return System.getProperty("user.home") + File.separator + "Cipher Labs";
    }

    public File createFile(String fileName) {
        File file = new File(getCipherLabsFolder() + File.separator + fileName);
        try {
            if (!file.exists()) {
                Files.createFile(Path.of(file.getPath()));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return file;
    }

    public void setData(File file, List<Map<String, Object>> propertiesList) {
        try (FileOutputStream fos = new FileOutputStream(file)) {
            SortedProperties sortedProperties = new SortedProperties();
            propertiesList.forEach(propertyMap -> {
                propertyMap.entrySet().forEach(entry -> {
                    String key = entry.getKey();
                    String value = String.valueOf(entry.getValue());
                    sortedProperties.setProperty(key, value);
                });
            });
            sortedProperties.store(fos, null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setProperty(File file, String key, Object newValue) {
        SortedProperties properties = new SortedProperties();
        try (FileInputStream fis = new FileInputStream(file)) {
            properties.load(fis);

        } catch (Exception e) {

        }

        properties.setProperty(key, String.valueOf(newValue));
        try (FileOutputStream fos = new FileOutputStream(file)) {
            properties.store(fos, null);
        } catch (Exception e) {

        }
    }

    private Object getRawProperty(File file, String key) {
        try (FileInputStream fis = new FileInputStream(file)) {
            SortedProperties properties = new SortedProperties();
            properties.load(fis);
            return properties.getAppropriateProperty(key);
        } catch (Exception e) {
            return null;
        }
    }

    public Character getCharacterProperty(File file, String key) {
        String value = getStringProperty(file, key);
        return value.charAt(0);
    }

    public String getStringProperty(File file, String key) {
        Object value = getRawProperty(file, key);
        return String.valueOf(value);
    }

    public Integer getIntegerProperty(File file, String key) {
        String value = getStringProperty(file, key);
        return Integer.parseInt(value);
    }

    public Double getDoubleProperty(File file, String key) {
        String value = getStringProperty(file, key);
        return Double.parseDouble(value);
    }

    public Boolean getBooleanProperty(File file, String key) {
        String value = getStringProperty(file, key);
        return Boolean.parseBoolean(value);
    }

    public Float getFloatProperty(File file, String key) {
        String value = getStringProperty(file, key);
        return Float.parseFloat(value);
    }

    public Long getLongProperty(File file, String key) {
        String value = getStringProperty(file, key);
        return Long.parseLong(value);
    }
}
