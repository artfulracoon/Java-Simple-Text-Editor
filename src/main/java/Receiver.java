import javax.swing.*;

public class Receiver {
    private TextChangeStack stack;
    private JTextArea textArea;

    public Receiver(TextChangeStack thatStack, JTextArea thatTextArea){
        this.setStack(thatStack);
        this.setTextArea(thatTextArea);
    }

    public void geriAl(){
        getStack().pop();
        getTextArea().setText(getStack().peek());
    }

    public TextChangeStack getStack() {
        return stack;
    }

    public void setStack(TextChangeStack stack) {
        this.stack = stack;
    }

    public JTextArea getTextArea() {
        return textArea;
    }

    public void setTextArea(JTextArea textArea) {
        this.textArea = textArea;
    }
}
