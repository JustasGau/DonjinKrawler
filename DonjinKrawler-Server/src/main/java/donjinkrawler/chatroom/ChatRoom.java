package donjinkrawler.chatroom;

import java.util.HashMap;
import java.util.Map;

public final class ChatRoom implements ChatMediator {

    private final Map<Integer, RoomMate> roomMates;

    public ChatRoom(){
        this.roomMates = new HashMap<>();
    }

    @Override
    public void addRoomMate(int id, RoomMate roomMate) {
        this.roomMates.put(id, roomMate);
    }

    @Override
    public RoomMate getRoomMate(int id) {
        return this.roomMates.get(id);
    }

    @Override
    public void sendMessage(RoomMate sender, String message) {
        for(RoomMate receiver : this.roomMates.values()) {
            // Message should not be received by the user sending it
            if(receiver != sender){
                receiver.receive(sender, message);
            }
        }
    }
}
