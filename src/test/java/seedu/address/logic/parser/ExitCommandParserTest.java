package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.ExitCommand;

public class ExitCommandParserTest {
    private static final ExitCommandParser PARSER = new ExitCommandParser();

    @Test
    public void parse_noArgs_returnsExitCommand() {
        assertParseSuccess(PARSER, "", new ExitCommand());
        assertParseSuccess(PARSER, " ", new ExitCommand());
    }

    @Test
    public void parseArgs_throwsParseException() {
        assertParseFailure(PARSER, "1", String.format(MESSAGE_INVALID_COMMAND_FORMAT, ExitCommand.MESSAGE_USAGE));
    }
}
