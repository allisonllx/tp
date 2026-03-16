package seedu.address.model;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.model.contact.Contact;

/**
 * A record to act as the snapshot of the current {@code Model}.
 *
 * @param contactList List of contacts from the {@code AddressBook} of the {@code Model}.
 * @param userPrefs {@code ReadOnlyUserPrefs} of the {@code Model}.
 * @param filterPredicate {@code Predicate<Contact>} of the {@code FilteredList<Contact>} of the {@code Model}.
 */
public record Snapshot(
        List<Contact> contactList, ReadOnlyUserPrefs userPrefs, Predicate<Contact> filterPredicate) {

}
