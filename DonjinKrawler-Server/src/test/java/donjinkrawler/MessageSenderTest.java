package donjinkrawler;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Server;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class MessageSenderTest {

    Server mockServer;
    Connection mockConnection;

    @BeforeEach
    public void setUp() {
        mockServer = mock(Server.class);
        mockConnection = mock(Connection.class);
        when(mockConnection.getID()).thenReturn(1);
    }

    @Test
    public void testSendMessageToSingle() {
        MessageSender.sendMessageToSingle(mockConnection, mockServer, "Message");
        Mockito.verify(mockServer).sendToUDP(eq(1), any());
    }

    @Test
    public void testSendMessageToAll() {
        MessageSender.sendMessageToAll(mockServer, "Message");
        Mockito.verify(mockServer).sendToAllUDP(any());
    }

    @Test
    public void testSendMessageToAllExcept() {
        MessageSender.sendMessageToAllExcept(mockConnection, mockServer, "Message");
        Mockito.verify(mockServer).sendToAllExceptUDP(eq(1), any());
    }

}
