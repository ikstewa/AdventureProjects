package org.ikstewa.adventureprojects.command.operations;

import org.ikstewa.adventureprojects.SlotSeries;

import java.util.Optional;

/**
 * Creates a new state with the provided size. Any slots trimmed out will drop
 * their boxes.
 */
public class Size implements Operation {

    private final Integer targetSize;

    public Size(final Integer size) {
        if (size == null || size < 0) {
            throw new IllegalArgumentException("Slot size must be a non-negative integer");
        }
        this.targetSize = size;
    }

    public SlotSeries apply(final Optional<SlotSeries> initialState) {
        return initialState
                .map(SlotSeries::toBuilder)
                .orElseGet(SlotSeries::builder)
                .resize(targetSize)
                .build();
    }

    @Override
    public String toString() {
        return String.format("size %d", targetSize);
    }
}

