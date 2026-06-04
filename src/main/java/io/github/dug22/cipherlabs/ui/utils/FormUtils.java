package io.github.dug22.cipherlabs.ui.utils;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

public class FormUtils {

    public static String getHeaderFormContent(String file){
        StringBuilder header = new StringBuilder();
        try(BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(FormUtils.class.getResourceAsStream(file)))) {
            String line;
            while ((line = bufferedReader.readLine()) != null){
                header.append(line);
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return header.toString();
    }
}
