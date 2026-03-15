package seedu.address.logic.commands;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_CONTACTS;

import java.util.ArrayList;
import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.contact.Contact;
import seedu.address.model.contact.Reminder;

/**
 * Remove lines of notes from an existing contact in the address book.
 */
public class ReminderRemoveCommand extends ReminderCommand {

    public static final String MESSAGE_REMOVE_REMINDERS_SUCCESS = "Removed reminders from Contact: %1$s";

    private final Index index;
    private final int numReminders;

    /**
     * @param index    Index of the contact in the filtered contact list.
     * @param numReminders How many reminders to remove.
     */
    public ReminderRemoveCommand(Index index, int numReminders) {
        requireAllNonNull(index);

        this.index = index;
        this.numReminders = numReminders;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        List<Contact> lastShownList = model.getFilteredContactList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_CONTACT_DISPLAYED_INDEX);
        }

        Contact contactToEdit = lastShownList.get(index.getZeroBased());

        List<Reminder> newReminders = new ArrayList<>(contactToEdit.getReminders());

        int numExistingLines = newReminders.size();
        newReminders = newReminders.subList(Math.min(numReminders, numExistingLines), numExistingLines);

        Contact editedContact = new Contact(contactToEdit.getName(), contactToEdit.getPhone(), contactToEdit.getEmail(),
                contactToEdit.getAddress(), contactToEdit.getNotes(), contactToEdit.getTags(), newReminders);

        model.setContact(contactToEdit, editedContact);
        model.updateFilteredContactList(PREDICATE_SHOW_ALL_CONTACTS);

        return new CommandResult(generateSuccessMessage(editedContact));
    }

    /**
     * Generates a command execution success message.
     * {@code contactToEdit}.
     */
    private String generateSuccessMessage(Contact contactToEdit) {
        return String.format(MESSAGE_REMOVE_REMINDERS_SUCCESS, Messages.format(contactToEdit));
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof ReminderRemoveCommand e)) {
            return false;
        }

        // state check
        return index.equals(e.index)
                && numReminders == e.numReminders;
    }
}
