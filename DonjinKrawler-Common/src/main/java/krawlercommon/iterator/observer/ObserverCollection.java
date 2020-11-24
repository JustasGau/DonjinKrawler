package krawlercommon.iterator.observer;

import krawlercommon.iterator.IterableCollection;
import krawlercommon.iterator.Iterator;
import krawlercommon.observer.Observer;

import java.util.ArrayList;

public class ObserverCollection implements IterableCollection {
    public ArrayList<Observer> observers = new ArrayList<>();

    @Override
    public Iterator getIterator() {
        return new ObserverIterator(this);
    }
}
