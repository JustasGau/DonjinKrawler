package donjinkrawler.interpreter;

import com.esotericsoftware.kryonet.Client;

public class ArgumentExpression implements Expression {
    private String value;

    public ArgumentExpression(String value) throws InvalidCommandException {
        if (value.isBlank()) {
            throw new InvalidCommandException("you must provide an argument to this command");
        } else {
            this.value = value;
        }
    }

    public String getValue() {
        return value;
    }

    @Override
    public void interpret(Client client) {
        System.out.println("No operations supported with this type of expression");
    }
}
