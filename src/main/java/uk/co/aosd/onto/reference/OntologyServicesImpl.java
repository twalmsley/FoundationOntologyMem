package uk.co.aosd.onto.reference;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import org.decimal4j.immutable.Decimal3f;
import uk.co.aosd.onto.biological.DNA;
import uk.co.aosd.onto.events.Started;
import uk.co.aosd.onto.events.Stopped;
import uk.co.aosd.onto.foundation.Class;
import uk.co.aosd.onto.foundation.Event;
import uk.co.aosd.onto.foundation.Individual;
import uk.co.aosd.onto.foundation.Role;
import uk.co.aosd.onto.foundation.ScalarValue;
import uk.co.aosd.onto.foundation.State;
import uk.co.aosd.onto.foundation.UniquelyIdentifiable;
import uk.co.aosd.onto.foundation.Unit;
import uk.co.aosd.onto.model.Model;
import uk.co.aosd.onto.money.Currency;
import uk.co.aosd.onto.money.MonetaryValue;
import uk.co.aosd.onto.reference.events.AggregatedImpl;
import uk.co.aosd.onto.reference.events.AppointedImpl;
import uk.co.aosd.onto.reference.events.BirthImpl;
import uk.co.aosd.onto.reference.events.CreatedImpl;
import uk.co.aosd.onto.reference.events.DeathImpl;
import uk.co.aosd.onto.reference.events.DeletedImpl;
import uk.co.aosd.onto.reference.events.DisaggregatedImpl;
import uk.co.aosd.onto.reference.events.DissolvedImpl;
import uk.co.aosd.onto.reference.events.FormedImpl;
import uk.co.aosd.onto.reference.events.RemovedImpl;
import uk.co.aosd.onto.reference.events.ResignifiedImpl;
import uk.co.aosd.onto.reference.events.TransferredFromImpl;
import uk.co.aosd.onto.reference.events.TransferredToImpl;
import uk.co.aosd.onto.signifying.Signifier;

/**
 * A reference implementation of the OntologyServices interface.
 *
 * <p>
 * This allows users of the library to code to the ontology interfaces without
 * knowing about the implementation classes directly.
 * </p>
 *
 * @author Tony Walmsley
 */
public class OntologyServicesImpl {

    public LanguageImpl createLanguage(final String identifier, final String name) {
        return new LanguageImpl(identifier, name);
    }

    public <T> SignifierImpl<T> createSignifier(final String identifier, final T value, final LanguageImpl language, final ResignifiedImpl from,
        final ResignifiedImpl to) {
        return new SignifierImpl<T>(identifier, value, language, from, to);
    }

    public <T extends UniquelyIdentifiable> ClassImpl<T> createClass(final String identifier, final Set<T> members) {
        return new ClassImpl<T>(identifier, members);
    }

    public HumanImpl createHuman(final String identifier, final BirthImpl born, final DeathImpl died, final Class<Signifier<String, ResignifiedImpl>> names,
        final LanguageImpl nativeLanguage, final Class<LanguageImpl> languages, final DNA dna) {
        return new HumanImpl(identifier, born, died, names, nativeLanguage, languages, dna);
    }

    public <R extends Role> MembershipImpl<R> createMembership(final String identifier, final HumanImpl human, final R role, final AppointedImpl from,
        final RemovedImpl to) {
        return new MembershipImpl<>(identifier, human, role, from, to);
    }

    public <R extends Role> OrganisationImpl<R> createOrganisation(final String identifier, final Class<MembershipImpl<R>> memberships, final String purpose,
        final Class<OrganisationImpl<R>> units, final Class<Signifier<String, ResignifiedImpl>> names, final FormedImpl from, final DissolvedImpl to) {
        return new OrganisationImpl<>(identifier, memberships, purpose, units, names, from, to);
    }

    public PossibleWorldImpl createPossibleWorld(final String identifier, final Set<Individual<? extends Event, ? extends Event>> parts, final CreatedImpl from,
        final DeletedImpl to) {
        return new PossibleWorldImpl(identifier, parts, from, to);
    }

    public <B extends Event, E extends Event, T extends Individual<B, E>> State<B, E, T> createState(final String identifier, final T individual,
        final B from, final E to) {
        return new StateImpl<B, E, T>(identifier, individual, from, to);
    }

    public DNA createDna(final String identifier, final String dna) {
        return new DNAImpl(identifier, dna);
    }

    public <A extends Event, B extends Event, C extends Event, D extends Event> OwningImpl<A, B, C, D> createOwnership(final String identifier,
        final String actionsDescription, final Individual<A, B> owner, final Individual<C, D> owned, final TransferredFromImpl from,
        final TransferredToImpl to) {
        return new OwningImpl<>(identifier, actionsDescription, owner, owned, from, to);
    }

    /**
     * Create a transfer of ownership event.
     */
    public <A extends Event, B extends Event, C extends Event, D extends Event> TransferringOfOwnershipImpl<A, B, C, D> transferOwnership(
        final String identifier, final String actionsDescription, final OwningImpl<A, B, C, D> current, final Individual<A, B> newOwner, final Started from,
        final Stopped to) {
        // The previous owneship ends at the from event.
        final var transferredFromEvent = new TransferredFromImpl(randId(), from.getFrom(), from.getTo());
        final var transferredToEvent = new TransferredToImpl(randId(), to.getFrom(), to.getTo());
        final var endOwnership = createOwnership(current.getIdentifier(), current.getActionsDescription(), current.getOwner(), current.getOwned(),
            current.getBeginning(), transferredToEvent);

        // The new ownership starts at the from event.
        final var ownershipEnds = new TransferredToImpl(randId(), null, null);
        final var newOwnership = createOwnership(identifier, actionsDescription, newOwner, current.getOwned(), transferredFromEvent, ownershipEnds);

        // The transfer happens at the from event and finishes at the from event.
        return new TransferringOfOwnershipImpl<>(identifier, actionsDescription, endOwnership, newOwnership, transferredFromEvent, transferredToEvent);
    }

    public Currency createCurrency(final String identifier, final String code, final String name, final char symbol) {
        return new CurrencyImpl(identifier, code, name, symbol);
    }

    public <U extends Currency> MonetaryValue<U> createMonetaryValue(final Decimal3f value, final U unit) {
        return new MonetaryValueImpl<U>(value, unit);
    }

    public Model createModel(final String identifier) {
        return new ModelImpl(identifier, new HashSet<>());
    }

    public AgglomerateImpl createAgglomerate(final String identifier, final Set<Individual<? extends Event, ? extends Event>> items, final AggregatedImpl from,
        final DisaggregatedImpl to) {
        return new AgglomerateImpl(identifier, items, from, to);
    }

    public <N extends Number, U extends Unit, T> AggregateImpl<N, U, T> createAggregate(final String identifier, final java.lang.Class<T> kind,
        final ScalarValue<N, U> quantity, final AggregatedImpl from, final DisaggregatedImpl to) {
        return new AggregateImpl<>(identifier, kind, quantity, from, to);
    }

    public <N extends Number, U extends Unit> ScalarValue<N, U> createScalarValue(final N value, final U unit) {
        return new ScalarValueImpl<N, U>(value, unit);
    }

    private static String randId() {
        return UUID.randomUUID().toString();
    }
}
