package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.SortCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.contact.ContactFieldComparator;
import seedu.address.model.contact.ContactTagComparator;

public class SortCommandParserTest {
    private static final SortCommandParser PARSER = new SortCommandParser();

    @Test
    public void parse_emptyArg_returnsSortCommand() {
        assertParseSuccess(PARSER, "     ", new SortCommand());
    }

    @Test
    public void parse_validArgs_returnsSortCommand() throws ParseException {
        assertParseSuccess(PARSER, " n/ASC t/friends:DESC",
                new SortCommand(new ContactFieldComparator(ContactFieldComparator.Field.NAME,
                        ContactFieldComparator.Order.ASCENDING)
                        .thenComparing(new ContactTagComparator("friends", ContactTagComparator.Order.DESCENDING))));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        // Invalid order keyword
        assertParseFailure(PARSER, " n/invalid",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortCommand.MESSAGE_USAGE));

        // Should specify tag order
        assertParseFailure(PARSER, " t/friends",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortCommand.MESSAGE_USAGE));
        assertParseFailure(PARSER, " t/friends:",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortCommand.MESSAGE_USAGE));
        assertParseFailure(PARSER, " t/friends:invalid",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortCommand.MESSAGE_USAGE));

        // Preamble not allowed
        assertParseFailure(PARSER, " somePreamble n/ASC",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortCommand.MESSAGE_USAGE));
    }
}
