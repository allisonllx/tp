package seedu.address.logic.commands;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import seedu.address.commons.core.Themes;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

/**
 * Changes the address book file path.
 */
public class ThemeCommand extends Command {
    public static final String MESSAGE_SUCCESS = "Set to \"%1$s\" theme";

    public static final String COMMAND_WORD = "theme";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Changes the theme currently used"
            + Themes.AVAILABLE_THEMES_MESSAGE
            + "Example: " + COMMAND_WORD + "sakura";

    private final String key;

    /**
     * @param theme Name of the theme to set to.
     */
    public ThemeCommand(String theme) {
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
        if (!(other instanceof ThemeCommand)) {
            return false;
        }

        // state check
        ThemeCommand o = (ThemeCommand) other;
        return key.equals(o.key);
    }
}
