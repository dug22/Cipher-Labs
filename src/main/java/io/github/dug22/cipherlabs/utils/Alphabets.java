package io.github.dug22.cipherlabs.utils;

import java.util.List;

public class Alphabets {

    public static String ALPHABET = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    public static char[] ALPHABET_CHAR_ARRAY = ALPHABET.toCharArray();
    public static List<Character> ALPHABET_CHAR_LIST = ALPHABET.chars()
            .mapToObj(c -> (char) c)
            .toList();

    public static String ALNUM = ALPHABET + "0123456789";
    public static char[] ALNUM_CHAR_ARRAY = ALNUM.toCharArray();
    public static List<Character> ALNUM_CHAR_LIST = ALNUM.chars()
            .mapToObj(c -> (char) c)
            .toList();

    public static boolean isInAlphabet(char value){
        return ALPHABET.contains(String.valueOf(value));
    }
}
