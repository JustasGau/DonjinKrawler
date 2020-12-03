package donjinkrawler;

import com.esotericsoftware.kryonet.Connection;
import donjinkrawler.proxy.ListenerProxy;
import krawlercommon.ConnectionManager;
import krawlercommon.PlayerData;
import krawlercommon.packets.ServerFullPacket;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.*;

public class ListenerProxyTest {

    @AfterEach
    public void teardown() {
        ConnectionManager.getInstance().resetConnectionManager();
    }

    @Test
    public void testProxyWhenServerFull() {
        Connection connection = mock(Connection.class);
        when(connection.getID()).thenReturn(1);
        ConnectionManager.getInstance().addPlayer(connection, new PlayerData());
        when(connection.getID()).thenReturn(2);
        ConnectionManager.getInstance().addPlayer(connection, new PlayerData());
        when(connection.getID()).thenReturn(3);
        ConnectionManager.getInstance().addPlayer(connection, new PlayerData());
        when(connection.getID()).thenReturn(4);
        ConnectionManager.getInstance().addPlayer(connection, new PlayerData());
        ListenerProxy proxy = new ListenerProxy();
        when(connection.getID()).thenReturn(5);
        proxy.connected(connection);
        ConnectionManager.getInstance().removeConnectionById(4);
        proxy.connected(connection);
        Mockito.verify(connection, atMostOnce()).sendTCP(isA(ServerFullPacket.class));
        assertEquals(4, ConnectionManager.getInstance().getAllPlayers().size());
    }
}
