package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

/**
 * Reverts the model to a previous snapshot.
 */
public class UndoCommand extends Command {

    public static final String COMMAND_WORD = "undo";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Reverts the model back by one command.\n"
            + "Example: " + COMMAND_WORD;

    public static final String MESSAGE_UNDO_SUCCESS = "Undo command: \n";

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        return new CommandResult(String.format(MESSAGE_UNDO_SUCCESS), -1);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        return other instanceof UndoCommand;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .toString();
    }
}
