import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class CaesarCipher {

    private int key;

    public CaesarCipher(int key) {
        this.key = key;
    }

    public String encrypt(String text, int key) {
        StringBuilder encryptedText = processText(text, key);
        return encryptedText.toString();
    }

    public String decrypt(String text) {
        return encrypt(text, 33 - key);
    }

    public static StringBuilder processText(String initialText, int key) {
        StringBuilder processedText = new StringBuilder();                                  //initialize stringbuilder
        for (char initialLetter : initialText.toCharArray()) {
            if (Character.isLetter(initialLetter)) {
                if (Character.isUpperCase(initialLetter)) {
                    processedText.append((char) ('А' + (initialLetter - 'А' + key) % 33));  //add uppercase letter to the processed text
                } else {
                    processedText.append((char) ('а' + (initialLetter - 'а' + key) % 33));  //add lowercase letter to the processed text
                }
            } else {
                processedText.append(initialLetter);                                        //add other symbol
            }
        }
        return processedText;
    }

    public static String statisticalDecryption(String text) throws IOException {
        Map<Character, Integer> frequency = CaesarCipher.getCharFrequency(text);            //get frequency of every character in text
        int key = CaesarCipher.getMostFrequentCharKey(frequency);                           //get key according to the frequency
        System.out.println("Key: " + key);

        CaesarCipher decryptionCipher = new CaesarCipher(key);
        String decryptedText = decryptionCipher.decrypt(text);                              //decrypt
        System.out.println("Decrypted text: " + decryptedText);
        return decryptedText;
    }

    public static Map<Character, Integer> getCharFrequency(String text) {
        Map<Character, Integer> letterFrequency = new HashMap<>();
        for (char c : text.toCharArray()) {                             //count letter frequency of every char
            if (Character.isLetter(c)) {                                //skip symbols other than letters
                if (!letterFrequency.containsKey(c)) {                  //first time met letter
                    letterFrequency.put(c, 1);
                } else {
                    letterFrequency.put(c, letterFrequency.get(c) + 1); //actual amount of letters
                }
            }
        }
        return letterFrequency;
    }

    public static int getMostFrequentCharKey(Map<Character, Integer> frequency) {
        int maxCount = 0;
        char mostFrequentChar = '\u0000';                                   //Initialization
        for (Map.Entry<Character, Integer> entry : frequency.entrySet()) {  //looking for most frequent char
            if (entry.getValue() > maxCount) {
                maxCount = entry.getValue();
                mostFrequentChar = entry.getKey();
            }
        }
        int key = mostFrequentChar - 'о';                                   //'о' is the most frequent letter in Russian alphabet
        if (key < 0) {                                                      //just in case we got capital letter most frequent
            key += 33;
        }
        return key;
    }

    public static void bruteForce(String text) {
        for (int key = 1; key < 33; key++) {
            StringBuilder result = processText(text, key);
            System.out.println("Key: " + key + ", Text: " + result);
        }
    }

}