package command;

import donjinkrawler.Player;

public class MoveRightCommand implements Command{
    Player controllerPlayer;
    int oldX;
    int dx;

    public MoveRightCommand(Player newPlayer, int dx) {
        controllerPlayer = newPlayer;
        oldX = controllerPlayer.getX();
        this.dx = dx;
    }
    @Override
    public void execute() {
        controllerPlayer.setX(oldX + dx);
    }

    @Override
    public void undo() {
        controllerPlayer.setX(oldX);
    }
}