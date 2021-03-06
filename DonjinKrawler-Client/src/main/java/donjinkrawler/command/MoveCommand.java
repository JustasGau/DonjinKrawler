package donjinkrawler.command;

import donjinkrawler.Player;

public class MoveCommand implements Command {

    Player controllerPlayer;
    int oldY;
    int oldX;
    int dy;
    int dx;

    public MoveCommand(Player newPlayer, int dx, int dy) {
        controllerPlayer = newPlayer;
        oldY = controllerPlayer.getY();
        oldX = controllerPlayer.getX();
        this.dy = dy;
        this.dx = dx;
    }

    @Override
    public void execute() {
        controllerPlayer.setY(oldY + dy);
        controllerPlayer.setX(oldX + dx);
    }

    @Override
    public void undo() {
        controllerPlayer.setY(oldY);
        controllerPlayer.setX(oldX);
    }
}
