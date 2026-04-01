package seedu.address.logic;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import seedu.address.logic.parser.Prefix;
import seedu.address.model.contact.Contact;
import seedu.address.model.contact.Note;

/**
 * Container for user visible messages.
 */
public class Messages {

    public static final String MESSAGE_UNKNOWN_COMMAND = "Unknown command";
    public static final String MESSAGE_INVALID_COMMAND_FORMAT = "Invalid command format! \n%1$s";
    public static final String MESSAGE_INVALID_CONTACT_DISPLAYED_INDEX = "The contact index provided is invalid";
    public static final String MESSAGE_CONTACTS_LISTED_OVERVIEW = "%1$d contacts listed!";
    public static final String MESSAGE_CONTACTS_SORTED_OVERVIEW = "%1$d contacts sorted!";
    public static final String MESSAGE_MISSING_INDEX = "Missing an INDEX";
    public static final String MESSAGE_MISSING_KEYWORD = "Missing a keyword";
    public static final String MESSAGE_INVALID_INDEX = "INDEX should be between 1 and %d";
    public static final String MESSAGE_DUPLICATE_FIELDS =
            "Multiple values specified for the following single-valued field(s): ";
    public static final String MESSAGE_DUPLICATE_TAGS = "Tag names should be unique";

    /**
     * Returns an error message indicating the duplicate prefixes.
     */
    public static String getErrorMessageForDuplicatePrefixes(Prefix... duplicatePrefixes) {
        assert duplicatePrefixes.length > 0;

        Set<String> duplicateFields =
                Stream.of(duplicatePrefixes).map(Prefix::toString).collect(Collectors.toSet());

        return MESSAGE_DUPLICATE_FIELDS + String.join(" ", duplicateFields);
    }

    /**
     * Formats the {@code contact} for display to the user.
     */
    public static String format(Contact contact) {
        final StringBuilder builder = new StringBuilder();
        builder.append(contact.getName());
        contact.getPhone().ifPresent((phone) -> builder.append("; Phone: ").append(phone));
        contact.getEmail().ifPresent((email) -> builder.append("; Email: ").append(email));
        contact.getAddress().ifPresent((address) -> builder.append("; Address: ").append(address));
        contact.getLastContacted().ifPresent(lastContacted -> {
            builder.append("; Last Contacted: ");
            builder.append(lastContacted);
        });
        if (!contact.getTags().isEmpty()) {
            builder.append("; Tags: ");
            contact.getTags().forEach(builder::append);
        }
        return builder.toString();
    }

    /**
     * Formats a range message for 1-indexed contact list indices.
     */
    public static String formatIndexOutOfRange(int maxIndex) {
        return String.format(MESSAGE_INVALID_INDEX, maxIndex);
    }

    /**
     * Backward-compatible alias for index range message formatter.
     */
    public static String getIndexOutOfRangeMessage(int maxIndex) {
        return formatIndexOutOfRange(maxIndex);
    }

    /**
     * Formats a command parse failure that includes the command usage text.
     */
    public static String formatInvalidCommandFormat(String failureMessage, String commandUsage) {
        return String.format(MESSAGE_INVALID_COMMAND_FORMAT, failureMessage + "\n" + commandUsage);
    }

    /**
     * Backward-compatible alias for invalid command format builder.
     */
    public static String getCommandErrorWithUsage(String failureMessage, String commandUsage) {
        return formatInvalidCommandFormat(failureMessage, commandUsage);
    }

    /**
     * Formats note command success output with updated full notes shown beneath contact details.
     * Contact references {@code @{UUID}} in stored notes are shown as {@code Name (@n)} (or name only)
     * when {@code displayedContacts} and {@code allContacts} are provided; otherwise raw stored text is used.
     */
    public static String formatNoteOutput(String headerPrefix, Contact contact) {
        return formatNoteOutput(headerPrefix, contact, null, null);
    }

    /**
     * @see #formatNoteOutput(String, Contact)
     */
    public static String formatNoteOutput(String headerPrefix, Contact contact, List<Contact> displayedContacts,
            List<Contact> allContacts) {
        String notesBlock = contact.getNotes().stream()
                .map(note -> Note.formatContactReferencesForDisplay(note.value, displayedContacts, allContacts))
                .collect(Collectors.joining("\n"));
        return String.format("%s: %s\n\n%s", headerPrefix, format(contact), notesBlock);
    }

}
