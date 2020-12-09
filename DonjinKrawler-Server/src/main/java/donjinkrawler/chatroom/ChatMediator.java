package donjinkrawler.chatroom;

public interface ChatMediator {

    void sendMessage(RoomMate roomMate, String message);

    void addRoomMate(int id, RoomMate roomMate);

    RoomMate getRoomMate(int id);
}
