package io.github.dug22.cipherlabs.ciphers.algorithm.classic.steganography;

import io.github.dug22.cipherlabs.CipherLabsCore;
import io.github.dug22.cipherlabs.ciphers.algorithm.classic.ClassicSteganographicCipher;
import io.github.dug22.cipherlabs.ciphers.steps.BaconCipherStep;
import io.github.dug22.cipherlabs.config.ConfigurationManager;
import io.github.dug22.cipherlabs.config.ConfigurationRegistry;
import io.github.dug22.cipherlabs.config.impl.EncryptionDecryptionConfig;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

public class BaconianCipher extends ClassicSteganographicCipher {

    private List<BaconCipherStep> steps;
    private final CipherLabsCore instance = CipherLabsCore.getInstance();
    private final ConfigurationManager configurationManager = instance.getConfigManager();
    private final ConfigurationRegistry configurationRegistry = instance.getConfigRegistry();
    private final EncryptionDecryptionConfig encryptionDecryptionConfig = (EncryptionDecryptionConfig) configurationRegistry.getConfigFile("encryption-decryption-config");
    private final File encryptionDecryptionFileInstance = encryptionDecryptionConfig.getFile();

    private final Map<String, String> earlyBaconCodeEncryptionMap = new HashMap<>(Map.ofEntries(
            Map.entry("A", "AAAAA"), Map.entry("B", "AAAAB"), Map.entry("C", "AAABA"), Map.entry("D", "AAABB"),
            Map.entry("E", "AABAA"), Map.entry("F", "AABAB"), Map.entry("G", "AABBA"), Map.entry("H", "AABBB"),
            Map.entry("I", "ABAAA"), Map.entry("J", "ABAAA"), Map.entry("K", "ABAAB"), Map.entry("L", "ABABA"),
            Map.entry("M", "ABABB"), Map.entry("N", "ABBAA"), Map.entry("O", "ABBAB"), Map.entry("P", "ABBBA"),
            Map.entry("Q", "ABBBB"), Map.entry("R", "BAAAA"), Map.entry("S", "BAAAB"), Map.entry("T", "BAABA"),
            Map.entry("U", "BAABB"), Map.entry("V", "BAABB"), Map.entry("W", "BABAA"), Map.entry("X", "BABAB"),
            Map.entry("Y", "BABBA"), Map.entry("Z", "BABBB")
    ));

    private final Map<String, String> earlyBaconCodeDecryptionMap = new HashMap<>(Map.ofEntries(
            Map.entry("AAAAA", "A"), Map.entry("AAAAB", "B"), Map.entry("AAABA", "C"), Map.entry("AAABB", "D"),
            Map.entry("AABAA", "E"), Map.entry("AABAB", "F"), Map.entry("AABBA", "G"), Map.entry("AABBB", "H"),
            Map.entry("ABAAA", "I"), Map.entry("ABAAB", "K"), Map.entry("ABABA", "L"), Map.entry("ABABB", "M"),
            Map.entry("ABBAA", "N"), Map.entry("ABBAB", "O"), Map.entry("ABBBA", "P"), Map.entry("ABBBB", "Q"),
            Map.entry("BAAAA", "R"), Map.entry("BAAAB", "S"), Map.entry("BAABA", "T"), Map.entry("BAABB", "U"),
            Map.entry("BABAA", "W"), Map.entry("BABAB", "X"), Map.entry("BABBA", "Y"), Map.entry("BABBB", "Z")
    ));

    private final Map<String, String> earlyBaconBinaryEncryptionMap = new HashMap<>(Map.ofEntries(
            Map.entry("A", "00000"), Map.entry("B", "00001"), Map.entry("C", "00010"), Map.entry("D", "00011"),
            Map.entry("E", "00100"), Map.entry("F", "00101"), Map.entry("G", "00110"), Map.entry("H", "00111"),
            Map.entry("I", "01000"), Map.entry("J", "01000"), Map.entry("K", "01001"), Map.entry("L", "01010"),
            Map.entry("M", "01011"), Map.entry("N", "01100"), Map.entry("O", "01101"), Map.entry("P", "01110"),
            Map.entry("Q", "01111"), Map.entry("R", "10000"), Map.entry("S", "10001"), Map.entry("T", "10010"),
            Map.entry("U", "10011"), Map.entry("V", "10011"), Map.entry("W", "10100"), Map.entry("X", "10101"),
            Map.entry("Y", "10110"), Map.entry("Z", "10111")
    ));

    private final Map<String, String> earlyBaconBinaryDecryptionMap = new HashMap<>(Map.ofEntries(
            Map.entry("00000", "A"), Map.entry("00001", "B"), Map.entry("00010", "C"), Map.entry("00011", "D"),
            Map.entry("00100", "E"), Map.entry("00101", "F"), Map.entry("00110", "G"), Map.entry("00111", "H"),
            Map.entry("01000", "I"), Map.entry("01001", "K"), Map.entry("01010", "L"), Map.entry("01011", "M"),
            Map.entry("01100", "N"), Map.entry("01101", "O"), Map.entry("01110", "P"), Map.entry("01111", "Q"),
            Map.entry("10000", "R"), Map.entry("10001", "S"), Map.entry("10010", "T"), Map.entry("10011", "U"),
            Map.entry("10100", "W"), Map.entry("10101", "X"), Map.entry("10110", "Y"), Map.entry("10111", "Z")
    ));

    private final Map<String, String> modernBaconCodeEncryptionMap = new HashMap<>(Map.ofEntries(
            Map.entry("A", "AAAAA"), Map.entry("B", "AAAAB"), Map.entry("C", "AAABA"), Map.entry("D", "AAABB"),
            Map.entry("E", "AABAA"), Map.entry("F", "AABAB"), Map.entry("G", "AABBA"), Map.entry("H", "AABBB"),
            Map.entry("I", "ABAAA"), Map.entry("J", "ABAAB"), Map.entry("K", "ABABA"), Map.entry("L", "ABABB"),
            Map.entry("M", "ABBAA"), Map.entry("N", "ABBAB"), Map.entry("O", "ABBBA"), Map.entry("P", "ABBBB"),
            Map.entry("Q", "BAAAA"), Map.entry("R", "BAAAB"), Map.entry("S", "BAABA"), Map.entry("T", "BAABB"),
            Map.entry("U", "BABAA"), Map.entry("V", "BABAB"), Map.entry("W", "BABBA"), Map.entry("X", "BABBB"),
            Map.entry("Y", "BBAAA"), Map.entry("Z", "BBAAB")
    ));

    private final Map<String, String> modernBaconCodeDecryptionMap = new HashMap<>(Map.ofEntries(
            Map.entry("AAAAA", "A"), Map.entry("AAAAB", "B"), Map.entry("AAABA", "C"), Map.entry("AAABB", "D"),
            Map.entry("AABAA", "E"), Map.entry("AABAB", "F"), Map.entry("AABBA", "G"), Map.entry("AABBB", "H"),
            Map.entry("ABAAA", "I"), Map.entry("ABAAB", "J"), Map.entry("ABABA", "K"), Map.entry("ABABB", "L"),
            Map.entry("ABBAA", "M"), Map.entry("ABBAB", "N"), Map.entry("ABBBA", "O"), Map.entry("ABBBB", "P"),
            Map.entry("BAAAA", "Q"), Map.entry("BAAAB", "R"), Map.entry("BAABA", "S"), Map.entry("BAABB", "T"),
            Map.entry("BABAA", "U"), Map.entry("BABAB", "V"), Map.entry("BABBA", "W"), Map.entry("BABBB", "X"),
            Map.entry("BBAAA", "Y"), Map.entry("BBAAB", "Z")
    ));

    private final Map<String, String> modernBaconBinaryEncryptionMap = new HashMap<>(Map.ofEntries(
            Map.entry("A", "00000"), Map.entry("B", "00001"), Map.entry("C", "00010"), Map.entry("D", "00011"),
            Map.entry("E", "00100"), Map.entry("F", "00101"), Map.entry("G", "00110"), Map.entry("H", "00111"),
            Map.entry("I", "01000"), Map.entry("J", "01001"), Map.entry("K", "01010"), Map.entry("L", "01011"),
            Map.entry("M", "01100"), Map.entry("N", "01101"), Map.entry("O", "01110"), Map.entry("P", "01111"),
            Map.entry("Q", "10000"), Map.entry("R", "10001"), Map.entry("S", "10010"), Map.entry("T", "10011"),
            Map.entry("U", "10100"), Map.entry("V", "10101"), Map.entry("W", "10110"), Map.entry("X", "10111"),
            Map.entry("Y", "11000"), Map.entry("Z", "11001")
    ));

    private final Map<String, String> modernBaconBinaryDecryptionMap = new HashMap<>(Map.ofEntries(
            Map.entry("00000", "A"), Map.entry("00001", "B"), Map.entry("00010", "C"), Map.entry("00011", "D"),
            Map.entry("00100", "E"), Map.entry("00101", "F"), Map.entry("00110", "G"), Map.entry("00111", "H"),
            Map.entry("01000", "I"), Map.entry("01001", "J"), Map.entry("01010", "K"), Map.entry("01011", "L"),
            Map.entry("01100", "M"), Map.entry("01101", "N"), Map.entry("01110", "O"), Map.entry("01111", "P"),
            Map.entry("10000", "Q"), Map.entry("10001", "R"), Map.entry("10010", "S"), Map.entry("10011", "T"),
            Map.entry("10100", "U"), Map.entry("10101", "V"), Map.entry("10110", "W"), Map.entry("10111", "X"),
            Map.entry("11000", "Y"), Map.entry("11001", "Z")
    ));

    public BaconianCipher() {
        this.steps = new CopyOnWriteArrayList<>();
    }

    @Override
    public String name() {
        return "Baconian Cipher";
    }

    @Override
    public String versionIntroduced() {
        return "1.0.0";
    }

    @Override
    public boolean isClassic() {
        return true;
    }

    @Override
    public boolean isModern() {
        return false;
    }

    @Override
    public int maxKeyLength() {
        return 0;
    }

    @Override
    public String encrypt(String plaintext) {
        steps.clear();
        plaintext = plaintext.toUpperCase().replaceAll("[^A-Z]", "");
        StringBuilder ciphertext = new StringBuilder();
        Map<String, String> encryptionMap = getAppropriateEncryptionMap();
        for (int i = 0; i < plaintext.length(); i++) {
            char character = plaintext.charAt(i);
            ciphertext.append(encryptionMap.get(String.valueOf(character)));
            steps.add(new BaconCipherStep(String.valueOf(character), encryptionMap.get(String.valueOf(character))));
        }
        return ciphertext.toString();
    }

    @Override
    public String decrypt(String ciphertext) {
        steps.clear();
        ciphertext = ciphertext.toUpperCase();

        if (useBinary()) {
            ciphertext = ciphertext.replaceAll("[^01]", "");
        } else {
            ciphertext = ciphertext.replaceAll("[^AB]", "");
        }

        StringBuilder plaintext = new StringBuilder();
        Map<String, String> decryptionMap = getAppropriateDecryptionMap();
        for (int i = 0; i < ciphertext.length(); i += 5) {
            if (i + 5 > ciphertext.length()) break;
            String encodedText = ciphertext.substring(i, i + 5);
            plaintext.append(decryptionMap.get(encodedText));
            steps.add(new BaconCipherStep(decryptionMap.get(encodedText), encodedText));
        }
        return plaintext.toString();
    }

    public boolean useEarlyBaconianCipherEncoding() {
        return configurationManager.getBooleanProperty(encryptionDecryptionFileInstance, "baconian-cipher-use-early-era-encoding");
    }

    public boolean useBinary() {
        return configurationManager.getBooleanProperty(encryptionDecryptionFileInstance, "baconian-cipher-use-binary-encoding");
    }

    public Map<String, String> getEarlyBaconCodeEncryptionMap() {
        return earlyBaconCodeEncryptionMap;
    }

    public Map<String, String> getEarlyBaconCodeDecryptionMap() {
        return earlyBaconCodeDecryptionMap;
    }

    public Map<String, String> getEarlyBaconBinaryEncryptionMap() {
        return earlyBaconBinaryEncryptionMap;
    }

    public Map<String, String> getEarlyBaconBinaryDecryptionMap() {
        return earlyBaconBinaryDecryptionMap;
    }

    public Map<String, String> getModernBaconCodeEncryptionMap() {
        return modernBaconCodeEncryptionMap;
    }

    public Map<String, String> getModernBaconCodeDecryptionMap() {
        return modernBaconCodeDecryptionMap;
    }

    public Map<String, String> getModernBaconBinaryEncryptionMap() {
        return modernBaconBinaryEncryptionMap;
    }

    public Map<String, String> getModernBaconBinaryDecryptionMap() {
        return modernBaconBinaryDecryptionMap;
    }

    public List<BaconCipherStep> getSteps() {
        return steps;
    }

    public Map<String, String> getAppropriateEncryptionMap() {
        Map<String, String> encryptionMap;
        if (useBinary()) {
            if (useEarlyBaconianCipherEncoding()) {
                encryptionMap = getEarlyBaconBinaryEncryptionMap();
            } else {
                encryptionMap = getModernBaconBinaryEncryptionMap();
            }
        } else {
            if (useEarlyBaconianCipherEncoding()) {
                encryptionMap = getEarlyBaconCodeEncryptionMap();
            } else {
                encryptionMap = getModernBaconCodeEncryptionMap();
            }
        }
        return encryptionMap;
    }

    public Map<String, String> getAppropriateDecryptionMap() {
        Map<String, String> decryptionMap;
        if (useBinary()) {
            if (useEarlyBaconianCipherEncoding()) {
                decryptionMap = getEarlyBaconBinaryDecryptionMap();
            } else {
                decryptionMap = getModernBaconBinaryDecryptionMap();
            }
        } else {
            if (useEarlyBaconianCipherEncoding()) {
                decryptionMap = getEarlyBaconCodeDecryptionMap();
            } else {
                decryptionMap = getModernBaconCodeDecryptionMap();
            }
        }

        return decryptionMap;
    }
}
