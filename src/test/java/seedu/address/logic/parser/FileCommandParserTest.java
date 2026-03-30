package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DELETE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ON;
import static seedu.address.logic.parser.CliSyntax.PREFIX_OPEN;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.FileCommand;
import seedu.address.logic.commands.FileDeleteCommand;
import seedu.address.logic.commands.FileOpenCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.UserPrefs;

public class FileCommandParserTest {
    private static final String FILENAME = "new_book";
    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, FileCommand.MESSAGE_USAGE);
    private static final String MESSAGE_INVALID_FILENAME =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, UserPrefs.FILENAME_CONSTRAINTS_MESSAGE);

    private FileCommandParser parser = new FileCommandParser();

    @Test
    public void parse_fileOpenCommand_success() {
        FileOpenCommand expectedFileOpenCommand = new FileOpenCommand(FILENAME);
        assertParseSuccess(parser, " " + PREFIX_OPEN + FILENAME, expectedFileOpenCommand);
    }

    @Test
    public void parse_fileDeleteCommand_success() {
        FileDeleteCommand expectedFileDeleteCommand =
                new FileDeleteCommand(UserPrefs.formatAddressBookFilePath(FILENAME));
        assertParseSuccess(parser, " " + PREFIX_DELETE + FILENAME, expectedFileDeleteCommand);
    }

    @Test
    public void parse_invalidArguments_failure() {
        // no index specified
        assertParseFailure(parser, PREFIX_OPEN.toString(), MESSAGE_INVALID_FORMAT);

        // no field specified
        assertParseFailure(parser, "1", MESSAGE_INVALID_FORMAT);

        // no index and no field specified
        assertParseFailure(parser, "", MESSAGE_INVALID_FORMAT);

        // clear command with no index specified
        assertParseFailure(parser, " " + PREFIX_OPEN + "&newbook", MESSAGE_INVALID_FILENAME);

        assertThrows(ParseException.class, () -> parser.parse(PREFIX_ON + "&newbook"));
    }
}
