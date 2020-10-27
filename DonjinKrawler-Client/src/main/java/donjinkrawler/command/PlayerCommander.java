package donjinkrawler.command;

import java.util.LinkedList;

public class PlayerCommander {

    LinkedList<Command> history;

    public PlayerCommander() {
        history = new LinkedList<>();
    }

    public void execute(Command newCommand) {
        newCommand.execute();
        history.add(newCommand);
    }

    public void undo() {
        if(history.size() > 0) {
            Command oldCommand = history.removeLast();
            oldCommand.undo();
        }
    }
}
