package org.ikstewa.adventureprojects.command;

import org.ikstewa.adventureprojects.Printer;
import org.ikstewa.adventureprojects.StateManager;
import org.ikstewa.adventureprojects.command.operations.Operation;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Optional;

/**
 * Removes the last specified number of operations. If there are no more
 * operations to remove the command will finish successfully.
 */
public class Undo implements Command {

    private StateManager stateManager;
    private int count;

    public Undo(final StateManager stateManager, final Integer count) {
        if (count == null || count <= 0) {
            throw new IllegalArgumentException("Count must be a non-negative integer");
        }
        this.stateManager = stateManager;
        this.count = count;
    }

    @Override
    public String execute() {
        final StringBuilder outputBuilder = new StringBuilder();
        for (int i = 1; i <= count; i++) {
            Optional<Operation> lastOperation = this.stateManager.undoOperation();
            if (lastOperation.isPresent()) {
                outputBuilder.append(String.format("> %s%n", lastOperation.get()));
                outputBuilder.append(String.format(Printer.printSlots(this.stateManager.getCurrentState())));
            }

        }
        if (outputBuilder.length() > 0) {
            return String.format("----Undo----%n%s------------%n", outputBuilder.toString());
        } else {
            return "";
        }
    }

}
