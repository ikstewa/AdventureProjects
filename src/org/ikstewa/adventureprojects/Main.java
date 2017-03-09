package org.ikstewa.adventureprojects;

import org.ikstewa.adventureprojects.command.Command;
import org.ikstewa.adventureprojects.command.CommandFactory;

import java.io.Console;

public class Main {

    public static void main(String[] args) {

        final StateManager state = new StateManager();
        final CommandFactory commandFactory = new CommandFactory();

        final Console console = System.console();

        while (true) {
            final String input = console.readLine("> ");

            try {
                final Command command = commandFactory.loadCommand(state, input);
                console.format("%s%n", command.execute());
            } catch (final IllegalArgumentException e) {
                console.format("ERROR: %s%n", e.getMessage());
                //e.printStackTrace(console.writer());
            }
        }
    }
}
