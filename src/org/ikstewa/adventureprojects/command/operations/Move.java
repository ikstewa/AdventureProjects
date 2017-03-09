package org.ikstewa.adventureprojects.command.operations;

import org.ikstewa.adventureprojects.SlotSeries;

import java.util.Optional;

/**
 * Creates a new state with a box moved from one slot to another. Removing a box from
 * a slot which has no boxes will result in an error.
 */
public class Move implements Operation {

    // one-based slot index
    private final Integer slotFrom;
    private final Integer slotTo;

    public Move(final Integer slotFrom, final Integer slotTo) {
        if (slotFrom == null || slotFrom <= 0) {
            throw new IllegalArgumentException("Slot must be a non-zero integer");
        }
        if (slotTo == null || slotTo <= 0) {
            throw new IllegalArgumentException("Slot must be a non-zero integer");
        }
        this.slotFrom = slotFrom;
        this.slotTo = slotTo;
    }

    @Override
    public SlotSeries apply(Optional<SlotSeries> initialState) {
        final SlotSeries initial =
                initialState
                        .filter(s -> s.size() >= slotFrom)
                        .filter(s -> s.size() >= slotTo)
                        .orElseThrow(() -> new IllegalArgumentException(
                                String.format("Slot %d or %d is not configured.", slotFrom, slotTo)));
        if (initial.stackSize(slotFrom - 1) <= 0) {
            throw new IllegalArgumentException(String.format("No boxes found in slot %d.", slotFrom));
        }
        return initial.toBuilder()
                .decrementSlot(slotFrom - 1)
                .incrementSlot(slotTo - 1)
                .build();
    }

    @Override
    public String toString() {
        return String.format("move %d %d", slotFrom, slotTo);
    }
}
