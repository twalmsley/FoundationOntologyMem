package uk.co.aosd.onto.reference;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import uk.co.aosd.onto.language.Language;
import uk.co.aosd.onto.reference.events.ResignifiedImpl;
import uk.co.aosd.onto.signifying.Signifier;

/**
 * An implementation of Signifier.
 *
 * @author Tony Walmsley
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SignifierImpl<T> implements Signifier<T, ResignifiedImpl> {
    private String identifier;
    private T name;
    private Language language;
    private ResignifiedImpl beginning;
    private ResignifiedImpl ending;
}
