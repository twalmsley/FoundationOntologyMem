package uk.co.aosd.onto.reference;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import uk.co.aosd.onto.biological.DNA;
import uk.co.aosd.onto.biological.Human;
import uk.co.aosd.onto.foundation.Class;
import uk.co.aosd.onto.reference.events.BirthImpl;
import uk.co.aosd.onto.reference.events.DeathImpl;
import uk.co.aosd.onto.reference.events.ResignifiedImpl;
import uk.co.aosd.onto.signifying.Signifier;

/**
 * An example implementation of the Human interface. This may need to change.
 *
 * @author Tony Walmsley
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class HumanImpl implements Human<BirthImpl, DeathImpl, ResignifiedImpl, LanguageImpl> {
    private String identifier;
    private BirthImpl beginning;
    private DeathImpl ending;
    private Class<Signifier<String, ResignifiedImpl>> names;
    private LanguageImpl nativeLanguage;
    private Class<LanguageImpl> languages;
    private DNA dna;
}
