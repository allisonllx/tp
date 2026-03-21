package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_CONTACT;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.CrossRefCommand;

/**
 * Tests for {@code CrossRefCommandParser}.
 */
public class CrossRefCommandParserTest {

    private CrossRefCommandParser parser = new CrossRefCommandParser();

    @Test
    public void parse_validArgs_returnsCrossRefCommand() {
        assertParseSuccess(parser, "1", new CrossRefCommand(INDEX_FIRST_CONTACT));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "a",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, CrossRefCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_emptyArgs_throwsParseException() {
        assertParseFailure(parser, "",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, CrossRefCommand.MESSAGE_USAGE));
    }
}
