package seedu.address.logic.commands;

import static seedu.address.logic.parser.CliSyntax.PREFIX_DELETE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_OPEN;

/**
 * Parent class for all file-related commands.
 */
public abstract class FileCommand extends Command {
    public static final String COMMAND_WORD = "file";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Manages the contact list files being used.\n"
            + "Format [Open file]: " + COMMAND_WORD + " " + PREFIX_OPEN + "FILE_NAME\n"
            + "Format [Delete file]: " + COMMAND_WORD + " " + PREFIX_DELETE + "FILE_NAME\n"
            + "Parameters:\n"
            + "- FILE_NAME (should only contain alphanumeric characters and the underscore character '_'.)\n"
            + "Example [Open file]: " + COMMAND_WORD + " " + PREFIX_OPEN + "new_list\n"
            + "Example [Delete file]: " + COMMAND_WORD + " " + PREFIX_DELETE + "OldFile\n";
}
