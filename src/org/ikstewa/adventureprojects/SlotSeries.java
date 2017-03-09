package org.ikstewa.adventureprojects;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.ListIterator;
import java.util.Objects;
import java.util.SortedMap;
import java.util.TreeMap;

/**
 * Immutable value for representing a snapshot of the state of the stacks.
 * Because state can be lost when removing slots, we keep a snapshot of the
 * result of each operation to support undo.
 */
public class SlotSeries {

    private final List<Integer> slots;

    private SlotSeries(final List<Integer> slots) {
        this.slots = Collections.unmodifiableList(Objects.requireNonNull(slots));
    }

    /**
     * @return the number of slots
     */
    public int size() {
        return slots.size();
    }

    /**
     * Determines the number of boxes in the provided slot index.
     *
     * @param slotIndex zero-based index
     * @return the number of boxes in the specified slot
     * @throws IndexOutOfBoundsException - if the index is out of range
     *                                   (slotIndex < 0 || slotIndex >= size())
     */
    public int stackSize(final int slotIndex) {
        return slots.get(slotIndex);
    }

    public Builder toBuilder() {
        final Builder builder = SlotSeries.builder();

        final ListIterator<Integer> itr = this.slots.listIterator();
        while (itr.hasNext()) {
            builder.withSlot(itr.nextIndex(), itr.next());
        }

        return builder;
    }

    public static Builder builder() {
        return new Builder();
    }

    /**
     * Builder for creating a new slot configuration.
     */
    public static class Builder {

        private SortedMap<Integer, Integer> configuredSlots = new TreeMap<>();

        /**
         * Configures the specified slot to contain the specified count. If the
         * provided index exceedes the currently configured capacity it will
         * adjust the number of slots available.
         */
        protected Builder withSlot(final int slotIndex, final int boxCount) {
            if (slotIndex < 0) {
                throw new IllegalArgumentException("Invalid slot index.");
            }
            if (boxCount < 0) {
                throw new IllegalArgumentException("Invalid box count.");
            }
            this.configuredSlots.put(slotIndex, boxCount);
            return this;
        }

        public Builder incrementSlot(final int slotIndex) {
            return withSlot(slotIndex, this.configuredSlots.getOrDefault(slotIndex, 0) + 1);
        }

        public Builder decrementSlot(final int slotIndex) {
            return withSlot(slotIndex, this.configuredSlots.getOrDefault(slotIndex, 0) - 1);
        }

        /**
         * Resizes the number of available slots. All boxes found in the removed
         * slots will be dropped.
         *
         * @param slotCount number of slots to resize to
         * @throws IllegalArgumentException - (slotCount < 0)
         */
        public Builder resize(final int slotCount) {
            if (slotCount < 0) {
                throw new IllegalArgumentException("Invalid slot count size.");
            }

            // Trim out all slots after the new size
            this.configuredSlots = this.configuredSlots.subMap(0, slotCount);

            // Configure the last slot as it marks the max size
            if (slotCount > 0) {
                this.withSlot(slotCount - 1, this.configuredSlots.getOrDefault(slotCount - 1, 0));
            }
            return this;
        }

        /**
         * Builds a new SlotSeries with the configured slots. All unconfigured
         * slots with an index less the the max configured index will default
         * to having zero boxes.
         * 
         * @return a new SlotSeries
         */
        public SlotSeries build() {
            if (this.configuredSlots.size() == 0) {
                return new SlotSeries(new ArrayList<>(0));
            }

            final int slotCount = configuredSlots.lastKey() + 1;
            final List<Integer> slotList = new ArrayList<>(slotCount);

            for (int slotIndex = 0; slotIndex < slotCount; slotIndex++) {
                slotList.add(
                    slotIndex,
                    this.configuredSlots.getOrDefault(slotIndex, 0));
            }

            return new SlotSeries(slotList);
        }
        
    }
}
