package Mediator;

import javax.swing.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;

public class HataliKelimeleriDuzelt {
    private Vocabulary sozluk;
    private JTextArea textArea;
    private JFrame frame;
    private String[] parsedText;

    public HataliKelimeleriDuzelt(JTextArea thatTextArea, JFrame thatJFrame) {
        setTextArea(thatTextArea);
        setFrame(thatJFrame);
        setParsedText(getTextArea().getText().split("[ .,;\n]"));
        setSozluk(new Vocabulary("veri.txt"));
    }

    public void execute() {
        Iterator<String> iterator = getSozluk().getSozlukList().iterator();
        boolean check = false;
        for (String word : getParsedText()) { // for loopu text'deki sözcük sayısı kadar dönüyor.
            while (iterator.hasNext()) {
                if (iterator.next().equals(word)) {
                    check = true;
                    break;
                }
            }
            if (!check) {
                ArrayList<String> liste = new ArrayList<>(); // Doğru transpositionları tutan geçici liste
                char[] wordChars = new char[word.length()];
                word.getChars(0, word.length(), wordChars, 0);// Yoksa kelimeyi harflerine bölüp
                for (int i = 0; i < (wordChars.length - 1); i++) { //tüm single transpositionlarını buluyor.
                    char[] wordCharsPlaceholder = wordChars.clone(); // Daha sonra bu transpositionları sözlük ile karşılaştırıp,
                    char placeholder;                           // Eğer sözlükte bulursa altta listeye ekliyor
                    placeholder = wordCharsPlaceholder[i];
                    wordCharsPlaceholder[i] = wordCharsPlaceholder[i + 1];
                    wordCharsPlaceholder[i + 1] = placeholder;
                    String duzeltilmisWord = new String(wordCharsPlaceholder);
                    if (getSozluk().getSozlukList().contains(duzeltilmisWord)) {
                        liste.add(duzeltilmisWord);
                    }
                }
                if (liste.size() > 1) {
                    String s = liste.toString();
                    JOptionPane.showMessageDialog(getFrame(), "HATALI KELİME: " + word + "\nKelimenizin hatası birden fazla kelimeye düzeltilebilmektedir." +
                            "\nBunlar: \n" + s + "\nBu yüzden kelimenizde değişiklik yapılmamıştır."); // Liste 1'den uzunsa değişiklik yapılmıyor
                } else if (liste.size() == 1) {
                    getTextArea().replaceRange(liste.get(0), getTextArea().getText().indexOf(word), getTextArea().getText().indexOf(word) + word.length()); // Kelime düzeltmeyi o kelimenin index'i'ni kullanarak yapıyor.
                    JOptionPane.showMessageDialog(getFrame(), "HATALI KELİME: " + word + "\nDÜZELTİLMİŞ HALİ: " + liste.get(0));// düzelttiklerini popup olarak gösteriyor.
                }
            }
            check = false;
        }
    }

    public JTextArea getTextArea() {
        return textArea;
    }

    public void setTextArea(JTextArea textArea) {
        this.textArea = textArea;
    }

    public JFrame getFrame() {
        return frame;
    }

    public void setFrame(JFrame frame) {
        this.frame = frame;
    }

    public String[] getParsedText() {
        return parsedText;
    }

    public void setParsedText(String[] parsedText) {
        this.parsedText = parsedText;
    }

    public static class Vocabulary {
        private ArrayList<String> sozlukList;

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

    public Vocabulary getSozluk() {
        return sozluk;
    }

    public void setSozluk(Vocabulary sozluk) {
        this.sozluk = sozluk;
    }
}
