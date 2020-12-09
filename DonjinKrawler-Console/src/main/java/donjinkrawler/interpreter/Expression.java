package donjinkrawler.interpreter;

import com.esotericsoftware.kryonet.Client;

public interface Expression {
    void interpret(Client client);
}
