package org.ikstewa.adventureprojects.command;

import org.ikstewa.adventureprojects.Printer;
import org.ikstewa.adventureprojects.StateManager;
import org.ikstewa.adventureprojects.command.operations.Operation;

import java.util.ArrayDeque;
import java.util.Optional;
import java.util.Stack;

/**
 * Replays the last specified number of commands showing how the state was
 * previously modified.
 */
public class Replay implements Command {

    private StateManager stateManager;
    private int count;

    public Replay(final StateManager stateManager, final Integer count) {
        if (count == null || count <= 0) {
            throw new IllegalArgumentException("Count must be a non-negative integer");
        }
        this.stateManager = stateManager;
        this.count = count;
    }

    @Override
    public String execute() {
        final Stack<Operation> replayHistory = new Stack<>();

        // Undo the operations from the current state
        for (int i = 1; i <= count; i++) {
            Optional<Operation> lastCommand = this.stateManager.undoOperation();
            if (lastCommand.isPresent()) {
                replayHistory.push(lastCommand.get());
            }
        }

        // Replay all the removed operations
        final StringBuilder outputBuilder = new StringBuilder();
        while (!replayHistory.empty()) {
            final Operation op = replayHistory.pop();
            this.stateManager.applyOperation(op);

            outputBuilder.append(String.format("> %s%n", op));
            outputBuilder.append(String.format(Printer.printSlots(this.stateManager.getCurrentState())));
        }

        if (outputBuilder.length() > 0) {
            return String.format("----Replay----%n%s--------------%n", outputBuilder.toString());
        } else {
            return "";
        }
    }

}
