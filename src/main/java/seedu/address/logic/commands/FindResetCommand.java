package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

/**
 * Empties the find command filters, if any were in place.
 */
public class FindResetCommand extends FindCommand {
    public static final String MESSAGE_SUCCESS = "Cleared find filters to display all contacts.";

    /**
     * Creates a FindResetCommand that clears contact filters.
     */
    public FindResetCommand() {}

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        //An always true predicate is used to show all contacts
        model.filterDisplayedContactList(contact -> true);

        String feedback = MESSAGE_SUCCESS;
        model.saveSnapshot(feedback);
        return new CommandResult(feedback);
    }

    @Override
    public boolean equals(Object other) {
        return other instanceof FindResetCommand;
    }
}
