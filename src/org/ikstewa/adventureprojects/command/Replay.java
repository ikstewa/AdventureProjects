package org.ikstewa.adventureprojects.command;

import org.ikstewa.adventureprojects.Printer;
import org.ikstewa.adventureprojects.StateManager;
import org.ikstewa.adventureprojects.command.operations.Operation;

import java.util.Deque;

/**
 * Replays the last specified number of commands on to the current state.
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

        final Deque<Operation> replayHistory = this.stateManager.getOperationHistory(this.count);

        // Replay all the removed operations
        final StringBuilder outputBuilder = new StringBuilder();
        while (!replayHistory.isEmpty()) {
            final Operation op = replayHistory.removeLast();
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
