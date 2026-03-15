package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CLEAR;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ON;
import static seedu.address.logic.parser.CliSyntax.PREFIX_REMOVE;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.ReminderAddCommand;
import seedu.address.logic.commands.ReminderClearCommand;
import seedu.address.logic.commands.ReminderCommand;
import seedu.address.logic.commands.ReminderRemoveCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.contact.Reminder;

/**
 * Parses input arguments and creates a new {@code ReminderCommand} object
 */
public class ReminderCommandParser implements Parser<ReminderCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the {@code RemindersCommand}
     * and returns a {@code RemindersCommand} object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public ReminderCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_ON, PREFIX_REMOVE, PREFIX_CLEAR);

        boolean isOnPrefixPresent = argMultimap.getValue(PREFIX_ON).isPresent();
        boolean isRemovePrefixPresent = argMultimap.getValue(PREFIX_REMOVE).isPresent();
        boolean isClearPrefixPresent = argMultimap.getValue(PREFIX_CLEAR).isPresent();
        boolean isPreamblePresent = !argMultimap.getPreamble().isEmpty();

        if (!isPreamblePresent) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ReminderCommand.MESSAGE_USAGE));
        }

        if ((isOnPrefixPresent ? 1 : 0) + (isRemovePrefixPresent ? 1 : 0) + (isClearPrefixPresent ? 1 : 0) != 1) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ReminderCommand.MESSAGE_USAGE));
        }

        if (isRemovePrefixPresent) {
            return parseReminderRemoveCommand(argMultimap);
        }

        if (isClearPrefixPresent) {
            return parseReminderClearCommand(argMultimap);
        }

        return parseReminderAddCommand(argMultimap);
    }

    /**
     * Parses the given {@code ArgumentMultimap} in the context of the {@code ReminderRemoveCommand}
     * and returns a {@code ReminderRemoveCommand} object for execution.
     *
     * @param argMultimap The ArgumentMultimap containing an {@code Index} preamble
     *                    and a prefixed number of lines to remove.
     * @return A {@code ReminderRemoveCommand} object with the specified index and number of lines to remove.
     */
    private ReminderRemoveCommand parseReminderRemoveCommand(ArgumentMultimap argMultimap) throws ParseException {
        Index index = parseIndex(argMultimap.getPreamble());
        int numReminders = parseNumReminders(argMultimap.getValue(PREFIX_REMOVE).get());
        return new ReminderRemoveCommand(index, numReminders);
    }

    /**
     * Parses the given {@code ArgumentMultimap} in the context of the {@code ReminderClearCommand}
     * and returns a {@code ReminderClearCommand} object for execution.
     *
     * @param argMultimap The ArgumentMultimap containing an {@code Index} preamble.
     * @return A {@code ReminderClearCommand} object with the specified index.
     */
    private ReminderClearCommand parseReminderClearCommand(ArgumentMultimap argMultimap) throws ParseException {
        Index index = parseIndex(argMultimap.getPreamble());
        return new ReminderClearCommand(index);
    }

    /**
     * Parses the given {@code String} in the context of the {@code ReminderAddCommand}
     * and returns a {@code ReminderAddCommand} object for execution.
     *
     * @param argMultimap The ArgumentMultimap containing a preamble, which consists of an {@code Index} and may
     *                    contain a reminder note, and a {@code String} TimePoint.
     * @return A {@code ReminderAddCommand} object with the specified index and new Reminder.
     */
    private ReminderAddCommand parseReminderAddCommand(ArgumentMultimap argMultimap) throws ParseException {
        String[] reminderArgs = argMultimap.getPreamble().trim().split(" ", 2);

        Index index = parseIndex(reminderArgs[0]);

        String reminderNote = "";
        if (reminderArgs.length == 2) {
            reminderNote = reminderArgs[1];
        }

        Reminder reminder = new Reminder(
                reminderNote,
                TimePointParser.toTimePoint(argMultimap.getValue(PREFIX_ON).get()));

        return new ReminderAddCommand(index, reminder);
    }

    /**
     * Parses a {@code String} into an {@code Index}.
     *
     * @param index A {@code String} index.
     * @return A parsed {@code Index}.
     */
    private Index parseIndex(String index) throws ParseException {
        try {
            return ParserUtil.parseIndex(index);
        } catch (IllegalValueException ive) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, ReminderAddCommand.MESSAGE_USAGE), ive);
        }
    }

    /**
     * Parses a {@code String} into an {@code int} number of lines.
     *
     * @param numReminders A {@code String} number of lines.
     * @return A parsed {@code int} number of lines.
     */
    private int parseNumReminders(String numReminders) throws ParseException {
        try {
            return Integer.parseInt(numReminders);
        } catch (NumberFormatException nfe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, ReminderAddCommand.MESSAGE_USAGE), nfe);
        }
    }
}
