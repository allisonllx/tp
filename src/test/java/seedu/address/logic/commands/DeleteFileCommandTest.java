package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.testutil.TypicalContacts.getTypicalAddressBook;

import java.nio.file.Paths;

import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

public class DeleteFileCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void deleteNonExistentFile_throwsException() {
        DeleteCommand deleteCommand = new DeleteFileCommand(Paths.get("dummy.txt"));

        assertCommandFailure(deleteCommand, model, DeleteFileCommand.MESSAGE_FAILURE_FILE_NOT_FOUND);
    }

    @Test
    public void deleteCurrentFile_throwsException() {
        DeleteCommand deleteCommand = new DeleteFileCommand(model.getAddressBookFilePath());

        assertCommandFailure(deleteCommand, model, DeleteFileCommand.MESSAGE_FAILURE_IS_CURRENT_FILE);
    }
}
