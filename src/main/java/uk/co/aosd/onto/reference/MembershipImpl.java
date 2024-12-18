package uk.co.aosd.onto.reference;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import uk.co.aosd.onto.foundation.Role;
import uk.co.aosd.onto.organisation.Membership;
import uk.co.aosd.onto.reference.events.AppointedImpl;
import uk.co.aosd.onto.reference.events.BirthImpl;
import uk.co.aosd.onto.reference.events.DeathImpl;
import uk.co.aosd.onto.reference.events.RemovedImpl;
import uk.co.aosd.onto.reference.events.ResignifiedImpl;

/**
 * An implementation of the Membership interface.
 *
 * @author Tony Walmsley
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MembershipImpl<R extends Role> implements Membership<R, AppointedImpl, RemovedImpl, BirthImpl, DeathImpl, ResignifiedImpl, LanguageImpl> {
    private String identifier;
    private HumanImpl member;
    private R role;
    private AppointedImpl beginning;
    private RemovedImpl ending;
}