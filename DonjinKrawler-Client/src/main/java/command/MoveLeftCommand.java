package command;

import donjinkrawler.Player;

public class MoveLeftCommand implements Command{
    Player controllerPlayer;
    int oldX;
    int dx;

    public MoveLeftCommand(Player newPlayer, int dx) {
        controllerPlayer = newPlayer;
        oldX = controllerPlayer.getX();
        this.dx = dx;
    }
    @Override
    public void execute() {
        controllerPlayer.setX(oldX - dx);
    }

    @Override
    public void undo() {
        controllerPlayer.setX(oldX);
    }
}