package uk.co.aosd.onto.topics;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.junit.jupiter.api.Test;
import uk.co.aosd.onto.biological.DNA;
import uk.co.aosd.onto.events.Created;
import uk.co.aosd.onto.events.Deleted;
import uk.co.aosd.onto.events.Started;
import uk.co.aosd.onto.events.Stopped;
import uk.co.aosd.onto.foundation.Activity;
import uk.co.aosd.onto.foundation.Class;
import uk.co.aosd.onto.foundation.Event;
import uk.co.aosd.onto.foundation.Individual;
import uk.co.aosd.onto.foundation.JsonUtils;
import uk.co.aosd.onto.foundation.Role;
import uk.co.aosd.onto.foundation.UniquelyIdentifiable;
import uk.co.aosd.onto.reference.ClassImpl;
import uk.co.aosd.onto.reference.DNAImpl;
import uk.co.aosd.onto.reference.HumanImpl;
import uk.co.aosd.onto.reference.LanguageImpl;
import uk.co.aosd.onto.reference.MembershipImpl;
import uk.co.aosd.onto.reference.SignifierImpl;
import uk.co.aosd.onto.reference.events.AppointedImpl;
import uk.co.aosd.onto.reference.events.BirthImpl;
import uk.co.aosd.onto.reference.events.CreatedImpl;
import uk.co.aosd.onto.reference.events.DeathImpl;
import uk.co.aosd.onto.reference.events.DeletedImpl;
import uk.co.aosd.onto.reference.events.RemovedImpl;
import uk.co.aosd.onto.reference.events.ResignifiedImpl;
import uk.co.aosd.onto.reference.events.StartedImpl;
import uk.co.aosd.onto.reference.events.StoppedImpl;
import uk.co.aosd.onto.signifying.Named;
import uk.co.aosd.onto.signifying.Signifier;

/**
 * Modelling research topics example.
 *
 * @author Tony Walmsley
 */
public class TopicsTest {

    @Test
    public void test() throws JsonProcessingException {
        // Create an object to represent the User
        final var english = new LanguageImpl(randString(), "English");
        final var birth = new BirthImpl(randString(), Instant.ofEpochSecond(0), Instant.ofEpochSecond(0));
        final var death = new DeathImpl(randString(), null, null);
        final Class<LanguageImpl> languages = new ClassImpl<>(randString(), Set.of(english));
        final var dna = new DNAImpl(randString(), "gattaca");
        final var userNamedFrom = new ResignifiedImpl(randString(), null, null);
        final var userRenamedTo = new ResignifiedImpl(randString(), null, null);
        final Signifier<String, ResignifiedImpl> username = new SignifierImpl<String>(randString(), "user1", english, userNamedFrom, userRenamedTo);
        final Class<Signifier<String, ResignifiedImpl>> usernames = new ClassImpl<>(randString(), Set.of(username));
        final var user1 = new User(randString(), birth, death, usernames, english, languages, dna);
        //
        // Create a Topic. A topic is an activity for researching some subject area that
        // can have sub-topics, can be named and renamed, has an expert, an owner, and a
        // set of contibutors. It can refer to source information which in turn can
        // refer to individuals, and individuals can refer to each other.
        //
        final StartedImpl from = new StartedImpl(randString(), Instant.ofEpochSecond(0), Instant.ofEpochSecond(0));
        final StoppedImpl to = new StoppedImpl(randString(), null, null);
        final Class<MembershipImpl<OwnerRole>> owners = new ClassImpl<>(randString(), Set.of());
        final Class<MembershipImpl<ExpertRole>> experts = new ClassImpl<>(randString(), Set.of());
        final ResignifiedImpl topicNamed = new ResignifiedImpl(randString(), Instant.ofEpochSecond(0), Instant.ofEpochSecond(0));
        final ResignifiedImpl topicRenamed = new ResignifiedImpl(randString(), null, null);
        final Signifier<String, ResignifiedImpl> name = new SignifierImpl<String>(randString(), "Ontologies Topic", english, topicNamed, topicRenamed);
        final Class<Signifier<String, ResignifiedImpl>> names = new ClassImpl<>(randString(), Set.of(name));
        final ResignifiedImpl topicDescribed = new ResignifiedImpl(randString(), null, null);
        final ResignifiedImpl topicDescriptionUpdated = new ResignifiedImpl(randString(), null, null);
        final Signifier<String, ResignifiedImpl> description = new SignifierImpl<String>(randString(), "Research into Ontologies", english, topicDescribed,
            topicDescriptionUpdated);
        final Class<Signifier<String, ResignifiedImpl>> descriptions = new ClassImpl<>(randString(), Set.of(description));
        final Class<Topic> subTopics = new ClassImpl<>(randString(), Set.of());
        final Class<MembershipImpl<ContributorRole>> contributors = new ClassImpl<>(randString(), Set.of());
        final CreatedImpl sourceCreated = new CreatedImpl(randString(), Instant.ofEpochSecond(0), Instant.ofEpochSecond(1));
        final DeletedImpl sourceDeleted = new DeletedImpl(randString(), null, null);
        final Source source = new Source(randString(), "http://www.google.com", sourceCreated, sourceDeleted, user1, user1);
        final Class<Source> sources = new ClassImpl<>(randString(), Set.of(source));
        final Individual<Started, Stopped> individual = new Thing(randString(), new StartedImpl(randString(), null, null),
            new StoppedImpl(randString(), null, null));
        final Class<Individual<? extends Event, ? extends Event>> individuals = new ClassImpl<>(randString(), Set.of(individual));
        final SourceReference sourceReference = new SourceReference(randString(), source, SourceReferenceType.DOCUMENT, individual, user1, user1);
        final Class<SourceReference> sourceReferences = new ClassImpl<>(randString(), Set.of(sourceReference));
        final IndividualReference individualReference = new IndividualReference(randString(), IndividualReferenceType.WORKS_WITH, "References", source, source,
            from, to, user1, user1);
        final Class<IndividualReference> individualReferences = new ClassImpl<>(randString(), Set.of(individualReference));

        final var topic1 = new Topic(
            randString(),
            "Researching Ontologies",
            names,
            descriptions,
            subTopics,
            owners,
            experts,
            contributors,
            sources,
            individuals,
            sourceReferences,
            individualReferences,
            from,
            to,
            user1,
            user1);

        assertNotNull(topic1);

        // Create a Human as the owner, expert, and contributor
        final ResignifiedImpl namedFrom = new ResignifiedImpl(randString(), Instant.ofEpochSecond(0), Instant.ofEpochSecond(0));
        final ResignifiedImpl namedTo = new ResignifiedImpl(randString(), null, null);
        final Signifier<String, ResignifiedImpl> personName = new SignifierImpl<String>(randString(), "Alice", english, namedFrom, namedTo);
        final Class<Signifier<String, ResignifiedImpl>> personNames = new ClassImpl<>(randString(), Set.of(personName));

        final var newOwner = new HumanImpl(randString(), birth, death, personNames, english, languages, dna);
        final var newExpert = newOwner;
        final var contributor = newOwner;

        // Create the owner temporal extent.
        final AppointedImpl ownerFrom = new AppointedImpl(randString(), Instant.ofEpochSecond(1000), Instant.ofEpochSecond(1000));
        final RemovedImpl ownerTo = new RemovedImpl(randString(), null, null);

        // Create the expert temporal extent.
        final AppointedImpl expertFrom = new AppointedImpl(randString(), Instant.ofEpochSecond(1000), Instant.ofEpochSecond(1000));
        final RemovedImpl expertTo = new RemovedImpl(randString(), null, null);

        // Create the contributor temporal extent.
        final AppointedImpl contributorFrom = new AppointedImpl(randString(), Instant.ofEpochSecond(1000), Instant.ofEpochSecond(1000));
        final RemovedImpl contributorTo = new RemovedImpl(randString(), null, null);

        // Create the necessary Memberships for each role.
        final MembershipImpl<OwnerRole> ownerMembership = new MembershipImpl<>(randString(), newOwner, ownerRole, ownerFrom, ownerTo);
        final MembershipImpl<ExpertRole> expertMembership = new MembershipImpl<>(randString(), newExpert, expertRole, expertFrom, expertTo);
        final MembershipImpl<ContributorRole> contributorMembership = new MembershipImpl<>(randString(), contributor, contributorRole,
            contributorFrom, contributorTo);

        // Update the topic.
        final Topic updatedWithOwner = addOwner(topic1, ownerMembership, user1);
        final Topic updatedWithExpert = addExpert(updatedWithOwner, expertMembership, user1);
        final Topic updatedWithContributor = addContributor(updatedWithExpert, contributorMembership, user1);

        // Check the result.
        assertTrue(updatedWithContributor.getOwners().getMembers().contains(ownerMembership));
        assertTrue(updatedWithContributor.getExperts().getMembers().contains(expertMembership));
        assertTrue(updatedWithContributor.getContributors().getMembers().contains(contributorMembership));

        final var json = JsonUtils.writeJsonString(updatedWithContributor);
        final var deserialised = JsonUtils.readJsonString(json, Topic.class);
        assertEquals(deserialised, updatedWithContributor);
    }

    /**
     * Add a contributor.
     *
     * @param t
     *            Topic
     * @param contributor
     *            Human
     * @param beginning
     *            Appointed
     * @param ending
     *            Removed
     * @return Topic
     */
    private static Topic addContributor(final Topic t, final MembershipImpl<ContributorRole> contributor, final User updater) {

        final Set<MembershipImpl<ContributorRole>> contributorSet = new HashSet<>();
        contributorSet.addAll(t.getContributors().getMembers());
        contributorSet.add(contributor);
        final Class<MembershipImpl<ContributorRole>> contributors = new ClassImpl<>(t.getContributors().getIdentifier(), contributorSet);

        return new Topic(t.getIdentifier(), t.getActionsDescription(), t.getNames(), t.getDescriptions(), t.getSubTopics(), t.getOwners(), t.getExperts(),
            contributors, t.getSources(), t.getIndividuals(), t.getSourceReferences(), t.getIndividualReferences(), t.getBeginning(), t.getEnding(),
            t.getCreatedBy(), updater);
    }

    /**
     * Set the Expert.
     *
     * @param t
     *            Topic
     * @param expert
     *            Human
     * @return Topic
     */
    private Topic addExpert(final Topic t, final MembershipImpl<ExpertRole> expert, final User updater) {
        final Set<MembershipImpl<ExpertRole>> expertsSet = new HashSet<>();
        expertsSet.addAll(t.getExperts().getMembers());
        expertsSet.add(expert);

        final Class<MembershipImpl<ExpertRole>> experts = new ClassImpl<>(randString(), expertsSet);

        return new Topic(t.getIdentifier(), t.getActionsDescription(), t.getNames(), t.getDescriptions(), t.getSubTopics(), t.getOwners(), experts,
            t.getContributors(), t.getSources(), t.getIndividuals(), t.getSourceReferences(), t.getIndividualReferences(), t.getBeginning(), t.getEnding(),
            t.getCreatedBy(), updater);
    }

    /**
     * Set the owner.
     *
     * @param t
     *            Topic
     * @param owner
     *            Human
     * @return Topic
     */
    private static Topic addOwner(final Topic t, final MembershipImpl<OwnerRole> owner, final User updater) {
        final Set<MembershipImpl<OwnerRole>> ownersSet = new HashSet<>();
        ownersSet.addAll(t.getOwners().getMembers());
        ownersSet.add(owner);

        final Class<MembershipImpl<OwnerRole>> owners = new ClassImpl<>(randString(), ownersSet);

        return new Topic(t.getIdentifier(), t.getActionsDescription(), t.getNames(), t.getDescriptions(), t.getSubTopics(), owners, t.getExperts(),
            t.getContributors(), t.getSources(), t.getIndividuals(), t.getSourceReferences(), t.getIndividualReferences(), t.getBeginning(), t.getEnding(),
            t.getCreatedBy(), updater);
    }

    private static String randString() {
        return UUID.randomUUID().toString();
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    private static class Topic implements Activity<Started, Stopped>, Named<ResignifiedImpl> {
        private String identifier;
        private String actionsDescription;
        private Class<Signifier<String, ResignifiedImpl>> names;
        private Class<Signifier<String, ResignifiedImpl>> descriptions;
        private Class<Topic> subTopics;
        private Class<MembershipImpl<OwnerRole>> owners;
        private Class<MembershipImpl<ExpertRole>> experts;
        private Class<MembershipImpl<ContributorRole>> contributors;
        private Class<Source> sources;
        private Class<Individual<? extends Event, ? extends Event>> individuals;
        private Class<SourceReference> sourceReferences;
        private Class<IndividualReference> individualReferences;
        private StartedImpl beginning;
        private StoppedImpl ending;
        private User createdBy;
        private User updatedBy;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    private static class Source implements Individual<Created, Deleted> {
        private String identifier;
        private String reference;
        private Created beginning;
        private Deleted ending;
        private User createdBy;
        private User updatedBy;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    private static class SourceReference implements UniquelyIdentifiable {
        private String identifier;
        private Source source;
        private SourceReferenceType type;
        private Individual<? extends Event, ? extends Event> individual;
        private User createdBy;
        private User updatedBy;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    private static class IndividualReference implements Activity<Started, Stopped> {
        private String identifier;
        private IndividualReferenceType type;
        private String actionsDescription;
        private Individual<? extends Event, ? extends Event> from;
        private Individual<? extends Event, ? extends Event> to;
        private Started beginning;
        private Stopped ending;
        private User createdBy;
        private User updatedBy;
    }

    private static enum SourceReferenceType {
        URI, DOCUMENT, VIDEO, AUDIO, TEXT
    }

    private static enum IndividualReferenceType {
        WORKS_WITH, EMPLOYS, MANAGES
    }

    private static final ExpertRole expertRole = new ExpertRole(randString(), "Expert");
    private static final OwnerRole ownerRole = new OwnerRole(randString(), "Owner");
    private static final ContributorRole contributorRole = new ContributorRole(randString(), "Contributor");

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    private static class ExpertRole implements Role {
        private String identifier;
        private String name;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    private static class OwnerRole implements Role {
        private String identifier;
        private String name;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    private static class ContributorRole implements Role {
        private String identifier;
        private String name;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    private static final class Thing implements Individual<Started, Stopped> {
        private String identifier;
        private Started beginning;
        private Stopped ending;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @EqualsAndHashCode(callSuper = true)
    private static final class User extends HumanImpl {
        private String identifier;
        private BirthImpl beginning;
        private DeathImpl ending;
        private Class<Signifier<String, ResignifiedImpl>> names;
        private LanguageImpl nativeLanguage;
        private Class<LanguageImpl> languages;
        private DNA dna;
    }
}
