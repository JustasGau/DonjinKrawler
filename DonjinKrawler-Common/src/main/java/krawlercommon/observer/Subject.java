package krawlercommon.observer;

public interface Subject {
    void attachObserver(Observer observer);
    void detachObserver(Observer observer);
    void detachAllObservers();
    void notifyObservers();
}
