package donjinkrawler.clientpacketcontrol;

import donjinkrawler.Client;
import donjinkrawler.Game;

public class Request {

    private final Client client;
    private final Object object;

    public Request(Client client, Object object) {
        this.client = client;
        this.object = object;
    }

    public Client getClient() {
        return client;
    }

    public Object getObject() {
        return object;
    }

    public boolean isGameActive() {
        return this.getClient().getGame() != null;
    }

    public Game getGame() {
        return this.getClient().getGame();
    }
}
