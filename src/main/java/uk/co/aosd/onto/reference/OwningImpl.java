package uk.co.aosd.onto.reference;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import uk.co.aosd.onto.foundation.Event;
import uk.co.aosd.onto.foundation.Individual;
import uk.co.aosd.onto.ownership.Owning;
import uk.co.aosd.onto.reference.events.TransferredFromImpl;
import uk.co.aosd.onto.reference.events.TransferredToImpl;

/**
 * An implementation of the Owning interface.
 *
 * @author Tony Walmsley
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OwningImpl<A extends Event, B extends Event, C extends Event, D extends Event>
    implements Owning<A, B, C, D, TransferredFromImpl, TransferredToImpl> {
    private String identifier;
    private String actionsDescription;
    private Individual<A, B> owner;
    private Individual<C, D> owned;
    private TransferredFromImpl beginning;
    private TransferredToImpl ending;
}
