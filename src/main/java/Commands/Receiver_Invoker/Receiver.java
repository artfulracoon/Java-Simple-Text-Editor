package Commands.Receiver_Invoker;

import javax.swing.*;
import java.io.*;
import java.nio.file.Files;

import Memento_Originator_Caretaker.Caretaker;
import Memento_Originator_Caretaker.Originator;

public class Receiver {
    private Caretaker caretaker;
    private Originator originator;
    private JTextArea textArea;
    private JFrame frame;
    private String command;
    private static File dosya;
    private int result;
    private String[] parsedText; //verilen parametrelere göre kelimeleri teker teker alıp bir listeye atıyor.
    private JFileChooser fileChooser; // Bazı butonlarda gereken dosya seçme fonksiyonu için diyalog

    public Receiver(Caretaker thatCaretaker, Originator thatOriginator, JFrame thatFrame, JTextArea thatTextArea, String thatCommand) {
        setCaretaker(thatCaretaker);
        setOriginator(thatOriginator);
        setTextArea(thatTextArea);
        setFrame(thatFrame);
        setTextArea(thatTextArea);
        setParsedText(getTextArea().getText().split("[ .,;\n]"));
        setCommand(thatCommand);   //NotepadGUI sınıfında fonksiyonları çağırmak için gerekli değişkenleri constructor ile alıyoruz.
        setFileChooser(new JFileChooser());
    }

    public void yeni() { // Yeni dosya
        if (getFrame().getTitle().equals("Ege Notepad App") & !getTextArea().getText().equals("")) {                                  //Eğer boş değilse önce kaydedip etmemek istediğinizi soruyor.
            setResult(JOptionPane.showConfirmDialog(getFrame(), "Yeni dosya açmadan önce varolan yazınızı kaydetmek ister misiniz?"));
            if (getResult() == 0) {
                kaydet();           // Kaydetmek isterseniz kaydet() fonksiyonunu çağırıp önce kaydediyor,
                getTextArea().setText(""); // Sonra text area'yı temizleyip title'ı default'a ayarlıyor.
                getFrame().setTitle("Ege Notepad App");
                getCaretaker().emptyMementos();
            } else if (getResult() == 1) { //Kaydetmek istemezseniz direkt area temzileyip title atıyor.
                getTextArea().setText("");
                getFrame().setTitle("Ege Notepad App");
                getCaretaker().emptyMementos();
            }
        } else if (!getFrame().getTitle().equals("Ege Notepad App")) {
            if (checkIfSame(getDosya().getAbsolutePath(), getTextArea().getText())) {
                getTextArea().setText("");
                getFrame().setTitle("Ege Notepad App");
                getCaretaker().emptyMementos();
            } else {
                setResult(JOptionPane.showConfirmDialog(getFrame(), "Yeni dosya açmadan önce bu dosyayı kaydetmek ister misiniz?"));
                if (getResult() == 0) {
                    kaydet();           // Kaydetmek isterseniz kaydet() fonksiyonunu çağırıp önce kaydediyor,
                    getTextArea().setText(""); // Sonra text area'yı temizleyip title'ı default'a ayarlıyor.
                    getFrame().setTitle("Ege Notepad App");
                    getCaretaker().emptyMementos();
                } else if (getResult() == 1) { //Kaydetmek istemezseniz direkt area temzileyip title atıyor.
                    getTextArea().setText("");
                    getFrame().setTitle("Ege Notepad App");
                    getCaretaker().emptyMementos();
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
                getCaretaker().emptyMementos();
                getOriginator().setState(sl.toString());
                getCaretaker().add(getOriginator().save());//Yeni bir karakter girildiğinde stack'e son halini ekliyor.

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
                getCaretaker().emptyMementos();
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
                    getCaretaker().emptyMementos();
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

    public void geriAl() {
        if (getTextArea().getText().equals("")) {
            try {
                getTextArea().setText(getCaretaker().getMemento().getState());
            } catch (Exception ignored) {
            }
        }
        else if (getCaretaker().mementoCount() <= 1) {
            getCaretaker().remove();
            getTextArea().setText("");
        } else {
            //getCaretaker().remove();
            getTextArea().setText(getCaretaker().getMemento().getState());
            getCaretaker().remove();
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

    public Caretaker getCaretaker() {
        return caretaker;
    }

    public void setCaretaker(Caretaker caretaker) {
        this.caretaker = caretaker;
    }

    public Originator getOriginator() {
        return originator;
    }

    public void setOriginator(Originator originator) {
        this.originator = originator;
    }
}
