package uk.co.aosd.onto.foundation;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.time.Instant;
import java.util.Set;
import java.util.UUID;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.junit.jupiter.api.Test;
import uk.co.aosd.onto.biological.DNA;
import uk.co.aosd.onto.events.Dissolved;
import uk.co.aosd.onto.events.Formed;
import uk.co.aosd.onto.reference.LanguageImpl;
import uk.co.aosd.onto.reference.MembershipImpl;
import uk.co.aosd.onto.reference.OntologyServicesImpl;
import uk.co.aosd.onto.reference.OrganisationImpl;
import uk.co.aosd.onto.reference.events.AppointedImpl;
import uk.co.aosd.onto.reference.events.BirthImpl;
import uk.co.aosd.onto.reference.events.DeathImpl;
import uk.co.aosd.onto.reference.events.DissolvedImpl;
import uk.co.aosd.onto.reference.events.FormedImpl;
import uk.co.aosd.onto.reference.events.RemovedImpl;
import uk.co.aosd.onto.reference.events.ResignifiedImpl;
import uk.co.aosd.onto.signifying.Signifier;

/**
 * Model Donald Trump as POTUS.
 *
 * <P>
 * Model the USA as a Nation with a sub-organisation that is the US Government
 * with Donald Trump's two terms as POTUS.
 * </P>
 *
 * @author Tony Walmsley
 */
public class PotusExample {

    private static final OntologyServicesImpl svc = new OntologyServicesImpl();

    private static final Instant JAN_20TH_2025_END = Instant.parse("2025-01-20T23:59:59.99Z");
    private static final Instant JAN_20TH_2025_START = Instant.parse("2025-01-20T00:00:00.00Z");
    private static final Instant JAN_20TH_2021_END = Instant.parse("2021-01-20T23:59:59.99Z");
    private static final Instant JAN_20TH_2021_START = Instant.parse("2021-01-20T00:00:00.00Z");
    private static final Instant JAN_20TH_2017_END = Instant.parse("2017-01-20T23:59:59.99Z");
    private static final Instant JAN_20TH_2017_START = Instant.parse("2017-01-20T00:00:00.00Z");
    private static final Instant JUNE_14TH_1946_END = Instant.parse("1946-06-14T23:59:59.99Z");
    private static final Instant JUNE_14TH_1946_START = Instant.parse("1946-06-14T00:00:00.00Z");
    private static final Instant JUNE_4TH_1776_END = Instant.parse("1776-06-04T23:59:59.99Z");
    private static final Instant JUNE_4TH_1776_START = Instant.parse("1776-06-04T00:00:00.00Z");

    private static final DNA UNKNOWN_DNA = null;
    private static final AppointedImpl TRUMP_CITIZENSHIP_BEGINS = new AppointedImpl(randStr(), JUNE_14TH_1946_START, JUNE_14TH_1946_END);
    private static final AppointedImpl TRUMP_POTUS_START_1 = new AppointedImpl(randStr(), JAN_20TH_2017_START, JAN_20TH_2017_END);
    private static final AppointedImpl TRUMP_POTUS_START_2 = new AppointedImpl(randStr(), JAN_20TH_2025_START, JAN_20TH_2025_END);
    private static final BirthImpl TRUMP_BORN = new BirthImpl(randStr(), JUNE_14TH_1946_START, JUNE_14TH_1946_END);
    private static final DeathImpl TRUMP_DIED = new DeathImpl(randStr(), null, null);
    private static final DissolvedImpl USA_TO = new DissolvedImpl(randStr(), null, null);
    private static final FormedImpl USA_FROM = new FormedImpl(randStr(), JUNE_4TH_1776_START, JUNE_4TH_1776_END);
    private static final RemovedImpl TRUMP_CITIZENSHIP_ENDS = new RemovedImpl(randStr(), null, null);
    private static final RemovedImpl TRUMP_POTUS_END_1 = new RemovedImpl(randStr(), JAN_20TH_2021_START, JAN_20TH_2021_END);
    private static final RemovedImpl TRUMP_POTUS_END_2 = new RemovedImpl(randStr(), null, null);
    private static final ResignifiedImpl TRUMP_NAMED = new ResignifiedImpl(randStr(), JUNE_14TH_1946_START, JUNE_14TH_1946_END);
    private static final ResignifiedImpl TRUMP_RENAMED = new ResignifiedImpl(randStr(), null, null);
    private static final ResignifiedImpl USA_NAMED = new ResignifiedImpl(randStr(), JUNE_4TH_1776_START, JUNE_4TH_1776_END);
    private static final ResignifiedImpl USA_RENAMED = new ResignifiedImpl(randStr(), null, null);
    private static final String PURPOSE = "To occupy its territory and serve its people.";

    private static final CitizenOfTheUsa CITIZEN_USA = new CitizenOfTheUsa(randStr(), "Citizen of the United States of America");

    @Test
    public void test() throws JsonProcessingException {

        // Create Donald Trump
        final var usEnglish = svc.createLanguage(randStr(), "American English");
        final Class<LanguageImpl> languages = svc.createClass(randStr(), Set.of(usEnglish));
        final var personSignifier1 = svc.createSignifier(randStr(), "Donald Trump", usEnglish, TRUMP_NAMED, TRUMP_RENAMED);
        final Class<Signifier<String, ResignifiedImpl>> person1Names = svc.createClass(randStr(), Set.of(personSignifier1));

        final var donaldTrump = svc.createHuman(randStr(), TRUMP_BORN, TRUMP_DIED, person1Names, usEnglish, languages, UNKNOWN_DNA);

        // Create the names of the USA
        final Signifier<String, ResignifiedImpl> usaSignifier1 = svc.createSignifier(randStr(), "USA", usEnglish, USA_NAMED, USA_RENAMED);
        final Signifier<String, ResignifiedImpl> usaSignifier2 = svc.createSignifier(randStr(), "United States of America", usEnglish, USA_NAMED, USA_RENAMED);
        final Signifier<String, ResignifiedImpl> usaSignifier3 = svc.createSignifier(randStr(), "US", usEnglish, USA_NAMED, USA_RENAMED);
        final Signifier<String, ResignifiedImpl> usaSignifier4 = svc.createSignifier(randStr(), "United States", usEnglish, USA_NAMED, USA_RENAMED);
        final Signifier<String, ResignifiedImpl> usaSignifier5 = svc.createSignifier(randStr(), "America", usEnglish, USA_NAMED, USA_RENAMED);
        final Set<Signifier<String, ResignifiedImpl>> setOfSignifiers = Set.of(usaSignifier1, usaSignifier2, usaSignifier3, usaSignifier4, usaSignifier5);
        final var namesOfTheUsa = svc.createClass(randStr(), setOfSignifiers);

        // Create the name of the USA Government
        final var usaGovSignifier1 = svc.createSignifier(randStr(), "The Government of the United States of America", usEnglish, USA_NAMED, USA_RENAMED);
        final Class<Signifier<String, ResignifiedImpl>> namesOfTheUsaGov = svc.createClass(randStr(), Set.of(usaGovSignifier1));

        // Register Donald Trump's memberships of the US Government in the role of POTUS
        final var potus1 = svc.createMembership(randStr(), donaldTrump, CITIZEN_USA, TRUMP_POTUS_START_1, TRUMP_POTUS_END_1);
        final var potus2 = svc.createMembership(randStr(), donaldTrump, CITIZEN_USA, TRUMP_POTUS_START_2, TRUMP_POTUS_END_2);
        final var memberships = svc.createClass(randStr(), Set.of(potus1, potus2));

        // Register Donald Trump as a member of the USA in the role of citizen.
        final var citizen1 = svc.createMembership(randStr(), donaldTrump, CITIZEN_USA, TRUMP_CITIZENSHIP_BEGINS, TRUMP_CITIZENSHIP_ENDS);
        final var citizenships = svc.createClass(randStr(), Set.of(citizen1));

        // Create the US Government
        final Class<OrganisationImpl<CitizenOfTheUsa>> units = svc.createClass(randStr(), Set.of());
        final var usGovt = svc.createOrganisation(randStr(), memberships, "To govern the USA", units, namesOfTheUsaGov, USA_FROM,
            USA_TO);

        // Sub-organisations of the USA
        final Class<OrganisationImpl<CitizenOfTheUsa>> usaUnits = svc.createClass(randStr(), Set.of(usGovt));

        // Create the USA
        final var usaTerritory = new Territory(randStr(), USA_FROM, USA_TO);
        final var usa = new Nation(randStr(), usaTerritory, citizenships, PURPOSE, namesOfTheUsa, usaUnits, USA_FROM, USA_TO);

        assertNotNull(usa);

        final var json = JsonUtils.writeJsonString(usa);
        final var usa2 = JsonUtils.readJsonString(json, Nation.class);

        assertEquals(usa, usa2);
    }

    private static String randStr() {
        return UUID.randomUUID().toString();
    }

}

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
class Nation extends OrganisationImpl<CitizenOfTheUsa> {
    private String identifier;
    private Territory territory;
    private Class<MembershipImpl<CitizenOfTheUsa>> members;
    private String purpose;
    private Class<Signifier<String, ResignifiedImpl>> names;
    private Class<OrganisationImpl<CitizenOfTheUsa>> units;
    private FormedImpl beginning;
    private DissolvedImpl ending;
}

@Data
@AllArgsConstructor
@NoArgsConstructor
class Territory implements Individual<Formed, Dissolved> {
    private String identifier;
    private Formed beginning;
    private Dissolved ending;
}

@Data
@AllArgsConstructor
@NoArgsConstructor
class CitizenOfTheUsa implements Role {
    private String identifier;
    private String name;
}