package seedu.address.ui;

import java.util.logging.Logger;

import javafx.application.Platform;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.contact.Contact;

/**
 * Panel containing the list of contacts.
 */
public class ContactListPanel extends UiPart<Region> {
    private static final String FXML = "ContactListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(ContactListPanel.class);

    @FXML
    private ListView<Contact> contactListView;

    private final ObservableList<Contact> allContacts;

    /**
     * Creates a {@code ContactListPanel} with the given {@code ObservableList}.
     */
    public ContactListPanel(ObservableList<Contact> contactList, ObservableList<Contact> allContacts) {
        super(FXML);
        this.allContacts = allContacts;
        contactListView.setItems(contactList);
        contactListView.setCellFactory(listView -> new ContactListViewCell());

        // Refresh all visible cells when any contact changes (e.g. name edit)
        // so that note references (@{UUID}) resolve to the updated name.
        allContacts.addListener((ListChangeListener<Contact>) change -> {
            contactListView.refresh();
        });
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Contact} using a {@code ContactCard}.
     */
    class ContactListViewCell extends ListCell<Contact> {
        @Override
        protected void updateItem(Contact contact, boolean empty) {
            super.updateItem(contact, empty);

            if (empty || contact == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new ContactCard(contact, getIndex() + 1, allContacts).getRoot());
            }
        }
    }

    /**
     * Scrolls the list to the top.
     */
    public void scrollToTop() {
        Platform.runLater(() -> contactListView.scrollTo(0));
    }

    /**
     * Scrolls the list to the bottom.
     */
    public void scrollToBottom() {
        Platform.runLater(() -> contactListView.scrollTo(contactListView.getItems().size() - 1));
    }
}
