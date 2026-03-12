package seedu.address.logic.commands;

import seedu.address.logic.parser.Prefix;

/**
 * Parent class for all note-related commands.
 */
public abstract class NoteCommand extends Command {

    public static final String COMMAND_WORD = "note";
    public static final Prefix PREFIX_REMOVE = new Prefix("c/");
    public static final Prefix PREFIX_CLEAR = new Prefix("ca/");

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the notes of the contact identified "
            + "by the index number used in the last contact listing. "
            + "New notes will be stacked underneath existing ones.\n"
            + "Format [Add]: " + COMMAND_WORD + " INDEX NOTE\n"
            + "Format [Clear]: " + COMMAND_WORD + " INDEX " + PREFIX_CLEAR + "\n"
            + "Format [Remove]: " + COMMAND_WORD + " INDEX " + PREFIX_REMOVE + "0\n"
            + "Parameters:\n"
            + "- INDEX (must be a positive integer)\n"
            + "- NOTE (should be a non-empty string)\n"
            + "- LINES_TO_REMOVE (must be a non-negative integer)\n"
            + "Example [Add]: " + COMMAND_WORD + " 1 " + "Likes to swim.\n"
            + "Example [Clear]: " + COMMAND_WORD + " 1 " + PREFIX_CLEAR + "\n"
            + "Example [Remove]: " + COMMAND_WORD + " 1 " + PREFIX_REMOVE + "1";
}
