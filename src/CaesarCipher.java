import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class CaesarCipher {
    private int key;

    public CaesarCipher(int key) {
        this.key = key;
    }

    public String encrypt(String text, int key) {
        StringBuilder result = new StringBuilder();
        for (char c : text.toCharArray()) {
            if (Character.isLetter(c)) {
                if (Character.isUpperCase(c)) {
                    result.append((char) ('А' + (c - 'А' + key) % 33));
                } else {
                    result.append((char) ('а' + (c - 'а' + key) % 33));
                }
            } else {
                result.append(c);
            }
        }
        return result.toString();
    }

    public String decrypt(String text) {
        return encrypt(text, 33 - key);
    }

    public static Map<Character, Integer> getCharFrequency(String text) {
        Map<Character, Integer> frequency = new HashMap<>();
        for (char c : text.toCharArray()) {
            if (Character.isLetter(c)) {
                if (!frequency.containsKey(c)) {
                    frequency.put(c, 1);
                } else {
                    frequency.put(c, frequency.get(c) + 1);
                }
            }
        }
        return frequency;
    }

    public static int getMostFrequentCharKey(Map<Character, Integer> frequency) {
        int maxCount = 0;
        char mostFrequentChar = '\u0000';
        for (Map.Entry<Character, Integer> entry : frequency.entrySet()) {
            if (entry.getValue() > maxCount) {
                maxCount = entry.getValue();
                mostFrequentChar = entry.getKey();
            }
        }
        int key = mostFrequentChar - 'е';
        if (key < 0) {
            key += 33;
        }
        return key;
    }

    public static String bruteForce(String text) {
        for (int key = 1; key < 36; key++) {
            StringBuilder result = new StringBuilder();
            for (char c : text.toCharArray()) {
                if (Character.isLetter(c)) {
                    if (Character.isUpperCase(c)) {
                        result.append((char) ('А' + (c - 'А' + key) % 33));
                    } else {
                        result.append((char) ('а' + (c - 'а' + key) % 33));
                    }
                } else {
                    result.append(c);
                }
            }
            System.out.println("Key: " + key + ", Text: " + result);
        }
        return null;
    }
}