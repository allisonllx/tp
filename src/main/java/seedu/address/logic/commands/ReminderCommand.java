package seedu.address.logic.commands;

import static seedu.address.logic.parser.CliSyntax.PREFIX_CLEAR;
import static seedu.address.logic.parser.CliSyntax.PREFIX_REMOVE;

/**
 * Parent class for all reminder-related commands.
 */
public abstract class ReminderCommand extends Command {

    public static final String COMMAND_WORD = "reminder";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a reminder to the contact identified "
            + "by the index number used in the last contact listing. "
            + "New notes will be stacked underneath existing ones.\n"
            + "Format [Add]: " + COMMAND_WORD + " INDEX [REMINDER_NOTE] on/TIME\n"
            + "Format [Clear]: " + COMMAND_WORD + " INDEX " + PREFIX_CLEAR + "\n"
            + "Format [Remove]: " + COMMAND_WORD + " INDEX " + PREFIX_REMOVE + "REMINDERS_TO_REMOVE\n"
            + "Parameters:\n"
            + "- INDEX (must be a positive integer)\n"
            + "- REMINDER_NOTE (optional note)\n"
            + "- REMINDERS_TO_REMOVE (must be a non-negative integer)\n"
            + "Example [Add]: " + COMMAND_WORD + " 1 " + "General meeting on/15 March\n"
            + "Example [Clear]: " + COMMAND_WORD + " 1 " + PREFIX_CLEAR + "\n"
            + "Example [Remove]: " + COMMAND_WORD + " 1 " + PREFIX_REMOVE + "1";
}
