import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

class VocabularyTest {

    @org.junit.jupiter.api.AfterEach
    void tearDown() {
        Runtime r = Runtime.getRuntime();
        System.out.println("Collecting garbage...");
        r.gc();
    }

    @Test
    public void vocabularySetterTest() { //vocabularySetter fonksiyonun testi.
        try {
            Scanner input = new Scanner(new File("veri.txt"));
            assertNotNull(input, "Dosya okumada hata çıktı.");
            ArrayList<String> sozcukler = new ArrayList<>();

            while (input.hasNextLine()) {
                Scanner scanner = new Scanner(input.nextLine());
                assertNotNull(scanner, "Dosya okumada hata çıktı.");
                while (scanner.hasNext()) {
                    ArrayList<String> placeholder = (ArrayList<String>) sozcukler.clone();
                    sozcukler.add(scanner.next());
                    assertNotEquals(placeholder, sozcukler);
                }
                assertNotNull(sozcukler, "Veriler okundu ama nedense ArrayList'e yazılamadı.");
            }
        } catch (FileNotFoundException e) {
            System.out.println("File Not Found");
        }
    }
}