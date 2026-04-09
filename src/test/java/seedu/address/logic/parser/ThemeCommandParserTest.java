package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.theme.Themes;
import seedu.address.logic.commands.ThemeCommand;

public class ThemeCommandParserTest {
    private static final String THEME = "light";
    private static final String MESSAGE_INVALID_THEME =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, ThemeCommand.MESSAGE_USAGE);

    private ThemeCommandParser parser = new ThemeCommandParser();

    @Test
    public void parse_setThemeCommand_success() {
        ThemeCommand expectedSetThemeCommand =
                new ThemeCommand(Themes.get("light"));
        assertParseSuccess(
                parser,
                " " + THEME,
                expectedSetThemeCommand);
    }

    @Test
    public void parse_invalidTheme_failure() {
        //Random string of characters used as it is least likely to be a real theme
        assertParseFailure(parser, " " + "%$#@DSGF23ds@#4g5a", MESSAGE_INVALID_THEME);
    }

    @Test
    public void nullArgument_throwsAssertionError() {
        assertThrows(AssertionError.class, () -> parser.parse(null));
    }
}
