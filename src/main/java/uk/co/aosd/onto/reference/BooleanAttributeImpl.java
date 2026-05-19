package uk.co.aosd.onto.reference;

import java.time.Instant;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import uk.co.aosd.onto.foundation.*;

/**
 * An implementation of the BooleanAttribute interface.
 *
 * @author Tony Walmsley
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BooleanAttributeImpl<I extends Individual<? extends Event, ? extends Event>, U extends Unit> implements BooleanAttribute<I, U> {
    private String identifier;
    private I individual;
    private BooleanValue<U> property;
    private Instant from;
    private Instant to;
}
