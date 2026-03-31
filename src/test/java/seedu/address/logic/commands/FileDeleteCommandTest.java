package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalContacts.getTypicalAddressBook;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

public class FileDeleteCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void deleteNonExistentFile_throwsException() {
        FileDeleteCommand fileDeleteCommand = new FileDeleteCommand(Paths.get("dummy.txt"));

        assertCommandFailure(fileDeleteCommand, model, FileDeleteCommand.MESSAGE_FAILURE_FILE_NOT_FOUND);
    }

    @Test
    public void deleteCurrentFile_throwsException() {
        FileDeleteCommand fileDeleteCommand = new FileDeleteCommand(model.getAddressBookFilePath());

        assertCommandFailure(fileDeleteCommand, model, FileDeleteCommand.MESSAGE_FAILURE_IS_CURRENT_FILE);
    }

    @Test
    public void deleteValidEmptyFile_success() throws IOException {
        Path emptyFilePath =
                Paths.get("src", "test", "data", "JsonSerializableAddressBookTest", "emptyContactAddressBook.json");
        Path filePath =
                Paths.get("src", "test", "data",
                        "JsonSerializableAddressBookTest", "emptyContactAddressBookDuplicate.json");
        Files.copy(emptyFilePath, filePath);
        FileDeleteCommand fileDeleteCommand = new FileDeleteCommand(filePath);
        Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());

        assertCommandSuccess(
                fileDeleteCommand, expectedModel,
                String.format(FileDeleteCommand.MESSAGE_SUCCESS, filePath.getFileName()),
                model);
    }

    @Test
    public void equals() {
        FileDeleteCommand fileDeleteCommand = new FileDeleteCommand(Paths.get("dummy.txt"));

        // same object -> returns true
        assertTrue(fileDeleteCommand.equals(fileDeleteCommand));

        // null -> returns false
        assertFalse(fileDeleteCommand.equals(null));

        // different types -> returns false
        assertFalse(fileDeleteCommand.equals(new ClearCommand()));

        // same path -> returns true
        assertTrue(fileDeleteCommand.equals(new FileDeleteCommand(Paths.get("dummy.txt"))));

        // different path -> returns false
        assertFalse(fileDeleteCommand.equals(new FileDeleteCommand(Paths.get("temp.txt"))));
    }
}
