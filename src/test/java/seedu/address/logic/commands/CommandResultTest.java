package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.TypicalContacts.ALICE;

import org.junit.jupiter.api.Test;

import seedu.address.model.contact.Contact;

public class CommandResultTest {
    @Test
    public void equals() {
        CommandResult commandResult = new CommandResult("feedback");

        // same values -> returns true
        assertTrue(commandResult.equals(new CommandResult("feedback")));
        assertTrue(commandResult.equals(new CommandResult("feedback", null, false, null, false, false, -1)));

        // same object -> returns true
        assertTrue(commandResult.equals(commandResult));

        // null -> returns false
        assertFalse(commandResult.equals(null));

        // different types -> returns false
        assertFalse(commandResult.equals(0.5f));

        // different feedbackToUser value -> returns false
        assertFalse(commandResult.equals(new CommandResult("different")));

        // different showHelp value -> returns false
        assertFalse(commandResult.equals(new CommandResult(
                "feedback", HelpInfo.DEFAULT, false, null, false, false, -1)));

        // different exit value -> returns false
        assertFalse(commandResult.equals(new CommandResult("feedback", null, true, null, false, false, -1)));
    }

    @Test
    public void hashcode() {
        CommandResult commandResult = new CommandResult("feedback");

        // same values -> returns same hashcode
        assertEquals(commandResult.hashCode(), new CommandResult("feedback").hashCode());

        // different feedbackToUser value -> returns different hashcode
        assertNotEquals(commandResult.hashCode(), new CommandResult("different").hashCode());

        // different showHelp value -> returns different hashcode
        assertNotEquals(commandResult.hashCode(),
                new CommandResult(
                        "feedback", HelpInfo.DEFAULT, false, null, false, false, -1).hashCode());

        // different exit value -> returns different hashcode
        assertNotEquals(commandResult.hashCode(),
                new CommandResult("feedback", null, true, null, false, false, -1).hashCode());
    }

    @Test
    public void toStringMethod() {
        CommandResult commandResult = new CommandResult("feedback");
        String expected = CommandResult.class.getCanonicalName() + "{feedbackToUser="
                + commandResult.getFeedbackToUser() + ", helpInfo=" + commandResult.getHelpInfo().orElse(null)
                + ", exit=" + commandResult.isExit() + ", contactToView=null"
                + ", hideViewPanel=false" + ", showFileList=false"
                + ", scrollToIndex=-1}";
        assertEquals(expected, commandResult.toString());
    }

    @Test
    public void getContactToView_noContact_returnsEmptyOptional() {
        CommandResult commandResult = new CommandResult("feedback");
        assertTrue(commandResult.getContactToView().isEmpty());
    }

    @Test
    public void getContactToView_withContact_returnsContact() {
        Contact contact = ALICE;
        CommandResult commandResult = new CommandResult("feedback", null, false, contact, false, false, -1);
        assertTrue(commandResult.getContactToView().isPresent());
        assertEquals(contact, commandResult.getContactToView().get());
    }

    @Test
    public void isShowContactDetail_noContact_returnsFalse() {
        CommandResult commandResult = new CommandResult("feedback");
        assertFalse(commandResult.isShowContactDetail());
    }

    @Test
    public void isShowContactDetail_withContact_returnsTrue() {
        Contact contact = ALICE;
        CommandResult commandResult = new CommandResult("feedback", null, false, contact, false, false, -1);
        assertTrue(commandResult.isShowContactDetail());
    }

    @Test
    public void equals_withContactToView() {
        Contact contact = ALICE;
        CommandResult commandResult = new CommandResult("feedback", null, false, contact, false, false, -1);

        // same values -> returns true
        assertTrue(commandResult.equals(new CommandResult("feedback", null, false, contact, false, false, -1)));

        // same object -> returns true
        assertTrue(commandResult.equals(commandResult));

        // null -> returns false
        assertFalse(commandResult.equals(null));

        // different types -> returns false
        assertFalse(commandResult.equals(0.5f));

        // different contactToView value -> returns false
        assertFalse(commandResult.equals(new CommandResult("feedback", null, false, null, false, false, -1)));
        assertFalse(commandResult.equals(new CommandResult("feedback")));
    }

    @Test
    public void hashcode_withContactToView() {
        Contact contact = ALICE;
        CommandResult commandResult = new CommandResult("feedback", null, false, contact, false, false, -1);

        // same values -> returns same hashcode
        assertEquals(commandResult.hashCode(),
                new CommandResult("feedback", null, false, contact, false, false, -1).hashCode());

        // different contactToView value -> returns different hashcode
        assertNotEquals(commandResult.hashCode(),
                new CommandResult("feedback", null, false, null, false, false, -1).hashCode());
    }

    @Test
    public void isHideContactDetail_defaultFalse() {
        CommandResult commandResult = new CommandResult("feedback");
        assertFalse(commandResult.isHideViewPanel());
    }

    @Test
    public void isHideContactDetail_setTrue() {
        CommandResult commandResult = new CommandResult("feedback", null, false, null, true, false, -1);
        assertTrue(commandResult.isHideViewPanel());
    }

    @Test
    public void equals_withHideContactDetail() {
        CommandResult commandResult = new CommandResult("feedback", null, false, null, true, false, -1);

        // same values -> returns true
        assertTrue(commandResult.equals(new CommandResult("feedback", null, false, null, true, false, -1)));

        // different hideContactDetail -> returns false
        assertFalse(commandResult.equals(new CommandResult("feedback", null, false, null, false, false, -1)));
    }

    @Test
    public void hashcode_withHideContactDetail() {
        CommandResult commandResult = new CommandResult("feedback", null, false, null, true, false, -1);

        // same values -> returns same hashcode
        assertEquals(commandResult.hashCode(),
                new CommandResult("feedback", null, false, null, true, false, -1).hashCode());

        // different hideContactDetail -> returns different hashcode
        assertNotEquals(commandResult.hashCode(),
                new CommandResult("feedback", null, false, null, false, false, -1).hashCode());
    }

    @Test
    public void constructor_withHelpInfo_setsFieldsCorrectly() {
        HelpInfo helpInfo = new HelpInfo("msg", "https://example.com");
        CommandResult result = new HelpCommandResult("feedback", helpInfo);

        assertEquals("feedback", result.getFeedbackToUser());
        assertTrue(result.isShowHelp());
        assertTrue(result.getHelpInfo().isPresent());
        assertEquals(helpInfo, result.getHelpInfo().get());
        assertFalse(result.isExit());
        assertFalse(result.isShowContactDetail());
        assertFalse(result.isHideViewPanel());
        assertFalse(result.isShowFileList());
    }

    @Test
    public void equals_withHelpInfo() {
        HelpInfo helpInfo = new HelpInfo("msg", "https://example.com");
        HelpInfo otherInfo = new HelpInfo("other", "https://other.com");
        CommandResult result = new HelpCommandResult("feedback", helpInfo);

        // same values -> true
        assertTrue(result.equals(new HelpCommandResult("feedback", helpInfo)));

        // different helpInfo -> false
        assertFalse(result.equals(new HelpCommandResult("feedback", otherInfo)));

        // no helpInfo vs helpInfo -> false
        assertFalse(result.equals(new CommandResult("feedback")));
    }
}
