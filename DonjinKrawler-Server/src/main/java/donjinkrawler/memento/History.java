package donjinkrawler.memento;

import java.util.LinkedList;

public class History {
    private LinkedList<Memento> history = new LinkedList<>();

    public void push(Memento m) {
        history.add(m);
    }

    public boolean undo() throws InterruptedException {
        Memento m = getUndo();
        if (m == null) {
            return false;
        }
        m.restore();
        return true;
    }


    private Memento getUndo() {
        if (history.size() > 0) {
            return history.removeLast();
        } else {
            return null;
        }
    }

}