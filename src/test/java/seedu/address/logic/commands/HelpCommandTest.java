package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.HelpCommand.SHOWING_HELP_MESSAGE;

import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;

public class HelpCommandTest {
    private Model model = new ModelManager();

    @Test
    public void execute_noArgs_showsHelpWindow() {
        HelpCommand helpCommand = new HelpCommand();
        CommandResult result = helpCommand.execute(model);
        assertTrue(result.isShowHelp());
        assertEquals(SHOWING_HELP_MESSAGE, result.getFeedbackToUser());
        assertTrue(result.getHelpInfo().isPresent());
        assertEquals(HelpInfo.DEFAULT, result.getHelpInfo().get());
    }

    @Test
    public void execute_validCommand_showsHelpWindowWithCommandInfo() {
        HelpCommand helpAdd = new HelpCommand("add");
        CommandResult result = helpAdd.execute(model);
        assertTrue(result.isShowHelp());
        assertTrue(result.getHelpInfo().isPresent());
        assertEquals(AddCommand.MESSAGE_USAGE, result.getHelpInfo().get().getMessage());
        assertTrue(result.getHelpInfo().get().getUrl().contains("add-contact.html"));
    }

    @Test
    public void execute_helpForHelp_showsHelpWindowWithHelpInfo() {
        HelpCommand helpHelp = new HelpCommand("help");
        CommandResult result = helpHelp.execute(model);
        assertTrue(result.isShowHelp());
        assertTrue(result.getHelpInfo().isPresent());
        assertEquals(HelpCommand.MESSAGE_USAGE, result.getHelpInfo().get().getMessage());
        assertTrue(result.getHelpInfo().get().getUrl().contains("view-help.html"));
    }

    @Test
    public void execute_unknownCommand_showsError() {
        HelpCommand helpUnknown = new HelpCommand("foobar");
        CommandResult result = helpUnknown.execute(model);
        assertFalse(result.isShowHelp());
        assertTrue(result.getFeedbackToUser().contains("Unknown command: foobar"));
        assertTrue(result.getFeedbackToUser().contains("Available commands:"));
    }

    @Test
    public void execute_fileCommand_showsHelpWindow() {
        HelpCommand helpFile = new HelpCommand("file");
        CommandResult result = helpFile.execute(model);
        assertTrue(result.isShowHelp());
        assertTrue(result.getHelpInfo().isPresent());
        assertTrue(result.getHelpInfo().get().getUrl().contains("file.html"));
    }

    @Test
    public void execute_themeCommand_showsHelpWindow() {
        HelpCommand helpTheme = new HelpCommand("theme");
        CommandResult result = helpTheme.execute(model);
        assertTrue(result.isShowHelp());
        assertTrue(result.getHelpInfo().isPresent());
        assertTrue(result.getHelpInfo().get().getUrl().contains("set-theme.html"));
    }

    @Test
    public void getAvailableCommands_containsAllCommands() {
        String available = HelpCommand.getAvailableCommands();
        assertTrue(available.contains("add"));
        assertTrue(available.contains("delete"));
        assertTrue(available.contains("file"));
        assertTrue(available.contains("theme"));
        assertTrue(available.contains("help"));
    }

    @Test
    public void equals() {
        HelpCommand helpNoArgs = new HelpCommand();
        HelpCommand helpAdd = new HelpCommand("add");
        HelpCommand helpAddCopy = new HelpCommand("add");
        HelpCommand helpDelete = new HelpCommand("delete");

        // same object
        assertTrue(helpNoArgs.equals(helpNoArgs));
        assertTrue(helpAdd.equals(helpAdd));

        // same values
        assertTrue(helpAdd.equals(helpAddCopy));

        // different values
        assertFalse(helpAdd.equals(helpDelete));
        assertFalse(helpNoArgs.equals(helpAdd));

        // null
        assertFalse(helpNoArgs.equals(null));

        // different type
        assertFalse(helpNoArgs.equals(1));
    }
}
