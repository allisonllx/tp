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
 * Adds a reminder to an existing contact in the address book.
 */
public class ReminderAddCommand extends ReminderCommand {

    public static final String MESSAGE_ADD_REMINDERS_SUCCESS = "Added reminder to Contact: %1$s";

    private final Index index;
    private final Reminder reminder;

    /**
     * @param index of the contact in the filtered contact list to edit the reminder
     * @param reminder of the contact to be updated to
     */
    public ReminderAddCommand(Index index, Reminder reminder) {
        requireAllNonNull(index, reminder);

        this.index = index;
        this.reminder = reminder;
    }
    @Override
    public CommandResult execute(Model model) throws CommandException {
        List<Contact> lastShownList = model.getFilteredContactList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_CONTACT_DISPLAYED_INDEX);
        }

        Contact contactToEdit = lastShownList.get(index.getZeroBased());
        List<Reminder> newReminders = new ArrayList<>(contactToEdit.getReminders());
        newReminders.add(reminder);
        Contact editedContact = new Contact(contactToEdit.getName(), contactToEdit.getPhone(), contactToEdit.getEmail(),
                contactToEdit.getAddress(), contactToEdit.getNotes(), contactToEdit.getTags(), newReminders);

        model.setContact(contactToEdit, editedContact);
        model.updateFilteredContactList(PREDICATE_SHOW_ALL_CONTACTS);

        return new CommandResult(generateSuccessMessage(editedContact));
    }

    /**
     * Generates a command execution success message based on whether the Reminders are added to or removed from
     * {@code contactToEdit}.
     */
    private String generateSuccessMessage(Contact contactToEdit) {
        return String.format(MESSAGE_ADD_REMINDERS_SUCCESS, Messages.format(contactToEdit));
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof ReminderAddCommand e)) {
            return false;
        }

        // state check
        return index.equals(e.index)
                && reminder.equals(e.reminder);
    }
}
