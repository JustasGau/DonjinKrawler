package krawlercommon.iterator.door;

import krawlercommon.iterator.Iterator;

public class DoorIterator implements Iterator {
    private DoorCollection collection;
    private Integer currentIndex = 0;

    public DoorIterator(DoorCollection collection) {
        this.collection = collection;
    }

    @Override
    public Boolean hasNext() {
        return currentIndex < collection.doors.size();
    }

    @Override
    public Object getNext() {
        if (hasNext()) {
            return collection.doors.get(currentIndex++);
        }

        return null;
    }
}
