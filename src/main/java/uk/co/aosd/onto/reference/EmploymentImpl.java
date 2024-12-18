package uk.co.aosd.onto.reference;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import uk.co.aosd.onto.foundation.Role;
import uk.co.aosd.onto.organisation.Employment;
import uk.co.aosd.onto.reference.events.AppointedImpl;
import uk.co.aosd.onto.reference.events.BirthImpl;
import uk.co.aosd.onto.reference.events.DeathImpl;
import uk.co.aosd.onto.reference.events.DissolvedImpl;
import uk.co.aosd.onto.reference.events.FormedImpl;
import uk.co.aosd.onto.reference.events.RemovedImpl;
import uk.co.aosd.onto.reference.events.ResignifiedImpl;

/**
 * An implementation of the Employment interface.
 *
 * @author Tony Walmsley
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmploymentImpl<C>
    implements Employment<C, AppointedImpl, RemovedImpl, FormedImpl, DissolvedImpl, ResignifiedImpl, BirthImpl, DeathImpl, LanguageImpl> {
    private String identifier;
    private OrganisationImpl<? extends Role> employer;
    private HumanImpl employee;
    private String actionsDescription;
    private C contract;
    private AppointedImpl beginning;
    private RemovedImpl ending;
}
