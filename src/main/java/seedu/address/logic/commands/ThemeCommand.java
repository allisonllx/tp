package seedu.address.logic.commands;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import seedu.address.commons.core.theme.Theme;
import seedu.address.commons.core.theme.Themes;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

/**
 * Changes the address book file path.
 */
public class ThemeCommand extends Command {
    public static final String MESSAGE_SUCCESS = "Set to \"%1$s\" theme";

    public static final String COMMAND_WORD = "theme";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Changes the theme currently used\n"
            + Themes.AVAILABLE_THEMES_MESSAGE + "\n"
            + "Example: " + COMMAND_WORD + " sakura";

    private final Theme theme;

    /**
     * @param theme Name of the theme to set to.
     */
    public ThemeCommand(Theme theme) {
        requireAllNonNull(theme);
        this.theme = theme;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        model.setTheme(theme);

        String feedback = String.format(MESSAGE_SUCCESS, theme.getName());
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
        return theme.equals(o.theme);
    }
}
