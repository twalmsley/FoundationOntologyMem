package uk.co.aosd.onto.reference;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import uk.co.aosd.onto.foundation.Class;
import uk.co.aosd.onto.foundation.Role;
import uk.co.aosd.onto.organisation.Organisation;
import uk.co.aosd.onto.reference.events.DissolvedImpl;
import uk.co.aosd.onto.reference.events.FormedImpl;
import uk.co.aosd.onto.reference.events.ResignifiedImpl;
import uk.co.aosd.onto.signifying.Signifier;

/**
 * An implementation of the Organisation interface.
 *
 * @author Tony Walmsley
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrganisationImpl<R extends Role> implements Organisation<FormedImpl, DissolvedImpl, ResignifiedImpl> {
    private String identifier;
    private Class<MembershipImpl<R>> members;
    private String purpose;
    private Class<OrganisationImpl<R>> units;
    private Class<Signifier<String, ResignifiedImpl>> names;
    private FormedImpl beginning;
    private DissolvedImpl ending;
}