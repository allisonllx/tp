package seedu.address.model.contact;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ON;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.address.commons.core.timepoint.TimePoint;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.parser.ArgumentMultimap;
import seedu.address.logic.parser.ArgumentTokenizer;
import seedu.address.logic.parser.TimePointParser;

/**
 * Represents a Contact's notes in the address book, may contain a {@code TimePoint} to act as a reminder.
 * Notes can contain contact references in the format {@code @{UUID}} which link to other contacts.
 * Guarantees: immutable; is always valid
 */
public class Note {

    //Temporarily placed here for convenience, should be moved into
    //UserPrefs to allow users to set their own reminder periods
    public static final int DUE_PERIOD_DAYS = 7;

    /** Regex pattern matching contact references stored as @{UUID} in note text. */
    public static final Pattern CONTACT_REF_PATTERN =
            Pattern.compile("@\\{([0-9a-fA-F\\-]{36})\\}");

    /** Regex pattern matching user-typed @INDEX references in note text. */
    public static final Pattern CONTACT_INDEX_PATTERN =
            Pattern.compile("@(\\d+)");

    public final String value;
    public final Optional<TimePoint<?>> timePoint;

    /**
     * Constructs a {@code Note}.
     *
     * @param note A valid note.
     */
    public Note(String note) {
        requireNonNull(note);
        value = note;
        this.timePoint = Optional.empty();
    }

    /**
     * Constructs a {@code Note} with a {@code TimePoint} to act as a reminder.
     *
     * @param note A valid note.
     */
    public Note(String note, TimePoint<?> timePoint) {
        requireNonNull(note);
        value = note;
        this.timePoint = Optional.ofNullable(timePoint);
    }

    /**
     * Outputs a {@code JSON} formatted string to represent this note.
     */
    public String toJsonString() {
        return value + timePoint.map(tp -> " on/" + timePoint.get().toString()).orElse("");
    }

    /**
     * Creates a Note from a {@code JSON} formatted string.
     */
    public static Note fromJsonString(String string) {
        requireNonNull(string);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(string, PREFIX_ON);
        if (argMultimap.getValue(PREFIX_ON).isPresent()) {
            return new Note(
                    argMultimap.getPreamble(),
                    TimePointParser.toTimePoint(argMultimap.getValue(PREFIX_ON).get()));
        }
        return new Note(string);
    }

    public boolean isReminder() {
        return timePoint.isPresent();
    }

    /**
     * Checks if this note is a reminder that is due in {@code DUE_PERIOD_DAYS} number of days.
     */
    public boolean hasDueReminder() {
        TimePoint<?> cutOffTime = TimePoint.of(LocalDateTime.now().plusDays(DUE_PERIOD_DAYS));
        return timePoint.map(tp -> tp.isBefore(cutOffTime) && tp.isAfter(TimePoint.now())).orElse(false);
    }

    /**
     * Checks if this note is a reminder that is due in {@code DUE_PERIOD_DAYS} number of days.
     */
    public boolean hasActiveReminder() {
        return timePoint.map(tp -> tp.isAfter(TimePoint.now())).orElse(false);
    }

    /**
     * Returns true if this note contains any contact references ({@code @{UUID}}).
     */
    public boolean hasContactReferences() {
        return CONTACT_REF_PATTERN.matcher(value).find();
    }

    /**
     * Returns a new Note with the given UUID reference replaced by the plain text name.
     * Used when a referenced contact is deleted — the name loses its styled rendering.
     */
    public Note dereferenceContact(UUID contactId, String contactName) {
        String newValue = value.replace("@{" + contactId.toString() + "}", contactName);
        return timePoint.map(tp -> new Note(newValue, tp)).orElse(new Note(newValue));
    }

    /**
     * Formats stored note text for user-facing output: each {@code @{UUID}} is shown with the
     * contact's name. If they appear in {@code displayedContacts}, the form is
     * {@code Name (@n)} (1-based index in that list). If only found in {@code allContacts},
     * the name alone is used. Otherwise the original token is kept.
     */
    public static String formatContactReferencesForDisplay(String text, List<Contact> displayedContacts,
            List<Contact> allContacts) {
        requireNonNull(text);
        if (displayedContacts == null) {
            return text;
        }
        Matcher matcher = CONTACT_REF_PATTERN.matcher(text);
        StringBuffer sb = new StringBuffer();
        while (matcher.find()) {
            String uuidStr = matcher.group(1);
            UUID refId = UUID.fromString(uuidStr);
            String replacement = replacementForContactRef(refId, uuidStr, displayedContacts, allContacts);
            matcher.appendReplacement(sb, Matcher.quoteReplacement(replacement));
        }
        matcher.appendTail(sb);
        return sb.toString();
    }

    /**
     * Returns {@code Name (@n)} if {@code refId} matches the {@code i}-th contact in {@code displayedContacts}
     * (1-based {@code n = i + 1}).
     */
    private static Optional<String> displayFormForRef(UUID refId, List<Contact> displayedContacts) {
        for (int i = 0; i < displayedContacts.size(); i++) {
            Contact c = displayedContacts.get(i);
            if (c.getId().equals(refId)) {
                return Optional.of(c.getName().fullName + " (@" + (i + 1) + ")");
            }
        }
        return Optional.empty();
    }

    /**
     * Returns the contact's display name if {@code refId} exists in {@code allContacts}.
     */
    private static Optional<String> nameFromAllContacts(UUID refId, List<Contact> allContacts) {
        if (allContacts == null) {
            return Optional.empty();
        }
        for (Contact c : allContacts) {
            if (c.getId().equals(refId)) {
                return Optional.of(c.getName().fullName);
            }
        }
        return Optional.empty();
    }

    /**
     * Resolves a single {@code @{uuid}} token to display text for CLI output.
     */
    private static String replacementForContactRef(UUID refId, String uuidStr, List<Contact> displayedContacts,
            List<Contact> allContacts) {
        return displayFormForRef(refId, displayedContacts)
                .or(() -> nameFromAllContacts(refId, allContacts))
                .orElse("@{" + uuidStr + "}");
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("value", value)
                .add("timePoint", timePoint)
                .toString();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof Note)) {
            return false;
        }
        Note otherNote = (Note) other;
        return value.equals(otherNote.value)
                && Objects.equals(timePoint, otherNote.timePoint);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value, timePoint);
    }
}
