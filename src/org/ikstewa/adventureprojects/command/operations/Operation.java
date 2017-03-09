package org.ikstewa.adventureprojects.command.operations;

import org.ikstewa.adventureprojects.SlotSeries;

import java.util.Optional;

/**
 * An operation is a transformation on the set of slots. The slots are
 * immutable so a new instance representing the state must be returned.
 */
public interface Operation {

    SlotSeries apply(Optional<SlotSeries> initialState);

}
