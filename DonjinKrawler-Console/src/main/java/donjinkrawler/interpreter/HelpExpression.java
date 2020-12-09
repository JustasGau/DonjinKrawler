package donjinkrawler.interpreter;

import com.esotericsoftware.kryonet.Client;

public class HelpExpression implements Expression {
    @Override
    public void interpret(Client client) {
        System.out.println("List of supported commands:");
        System.out.println("exit\t\t\t-\texits out of DonjinKrawler-Console");
        System.out.println("kick player_id -\tkicks a player");
        System.out.println("getPlayers\t\t-\tgets a list of players");
    }
}
