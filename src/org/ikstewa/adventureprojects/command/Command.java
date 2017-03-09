package org.ikstewa.adventureprojects.command;

import org.ikstewa.adventureprojects.Printer;

/**
 * Interface for a command that can be executed.
 */
public interface Command {

    /**
     * Execute the given command.
     * @return output to display
     */
    String execute();

}
