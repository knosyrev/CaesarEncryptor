public class CaesarCipher {

    private final int KEY;

    public CaesarCipher(int key) {
        this.KEY = key;
    }

    public String encrypt(String text, int key) {
        StringBuilder encryptedText = TextProcessor.processText(text, key);
        return encryptedText.toString();
    }

    public String decrypt(String text) {
        return encrypt(text, TextProcessor.getLetterAmount() - KEY);
    }

}