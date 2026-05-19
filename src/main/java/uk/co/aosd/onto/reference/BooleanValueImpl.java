package uk.co.aosd.onto.reference;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import uk.co.aosd.onto.foundation.BooleanValue;
import uk.co.aosd.onto.foundation.Unit;

/**
 * An implementaion of the BooleanValue interface.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BooleanValueImpl<U extends Unit> implements BooleanValue<U> {
    private Boolean value;
    private U unit;
}
