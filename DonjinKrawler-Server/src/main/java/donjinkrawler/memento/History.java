package donjinkrawler.memento;

import java.util.ArrayList;
import java.util.List;

public class History {
    private List<Memento> history = new ArrayList<>();
    private int virtualSize = 0;

    public void push(Memento m) {
        if (virtualSize != history.size() && virtualSize > 0) {
            history = history.subList(0, virtualSize - 1);
        }
        history.add(m);
        virtualSize = history.size();
    }

    public boolean undo() {
        Memento m = getUndo();
        if (m == null) {
            return false;
        }
        m.restore();
        return true;
    }


    private Memento getUndo() {
        if (virtualSize == 0) {
            return null;
        }
        virtualSize = Math.max(0, virtualSize - 1);
        return history.get(virtualSize);
    }

}