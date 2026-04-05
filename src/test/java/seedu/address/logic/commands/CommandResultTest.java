package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.TypicalContacts.ALICE;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_CONTACT;

import java.util.Optional;

import org.junit.jupiter.api.Test;

import seedu.address.model.contact.Contact;

public class CommandResultTest {
    @Test
    public void equals() {
        CommandResult commandResult = new CommandResult("feedback");

        // same values -> returns true
        assertTrue(commandResult.equals(new CommandResult("feedback")));
        assertTrue(commandResult.equals(
                new CommandResult("feedback", null, false, null, false, false, Optional.empty())));

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
                "feedback", HelpInfo.DEFAULT, false, null, false, false, Optional.empty())));

        // different exit value -> returns false
        assertFalse(commandResult.equals(
                new CommandResult("feedback", null, true, null, false, false, Optional.empty())));
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
                        "feedback", HelpInfo.DEFAULT, false, null, false, false, Optional.empty()).hashCode());

        // different exit value -> returns different hashcode
        assertNotEquals(commandResult.hashCode(),
                new CommandResult("feedback", null, true, null, false, false, Optional.empty()).hashCode());
    }

    @Test
    public void toStringMethod() {
        CommandResult commandResult = new CommandResult("feedback");
        String expected = CommandResult.class.getCanonicalName() + "{feedbackToUser="
                + commandResult.getFeedbackToUser() + ", helpInfo=" + commandResult.getHelpInfo().orElse(null)
                + ", exit=" + commandResult.isExit() + ", contactToView=null"
                + ", hideViewPanel=false" + ", showFileList=false"
                + ", scrollToIndex=null}";
        assertEquals(expected, commandResult.toString());
    }

    @Test
    public void toStringMethod_withScrollToIndex() {
        CommandResult commandResult =
                new CommandResult("feedback", null, false, null, false, false, Optional.of(INDEX_FIRST_CONTACT));
        String expected = CommandResult.class.getCanonicalName() + "{feedbackToUser="
                + commandResult.getFeedbackToUser() + ", helpInfo=" + commandResult.getHelpInfo().orElse(null)
                + ", exit=" + commandResult.isExit() + ", contactToView=null"
                + ", hideViewPanel=false" + ", showFileList=false"
                + ", scrollToIndex=" + INDEX_FIRST_CONTACT + "}";
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
        CommandResult commandResult =
                new CommandResult("feedback", null, false, contact, false, false, Optional.empty());
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
        CommandResult commandResult =
                new CommandResult("feedback", null, false, contact, false, false, Optional.empty());
        assertTrue(commandResult.isShowContactDetail());
    }

    @Test
    public void equals_withContactToView() {
        Contact contact = ALICE;
        CommandResult commandResult =
                new CommandResult("feedback", null, false, contact, false, false, Optional.empty());

        // same values -> returns true
        assertTrue(commandResult.equals(
                new CommandResult("feedback", null, false, contact, false, false, Optional.empty())));

        // same object -> returns true
        assertTrue(commandResult.equals(commandResult));

        // null -> returns false
        assertFalse(commandResult.equals(null));

        // different types -> returns false
        assertFalse(commandResult.equals(0.5f));

        // different contactToView value -> returns false
        assertFalse(commandResult.equals(
                new CommandResult("feedback", null, false, null, false, false, Optional.empty())));
        assertFalse(commandResult.equals(new CommandResult("feedback")));
    }

    @Test
    public void hashcode_withContactToView() {
        Contact contact = ALICE;
        CommandResult commandResult =
                new CommandResult("feedback", null, false, contact, false, false, Optional.empty());

        // same values -> returns same hashcode
        assertEquals(commandResult.hashCode(),
                new CommandResult("feedback", null, false, contact, false, false, Optional.empty()).hashCode());

        // different contactToView value -> returns different hashcode
        assertNotEquals(commandResult.hashCode(),
                new CommandResult("feedback", null, false, null, false, false, Optional.empty()).hashCode());
    }

    @Test
    public void isHideContactDetail_defaultFalse() {
        CommandResult commandResult = new CommandResult("feedback");
        assertFalse(commandResult.isHideViewPanel());
    }

    @Test
    public void isHideContactDetail_setTrue() {
        CommandResult commandResult =
                new CommandResult("feedback", null, false, null, true, false, Optional.empty());
        assertTrue(commandResult.isHideViewPanel());
    }

    @Test
    public void equals_withHideContactDetail() {
        CommandResult commandResult = new CommandResult("feedback", null, false, null, true, false, Optional.empty());

        // same values -> returns true
        assertTrue(commandResult.equals(
                new CommandResult("feedback", null, false, null, true, false, Optional.empty())));

        // different hideContactDetail -> returns false
        assertFalse(commandResult.equals(
                new CommandResult("feedback", null, false, null, false, false, Optional.empty())));
    }

    @Test
    public void hashcode_withHideContactDetail() {
        CommandResult commandResult = new CommandResult("feedback", null, false, null, true, false, Optional.empty());

        // same values -> returns same hashcode
        assertEquals(commandResult.hashCode(),
                new CommandResult("feedback", null, false, null, true, false, Optional.empty()).hashCode());

        // different hideContactDetail -> returns different hashcode
        assertNotEquals(commandResult.hashCode(),
                new CommandResult("feedback", null, false, null, false, false, Optional.empty()).hashCode());
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
