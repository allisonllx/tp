package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.Theme;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

public class SetThemeCommandTest {
    private static final String DARK_THEME = Theme.AVAILABLE_THEMES.get("dark");
    private static final String LIGHT_THEME = Theme.AVAILABLE_THEMES.get("light");

    @Test
    public void invalidTheme_failure() {
        assertThrows(AssertionError.class, () -> new SetThemeCommand("foo"));
    }

    @Test
    public void executeTest() {
        Model model = new ModelManager();
        UserPrefs userPrefs = new UserPrefs();
        userPrefs.setGuiSettings(new GuiSettings(1, 2, 3, 4, DARK_THEME));
        model.setUserPrefs(userPrefs);
        SetThemeCommand setCommand = new SetThemeCommand("light");
        String expectedMessage = String.format(SetThemeCommand.MESSAGE_SUCCESS, "light");
        Model expectedModel = new ModelManager();
        UserPrefs expectedUserPrefs = new UserPrefs();
        expectedUserPrefs.setGuiSettings(new GuiSettings(1, 2, 3, 4, LIGHT_THEME));
        expectedModel.setUserPrefs(expectedUserPrefs);
        assertCommandSuccess(setCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void equals() {
        final SetThemeCommand setThemeCommand = new SetThemeCommand("dark");

        // same object -> returns true
        assertTrue(setThemeCommand.equals(setThemeCommand));

        // same theme word -> returns true
        assertTrue(setThemeCommand.equals(new SetThemeCommand("dark")));

        // null -> returns false
        assertFalse(setThemeCommand.equals(null));

        // different types -> returns false
        assertFalse(setThemeCommand.equals(new ClearCommand()));

        // different theme word -> returns false
        assertFalse(setThemeCommand.equals(new SetThemeCommand("light")));
    }
}
