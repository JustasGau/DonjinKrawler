package donjinkrawler.observer;

public interface Subject {
    void attach(Pet pet);
    void detach(Pet pet);
    void notifyMovement();
}
