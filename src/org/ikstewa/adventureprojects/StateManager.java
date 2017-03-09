package org.ikstewa.adventureprojects;

import org.ikstewa.adventureprojects.command.operations.Operation;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Optional;

/**
 * StateManager is responsible for providing the latest state of the stacks,
 * applying operations, and providing the ability to undo previous operations.
 *
 * Note: This class is not thread safe and assumes only a single thread.
 */
public class StateManager {

    private final Deque<StateNode> stateHistory = new ArrayDeque<>();

    /**
     * @return the latest state of the stacks
     */
    public Optional<SlotSeries> getCurrentState() {
        return Optional.ofNullable(stateHistory.peekFirst())
                .map(StateNode::getEndingState);
    }

    /**
     * Applies the provided operation and updates the current state.
     * @param operation the operation to apply
     */
    public void applyOperation(final Operation operation) {
        stateHistory.addFirst(
                new StateNode(
                        operation,
                        operation.apply(getCurrentState())));
    }

    /**
     * Removes the last applied operation from the current stack.
     * @return the operation removed
     */
    public Optional<Operation> undoOperation() {
        return Optional.ofNullable(this.stateHistory.pollFirst())
                .map(StateNode::getAppliedOperation);
    }

    private static class StateNode {

        private Operation appliedOperation;
        private SlotSeries endingState;

        public StateNode(
                final Operation appliedOperation,
                final SlotSeries endingState) {
            this.appliedOperation = appliedOperation;
            this.endingState = endingState;
        }

        public Operation getAppliedOperation() {
            return this.appliedOperation;
        }

        public SlotSeries getEndingState() {
            return this.endingState;
        }
    }

}
