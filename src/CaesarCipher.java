import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class CaesarCipher {

    private final int KEY;
    private static final char[] ALPHABET_RU = {
            'а', 'б', 'в', 'г', 'д', 'е', 'ж', 'з', 'и', 'й', 'к',
            'л', 'м', 'н', 'о', 'п', 'р', 'с', 'т', 'у', 'ф', 'х',
            'ц', 'ч', 'ш', 'щ', 'ъ', 'ы', 'ь', 'э', 'ю', 'я'
    };
    private static final char MOST_FREQUENT_CHAR_RU = 'о';       //'о' is the most frequent letter in Russian alphabet
    private static final int LETTER_AMOUNT = ALPHABET_RU.length; //amount of letters in russian alphabet

    public CaesarCipher(int key) {
        this.KEY = key;
    }

    public String encrypt(String text, int key) {
        StringBuilder encryptedText = processText(text, key);
        return encryptedText.toString();
    }

    public String decrypt(String text) {
        return encrypt(text, LETTER_AMOUNT - KEY);
    }

    public static StringBuilder processText(String initialText, int key) {
        StringBuilder processedText = new StringBuilder();                                  //initialize string builder

        for (char initialLetter : initialText.toCharArray()) {
            if (Character.isLetter(initialLetter)) {
                processedText.append(
                        Character.isUpperCase(initialLetter) ?
                        Character.toUpperCase(processLetter(initialLetter, key)) :          //add uppercase letter to the processed text
                        processLetter(initialLetter, key));                                 //add lowercase letter to the processed text
            } else {
                processedText.append(initialLetter);                                        //add other symbol
            }
        }

        return processedText;
    }

    public static char processLetter(Character initialLetter, int key) {
       return (char) (ALPHABET_RU[0] + (initialLetter - ALPHABET_RU[0] + key) % LETTER_AMOUNT);
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
        for (char character : text.toCharArray()) {                             //count letter frequency of every char
            if (Character.isLetter(character)) {                                //skip symbols other than letters
                if (!letterFrequency.containsKey(character)) {                  //first time met letter
                    letterFrequency.put(character, 1);
                } else {
                    letterFrequency.put(character, letterFrequency.get(character) + 1); //amount of letters counter iterator
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

        int key = mostFrequentChar - MOST_FREQUENT_CHAR_RU;                 //'о' is the most frequent letter in Russian alphabet
        if (key < 0) {                                                      //just in case we got capital letter most frequent
            key += LETTER_AMOUNT;
        }
        return key;
    }

    public static String bruteForce(String text) {
        StringBuilder decryptedText = new StringBuilder();
        for (int key = 1; key < LETTER_AMOUNT; key++) {                     //iterate over key value
            StringBuilder result = processText(text, key);                  //process text
            System.out.println("Key: " + key + ", Text: " + result);        //print result to console
            decryptedText.append("Key: ").append(key).append(",\nText: ").append(result).append('\n');
        }
        return decryptedText.toString();
    }

}