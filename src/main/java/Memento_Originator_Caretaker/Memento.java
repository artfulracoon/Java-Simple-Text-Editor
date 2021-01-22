package Memento_Originator_Caretaker;

import javax.swing.*;

public class Memento {
    private String state;

    public Memento(String thatText) {
        setState(thatText);
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
