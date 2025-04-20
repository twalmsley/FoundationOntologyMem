package uk.co.aosd.onto.foundation;

import static org.junit.jupiter.api.Assertions.assertSame;

import java.time.Instant;
import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.junit.jupiter.api.Test;
import uk.co.aosd.onto.events.Assembled;
import uk.co.aosd.onto.events.Created;
import uk.co.aosd.onto.events.Destroyed;
import uk.co.aosd.onto.events.Disassembled;
import uk.co.aosd.onto.events.Started;
import uk.co.aosd.onto.events.Stopped;
import uk.co.aosd.onto.reference.AgglomerateImpl;
import uk.co.aosd.onto.reference.events.AggregatedImpl;
import uk.co.aosd.onto.reference.events.AssembledImpl;
import uk.co.aosd.onto.reference.events.CreatedImpl;
import uk.co.aosd.onto.reference.events.DestroyedImpl;
import uk.co.aosd.onto.reference.events.DisaggregatedImpl;
import uk.co.aosd.onto.reference.events.DisassembledImpl;
import uk.co.aosd.onto.reference.events.StartedImpl;
import uk.co.aosd.onto.reference.events.StoppedImpl;

/**
 * An example to show object composition and how it changes over time.
 *
 * <p>
 * Trigger's broom consists of some bristles, a broom head, a broom handle, and
 * a bracket to connect the handle to the head. In this example we construct a
 * broom and replace some or all of its parts.
 * </p>
 *
 * <p>
 * The Broom class represents a composition of the BroomHandle and the
 * BroomHeadWithBracketAssembly, which then also has nested parts. In this
 * example the composition reflects the way the broom was constructed, but it is
 * also possible to use a flatter structure in which the broom has the four
 * parts as immediate child properties. In this second case it may be necessary
 * to construct the broom in one go, or to make the parts Optional, however this
 * would not capture the 4D aspects of the object relationships correctly.
 * </p>
 *
 * @author Tony Walmsley
 */
public class TriggersBroomTest {

    private static final Created LIFE_START = new CreatedImpl(randStr(), Instant.parse("2024-01-01T12:00:00.00Z"), Instant.parse("2024-01-01T12:00:00.00Z"));
    private static final Destroyed UNKNOWN_END = new DestroyedImpl(randStr(), null, null);
    private static final AggregatedImpl AGGREGATED = new AggregatedImpl(randStr(), Instant.parse("2024-01-01T12:00:00.00Z"),
        Instant.parse("2024-01-01T12:00:00.00Z"));
    private static final DisaggregatedImpl DISAGGREGATED = new DisaggregatedImpl(randStr(), null, null);
    private static final Assembled ASSEMBLY_START = new AssembledImpl(randStr(), Instant.parse("2024-11-01T12:00:00.00Z"),
        Instant.parse("2024-11-01T12:00:00.00Z"));
    private static final Disassembled ASSEMBLY_END = new DisassembledImpl(randStr(), null, null);

    @Test
    public void test() {
        //
        // Create the parts
        //
        final var broomHandle = new BroomHandle(randStr(), LIFE_START, UNKNOWN_END);
        final var broomHead = new BroomHead(randStr(), LIFE_START, UNKNOWN_END);
        final var bristles = new Bristles(randStr(), LIFE_START, UNKNOWN_END);
        final var broomBracket = new BroomBracket(randStr(), LIFE_START, UNKNOWN_END);

        // Gather the parts into an Agglomerate (not really necessary, this just shows
        // what an Agglomerate is)
        final var parts = new AgglomerateImpl(randStr(), Set.of(broomBracket, bristles, broomHead, broomHandle), AGGREGATED, DISAGGREGATED);

        // Assemble the broom composite from the set of parts
        final var broom = assembleBroom(parts);

        //
        // Check the composition is correct.
        //
        assertSame(broom.getHandle(), broomHandle);
        assertSame(broom.getHeadWithBracketAssembly().getBracket(), broomBracket);
        assertSame(broom.getHeadWithBracketAssembly().getHeadAssembly().getBristles(), bristles);
        assertSame(broom.getHeadWithBracketAssembly().getHeadAssembly().getHead(), broomHead);

        // The bristles are worn out after much use, so replace them
        final var activityFrom = new StartedImpl(randStr(), Instant.parse("2024-11-11T12:00:00.00Z"), Instant.parse("2024-11-11T12:00:00.00Z"));
        final var activityTo = new StoppedImpl(randStr(), Instant.parse("2024-11-11T12:30:00.00Z"), Instant.parse("2024-11-11T12:30:00.00Z"));
        final var activityRecord = replaceBristles(broom, new Bristles(randStr(), LIFE_START, UNKNOWN_END), activityFrom, activityTo);

        assertSame(activityFrom, activityRecord.getBeginning());
        assertSame(activityTo, activityRecord.getEnding());

        assertSame(activityFrom.getFrom(), activityRecord.getOldBroom().getEnding().getFrom());
        assertSame(activityFrom.getFrom(), activityRecord.getOldBroom().getHeadWithBracketAssembly().getHeadAssembly().getBristles().getEnding().getFrom());
        assertSame(activityFrom.getFrom(), activityRecord.getOldBristles().getEnding().getFrom());
        assertSame(activityFrom.getFrom(), activityRecord.getOldHeadAssembly().getEnding().getFrom());
        assertSame(activityFrom.getFrom(), activityRecord.getOldHeadWithBracketAssembly().getEnding().getFrom());

        assertSame(activityFrom.getFrom(), activityRecord.getNewBroom().getBeginning().getFrom());
        assertSame(UNKNOWN_END.getFrom(), activityRecord.getNewBroom().getEnding().getFrom());

        assertSame(broomBracket, activityRecord.getNewBroom().getHeadWithBracketAssembly().getBracket());
        assertSame(broomHead, activityRecord.getNewBroom().getHeadWithBracketAssembly().getHeadAssembly().getHead());
        assertSame(broomHandle, activityRecord.getNewBroom().getHandle());
    }

    /**
     * Replace the worn out bristles, close off the lifetimes of the old assemblies
     * and old bristles, * create new assemblies and a new broom.
     *
     * @param broom
     *            The Broom
     * @param bristles
     *            The new Bristles
     * @param activityStart
     *            Event
     * @param activityEnd
     *            Event
     * @return ReplaceBristlesActivity
     */
    private ReplaceBristlesActivity replaceBristles(final Broom broom, final Bristles bristles, final Started activityStart, final Stopped activityEnd) {

        final Assembled assembled = new AssembledImpl(randStr(), activityStart.getFrom(), activityStart.getFrom());
        final Destroyed destroyed = new DestroyedImpl(randStr(), activityStart.getFrom(), activityStart.getFrom());
        final Disassembled disassembled = new DisassembledImpl(randStr(), activityStart.getFrom(), activityStart.getFrom());
        final Disassembled unknownDisassembled = new DisassembledImpl(randStr(), null, null);

        // Set the ending for the old headAssembly
        final var headAssembly = broom.getHeadWithBracketAssembly().getHeadAssembly();
        final Bristles oldBristles = new Bristles(headAssembly.getBristles().getIdentifier(), headAssembly.getBristles().getBeginning(), destroyed);
        final var oldHeadAssembly = new BroomHeadAssembly(headAssembly.getIdentifier(), headAssembly.getHead(), oldBristles, assembled, disassembled);

        // Set the ending for the old headAndBracketAssembly
        final var headAndBracketAssembly = broom.getHeadWithBracketAssembly();
        final var oldHeadWithBracketAssembly = new BroomHeadWithBracketAssembly(headAndBracketAssembly.getIdentifier(), oldHeadAssembly,
            headAndBracketAssembly.getBracket(), assembled, disassembled);

        // Set the ending for the old Broom.
        final var oldBroom = new Broom(randStr(), broom.getHandle(), oldHeadWithBracketAssembly, broom.getBeginning(), disassembled);

        // Create the updated broom
        final var updatedHeadAssembly = fitBristles(randStr(), headAssembly.getHead(), bristles, assembled, disassembled);
        final var updatedHeadAndBracketAssembly = fitBracket(randStr(), updatedHeadAssembly, headAndBracketAssembly.getBracket(), assembled, disassembled);
        final var updatedBroom = fitHandle(randStr(), oldBroom.getHandle(), updatedHeadAndBracketAssembly, assembled, unknownDisassembled);

        return new ReplaceBristlesActivity(randStr(), "Replace bristles", oldBroom, updatedBroom, oldBristles, oldHeadAssembly,
            oldHeadWithBracketAssembly,
            activityStart, activityEnd);
    }

    /**
     * A KindOfActivity to convert an Agglomerate of broom parts into a composition which is a broom.
     *
     * @param parts
     *            An Agglomerate
     * @return Broom Ideally this would be a BroomAssemblyActivityRecord
     */
    private Broom assembleBroom(final AgglomerateImpl parts) {
        //
        // Get the parts from the agglomerate, then build the composite Broom
        //
        final var broomHandle = (BroomHandle) parts.getParts().stream().filter(x -> x instanceof BroomHandle).findFirst().orElse(null);
        final var broomHead = (BroomHead) parts.getParts().stream().filter(x -> x instanceof BroomHead).findFirst().orElse(null);
        final var bristles = (Bristles) parts.getParts().stream().filter(x -> x instanceof Bristles).findFirst().orElse(null);
        final var broomBracket = (BroomBracket) parts.getParts().stream().filter(x -> x instanceof BroomBracket).findFirst().orElse(null);

        //
        // Fit the bristles in the head.
        //
        final var broomHeadAssembly = fitBristles(randStr(), broomHead, bristles, ASSEMBLY_START,
            ASSEMBLY_END);

        //
        // Attach the bracket to the head.
        //
        final var broomHeadWithBracketAssembly = fitBracket(randStr(), broomHeadAssembly,
            broomBracket, ASSEMBLY_START, ASSEMBLY_END);

        //
        // Attach the handle to the bracket to complete the broom.
        //
        return fitHandle(randStr(), broomHandle, broomHeadWithBracketAssembly, ASSEMBLY_START,
            ASSEMBLY_END);
    }

    /**
     * Ideally this would also set the beginning event, but this is a simple
     * example.
     *
     * @param id
     *            String
     * @param broomHandle
     *            BroomHandle
     * @param broomHeadWithBracketAssembly
     *            BroomHeadWithBracketAssembly
     * @return Broom
     */
    private Broom fitHandle(final String id, final BroomHandle broomHandle,
        final BroomHeadWithBracketAssembly broomHeadWithBracketAssembly, final Assembled beginning,
        final Disassembled ending) {
        return new Broom(id, broomHandle, broomHeadWithBracketAssembly, beginning, ending);
    }

    /**
     * Ideally this would set the beginning event, but this is a simple example.
     *
     * @param id
     *            String
     * @param broomHeadAssembly
     *            BroomHeadAssembly
     * @param broomBracket
     *            BroomBracket
     * @return BroomHeadWithBracketAssembly
     */
    private static BroomHeadWithBracketAssembly fitBracket(final String id, final BroomHeadAssembly broomHeadAssembly,
        final BroomBracket broomBracket, final Assembled beginning, final Disassembled ending) {
        return new BroomHeadWithBracketAssembly(id, broomHeadAssembly, broomBracket, beginning, ending);
    }

    /**
     * Ideally this would also set the beginning event, but I'm keeping this example
     * simple.
     *
     * @param id
     *            String
     * @param head
     *            BroomHead
     * @param bristles
     *            Bristles
     * @return BroomHeadAssembly
     */
    private static BroomHeadAssembly fitBristles(final String id, final BroomHead head, final Bristles bristles,
        final Assembled beginning, final Disassembled ending) {
        return new BroomHeadAssembly(id, head, bristles, beginning, ending);
    }

    private static int id;

    private static String randStr() {
        return Integer.toString(id++);
    }

}

@Data
@AllArgsConstructor
@NoArgsConstructor
class Broom implements Individual<Assembled, Disassembled> {
    String identifier;
    BroomHandle handle;
    BroomHeadWithBracketAssembly headWithBracketAssembly;
    Assembled beginning;
    Disassembled ending;
}

@Data
@AllArgsConstructor
@NoArgsConstructor
class BroomHeadAssembly implements Individual<Assembled, Disassembled> {
    String identifier;
    BroomHead head;
    Bristles bristles;
    Assembled beginning;
    Disassembled ending;
}

@Data
@AllArgsConstructor
@NoArgsConstructor
class BroomHeadWithBracketAssembly implements Individual<Assembled, Disassembled> {
    String identifier;
    BroomHeadAssembly headAssembly;
    BroomBracket bracket;
    Assembled beginning;
    Disassembled ending;
}

@Data
@AllArgsConstructor
@NoArgsConstructor
class BroomHandle implements Individual<Created, Destroyed> {
    String identifier;
    Created beginning;
    Destroyed ending;
}

@Data
@AllArgsConstructor
@NoArgsConstructor
class BroomHead implements Individual<Created, Destroyed> {
    String identifier;
    Created beginning;
    Destroyed ending;
}

@Data
@AllArgsConstructor
@NoArgsConstructor
class Bristles implements Individual<Created, Destroyed> {
    String identifier;
    Created beginning;
    Destroyed ending;
}

@Data
@AllArgsConstructor
@NoArgsConstructor
class BroomBracket implements Individual<Created, Destroyed> {
    String identifier;
    Created beginning;
    Destroyed ending;
}

@Data
@AllArgsConstructor
@NoArgsConstructor
class ReplaceBristlesActivity implements Activity<Started, Stopped> {
    String identifier;
    String actionsDescription;
    Broom oldBroom;
    Broom newBroom;
    Bristles oldBristles;
    BroomHeadAssembly oldHeadAssembly;
    BroomHeadWithBracketAssembly oldHeadWithBracketAssembly;
    Started beginning;
    Stopped ending;
}
