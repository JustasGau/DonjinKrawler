package donjinkrawler.chatroom;

public abstract class RoomMate {

    protected ChatMediator mediator;
    protected String username;

    public RoomMate(ChatMediator mediator, String username) {
        this.mediator = mediator;
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public abstract void send(String message);

    public abstract void receive(RoomMate from, String message);
}
