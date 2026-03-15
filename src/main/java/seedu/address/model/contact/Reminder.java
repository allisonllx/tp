package seedu.address.model.contact;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ON;

import seedu.address.logic.parser.ArgumentMultimap;
import seedu.address.logic.parser.ArgumentTokenizer;
import seedu.address.logic.parser.TimePointParser;

/**
 * Represents a Contact's reminders in the address book.
 * Guarantees: immutable; is always valid.
 */
public class Reminder {
    public final String note;
    public final TimePoint timePoint;

    /**
     * Constructs a {@code Reminder}.
     *
     * @param note A note to add onto the {@code Reminder}, may be empty.
     * @param timePoint The time attached to the {@code Reminder}, should be valid.
     */
    public Reminder(String note, TimePoint timePoint) {
        requireNonNull(timePoint);
        this.note = note;
        this.timePoint = timePoint;
    }

    @Override
    public String toString() {
        return String.format("Reminder: %s%s", note.isEmpty() ? "" : note + " on ", timePoint);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Reminder otherReminder)) {
            return false;
        }

        return note.equals(otherReminder.note) && timePoint.equals(otherReminder.timePoint);
    }

    @Override
    public int hashCode() {
        return timePoint.hashCode();
    }

    /**
     * Parse a string into a {@code Reminder}.
     *
     * @param reminder String that represents a {@code Reminder}. Must always contain " on/" to separate the reminder
     *                 note and reminder time.
     * @return A parsed {@code Reminder}.
     */
    public static Reminder parseReminder(final String reminder) {
        assert reminder.contains(" on/");
        ArgumentMultimap reminderMultimap = ArgumentTokenizer.tokenize(reminder, PREFIX_ON);
        return new Reminder(
                reminderMultimap.getPreamble(),
                TimePointParser.toTimePoint(reminderMultimap.getValue(PREFIX_ON).get()));
    }
}
