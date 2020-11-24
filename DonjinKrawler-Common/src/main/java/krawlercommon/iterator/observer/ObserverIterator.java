package krawlercommon.iterator.observer;

import krawlercommon.iterator.Iterator;

public class ObserverIterator implements Iterator {
    private ObserverCollection collection;
    private Integer currentIndex = 0;

    public ObserverIterator(ObserverCollection collection) {
        this.collection = collection;
    }

    @Override
    public Boolean hasNext() {
        return currentIndex < collection.observers.size();
    }

    @Override
    public Object getNext() {
        if (hasNext()) {
            return collection.observers.get(currentIndex++);
        }

        return null;
    }
}
