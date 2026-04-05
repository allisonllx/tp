package seedu.address.ui;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static seedu.address.testutil.TypicalContacts.ALICE;
import static seedu.address.testutil.TypicalContacts.BENSON;
import static seedu.address.testutil.TypicalContacts.CARL;

import org.junit.jupiter.api.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.commons.core.index.Index;
import seedu.address.model.contact.Contact;

/**
 * Contains tests for {@code ContactListPanel}.
 */
public class ContactListPanelTest extends GuiUnitTest {
    private ObservableList<Contact> contacts = FXCollections.observableArrayList(ALICE, BENSON, CARL);
    private ContactListPanel contactListPanel = new ContactListPanel(contacts, contacts);
    private ContactListPanel emptyContactListPanel =
            new ContactListPanel(FXCollections.observableArrayList(),
                    FXCollections.observableArrayList());

    @Test
    public void scrollToIndexTest() throws Exception {
        runAndWait(() -> {
            assertDoesNotThrow(() -> contactListPanel.scrollToIndex(Index.fromZeroBased(0)));
        });
    }

    @Test
    public void emptyListScrollToIndexTest() throws Exception {
        runAndWait(() -> {
            assertDoesNotThrow(() -> emptyContactListPanel.scrollToIndex(Index.fromZeroBased(0)));
        });
    }
}
