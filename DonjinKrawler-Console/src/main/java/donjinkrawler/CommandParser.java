package donjinkrawler;

import donjinkrawler.interpreter.*;

public final class CommandParser {
    private String command = "";
    private String argument = "";

    private void setFields(String input) {
        String[] parts = input.split(" ", 2);
        command = parts[0];

        if (parts.length == 2) {
            argument = parts[1];
        }
    }

    public Expression parse(String input) throws InvalidCommandException {
        setFields(input);

        if (command.equals("exit")) {
            return new ExitExpression();
        }

        if (command.equals("help")) {
            return new HelpExpression();
        }

        if (command.equals("kick")) {
            ArgumentExpression argumentExpression = new ArgumentExpression(argument);
            return new KickExpression(argumentExpression);
        }

        if (command.equals("getPlayers")) {
            return new GetPlayersExpression();
        }

        throw new InvalidCommandException("entered command is invalid");
    }
}
