import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;
import picocli.CommandLine.Parameters;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;

@Command(name = "caesar-cipher", mixinStandardHelpOptions = true, version = "1.0", description = "Encrypts and decrypts a file using the Caesar cipher")
public class CaesarCipherCommand implements Runnable {
    @Option(names = {"-e", "--encrypt"}, description = "Encrypts a file")
    private boolean encrypt;

    @Option(names = {"-d", "--decrypt"}, description = "Decrypts a file")
    private boolean decrypt;

    @Option(names = {"-b", "--brute-force"}, description = "Brute forces the key for a given file")
    private boolean bruteForce;

    @Option(names = {"-s", "--statistical-decryption"}, description = "Decrypts a file using statistical decryption")
    private boolean statisticalDecryption;

    @Parameters(index = "0", description = "The file to encrypt or decrypt")
    private Path file;

    @Parameters(index = "1", description = "The key to use for encryption or decryption (1-36)")
    private Integer key;

    @Override
    public void run() {
        Charset charset = StandardCharsets.UTF_8;
        try {
            String text = new String(Files.readAllBytes(file), charset);
            CaesarCipher caesarCipher = new CaesarCipher(key);
            if (encrypt) {
                String encryptedText = caesarCipher.encrypt(text, key);
                Files.write(file, encryptedText.getBytes());
            } else if (decrypt) {
                String decryptedText = caesarCipher.decrypt(text);
                Files.write(file, decryptedText.getBytes());
            } else if (bruteForce) {
                caesarCipher.bruteForce(text);
            } else if (statisticalDecryption) {
                Map<Character, Integer> frequency = caesarCipher.getCharFrequency(text);
                int key = caesarCipher.getMostFrequentCharKey(frequency);
                System.out.println("Key: " + key);

                CaesarCipher decryptionCipher = new CaesarCipher(key);
                String decryptedText = decryptionCipher.decrypt(text);
                System.out.println("Decrypted text: " + decryptedText);

                Files.write(file, decryptedText.getBytes());
            }
        } catch (IOException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        CommandLine cmd = new CommandLine(new CaesarCipherCommand());
        cmd.execute(args);
    }
}
