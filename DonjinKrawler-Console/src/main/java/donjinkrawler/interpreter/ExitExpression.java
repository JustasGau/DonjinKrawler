package donjinkrawler.interpreter;

import com.esotericsoftware.kryonet.Client;

public class ExitExpression implements Expression {
    @Override
    public void interpret(Client client) {
        System.out.println("Exiting...");
        client.close();
        System.exit(0);
    }
}
