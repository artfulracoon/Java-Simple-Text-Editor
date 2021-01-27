package Commands.Receiver_Invoker;

import javax.swing.*;
import java.io.*;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;

import Memento_Originator_Caretaker.Caretaker;
import Memento_Originator_Caretaker.Originator;

public class Receiver {
    Caretaker caretaker;
    Originator originator;
    private JTextArea textArea;
    private JFrame frame;
    private String command;
    private static File dosya;
    private int result;
    private Vocabulary sozluk; //Sözlüğü oluşturuyor. Açıklaması kendi sınıfında.
    private String text; // Yazı alanındaki kelimeleri String olarak tutuyor.
    private String[] parsedText; //verilen parametrelere göre kelimeleri teker teker alıp bir listeye atıyor.
    private JFileChooser fileChooser; // Bazı butonlarda gereken dosya seçme fonksiyonu için diyalog

    public Receiver(Caretaker thatCaretaker, Originator thatOriginator, JFrame thatFrame, JTextArea thatTextArea, String thatCommand) {
        caretaker = thatCaretaker;
        originator = thatOriginator;
        setTextArea(thatTextArea);
        setFrame(thatFrame);
        setTextArea(thatTextArea);
        setText(getTextArea().getText());
        setParsedText(getText().split("[ .,;\n]"));
        setCommand(thatCommand);   //NotepadGUI sınıfında fonksiyonları çağırmak için gerekli değişkenleri constructor ile alıyoruz.
        setFileChooser(new JFileChooser());
        setSozluk(new Vocabulary("veri.txt"));
    }

    public void yeni() { // Yeni dosya
        if (getFrame().getTitle().equals("Ege Notepad App") & !getTextArea().getText().equals("")) {                                  //Eğer boş değilse önce kaydedip etmemek istediğinizi soruyor.
            setResult(JOptionPane.showConfirmDialog(getFrame(), "Yeni dosya açmadan önce varolan yazınızı kaydetmek ister misiniz?"));
            if (getResult() == 0) {
                kaydet();           // Kaydetmek isterseniz kaydet() fonksiyonunu çağırıp önce kaydediyor,
                getTextArea().setText(""); // Sonra text area'yı temizleyip title'ı default'a ayarlıyor.
                getFrame().setTitle("Ege Notepad App");
                caretaker.emptyMementos();
            } else if (getResult() == 1) { //Kaydetmek istemezseniz direkt area temzileyip title atıyor.
                getTextArea().setText("");
                getFrame().setTitle("Ege Notepad App");
                caretaker.emptyMementos();
            }
        } else if (!getFrame().getTitle().equals("Ege Notepad App")) {
            if (checkIfSame(getDosya().getAbsolutePath(), getTextArea().getText())) {
                getTextArea().setText("");
                getFrame().setTitle("Ege Notepad App");
                caretaker.emptyMementos();
            } else {
                setResult(JOptionPane.showConfirmDialog(getFrame(), "Yeni dosya açmadan önce bu dosyayı kaydetmek ister misiniz?"));
                if (getResult() == 0) {
                    kaydet();           // Kaydetmek isterseniz kaydet() fonksiyonunu çağırıp önce kaydediyor,
                    getTextArea().setText(""); // Sonra text area'yı temizleyip title'ı default'a ayarlıyor.
                    getFrame().setTitle("Ege Notepad App");
                    caretaker.emptyMementos();
                } else if (getResult() == 1) { //Kaydetmek istemezseniz direkt area temzileyip title atıyor.
                    getTextArea().setText("");
                    getFrame().setTitle("Ege Notepad App");
                    caretaker.emptyMementos();
                }

            }
        }
    }

    public void ac() { // Dosya aç
        if (getFrame().getTitle().equals("Ege Notepad App") & !getTextArea().getText().equals("")) {
            setResult(JOptionPane.showConfirmDialog(getFrame(), "Yeni dosya açmadan önce yaptığınız değişiklikleri kaydetmek ister misiniz?"));
            if (getResult() == 0) {
                kaydet();
            }
        }

        if (!getFrame().getTitle().equals("Ege Notepad App") & !checkIfSame(getFrame().getTitle(), getTextArea().getText())) {
            setResult(JOptionPane.showConfirmDialog(getFrame(), "Yeni dosya açmadan önce yaptığınız değişiklikleri kaydetmek ister misiniz?"));
            if (getResult() == 0) {
                kaydet();
            }
        }

        setResult(getFileChooser().showOpenDialog(null)); //Dosya açma diyaloğunu gösteriyor.
        if (getResult() == JFileChooser.APPROVE_OPTION) { // Eğer açmayı tercih etmişse
            setDosya(new File(getFileChooser().getSelectedFile().getAbsolutePath())); //Açılan dosyayı değişkene atıyor.
            getFrame().setTitle(getDosya().getAbsolutePath()); // Title'ı dosya adı olarak ayarlıyor.

            try { //Açılan dosyanın içindekiler okunup en sonda text area'ya yansıtılıyor.
                String s1;
                StringBuilder sl;
                FileReader fileReader = new FileReader(getDosya());
                BufferedReader bufferedReader = new BufferedReader(fileReader);
                sl = new StringBuilder(bufferedReader.readLine());
                while ((s1 = bufferedReader.readLine()) != null) {
                    sl.append("\n").append(s1);
                }
                getTextArea().setText(sl.toString());
                caretaker.emptyMementos();
                originator.setState(sl.toString());
                caretaker.add(originator.save());//Yeni bir karakter girildiğinde stack'e son halini ekliyor.

            } catch (Exception evt) { // Dosya açmada hata olursa uyarı veriyor.
                JOptionPane.showMessageDialog(getFrame(), "Dosya açılamadı! Açmak istediğiniz dosyanın yerini kontrol ediniz.");
            }
        }
    }


    public void kapat() { // Dosyayı Kapat
        if (getDosya() == null) { // Ağer açık dosya yoksa uyarı mesajı veriyor.
            JOptionPane.showMessageDialog(getFrame(), "Açık bir dosya yok.");
        } else {
            if (checkIfSame(getDosya().getAbsolutePath(), getTextArea().getText())) {
                setDosya(null);  //Eğer açık dosya varsa önce dosya değişkenini null atıyor.
                getFrame().setTitle("Ege Notepad App"); // Sonra title'ı default atayıp text area'yı temizliyor.
                getTextArea().setText("");
                JOptionPane.showMessageDialog(getFrame(), "Dosya kapatıldı."); // En sonda da mesajı gösteriyor.
                caretaker.emptyMementos();
            } else {
                setResult(JOptionPane.showConfirmDialog(getFrame(), "Dosyayı kapatmadan önce kaydetmek ister misiniz?"));
                if (getResult() == 2) {
                    JOptionPane.showMessageDialog(getFrame(), "İşlem iptal edildi.");
                } else {
                    if (getResult() == 0) {
                        kaydet();
                    }
                    setDosya(null);  //Eğer açık dosya varsa önce dosya değişkenini null atıyor.
                    getFrame().setTitle("Ege Notepad App"); // Sonra title'ı default atayıp text area'yı temizliyor.
                    getTextArea().setText("");
                    JOptionPane.showMessageDialog(getFrame(), "Dosya kapatıldı."); // En sonda da mesajı gösteriyor.
                    caretaker.emptyMementos();
                }
            }
        }
    }

    public void kaydet() { // Dosya kaydet
        try { // Eğer halihazırda açık dosya varsa onun üstüne kaydediyor.
            FileWriter wr = new FileWriter(getDosya(), false);
            BufferedWriter w = new BufferedWriter(wr);
            w.write(getTextArea().getText());
            w.flush();
            w.close();
            JOptionPane.showMessageDialog(getFrame(), "Dosya kaydedildi.");

        } catch (Exception evt) {  //Eğer açık dosya yoksa nereye kaydedileceğini seçtirip, sonra kaydediyor.
            JOptionPane.showMessageDialog(getFrame(), "Sonraki diyalogda nereye kaydedileceğini seçiniz.");
            setResult(getFileChooser().showSaveDialog(null));
            if (getResult() == JFileChooser.APPROVE_OPTION) {
                setDosya(new File(getFileChooser().getSelectedFile().getAbsolutePath()));
                try {
                    FileWriter wr = new FileWriter(getDosya(), false);
                    BufferedWriter w = new BufferedWriter(wr);
                    w.write(getTextArea().getText());
                    w.flush();
                    w.close();
                    getFrame().setTitle(getDosya().getAbsolutePath()); //En sonda da kaydettiği dosyayı açmış şekilde bitiriyor.
                } catch (IOException e) {
                    e.printStackTrace();
                    JOptionPane.showMessageDialog(getFrame(), "Veriler seçtiğiniz yere kaydedilemedi.");
                }
            }
        }
    }

    public void kelimeBulveDegistir() { // Kelime bul ve değiştir.
        if (getTextArea().getText().equals("")) { // Eğer text area boşsa kelime girmeniz için uyarı veriyor.
            JOptionPane.showMessageDialog(getFrame(), "Hata tespit etmek için öncelikle bir kelime yazmanız gerekmektedir...");
        } else { // Aradığınız kelimeyi girmeniz için bir diyalog gösteriyor.
            String kelime = JOptionPane.showInputDialog("Bulmak ve değiştirmek istediğiniz kelimeyi giriniz.");
            for (String word : getParsedText()) { //text area'daki kelimeleri ayırıp tutuyor. for loop'u her kelime için dönüyor.
                if (word.equals(kelime)) { //Değiştirilecek kelimeyi bulunca hangi kelime ile değiştirmek istediğini soruyor.
                    String duzeltilecekKelime = JOptionPane.showInputDialog("Hangi kelime ile değiştirileceğini giriniz: ");
                    getTextArea().replaceRange(duzeltilecekKelime, getText().indexOf(kelime), getText().indexOf(kelime) + kelime.length());
                    setText(getTextArea().getText()); // Kelimeyi değiştirip yeni text'i text area'ya atıyor.
                    setParsedText(getText().split("[ .,;\n]"));  // kelimeleri de yeni text'e göre ayırıyor.
                }
            }
        }
    }

    public void geriAl() {
        if (getTextArea().getText().equals("")) {
            try {
                getTextArea().setText(caretaker.getMemento().getState());
            } catch (Exception ignored) {
            }
        }
        if (caretaker.mementoCount() <= 1) {
            caretaker.remove();
            getTextArea().setText("");
        } else {
            caretaker.remove();
            getTextArea().setText(caretaker.getMemento().getState());
            caretaker.remove();
        }
    }

    public void hataliKelimeDuzelt() {// Hatalı kelimeleri düzeltiyor.
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
                    getTextArea().replaceRange(liste.get(0), getText().indexOf(word), getText().indexOf(word) + word.length()); // Kelime düzeltmeyi o kelimenin index'i'ni kullanarak yapıyor.
                    JOptionPane.showMessageDialog(getFrame(), "HATALI KELİME: " + word + "\nDÜZELTİLMİŞ HALİ: " + liste.get(0));// düzelttiklerini popup olarak gösteriyor.
                }
            }
        }
    }

    public boolean checkIfSame(String path, String text) {
        try {
            File file = new File(path);
            return text.equals(Files.readString(file.toPath()));
        } catch (IOException e) {
            return false;
        }

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

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public JFrame getFrame() {
        return frame;
    }

    public void setFrame(JFrame frame) {
        this.frame = frame;
    }

    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }

    public String[] getParsedText() {
        return parsedText;
    }

    public void setParsedText(String[] parsedText) {
        this.parsedText = parsedText;
    }

    public JFileChooser getFileChooser() {
        return fileChooser;
    }

    public void setFileChooser(JFileChooser fileChooser) {
        this.fileChooser = fileChooser;
    }

    public JTextArea getTextArea() {
        return textArea;
    }

    public void setTextArea(JTextArea textArea) {
        this.textArea = textArea;
    }

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }

    public static File getDosya() {
        return dosya;
    }

    public static void setDosya(File dosya) {
        Receiver.dosya = dosya;
    }

    public Vocabulary getSozluk() {
        return sozluk;
    }

    public void setSozluk(Vocabulary sozluk) {
        this.sozluk = sozluk;
    }

}
