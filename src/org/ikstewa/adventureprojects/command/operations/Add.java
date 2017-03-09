package org.ikstewa.adventureprojects.command.operations;

import org.ikstewa.adventureprojects.SlotSeries;

import java.util.Optional;

/**
 * Creates a new state with the provided slot incremented.
 */
public class Add implements Operation {

    // one-based slot index
    private final Integer slot;

    public Add(final Integer slot) {
        if (slot == null || slot <= 0) {
            throw new IllegalArgumentException("Slot must be a non-zero integer");
        }
        this.slot = slot;
    }

    @Override
    public SlotSeries apply(Optional<SlotSeries> initialState) {
        return initialState
                .filter(s -> s.size() >= slot)
                .map(SlotSeries::toBuilder)
                .orElseThrow(() -> new IllegalArgumentException(String.format("Slot %d is not configured.", slot)))
                .incrementSlot(slot - 1)
                .build();
    }

    @Override
    public String toString() {
        return String.format("add %d", slot);
    }
}
