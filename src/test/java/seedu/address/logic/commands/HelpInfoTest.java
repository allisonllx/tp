package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class HelpInfoTest {

    private static final String TEST_MESSAGE = "test message";
    private static final String TEST_URL = "https://example.com/help";

    @Test
    public void constructor_validArgs_success() {
        HelpInfo helpInfo = new HelpInfo(TEST_MESSAGE, TEST_URL);
        assertEquals(TEST_MESSAGE, helpInfo.getMessage());
        assertEquals(TEST_URL, helpInfo.getUrl());
    }

    @Test
    public void defaultConstant_isNotNull() {
        assertNotNull(HelpInfo.DEFAULT);
        assertNotNull(HelpInfo.DEFAULT.getMessage());
        assertNotNull(HelpInfo.DEFAULT.getUrl());
        assertTrue(HelpInfo.DEFAULT.getUrl().contains(HelpInfo.USER_GUIDE_BASE_URL));
    }

    @Test
    public void equals() {
        HelpInfo helpInfo = new HelpInfo(TEST_MESSAGE, TEST_URL);
        HelpInfo helpInfoCopy = new HelpInfo(TEST_MESSAGE, TEST_URL);
        HelpInfo differentMessage = new HelpInfo("other", TEST_URL);
        HelpInfo differentUrl = new HelpInfo(TEST_MESSAGE, "https://other.com");

        // same object -> true
        assertTrue(helpInfo.equals(helpInfo));

        // same values -> true
        assertTrue(helpInfo.equals(helpInfoCopy));

        // null -> false
        assertFalse(helpInfo.equals(null));

        // different type -> false
        assertFalse(helpInfo.equals("string"));

        // different message -> false
        assertFalse(helpInfo.equals(differentMessage));

        // different url -> false
        assertFalse(helpInfo.equals(differentUrl));
    }

    @Test
    public void hashcode() {
        HelpInfo helpInfo = new HelpInfo(TEST_MESSAGE, TEST_URL);
        HelpInfo helpInfoCopy = new HelpInfo(TEST_MESSAGE, TEST_URL);
        HelpInfo different = new HelpInfo("other", "https://other.com");

        // same values -> same hashcode
        assertEquals(helpInfo.hashCode(), helpInfoCopy.hashCode());

        // different values -> different hashcode
        assertNotEquals(helpInfo.hashCode(), different.hashCode());
    }

    @Test
    public void toStringMethod() {
        HelpInfo helpInfo = new HelpInfo(TEST_MESSAGE, TEST_URL);
        String result = helpInfo.toString();
        assertTrue(result.contains(TEST_MESSAGE));
        assertTrue(result.contains(TEST_URL));
    }
}
