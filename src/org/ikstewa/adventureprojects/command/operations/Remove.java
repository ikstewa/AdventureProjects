package org.ikstewa.adventureprojects.command.operations;

import org.ikstewa.adventureprojects.SlotSeries;

import java.util.Optional;

/**
 * Creates a new state with the provided slot decremented. Removing a box from
 * a slot which has no boxes will result in an error.
 */
public class Remove implements Operation {

    // one-based slot index
    private final Integer slot;

    public Remove(final Integer slot) {
        if (slot == null || slot <= 0) {
            throw new IllegalArgumentException("Slot must be a non-zero integer");
        }
        this.slot = slot;
    }

    @Override
    public SlotSeries apply(Optional<SlotSeries> initialState) {
        final SlotSeries initial =
                initialState
                        .filter(s -> s.size() >= slot)
                        .orElseThrow(() -> new IllegalArgumentException(
                                String.format("Slot %d is not configured.", slot)));
        if (initial.stackSize(slot - 1) <= 0) {
            throw new IllegalArgumentException(String.format("No boxes found in slot %d.", slot));
        }
        return initial.toBuilder()
                .decrementSlot(slot - 1)
                .build();
    }

    @Override
    public String toString() {
        return String.format("rm %d", slot);
    }
}
