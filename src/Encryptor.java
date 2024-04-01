import javax.swing.*;
import java.io.RandomAccessFile;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.RandomAccess;
import java.util.stream.Stream;

public class Encryptor {
    private Path source;
    private Path destination;
    private int key;

    private Encryptor(String source, String destination, int key) {
        this.source = Paths.get(source);
        this.destination = Paths.get(destination);
        this.key = key;
    }

    public void of (String source, String destination, int key){
        if (Files.isRegularFile(Path.of(source))) {
            Encryptor encryptor = new Encryptor(source, destination, key);
        } else {
            System.out.println("Файл не найден");
        }
    }

    private void Encrypt() {
        try (RandomAccessFile sourceFile = new RandomAccessFile(source.toString() , "r")) {
            Stream<String> read = Files.lines(this.source);

        } catch (Exception e) {
            System.out.println("Something went wrong: " + e);
        }

    }
}
