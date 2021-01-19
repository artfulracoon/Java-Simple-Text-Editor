package Main;

import java.util.ArrayList;
import java.util.List;

public class TextChangeStack {  //BASIT BIR STACK SINIFI, COMMENTLEMEYE GEREK DUYMADIM.
    private List<String> changes = new ArrayList<>();

    public String peek() {
        if (getChanges().isEmpty()) {
            return null;
        }
        return getChanges().get(getChanges().size() - 1);
    }

    public String pop() {
        if (getChanges().isEmpty()) {
            return null;
        }
        String top = getChanges().get(getChanges().size() - 1);
        getChanges().remove(getChanges().size() - 1);
        return top;
    }

    public void push(String element) {
        getChanges().add(element);
    }

    public int size() {
        return getChanges().size();
    }

    public boolean isEmpty() {
        return getChanges().isEmpty();
    }

    public List<String> getChanges() {
        return changes;
    }

    public void setChanges(List<String> changes) {
        this.changes = changes;
    }
}