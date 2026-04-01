package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new ClearCommand object
 */
public class ClearCommandParser implements Parser<ClearCommand> {
    /**
     * Checks that the given {@code String} of arguments is empty and returns a ClearCommand object
     * for execution.
     * @throws ParseException if the user input does not conform to the expected format
     */
    public ClearCommand parse(String args) throws ParseException {
        if (args.trim().isEmpty()) {
            return new ClearCommand();
        }
        throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ClearCommand.MESSAGE_USAGE));
    }
}
