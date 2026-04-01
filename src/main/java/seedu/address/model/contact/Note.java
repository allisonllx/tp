package seedu.address.model.contact;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ON;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.address.commons.core.timepoint.TimePoint;
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
    public final TimePoint timePoint;

    /**
     * Constructs a {@code Note}.
     *
     * @param note A valid note.
     */
    public Note(String note) {
        requireNonNull(note);
        value = note;
        this.timePoint = null;
    }

    /**
     * Constructs a {@code Note} with a {@code TimePoint} to act as a reminder.
     *
     * @param note A valid note.
     */
    public Note(String note, TimePoint timePoint) {
        requireNonNull(note);
        value = note;
        this.timePoint = timePoint;
    }

    /**
     * Outputs a {@code JSON} formatted string to represent this note.
     */
    public String toJsonString() {
        return value + ((timePoint == null) ? "" : " on/" + timePoint.toString());
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
        return timePoint != null;
    }

    /**
     * Checks if this note is a reminder that is due in {@code DUE_PERIOD_DAYS} number of days.
     */
    public boolean hasDueReminder() {
        if (timePoint == null) {
            return false;
        }
        TimePoint cutOffTime = TimePoint.of(LocalDateTime.now().plusDays(DUE_PERIOD_DAYS));
        return timePoint.isBefore(cutOffTime) && timePoint.isAfter(TimePoint.now());
    }

    /**
     * Checks if this note is a reminder that is due in {@code DUE_PERIOD_DAYS} number of days.
     */
    public boolean hasActiveReminder() {
        if (timePoint == null) {
            return false;
        }
        return timePoint.isAfter(TimePoint.now());
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
        return new Note(newValue, timePoint);
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
            String replacement = null;
            for (int i = 0; i < displayedContacts.size(); i++) {
                if (displayedContacts.get(i).getId().equals(refId)) {
                    String name = displayedContacts.get(i).getName().fullName;
                    replacement = name + " (@" + (i + 1) + ")";
                    break;
                }
            }
            if (replacement == null && allContacts != null) {
                for (Contact c : allContacts) {
                    if (c.getId().equals(refId)) {
                        replacement = c.getName().fullName;
                        break;
                    }
                }
            }
            if (replacement == null) {
                replacement = "@{" + uuidStr + "}";
            }
            matcher.appendReplacement(sb, Matcher.quoteReplacement(replacement));
        }
        matcher.appendTail(sb);
        return sb.toString();
    }

    @Override
    public String toString() {
        return value;
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
                && Objects.equals(
                        timePoint == null ? null : timePoint.toString(),
                        otherNote.timePoint == null ? null : otherNote.timePoint.toString());
    }

    @Override
    public int hashCode() {
        return Objects.hash(value, timePoint == null ? null : timePoint.toString());
    }
}
