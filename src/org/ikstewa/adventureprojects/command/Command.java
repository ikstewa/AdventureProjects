package org.ikstewa.adventureprojects.command;

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
