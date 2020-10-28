package donjinkrawler.command;

import donjinkrawler.Player;

public class DamageCommand implements Command {

    Player controllerPlayer;
    double oldHealth;
    int damage;

    public DamageCommand(Player newPlayer, int damage) {
        controllerPlayer = newPlayer;
        oldHealth = controllerPlayer.getHealth();
        this.damage = damage;
    }

    @Override
    public void execute() {
        controllerPlayer.setHealth(oldHealth - damage);
    }

    @Override
    public void undo() {
        controllerPlayer.setHealth(oldHealth);
    }
}
