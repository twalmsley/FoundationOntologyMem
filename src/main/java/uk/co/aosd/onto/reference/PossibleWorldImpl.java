package uk.co.aosd.onto.reference;

import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import uk.co.aosd.onto.foundation.Event;
import uk.co.aosd.onto.foundation.Individual;
import uk.co.aosd.onto.foundation.PossibleWorld;
import uk.co.aosd.onto.reference.events.CreatedImpl;
import uk.co.aosd.onto.reference.events.DeletedImpl;

/**
 * An implementaton of the PossibleWorld interface.
 *
 * @author Tony Walmsley
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PossibleWorldImpl implements PossibleWorld<CreatedImpl, DeletedImpl> {
    private String identifier;
    private Set<Individual<? extends Event, ? extends Event>> parts;
    private CreatedImpl beginning;
    private DeletedImpl ending;
}
