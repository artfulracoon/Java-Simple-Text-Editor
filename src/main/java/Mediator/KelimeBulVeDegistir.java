package Mediator;

import javax.swing.*;

public class KelimeBulVeDegistir {

    private JTextArea textArea;
    private JFrame frame;
    private String[] parsedText;

    public KelimeBulVeDegistir(JTextArea thatTextArea, JFrame thatJFrame) {
        setTextArea(thatTextArea);
        setFrame(thatJFrame);
        setParsedText(getTextArea().getText().split("[ .,;\n]"));
    }

    public void execute() {
        if (getTextArea().getText().equals("")) { // Eğer text area boşsa kelime girmeniz için uyarı veriyor.
            JOptionPane.showMessageDialog(getFrame(), "Hata tespit etmek için öncelikle bir kelime yazmanız gerekmektedir...");
        } else { // Aradığınız kelimeyi girmeniz için bir diyalog gösteriyor.
            String kelime = JOptionPane.showInputDialog("Bulmak ve değiştirmek istediğiniz kelimeyi giriniz.");
            for (String word : getParsedText()) { //text area'daki kelimeleri ayırıp tutuyor. for loop'u her kelime için dönüyor.
                if (word.equals(kelime)) { //Değiştirilecek kelimeyi bulunca hangi kelime ile değiştirmek istediğini soruyor.
                    String duzeltilecekKelime = JOptionPane.showInputDialog("Hangi kelime ile değiştirileceğini giriniz: ");
                    getTextArea().replaceRange(duzeltilecekKelime, getTextArea().getText().indexOf(kelime), getTextArea().getText().indexOf(kelime) + kelime.length());
                    getTextArea().setText(getTextArea().getText()); // Kelimeyi değiştirip yeni text'i text area'ya atıyor.
                    setParsedText(getTextArea().getText().split("[ .,;\n]"));  // kelimeleri de yeni text'e göre ayırıyor.
                }
            }
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
}
