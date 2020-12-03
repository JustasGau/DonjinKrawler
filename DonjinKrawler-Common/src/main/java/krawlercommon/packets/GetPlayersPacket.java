package krawlercommon.packets;

import krawlercommon.PlayerData;

import java.util.List;

public class GetPlayersPacket {
    List<PlayerData> players;

    public void setPlayers(List<PlayerData> players) {
        this.players = players;
    }

    public List<PlayerData> getPlayers() {
        return players;
    }
}
