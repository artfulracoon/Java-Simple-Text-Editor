import javax.swing.*;
import java.awt.event.*;
import javax.swing.UIManager;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

class NotepadGUI extends JFrame implements ActionListener {
    private JFrame frame;
    private JTextArea textArea;
    private TextChangeStack stack = new TextChangeStack(); //TextArea'daki değişiklikleri tutan stack queue.

    public NotepadGUI() { // GUI Constructor
        try {
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch (Exception e) {
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

        duzenle.add(bulVeDegistir);
        duzenle.add(geriAl);
        duzenle.add(hataDuzelt);

        menuBar.add(dosya);    //Menuye sekme butonları ekleniyor.
        menuBar.add(duzenle);

        getFrame().setJMenuBar(menuBar);
        getFrame().add(scrollPane);    // Frame'in özellikleri bekleniyor ve menubar ekleniyor.
        getFrame().setSize(500, 500);
        getFrame().show();

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
                getStack().push(getTextArea().getText());//Yeni bir karakter girildiğinde stack'e son halini ekliyor.
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                getStack().pop();  // Bir karakter silindiğinde stackteki son update'i siliyor
            }                      // ve bu sayede bir önceki haline dönüyor.

            @Override
            public void changedUpdate(DocumentEvent e) {
            }
        });
    }

    public void actionPerformed(ActionEvent event) { // Tıklama durumlarında neler olacağını belirtiyoruz.
        String command = event.getActionCommand(); // Bastığımız butonun ismi tutuluyor, işlemleri buna göre yapacağız.
        ActionMethods action = new ActionMethods(getFrame(), getTextArea(), command); // ActionMethods sınıfından fonksiyonları çağırmak için gereken objeyi yaratıyoruz.
        Invoker invoker = new Invoker();

        switch (command) {  // Bu switch, butonlara basıldığında hangi aksiyonların gerçekleştirileceğini seçiyor.
            case "Yeni":    // Aksiyonların dediğimiz gibi ActionMethods sınıfında.
                action.yeni();  // Caseler ismen kendilerini açıklıyor. Fonksiyon açıklamaları ActionMethods'da.
                break;

            case "Kaydet":
                action.kaydet();
                break;

            case "Aç":
                action.ac();
                break;

            case "Kelime Bul ve Değiştir":
                action.kelimeBulveDegistir();
                break;

            case "Geri Al":
                invoker.executeGeriAl(new GeriAlCommand(new Receiver(getStack(), getTextArea())));
                break;

            case "Hatalı Kelimeleri Düzelt":
                action.hataliKelimeDuzelt();
                break;

            case "Kapat":
                action.kapat();
                break;

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

    public TextChangeStack getStack() {
        return stack;
    }

    public void setStack(TextChangeStack stack) {
        this.stack = stack;
    }
}
