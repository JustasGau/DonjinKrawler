package donjinkrawler.memento;

import donjinkrawler.GameServer;

public class Memento {
    private SavedObject backup;
    private GameServer server;

    public Memento(GameServer server) {
        this.server = server;
        this.backup = server.backup();
    }

    public void restore() {
        server.restore(backup);
    }
}