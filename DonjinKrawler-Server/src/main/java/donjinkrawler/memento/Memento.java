package donjinkrawler.memento;

import donjinkrawler.GameServer;

public class Memento {
    private SavedObject backup;
    private GameServer server;

    public Memento(GameServer server) throws CloneNotSupportedException {
        this.server = server;
        this.backup = server.backup();
    }

    public void restore() throws InterruptedException {
        server.restore(backup);
    }
}