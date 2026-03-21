package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.CrossRefCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new CrossRefCommand object.
 */
public class CrossRefCommandParser implements Parser<CrossRefCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the CrossRefCommand
     * and returns a CrossRefCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public CrossRefCommand parse(String args) throws ParseException {
        try {
            Index index = ParserUtil.parseIndex(args);
            return new CrossRefCommand(index);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, CrossRefCommand.MESSAGE_USAGE), pe);
        }
    }
}
