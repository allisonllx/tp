package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.logic.commands.ExitCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new ExitCommand object
 */
public class ExitCommandParser implements Parser<ExitCommand> {
    /**
     * Checks that the given {@code String} of arguments is empty and returns a ExitCommand object
     * for execution.
     *
     * @throws ParseException if the user input does not conform to the expected format
     */
    public ExitCommand parse(String args) throws ParseException {
        if (args.trim().isEmpty()) {
            return new ExitCommand();
        }
        throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ExitCommand.MESSAGE_USAGE));
    }
}
