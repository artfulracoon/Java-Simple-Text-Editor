package Main;

import Commands.*;
import Commands.Receiver_Invoker.*;

import Mediator.KelimeBulVeDegistir;
import Memento_Originator_Caretaker.*;
import Mediator.*;

import javax.swing.*;
import java.awt.event.*;
import javax.swing.UIManager;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

class NotepadGUI extends JFrame implements ActionListener {
    private JFrame frame;
    private JTextArea textArea;
    private Caretaker caretaker = new Caretaker();
    private Originator originator = new Originator();

    public NotepadGUI() { // GUI Constructor
        try {
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch (Exception ignored) {
        }

        setFrame(new JFrame("Ege Notepad App")); // GUI için frame'imizi oluşturuyoruz.
        JMenuBar menuBar = new JMenuBar(); // Dosya ve Düzenle sekmelerini içerecek menubar.
        setTextArea(new JTextArea()); // Kelimeleri yazacağımız text bölümünü oluşturuyoruz.
        JScrollPane scrollPane = new JScrollPane(getTextArea());
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        JMenu dosya = new JMenu("Dosya"); // Dosya sekmesi. İçerikleri altta
        JMenuItem yeni = new JMenuItem("Yeni"); // Yeni dosya
        JMenuItem ac = new JMenuItem("Aç"); // Dosya Aç
        JMenuItem kaydet = new JMenuItem("Kaydet"); // Dosya Kaydet
        JMenuItem kapat = new JMenuItem("Kapat"); // Dosya Kapat

        yeni.addActionListener(this);
        ac.addActionListener(this);   // Tıklama durumda olacaklar için listener ekleniyor.
        kaydet.addActionListener(this);
        kapat.addActionListener(this);

        dosya.add(yeni);
        dosya.add(ac);
        dosya.add(kaydet);   // Dosya sekmesine içerikler ekleniyor.
        dosya.add(kapat);

        JMenu duzenle = new JMenu("Düzenle"); // AYNI ISLEMLER DUZENLE SEKMESI ICIN YAPILIYOR
        JMenuItem bulVeDegistir = new JMenuItem("Kelime Bul ve Değiştir");
        JMenuItem geriAl = new JMenuItem("Geri Al");
        JMenuItem hataDuzelt = new JMenuItem("Hatalı Kelimeleri Düzelt");

        bulVeDegistir.addActionListener(this); // AYNI ISLEMLER
        geriAl.addActionListener(this);
        hataDuzelt.addActionListener(this);

        duzenle.add(geriAl);
        duzenle.add(bulVeDegistir);
        duzenle.add(hataDuzelt);

        menuBar.add(dosya);    //Menuye sekme butonları ekleniyor.
        menuBar.add(duzenle);

        getFrame().setJMenuBar(menuBar);
        getFrame().add(scrollPane);    // Frame'in özellikleri bekleniyor ve menubar ekleniyor.
        getFrame().setSize(500, 500);
        getFrame().setVisible(true);

        getFrame().addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                NotepadGUI.super.dispose();
                System.exit(0);
            }
        });
        setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);

        getTextArea().getDocument().addDocumentListener(new DocumentListener() {// Bu listener textArea'daki değişimlerde
            @Override                                                           // devreye giren fonksiyonları kapsıyor.
            public void insertUpdate(DocumentEvent e) {
                getOriginator().setState(getTextArea().getText());
                getCaretaker().add(getOriginator().save());//Yeni bir karakter girildiğinde stack'e son halini ekliyor.
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
            }
        });
    }

    public void actionPerformed(ActionEvent event) { // Tıklama durumlarında neler olacağını belirtiyoruz.
        Invoker invoker = Invoker.getInvoker();
        Receiver receiver = new Receiver(getCaretaker(), getOriginator(), getFrame(), getTextArea(), event.getActionCommand());

        Mediator mediator = new Mediator();
        mediator.add(new KelimeBulVeDegistir(getTextArea(), getFrame()));
        mediator.add(new HataliKelimeleriDuzelt(getTextArea(), getFrame()));

        // Bu switch, butonlara basıldığında hangi aksiyonların gerçekleştirileceğini seçiyor.
        switch (event.getActionCommand()) {
            case "Yeni" -> invoker.executeYeni(new YeniCommand(receiver));  // Caseler ismen kendilerini açıklıyor. Fonksiyonlar Commands paketinde.
            case "Kaydet" -> invoker.executeKaydet(new KaydetCommand(receiver));
            case "Aç" -> invoker.executeAc(new AcCommand(receiver));
            case "Kelime Bul ve Değiştir" -> mediator.executeKelimeBul();
            case "Geri Al" -> invoker.executeGeriAl(new GeriAlCommand(receiver));
            case "Hatalı Kelimeleri Düzelt" -> mediator.executeHataliKelime();
            case "Kapat" -> invoker.executeKapat(new KapatCommand(receiver));
        }
    }

    public JFrame getFrame() {
        return frame;
    }

    public void setFrame(JFrame frame) {
        this.frame = frame;
    }

    public JTextArea getTextArea() {
        return textArea;
    }

    public void setTextArea(JTextArea textArea) {
        this.textArea = textArea;
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

    public static class Main {
        public static void main(String[] args) {
            new NotepadGUI();
        }
    }
}
