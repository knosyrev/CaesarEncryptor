import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;
import picocli.CommandLine.Parameters;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

@Command(name = "caesar-cipher", mixinStandardHelpOptions = true,
         version = "1.0", description = "Encrypts and decrypts a file using the Caesar cipher")
public class CaesarCipherCommand implements Runnable {

    @Option(names = {"-b", "--brute-force"}, description = "Brute forces the key for a given file")
    private boolean bruteForce;

    @Option(names = {"-d", "--decrypt"}, description = "Decrypts a file")
    private boolean decrypt;

    @Option(names = {"-e", "--encrypt"}, description = "Encrypts a file")
    private boolean encrypt;

    @Option(names = {"-s", "--statistical-decryption"}, description = "Decrypts a file using statistical decryption")
    private boolean statisticalDecryption;

    @Option(names = {"-k", "--cypher-key"}, description = "The key to use for encryption or decryption (1-33)")
    private Integer key;

    @Parameters(index = "0", description = "The file to encrypt or decrypt")
    private Path sourceFile;

    @Parameters(index = "1", description = "The file to encrypt or decrypt")
    private Path outputFile;

    @Override
    public void run() {

        try {
            String text = Files.readString(sourceFile, StandardCharsets.UTF_8);    // read source file

            if (encrypt) {
                CaesarCipher caesarCipher = new CaesarCipher(key);                 //initialize key
                String encryptedText = caesarCipher.encrypt(text, key);            // encrypt text
                Files.write(outputFile, encryptedText.getBytes());                 // save encrypted text
            } else if (decrypt) {
                CaesarCipher caesarCipher = new CaesarCipher(key);                 //initialize key
                String decryptedText = caesarCipher.decrypt(text);                 // decrypt text
                Files.write(outputFile, decryptedText.getBytes());                 // save decrypted text
            } else if (bruteForce) {
                String decryptedText = TextProcessor.bruteForce(text);              //start bruteforce algorithm
                Files.write(outputFile, decryptedText.getBytes());                 // save output
            } else if (statisticalDecryption) {
                String decryptedText = TextProcessor.statisticalDecryption(text);   //start statistical decryption algorithm
                Files.write(outputFile, decryptedText.getBytes());
            }
        } catch (IOException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        CommandLine cmd = new CommandLine(new CaesarCipherCommand());
        cmd.execute( "-e", "-k=5", "res/SampleFile.txt", "res/output.txt");     //this code is used for execution in IDE
        //cmd.execute(args);                                                       //this code is used for execution in Terminal
    }

}
