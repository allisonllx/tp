package seedu.address.logic.commands;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_THEME;

import seedu.address.commons.core.Themes;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

/**
 * Changes the address book file path.
 */
public class SetThemeCommand extends SetCommand {
    public static final String MESSAGE_SUCCESS = "Set to \"%1$s\" theme";

    public static final String MESSAGE_USAGE = COMMAND_WORD + " " + PREFIX_THEME + ": Changes the theme currently used"
            + Themes.AVAILABLE_THEMES_MESSAGE
            + "Example: " + COMMAND_WORD + " " + PREFIX_THEME + "sakura";

    private final String key;

    /**
     * @param theme Name of the theme to set to.
     */
    public SetThemeCommand(String theme) {
        requireAllNonNull(theme);
        assert Themes.contains(theme);
        this.key = theme;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        model.setThemeUrl(Themes.get(key));

        String feedback = String.format(MESSAGE_SUCCESS, key);
        model.saveSnapshot(feedback);
        return new CommandResult(feedback);
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof SetThemeCommand)) {
            return false;
        }

        // state check
        SetThemeCommand o = (SetThemeCommand) other;
        return key.equals(o.key);
    }
}
