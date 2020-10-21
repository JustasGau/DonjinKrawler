package command;

import donjinkrawler.Player;

public class MoveDownCommand implements Command{

    Player controllerPlayer;
    int oldY;
    int dy;

    public MoveDownCommand(Player newPlayer, int dy) {
        controllerPlayer = newPlayer;
        oldY = controllerPlayer.getY();
        this.dy = dy;
    }
    @Override
    public void execute() {
        controllerPlayer.setY(oldY + dy);
    }

    @Override
    public void undo() {
        controllerPlayer.setY(oldY);
    }
}
