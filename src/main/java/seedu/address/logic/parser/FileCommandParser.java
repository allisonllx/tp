package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DELETE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_OPEN;

import seedu.address.logic.commands.FileCommand;
import seedu.address.logic.commands.FileDeleteCommand;
import seedu.address.logic.commands.FileOpenCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.UserPrefs;

/**
 * Parses input arguments and creates a new {@code FileCommand} object
 */
public class FileCommandParser implements Parser<FileCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the {@code FileCommand}
     * and returns a {@code FileCommand} object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public FileCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_OPEN, PREFIX_DELETE);

        boolean isPreamblePresent = !argMultimap.getPreamble().isEmpty();
        boolean isOpenPrefixPresent = argMultimap.getValue(PREFIX_OPEN).isPresent();
        boolean isDeletePrefixPresent = argMultimap.getValue(PREFIX_DELETE).isPresent();

        if (isPreamblePresent) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FileCommand.MESSAGE_USAGE));
        }

        if (isOpenPrefixPresent == isDeletePrefixPresent) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FileCommand.MESSAGE_USAGE));
        }

        if (isOpenPrefixPresent) {
            String fileName = argMultimap.getValue(PREFIX_OPEN).get();
            if (!UserPrefs.isValidFileName(fileName)) {
                throw new ParseException(
                        String.format(MESSAGE_INVALID_COMMAND_FORMAT, UserPrefs.FILENAME_CONSTRAINTS_MESSAGE));
            }
            return new FileOpenCommand(argMultimap.getValue(PREFIX_OPEN).get());
        }

        String fileName = argMultimap.getValue(PREFIX_DELETE).get();
        if (!UserPrefs.isValidFileName(fileName)) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, UserPrefs.FILENAME_CONSTRAINTS_MESSAGE));
        }
        return new FileDeleteCommand(UserPrefs.formatAddressBookFilePath(fileName));
    }
}
