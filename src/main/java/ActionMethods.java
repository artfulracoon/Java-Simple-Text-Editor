import javax.swing.*;
import java.io.*;
import java.util.ArrayList;

public class ActionMethods {

    private JFrame frame;
    private String command;
    private JTextArea textArea;
    private static File dosya;
    private int result;
    private Vocabulary sozluk; //Sözlüğü oluşturuyor. Açıklaması kendi sınıfında.
    private String text; // Yazı alanındaki kelimeleri String olarak tutuyor.
    private String[] parsedText; //verilen parametrelere göre kelimeleri teker teker alıp bir listeye atıyor.
    private JFileChooser fileChooser; // Bazı butonlarda gereken dosya seçme fonksiyonu için diyalog

    public ActionMethods(JFrame aFrame, JTextArea aTextArea, String aCommand) {
        setFrame(aFrame);
        setTextArea(aTextArea);
        setText(getTextArea().getText());
        setParsedText(getText().split("[ .,;\n]"));
        setCommand(aCommand);   //NotepadGUI sınıfında fonksiyonları çağırmak için gerekli değişkenleri constructor ile alıyoruz.
        setFileChooser(new JFileChooser());
        setSozluk(new Vocabulary("veri.txt"));
    }

    public void yeni() { // Yeni dosya
        if (getTextArea().getText().equals("")) { //Eğer text area boşsa herhangi bir işlem yapılmıyor.
        } else {                                  //Eğer boş değilse önce kaydedip etmemek istediğinizi soruyor.
            setResult(JOptionPane.showConfirmDialog(getFrame(), "Yeni dosya açmadan önce bu dosyayı kaydetmek ister misiniz?"));
            if (getResult() == 0) {
                kaydet();           // Kaydetmek isterseniz kaydet() fonksiyonunu çağırıp önce kaydediyor,
                getTextArea().setText(""); // Sonra text area'yı temizleyip title'ı default'a ayarlıyor.
                getFrame().setTitle("Ege Notepad App");
            } else if (getResult() == 1) { //Kaydetmek istemezseniz direkt area temzileyip title atıyor.
                getTextArea().setText("");
                getFrame().setTitle("Ege Notepad App");
            }
        }
    }

    public void ac() { // Dosya aç
        setResult(getFileChooser().showOpenDialog(null)); //Dosya açma diyaloğunu gösteriyor.
        if (getResult() == JFileChooser.APPROVE_OPTION) { // Eğer açmayı tercih etmişse
            setDosya(new File(getFileChooser().getSelectedFile().getAbsolutePath())); //Açılan dosyayı değişkene atıyor.
            getFrame().setTitle(getDosya().getName()); // Title'ı dosya adı olarak ayarlıyor.

            try { //Açılan dosyanın içindekiler okunup en sonda text area'ya yansıtılıyor.
                String s1 = "", sl = "";
                FileReader fileReader = new FileReader(getDosya());
                BufferedReader bufferedReader = new BufferedReader(fileReader);
                sl = bufferedReader.readLine();
                while ((s1 = bufferedReader.readLine()) != null) {
                    sl = sl + "\n" + s1;
                }
                getTextArea().setText(sl);

            } catch (Exception evt) { // Dosya açmada hata olursa uyarı veriyor.
                JOptionPane.showMessageDialog(getFrame(), "Dosya açılamadı! Açmak istediğiniz dosyanın yerini kontrol ediniz.");
            }
        }
    }

    public void kapat() { // Dosyayı Kapat
        if (getDosya() == null) { // Ağer açık dosya yoksa uyarı mesajı veriyor.
            JOptionPane.showMessageDialog(getFrame(), "Açık bir dosya yok.");
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
                    getFrame().setTitle(getDosya().getName()); //En sonda da kaydettiği dosyayı açmış şekilde bitiriyor.
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

    public void geriAl(TextChangeStack stack) { // Değişiklileri geri al.
        stack.pop(); // Değişiklikleri tutan stack'i parametre olarak aldıktan sonra, stacktaki son değişikliği çıkarıyor.
        getTextArea().setText(stack.peek()); // Sonuncuyu sildikten sonra text area'yı bir sonraki değişikliğe atıyor.
    }

    public void hataliKelimeDuzelt() {// Hatalı kelimeleri düzeltiyor.

        for (String word : getParsedText()) { // for loopu text'deki sözcük sayısı kadar dönüyor.
            if (getSozluk().getSozlukList().contains(word)) {
                // Eğer kelime sözlükte varsa o kelime geçiliyor..
            } else {
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
        ActionMethods.dosya = dosya;
    }

    public Vocabulary getSozluk() {
        return sozluk;
    }

    public void setSozluk(Vocabulary sozluk) {
        this.sozluk = sozluk;
    }
}
