package org.ikstewa.adventureprojects;

import java.util.Optional;

/**
 * Helper class for printing latest state.
 */
public class Printer {

    private Printer() {
    }

    public static String printSlots(final Optional<SlotSeries> slots) {

        final StringBuilder builder = new StringBuilder();
        if (!slots.isPresent() || slots.get().size() == 0) {
            builder.append(String.format("[No slots configured.]%n"));
        } else {
            for (int idx = 0; idx < slots.get().size(); idx++) {
                builder.append(String.format("%d: ", idx + 1));
                for (int boxCount = 0; boxCount < slots.get().stackSize(idx); boxCount++) {
                    builder.append("X");
                }
                builder.append(String.format("%n"));
            }
        }
        return builder.toString();
    }
}
