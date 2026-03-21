package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.testutil.TypicalContacts.getTypicalAddressBook;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_CONTACT;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_CONTACT;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.contact.Contact;
import seedu.address.testutil.ContactBuilder;

/**
 * Contains integration tests (interaction with the Model) and unit tests for
 * {@code CrossRefCommand}.
 */
public class CrossRefCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_validIndexWithSharedTags_success() throws Exception {
        // ALICE has tag "friends:best", BENSON has tags "owesMoney", "friends", DANIEL has tag "friends"
        // CrossRef on BENSON (index 2) should find ALICE and DANIEL who share "friends" tag
        Contact targetContact = model.getDisplayedContactList().get(INDEX_SECOND_CONTACT.getZeroBased());
        CrossRefCommand command = new CrossRefCommand(INDEX_SECOND_CONTACT);

        CommandResult result = command.execute(model);

        // Should find contacts sharing tags with BENSON
        assertTrue(model.getDisplayedContactList().size() > 0);
        assertTrue(result.getFeedbackToUser().contains("Cross-referencing"));
        assertTrue(result.getFeedbackToUser().contains(targetContact.getName().toString()));
    }

    @Test
    public void execute_validIndexNoTags_returnsNoTagsMessage() throws Exception {
        // Create a model with a contact that has no tags
        AddressBook ab = new AddressBook();
        Contact noTagContact = new ContactBuilder().withName("No Tag Person")
                .withPhone("91234567").withEmail("notag@example.com").build();
        ab.addContact(noTagContact);
        Model testModel = new ModelManager(ab, new UserPrefs());

        CrossRefCommand command = new CrossRefCommand(INDEX_FIRST_CONTACT);
        CommandResult result = command.execute(testModel);

        assertTrue(result.getFeedbackToUser().contains("has no tags"));
    }

    @Test
    public void execute_validIndexNoSharedTags_returnsNoRelatedMessage() throws Exception {
        // Create model with two contacts that have different tags
        AddressBook ab = new AddressBook();
        Contact contact1 = new ContactBuilder().withName("Person A")
                .withPhone("91234567").withTags("tagA").build();
        Contact contact2 = new ContactBuilder().withName("Person B")
                .withPhone("91234568").withTags("tagB").build();
        ab.addContact(contact1);
        ab.addContact(contact2);
        Model testModel = new ModelManager(ab, new UserPrefs());

        CrossRefCommand command = new CrossRefCommand(INDEX_FIRST_CONTACT);
        CommandResult result = command.execute(testModel);

        assertTrue(result.getFeedbackToUser().contains("No other contacts share tags"));
    }

    @Test
    public void execute_invalidIndex_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getDisplayedContactList().size() + 1);
        CrossRefCommand command = new CrossRefCommand(outOfBoundIndex);

        assertCommandFailure(command, model, Messages.MESSAGE_INVALID_CONTACT_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        CrossRefCommand crossRefFirstCommand = new CrossRefCommand(INDEX_FIRST_CONTACT);
        CrossRefCommand crossRefSecondCommand = new CrossRefCommand(INDEX_SECOND_CONTACT);

        // same object -> returns true
        assertTrue(crossRefFirstCommand.equals(crossRefFirstCommand));

        // same values -> returns true
        CrossRefCommand crossRefFirstCommandCopy = new CrossRefCommand(INDEX_FIRST_CONTACT);
        assertTrue(crossRefFirstCommand.equals(crossRefFirstCommandCopy));

        // different types -> returns false
        assertFalse(crossRefFirstCommand.equals(1));

        // null -> returns false
        assertFalse(crossRefFirstCommand.equals(null));

        // different index -> returns false
        assertFalse(crossRefFirstCommand.equals(crossRefSecondCommand));
    }

    @Test
    public void toStringMethod() {
        Index targetIndex = Index.fromOneBased(1);
        CrossRefCommand command = new CrossRefCommand(targetIndex);
        String expected = CrossRefCommand.class.getCanonicalName() + "{targetIndex=" + targetIndex + "}";
        assertEquals(expected, command.toString());
    }
}
