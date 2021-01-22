package Memento_Originator_Caretaker;

import javax.swing.*;

public class Originator {
    private String state;

    public Memento save() {
        return new Memento(getState());
    }

    public void restore(Memento memento) {
        setState(memento.getState());
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
