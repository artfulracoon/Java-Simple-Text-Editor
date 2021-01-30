package Memento_Originator_Caretaker;

import java.util.ArrayList;
import java.util.List;

public class Caretaker {
    private MementoStack stack = new MementoStack();

    public void add(Memento memento) {
        getStack().push(memento);
    }

    public Memento getMemento() {
        return getStack().peek();
    }

    public void remove() {
        getStack().pop();
    }

    public int mementoCount() {
        return getStack().size();
    }

    public void emptyMementos() {
        setStack(new MementoStack());
    }

    public MementoStack getStack() {
        return stack;
    }

    public void setStack(MementoStack stack) {
        this.stack = stack;
    }

    public static class MementoStack {  //BASIT BIR STACK SINIFI, COMMENTLEMEYE GEREK DUYMADIM.
        private List<Memento> changes = new ArrayList<>();

        public Memento peek() {
            if (getChanges().isEmpty()) {
                return null;
            }
            return getChanges().get(getChanges().size() - 1);
        }

        public Memento pop() {
            if (getChanges().isEmpty()) {
                return null;
            }
            Memento top = getChanges().get(getChanges().size() - 1);
            getChanges().remove(getChanges().size() - 1);
            return top;
        }

        public void push(Memento element) {
            getChanges().add(element);
        }

        public int size() {
            return getChanges().size();
        }

        public boolean isEmpty() {
            return getChanges().isEmpty();
        }

        public List<Memento> getChanges() {
            return changes;
        }

        public void setChanges(List<Memento> changes) {
            this.changes = changes;
        }
    }
}
