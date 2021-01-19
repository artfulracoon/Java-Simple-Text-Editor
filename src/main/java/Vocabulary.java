import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Vocabulary {
    private ArrayList<String> sozlukList;  // VOCABULARY SINIFININ TESTI VAR, TEST DOSYASINA BAKABILIRSINIZ.

    public Vocabulary(String s) {
        setSozlukList(vocabularySetter(s));
    }

    public ArrayList<String> vocabularySetter(String dosyaPath) {
        try {
            sozlukList = new ArrayList<>();
            Scanner input = new Scanner(new File(dosyaPath)); // Dosya okuyup dosyadaki sözcükleri
            while (input.hasNextLine()) {
                Scanner scanner = new Scanner(input.nextLine());
                while (scanner.hasNext()) {
                    getSozlukList().add(scanner.next());
                }
            }
            return getSozlukList();
        } catch (FileNotFoundException e) {
            System.out.println("File not found!");
            return null;
        }
    }

    public ArrayList<String> getSozlukList() {
        return sozlukList;
    }

    public void setSozlukList(ArrayList<String> sozlukList) {
        this.sozlukList = sozlukList;
    }
}
