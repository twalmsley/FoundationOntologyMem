package uk.co.aosd.onto.reference;

import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import uk.co.aosd.onto.foundation.Agglomerate;
import uk.co.aosd.onto.foundation.Event;
import uk.co.aosd.onto.foundation.Individual;
import uk.co.aosd.onto.reference.events.AggregatedImpl;
import uk.co.aosd.onto.reference.events.DisaggregatedImpl;

/**
 * An implementation of the Agglomerate interface.
 *
 * @author Tony Walmsley
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AgglomerateImpl implements Agglomerate<AggregatedImpl, DisaggregatedImpl> {
    private String identifier;
    private Set<Individual<? extends Event, ? extends Event>> parts;
    private AggregatedImpl beginning;
    private DisaggregatedImpl ending;
}
