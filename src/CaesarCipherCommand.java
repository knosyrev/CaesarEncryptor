import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;
import picocli.CommandLine.Parameters;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

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

    @Option(names = {"-k", "--cypher-key"}, description = "The key to use for encryption or decryption (1-36)")
    private Integer key;

    @Parameters(index = "0", description = "The file to encrypt or decrypt")
    private Path file;

    @Override
    public void run() {

        try {
            String text = Files.readString(file, StandardCharsets.UTF_8);          // read source file

            if (encrypt) {
                CaesarCipher caesarCipher = new CaesarCipher(key);                 //initialize key
                String encryptedText = caesarCipher.encrypt(text, key);            // encrypt text
                Files.write(file, encryptedText.getBytes());                       // save encrypted text
            } else if (decrypt) {
                CaesarCipher caesarCipher = new CaesarCipher(key);                 //initialize key
                String decryptedText = caesarCipher.decrypt(text);                 // decrypt text
                Files.write(file, decryptedText.getBytes());                       // save decrypted text
            } else if (bruteForce) {
                CaesarCipher.bruteForce(text);                                     //start bruteforce algorithm
            } else if (statisticalDecryption) {
                String decryptedText = CaesarCipher.statisticalDecryption(text);   //start statistical decryption algorithm
                Files.write(file, decryptedText.getBytes());
            }
        } catch (IOException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        CommandLine cmd = new CommandLine(new CaesarCipherCommand());
        cmd.execute( "-s", "SampleFile.txt");
    }

}
