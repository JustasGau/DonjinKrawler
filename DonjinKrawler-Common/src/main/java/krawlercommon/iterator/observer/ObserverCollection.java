package krawlercommon.iterator.observer;

import krawlercommon.iterator.IterableCollection;
import krawlercommon.iterator.Iterator;
import krawlercommon.observer.Observer;

import java.util.ArrayList;
import java.util.HashMap;

public class ObserverCollection implements IterableCollection {
    public HashMap<Integer, Observer> observers = new HashMap<>();

    @Override
    public Iterator getIterator() {
        return new ObserverIterator(this);
    }

    public void add(Observer observer) {
        observers.put(observers.size() + 1, observer);
    }

    public void remove(Observer observer) {
        int keyToRemove = -1;

        for (Integer key : observers.keySet()) {
            Observer temp = observers.get(key);
            if (temp.equals(observer)) {
                keyToRemove = key;
                break;
            }
        }

        if (keyToRemove != -1) {
            observers.remove(keyToRemove);
        }
    }
}
