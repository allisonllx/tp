package seedu.address.ui;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.testutil.Assert.assertThrows;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.DataLoadingException;

public class FileListPanelTest extends GuiUnitTest {
    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "JsonSerializableAddressBookTest");
    private static final Path TYPICAL_CONTACTS_FILE = TEST_DATA_FOLDER.resolve("typicalContactsAddressBook.json");
    private static final Path INVALID_CONTACT_FILE = TEST_DATA_FOLDER.resolve("invalidContactAddressBook.json");

    @Test
    public void getFileCardDetailsTest_success() throws Exception {
        File file = TYPICAL_CONTACTS_FILE.toFile();
        FileCardDetails details = FileListPanel.getFileCardDetails(file);
        assertEquals("typicalContactsAddressBook.json", details.name());
        assertEquals(FileListPanel.epochToTime(file.lastModified()), details.lastModifiedTime());
        assertEquals(7, details.contactCount());
    }

    @Test
    public void getInvalidFileCardDetails() {
        File file = INVALID_CONTACT_FILE.toFile();
        assertThrows(DataLoadingException.class, () -> FileListPanel.getFileCardDetails(file));
    }

    @Test
    public void constructTest() throws Exception {
        runAndWait(() -> {
            assertDoesNotThrow(() -> new FileListPanel(TEST_DATA_FOLDER));
        });
    }
}
