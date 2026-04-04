package seedu.address.logic.commands;

import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

import static java.util.Objects.requireNonNull;

/**
 * Empties the find command filters, if any were in place.
 */
public class FindResetCommand extends FindCommand {
    private static final String MESSAGE_SUCCESS = "Cleared find filters to display all contacts.";

    /**
     * Creates a FindResetCommand that clears contact filters.
     */
    public FindResetCommand() {}

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        model.filterDisplayedContactList(null);

        String feedback = MESSAGE_SUCCESS;
        model.saveSnapshot(feedback);
        return new CommandResult(feedback);
    }

    @Override
    public boolean equals(Object other) {
        return other instanceof FindResetCommand;
    }
}
