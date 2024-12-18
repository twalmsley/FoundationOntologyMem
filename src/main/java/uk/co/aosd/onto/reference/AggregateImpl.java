package uk.co.aosd.onto.reference;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import uk.co.aosd.onto.foundation.Aggregate;
import uk.co.aosd.onto.foundation.ScalarValue;
import uk.co.aosd.onto.foundation.Unit;
import uk.co.aosd.onto.reference.events.AggregatedImpl;
import uk.co.aosd.onto.reference.events.DisaggregatedImpl;

/**
 * An implementation of the Aggregation interface.
 *
 * @author Tony Walmsley
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AggregateImpl<N extends Number, U extends Unit, T> implements Aggregate<N, U, T, AggregatedImpl, DisaggregatedImpl> {
    private String identifier;
    private Class<T> kind;
    private ScalarValue<N, U> quantity;
    private AggregatedImpl beginning;
    private DisaggregatedImpl ending;
}
