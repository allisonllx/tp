package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.core.index.Index;
import seedu.address.model.Model;

/**
 * Lists all contacts in the address book to the user.
 */
public class ListCommand extends Command {

    public static final String COMMAND_WORD = "list";
    public static final String MESSAGE_USAGE =
            COMMAND_WORD + ": Lists all contacts in the address book.\nExample: " + COMMAND_WORD;
    public static final String MESSAGE_SUCCESS = "Listed all contacts";


    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);

        model.resetDisplayedContactList();

        String feedback = MESSAGE_SUCCESS;
        model.saveSnapshot(feedback);
        return new ScrollToIndexCommandResult(feedback, Index.fromZeroBased(0));
    }

    @Override
    public boolean equals(Object other) {
        return other instanceof ListCommand;
    }
}
