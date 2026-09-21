package io.github.dug22.cipherlabs.lockspot;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class LockSpotModelLoader<T> {

    public T cipherTypeModel;
    public T cipherAlgorithmModel;


    public LockSpotModelLoader() {
    }


    public static <T> LockSpotModelLoader<T> loadModel(String filePath, TypeToken<LockSpotModelLoader<T>> typeToken) {
        Gson gson = new Gson();
        try (BufferedReader fileReader = new BufferedReader(new InputStreamReader(LockSpotModelLoader.class.getResourceAsStream(filePath)))) {
            return gson.fromJson(fileReader, typeToken.getType());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}