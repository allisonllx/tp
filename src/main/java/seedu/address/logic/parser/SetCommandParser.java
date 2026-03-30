package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_FILE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_THEME;

import seedu.address.commons.core.Themes;
import seedu.address.logic.commands.SetAddressBookFilePathCommand;
import seedu.address.logic.commands.SetCommand;
import seedu.address.logic.commands.SetThemeCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.UserPrefs;

/**
 * Parses input arguments and creates a new {@code SetCommand} object
 */
public class SetCommandParser implements Parser<SetCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the {@code SetCommand}
     * and returns a {@code SetCommand} object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public SetCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_FILE, PREFIX_THEME);

        boolean isPreamblePresent = !argMultimap.getPreamble().isEmpty();
        boolean isFilePrefixPresent = argMultimap.getValue(PREFIX_FILE).isPresent();
        boolean isThemePrefixPresent = argMultimap.getValue(PREFIX_THEME).isPresent();

        if (isPreamblePresent) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, SetCommand.MESSAGE_USAGE));
        }

        if (isFilePrefixPresent == isThemePrefixPresent) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, SetCommand.MESSAGE_USAGE));
        }

        if (isFilePrefixPresent) {
            String fileName = argMultimap.getValue(PREFIX_FILE).get();
            if (!UserPrefs.isValidFileName(fileName)) {
                throw new ParseException(
                        String.format(MESSAGE_INVALID_COMMAND_FORMAT, UserPrefs.FILENAME_CONSTRAINTS_MESSAGE));
            }
            return new SetAddressBookFilePathCommand(argMultimap.getValue(PREFIX_FILE).get());
        }
        String theme = argMultimap.getValue(PREFIX_THEME).get();
        if (!Themes.contains(theme)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, SetThemeCommand.MESSAGE_USAGE));
        }
        return new SetThemeCommand(theme);
    }
}
