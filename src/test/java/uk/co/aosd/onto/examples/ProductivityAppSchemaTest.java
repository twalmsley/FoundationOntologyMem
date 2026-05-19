package uk.co.aosd.onto.examples;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.awt.Color;
import java.time.Instant;
import java.util.Optional;
import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.junit.jupiter.api.Test;
import uk.co.aosd.onto.foundation.*;
import uk.co.aosd.onto.foundation.Class;
import uk.co.aosd.onto.reference.*;
import uk.co.aosd.onto.reference.events.*;
import uk.co.aosd.onto.signifying.Signifier;
import uk.co.aosd.onto.units.Units;
import uk.co.aosd.onto.units.Units.Minutes;
import uk.co.aosd.onto.units.Units.Natural;

/**
 * An example of a traditional RDB schema converted to a FoundationOntoloty
 * schema.
 */
public class ProductivityAppSchemaTest {

    @Test
    public void testCreateUser() {
        final Instant userCreatedDate = Instant.parse("2026-05-19T07:00:00.00Z");
        final ResignifiedImpl userNamed = new ResignifiedImpl("18d80104-d51b-456a-afc9-0a88957d270c", userCreatedDate, userCreatedDate);
        final ResignifiedImpl userRenamed = new ResignifiedImpl("bfe57f9f-ae82-46b6-8fe3-56a626cde281", null, null);
        final ResignifiedImpl emailCreated = new ResignifiedImpl("e1a51459-80d1-4482-a9e2-0a737d0beda0", null, null);
        final ResignifiedImpl emailChanged = new ResignifiedImpl("50803b80-38df-4b5d-bcea-d39b850dfc96", null, null);

        final LanguageImpl englishLanguage = new LanguageImpl("016c47f1-f394-40d2-8be1-26aa44667fca", "English");
        final Class<LanguageImpl> userLanguages = new ClassImpl<>("88db6cc5-5ef7-4af4-b72d-629c21e92108", Set.of(englishLanguage));

        final CreatedImpl userCreated = new CreatedImpl("c92b4597-3c63-4c31-a615-5dcf26ae4976", userCreatedDate, userCreatedDate);
        final DestroyedImpl userDesroyed = new DestroyedImpl("cf299d3b-4587-4b15-9e09-91969bb80db9", null, null);
        final Signifier<String, ResignifiedImpl> username = new SignifierImpl<>("9bb91d39-5dab-44c1-8def-27b8605b4a27", "user1", englishLanguage, userNamed,
            userRenamed);
        final CreatedImpl emailAddressCreated = new CreatedImpl("75a84adf-f76d-48c4-95a7-f4cfd37d33d3", null, null);
        final DestroyedImpl emailAddressDestroyed = new DestroyedImpl("586cc577-b17f-4de5-9244-2b6e54d4b0ba", null, null);
        final EmailAddress mailAddress = new EmailAddress("user1@example.com", emailAddressCreated, emailAddressDestroyed);
        final Signifier<EmailAddress, ResignifiedImpl> emailAddressSignifier = new SignifierImpl<>("bf3eaedc-aa4c-4a12-84e8-4a24e62ef232", mailAddress,
            englishLanguage, emailCreated, emailChanged);
        final Class<Signifier<String, ResignifiedImpl>> usernames = new ClassImpl<>("28864174-40c8-4c46-83d6-6444b0b3a20f", Set.of(username));
        final Class<Signifier<EmailAddress, ResignifiedImpl>> userEmailAddresses = new ClassImpl<>("67c8ca75-f724-4d02-b35a-3efc2ae05060",
            Set.of(emailAddressSignifier));
        final BooleanValueImpl<ProStatus> userProStatus = new BooleanValueImpl<>(Boolean.TRUE, ProStatus.PRO_STATUS);
        final ScalarValueImpl<Integer, Minutes> focusDuration = new ScalarValueImpl<>(25, Units.MINUTES);
        final ScalarValueImpl<Integer, Minutes> shortBreakDuration = new ScalarValueImpl<>(5, Units.MINUTES);
        final ScalarValueImpl<Integer, Minutes> longBreakDuration = new ScalarValueImpl<>(15, Units.MINUTES);
        final ScalarValueImpl<Integer, Natural> longBreakInterval = new ScalarValueImpl<>(4, Units.NATURAL);
        final Theme theme = Theme.LIGHT;
        final Class<Task> tasks = new ClassImpl<>("1710fa1d-b329-43d4-9a05-e3dcb6233c91", Set.of());
        final Class<Project> projects = new ClassImpl<>("d8658d70-3d34-4348-974d-d4dd9a69ea07", Set.of());

        final StartedImpl startOfSubscription = new StartedImpl("3b07b21e-0823-48f7-8f56-6ecbd1554137", userCreatedDate, userCreatedDate);
        final StoppedImpl endOfSubscription = new StoppedImpl("017047f4-b188-464d-a126-51bd89f744bf", null, null);
        final SubscriptionLevel subscriptionLevel = SubscriptionLevel.PREMIUM;
        final SubscriptionStatus subscriptionStatus = SubscriptionStatus.FREE_TRIAL;
        final SubscriptionType subscriptionType = SubscriptionType.YEARLY;
        final String subscriptionPlan = "FREE_TRIAL";
        final Instant freeTrialExpiresAt = Instant.parse("2026-05-31T07:00:00.00Z");
        final String stripeCustomerId = "3b0c4ec3-a0bb-4569-95df-65ef7d8545ec";
        final UserSubscription userSubscription = new UserSubscription("2ef52355-9ffe-4d78-a0f7-ae3f077b622b", startOfSubscription, endOfSubscription,
            subscriptionStatus, subscriptionType, subscriptionLevel, subscriptionPlan, freeTrialExpiresAt, stripeCustomerId);
        final Class<UserSubscription> subscriptions = new ClassImpl<>("1ab98632-ed54-4d02-9999-53017f190caf", Set.of(userSubscription));
        final Class<UserSession> sessions = new ClassImpl<>("26bcb866-dd60-47fa-9f5f-e975b102a493", Set.of());
        final Class<JournalEntry> journalEntries = new ClassImpl<>("a26cd567-0530-464e-961e-c45951ba43d1", Set.of());
        final Class<CyclicTask> cyclicTasks = new ClassImpl<>("e021c634-ccaf-4988-96a0-249066aee542", Set.of());
        final Class<Tracker> trackers = new ClassImpl<>("b79357f1-9209-4d86-91ba-33cbb559e896", Set.of());
        final Class<Report> reports = new ClassImpl<>("60bbf872-2bf2-426b-a15e-97e1fd654711", Set.of());

        final var user = new User(
            "272e105c-5c06-4330-b454-9b5b73705250",
            userCreated,
            userDesroyed,
            usernames,
            englishLanguage,
            userLanguages,
            userEmailAddresses,
            userProStatus,
            focusDuration,
            shortBreakDuration,
            longBreakDuration,
            longBreakInterval,
            theme,
            tasks,
            projects,
            subscriptions,
            sessions,
            journalEntries,
            cyclicTasks,
            trackers,
            reports);
        assertTrue(user != null);
    }
}

@Data
@AllArgsConstructor
@NoArgsConstructor
class User implements Agent<CreatedImpl, DestroyedImpl, ResignifiedImpl, LanguageImpl> {
    private String identifier;
    private CreatedImpl beginning;
    private DestroyedImpl ending;
    private Class<Signifier<String, ResignifiedImpl>> names;
    private LanguageImpl nativeLanguage;
    private Class<LanguageImpl> languages;

    private Class<Signifier<EmailAddress, ResignifiedImpl>> emailAddresses;
    private BooleanValue<ProStatus> proStatus;

    private ScalarValueImpl<Integer, Units.Minutes> focusDuration;
    private ScalarValueImpl<Integer, Units.Minutes> shortBreakDuration;
    private ScalarValueImpl<Integer, Units.Minutes> longBreakDuration;
    private ScalarValueImpl<Integer, Units.Natural> longBreakInterval;
    private Theme theme;
    private Class<Task> tasks;
    private Class<Project> projects;
    private Class<UserSubscription> subscription;
    private Class<UserSession> sessions;
    private Class<JournalEntry> journalEntries;
    private Class<CyclicTask> cyclicTasks;
    private Class<Tracker> trackers;
    private Class<Report> reports;
}

@Data
@AllArgsConstructor
@NoArgsConstructor
class Report implements Individual<CreatedImpl, DestroyedImpl> {
    private String identifier;
    private CreatedImpl beginning;
    private DestroyedImpl ending;
    private Class<Signifier<String, ResignifiedImpl>> titles;
    private Class<Signifier<String, ResignifiedImpl>> markdown;
    private ReportType reportType;
}

enum ReportType {
    SUMMARY, DETAILED
}

@Data
@AllArgsConstructor
@NoArgsConstructor
class Tracker implements Individual<CreatedImpl, DestroyedImpl> {
    private String identifier;
    private CreatedImpl beginning;
    private DestroyedImpl ending;
    private Class<Signifier<String, ResignifiedImpl>> name;
    private Class<Signifier<String, ResignifiedImpl>> groupNames;
    private Class<TrackerEntry> entries;
}

@Data
@AllArgsConstructor
@NoArgsConstructor
class TrackerEntry implements Individual<CreatedImpl, DestroyedImpl> {
    private String identifier;
    private CreatedImpl beginning;
    private DestroyedImpl ending;
    private ScalarValueImpl<Integer, OneToOneHundred> value;
}

@Data
@AllArgsConstructor
@NoArgsConstructor
class CyclicTask implements Individual<CreatedImpl, DestroyedImpl> {
    private String identifier;
    private CreatedImpl beginning;
    private DestroyedImpl ending;
    private Class<Signifier<String, ResignifiedImpl>> groupNames;
    private Class<Signifier<String, ResignifiedImpl>> titles;
    private Class<Signifier<String, ResignifiedImpl>> description;
    private Instant lastCompletedDate;
}

@Data
@AllArgsConstructor
@NoArgsConstructor
class JournalEntry implements Individual<CreatedImpl, DestroyedImpl> {
    private String identifier;
    private CreatedImpl beginning;
    private DestroyedImpl ending;
    private Class<Signifier<String, ResignifiedImpl>> titles;
    private Class<Signifier<String, ResignifiedImpl>> content;
    private JournalEntryType type;
    private JournalMood mood;
    private Set<String> tags;
}

enum JournalMood {
    HAPPY, SAD, NEUTRAL, ANGRY, EXCITED
}

enum JournalEntryType {
    DAILY, FREEFORM, REVIEW
}

@Data
@AllArgsConstructor
@NoArgsConstructor
class UserSession implements Individual<CreatedImpl, DestroyedImpl> {
    private String identifier;
    private CreatedImpl beginning;
    private DestroyedImpl ending;
    private Instant expiryTime;
    private ScalarValueImpl<Integer, Units.Minutes> duration;
    private SessionType type;
}

enum SessionType {
    FOCUS, BREAK
}

@Data
@AllArgsConstructor
@NoArgsConstructor
class EmailAddress implements Individual<CreatedImpl, DestroyedImpl> {
    private String identifier; // Email address
    private CreatedImpl beginning;
    private DestroyedImpl ending;
}

@Data
@AllArgsConstructor
@NoArgsConstructor
class ProStatus implements Unit {
    public static final ProStatus PRO_STATUS = new ProStatus("8f0866c9-e62c-4927-95e2-d65307eebb9d", "Pro Status", "pro");
    private String identifier;
    private String name;
    private String abbreviation;
}

enum Theme {
    LIGHT, DARK, AUTO
}

@Data
@AllArgsConstructor
@NoArgsConstructor
class Task implements Activity<StartedImpl, StoppedImpl> {
    private String identifier;
    private StartedImpl beginning;
    private StoppedImpl ending;
    private String actionsDescription;
    private Project project;
    private Class<Signifier<String, ResignifiedImpl>> titles;
    private Class<Signifier<String, ResignifiedImpl>> notes;
    private ScalarValueImpl<Integer, Units.Natural> estimatedPomodoros;
    private ScalarValueImpl<Integer, Units.Natural> completedPomodoros;
    private Class<Attribute<Task, TaskStatus>> status;
    private Class<Attribute<Task, TaskPriority>> priority;
    private Class<Attribute<Task, Instant>> dueDate;
    private ScalarValueImpl<Integer, Units.Natural> position;
    private RepeatType repeatType;
    private ScalarValueImpl<Integer, Units.Natural> repeatInterval;
    private Attribute<Task, String> repeatDays;
    private ScalarValueImpl<Integer, OneToTwelve> repeatMonth;
    private ScalarValueImpl<Integer, OneToThirtyOne> repeatDay;
    private ScalarValueImpl<Integer, OneToFive> repeatWeekOfMonth;
    private ScalarValueImpl<Integer, ZeroToSix> repeatDayOfWeek;
    private Boolean isTemplate;
    private String templateTaskId;
}

@Data
@AllArgsConstructor
@NoArgsConstructor
class Project implements Activity<StartedImpl, StoppedImpl> {
    private String identifier;
    private StartedImpl beginning;
    private StoppedImpl ending;
    private String actionsDescription;
    private Color colour;
    private Class<Signifier<String, ResignifiedImpl>> names;
    private Class<Signifier<String, ResignifiedImpl>> description;
}

enum TaskStatus {
    BACKLOG, IN_PROGRESS, DONE
}

enum TaskPriority {
    HIGH, MEDIUM, LOW
}

enum RepeatType {
    NONE, DAILY, WEEKLY, MONTHLY, ANNUALLY, MONTHLY_BY_WEEKDAY
}

@Getter
@AllArgsConstructor
@NoArgsConstructor
class OneToFive implements Unit {
    public static final OneToFive ONE_TO_TWELVE = new OneToFive("Integer 1 to 5", "One to Five", "1-5");
    private String identifier;
    private String name;
    private String abbreviation;

    public static Optional<Integer> value(final int i) {
        if (i >= 1 && i <= 5) {
            return Optional.of(Integer.valueOf(i));
        }
        return Optional.empty();
    }
}

@Getter
@AllArgsConstructor
@NoArgsConstructor
class ZeroToSix implements Unit {
    public static final ZeroToSix ZERO_TO_SIX = new ZeroToSix("Integer 0 to 6", "Zero to Six", "0-6");
    private String identifier;
    private String name;
    private String abbreviation;

    public static Optional<Integer> value(final int i) {
        if (i >= 0 && i <= 6) {
            return Optional.of(Integer.valueOf(i));
        }
        return Optional.empty();
    }
}

@Getter
@AllArgsConstructor
@NoArgsConstructor
class OneToTwelve implements Unit {
    public static final OneToTwelve ONE_TO_TWELVE = new OneToTwelve("Integer 1 to 12", "One to Twelve", "1-12");
    private String identifier;
    private String name;
    private String abbreviation;

    public static Optional<Integer> value(final int i) {
        if (i >= 1 && i <= 12) {
            return Optional.of(Integer.valueOf(i));
        }
        return Optional.empty();
    }
}

@Getter
@AllArgsConstructor
@NoArgsConstructor
class OneToThirtyOne implements Unit {
    public static final OneToTwelve ONE_TO_TWELVE = new OneToTwelve("Integer 1 to 31", "One to Thirtyone", "1-31");
    private String identifier;
    private String name;
    private String abbreviation;

    public static Optional<Integer> value(final int i) {
        if (i >= 1 && i <= 31) {
            return Optional.of(Integer.valueOf(i));
        }
        return Optional.empty();
    }
}

@Getter
@AllArgsConstructor
@NoArgsConstructor
class OneToOneHundred implements Unit {
    public static final OneToOneHundred ONE_TO_ONE_HUNDRED = new OneToOneHundred("Integer 1 to 100", "One to OneHundred", "1-100");
    private String identifier;
    private String name;
    private String abbreviation;

    public static Optional<Integer> value(final int i) {
        if (i >= 1 && i <= 100) {
            return Optional.of(Integer.valueOf(i));
        }
        return Optional.empty();
    }
}

@Data
@AllArgsConstructor
@NoArgsConstructor
class UserSubscription implements Individual<StartedImpl, StoppedImpl> {
    private String identifier;
    private StartedImpl beginning;
    private StoppedImpl ending;
    private SubscriptionStatus status;
    private SubscriptionType type;
    private SubscriptionLevel level;
    private String plan;
    private Instant freeTrialExpiresAt;
    private String stripeCustomerId;
}

enum SubscriptionStatus {
    FREE_TRIAL, ACTIVE, PAST_DUE, CANCELLED, EXPIRED
}

enum SubscriptionType {
    MONTHLY, YEARLY
}

enum SubscriptionLevel {
    BASIC, PREMIUM
}
