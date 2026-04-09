package seedu.address.commons.core;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.theme.Themes;
import seedu.address.ui.UiUtil;

public class ThemesTest {

    @Test
    public void containsTest() {
        //Check that all themes intended to exist do exist
        assertTrue(Themes.contains("dark"));
        assertTrue(Themes.contains("light"));
        assertTrue(Themes.contains("book"));
        assertTrue(Themes.contains("sakura"));

        //Non-existent theme returns false
        assertFalse(Themes.contains("foo"));
    }

    @Test
    public void getTest() {
        assertEquals(UiUtil.getUrl("DarkTheme.css").toString(), Themes.get("dark").getUrl());

        //Non-existent theme causes assertion error
        assertThrows(AssertionError.class, () -> Themes.get("foo"));
    }
}
