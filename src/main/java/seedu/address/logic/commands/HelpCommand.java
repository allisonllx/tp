package seedu.address.logic.commands;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

import seedu.address.model.Model;

/**
 * Format full help instructions for every command for display.
 * Opens the help window with command-specific information and a link to the User Guide.
 */
public class HelpCommand extends Command {

    public static final String COMMAND_WORD = "help";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Shows program usage instructions.\n"
            + "To show help for a specific command: " + COMMAND_WORD + " COMMAND\n"
            + "Example: " + COMMAND_WORD + "\n"
            + "Example: " + COMMAND_WORD + " add";

    public static final String SHOWING_HELP_MESSAGE = "Opened help window.";

    public static final String MESSAGE_UNKNOWN_COMMAND = "Unknown command: %s\n"
            + "Available commands: %s";

    private static final String UG_COMMANDS_URL = HelpInfo.USER_GUIDE_BASE_URL + "commands/";

    private static final Map<String, HelpInfo> COMMAND_HELP_MAP = new LinkedHashMap<>();

    static {
        COMMAND_HELP_MAP.put(AddCommand.COMMAND_WORD,
                new HelpInfo(AddCommand.MESSAGE_USAGE, UG_COMMANDS_URL + "add-contact.html"));
        COMMAND_HELP_MAP.put(ClearCommand.COMMAND_WORD,
                new HelpInfo(ClearCommand.MESSAGE_USAGE, UG_COMMANDS_URL + "clear-contacts.html"));
        COMMAND_HELP_MAP.put(CloseViewCommand.COMMAND_WORD,
                new HelpInfo(CloseViewCommand.MESSAGE_USAGE,
                        UG_COMMANDS_URL + "close-view-contact.html"));
        COMMAND_HELP_MAP.put(DeleteCommand.COMMAND_WORD,
                new HelpInfo(DeleteCommand.MESSAGE_USAGE,
                        UG_COMMANDS_URL + "delete-contact.html"));
        COMMAND_HELP_MAP.put(EditCommand.COMMAND_WORD,
                new HelpInfo(EditCommand.MESSAGE_USAGE,
                        UG_COMMANDS_URL + "edit-contact.html"));
        COMMAND_HELP_MAP.put(ExitCommand.COMMAND_WORD,
                new HelpInfo("exit: Terminates the program.\nExample: exit",
                        UG_COMMANDS_URL + "exit-program.html"));
        COMMAND_HELP_MAP.put(FileCommand.COMMAND_WORD,
                new HelpInfo(FileCommand.MESSAGE_USAGE,
                        UG_COMMANDS_URL + "file.html"));
        COMMAND_HELP_MAP.put(FindCommand.COMMAND_WORD,
                new HelpInfo(FindCommand.MESSAGE_USAGE,
                        UG_COMMANDS_URL + "find-contacts.html"));
        COMMAND_HELP_MAP.put(COMMAND_WORD,
                new HelpInfo(MESSAGE_USAGE,
                        UG_COMMANDS_URL + "view-help.html"));
        COMMAND_HELP_MAP.put(ListCommand.COMMAND_WORD,
                new HelpInfo("list: Lists all contacts in the address book.\nExample: list",
                        UG_COMMANDS_URL + "list-contacts.html"));
        COMMAND_HELP_MAP.put(NoteCommand.COMMAND_WORD,
                new HelpInfo(NoteCommand.MESSAGE_USAGE,
                        UG_COMMANDS_URL + "notes.html"));
        COMMAND_HELP_MAP.put(RedoCommand.COMMAND_WORD,
                new HelpInfo(RedoCommand.MESSAGE_USAGE,
                        UG_COMMANDS_URL + "redo-command.html"));
        COMMAND_HELP_MAP.put(SortCommand.COMMAND_WORD,
                new HelpInfo(SortCommand.MESSAGE_USAGE,
                        UG_COMMANDS_URL + "sort-contacts.html"));
        COMMAND_HELP_MAP.put(ThemeCommand.COMMAND_WORD,
                new HelpInfo(ThemeCommand.MESSAGE_USAGE,
                        UG_COMMANDS_URL + "set-theme.html"));
        COMMAND_HELP_MAP.put(UndoCommand.COMMAND_WORD,
                new HelpInfo(UndoCommand.MESSAGE_USAGE,
                        UG_COMMANDS_URL + "undo-command.html"));
        COMMAND_HELP_MAP.put(ViewCommand.COMMAND_WORD,
                new HelpInfo(ViewCommand.MESSAGE_USAGE,
                        UG_COMMANDS_URL + "view-contact.html"));
    }

    private final String commandWord;

    /**
     * Creates a HelpCommand that opens the general help window.
     */
    public HelpCommand() {
        this.commandWord = null;
    }

    /**
     * Creates a HelpCommand that shows help for a specific command.
     */
    public HelpCommand(String commandWord) {
        this.commandWord = commandWord;
    }

    /**
     * Returns the available command words as a comma-separated string.
     */
    public static String getAvailableCommands() {
        return COMMAND_HELP_MAP.keySet().stream().collect(Collectors.joining(", "));
    }

    @Override
    public CommandResult execute(Model model) {
        if (commandWord == null) {
            return new HelpCommandResult(SHOWING_HELP_MESSAGE, HelpInfo.DEFAULT);
        }

        HelpInfo helpInfo = COMMAND_HELP_MAP.get(commandWord);
        if (helpInfo == null) {
            return new CommandResult(String.format(MESSAGE_UNKNOWN_COMMAND,
                    commandWord, getAvailableCommands()));
        }
        return new HelpCommandResult(SHOWING_HELP_MESSAGE, helpInfo);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof HelpCommand)) {
            return false;
        }
        HelpCommand otherCommand = (HelpCommand) other;
        if (commandWord == null) {
            return otherCommand.commandWord == null;
        }
        return commandWord.equals(otherCommand.commandWord);
    }
}
