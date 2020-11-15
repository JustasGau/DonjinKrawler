package donjinkrawler;

import java.io.IOException;

public class Server {

    public static void main(String[] args) throws IOException {
        GameServer gameServer = new GameServer();
        gameServer.startServer();
    }
}
