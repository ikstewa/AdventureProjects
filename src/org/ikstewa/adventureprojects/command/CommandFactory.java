package org.ikstewa.adventureprojects.command;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.BiFunction;

import org.ikstewa.adventureprojects.Printer;
import org.ikstewa.adventureprojects.StateManager;
import org.ikstewa.adventureprojects.command.operations.*;

/**
 * CommandFactory is responsible for parsing the input string and generating
 * the Command to be executed. Any new operations need to be registered here.
 */
public class CommandFactory {

    private static final Map<String, BiFunction<StateManager, String[], Command>> COMMAND_MAP = new HashMap<>();
    static {
        COMMAND_MAP.put("size", (state, args) ->
                new OperationCommand(state, new Size(parseInteger(args, 0, 1))));
        COMMAND_MAP.put("add", (state, args) ->
                new OperationCommand(state, new Add(parseInteger(args, 0, 1))));
        COMMAND_MAP.put("rm", (state, args) ->
                new OperationCommand(state, new Remove(parseInteger(args, 0, 1))));
        COMMAND_MAP.put("mv", (state, args) ->
                new OperationCommand(state, new Move(parseInteger(args, 0, 2), parseInteger(args, 1, 2))));
        COMMAND_MAP.put("undo", (state, args) ->
                new Undo(state, parseInteger(args, 0, 1)));
        COMMAND_MAP.put("replay", (state, args) ->
                new Replay(state, parseInteger(args, 0, 1)));
    }

    public Command loadCommand(final StateManager state, final String input) {
        if (input == null || input.trim().isEmpty()) {
            throw new IllegalArgumentException("Please enter a command.");
        }

        final String[] inputParts = input.trim().split(" ");
        final String command = inputParts[0];
        final String[] args = Arrays.copyOfRange(inputParts, 1, inputParts.length);

        final Optional<BiFunction<StateManager, String[], Command>> commandBuilder =
            Optional.ofNullable(COMMAND_MAP.get(command));

        return commandBuilder
                .map(builder -> builder.apply(state, args))
                .orElseThrow(
                        () -> new IllegalArgumentException(
                                String.format("Invalid command: '%s'", command)));
    }

    static Integer parseInteger(final String[] args, final int idx, final int argCount) {
        if (args.length != argCount) {
            throw new IllegalArgumentException("Unexpected argument count.");
        }

        Integer arg;
        try {
            arg = Integer.decode(args[idx]);
        } catch (final NumberFormatException e) {
            arg = null;
        }
        return arg;
    }

    /**
     * A Command which applies an Operation to the current state and prints the
     * latest state.
     */
    private static class OperationCommand implements Command {

        private final StateManager stateManager;
        private final Operation operation;

        public OperationCommand(
                final StateManager stateManager,
                final Operation operation) {
            this.stateManager = stateManager;
            this.operation = operation;
        }

        @Override
        public String execute() {
            this.stateManager.applyOperation(operation);
            return Printer.printSlots(stateManager.getCurrentState());
        }
    }
}
