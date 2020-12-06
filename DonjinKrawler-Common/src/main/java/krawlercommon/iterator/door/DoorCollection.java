package krawlercommon.iterator.door;

import krawlercommon.iterator.IterableCollection;
import krawlercommon.iterator.Iterator;
import krawlercommon.map.Door;

import java.util.ArrayList;

public class DoorCollection implements IterableCollection {
    public ArrayList<Door> doors = new ArrayList<>();

    @Override
    public Iterator getIterator() {
        return new DoorIterator(this);
    }

    public Door get(int i) {
        return doors.get(i);
    }

    public int size() {
        return doors.size();
    }

    public void add(Door door) {
        doors.add(door);
    }
}
