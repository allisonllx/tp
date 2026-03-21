package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.contact.Contact;
import seedu.address.model.tag.Tag;

/**
 * Cross-references a contact's tags against all other contacts in the address book,
 * showing contacts that share at least one tag with the selected contact.
 * This helps consultants understand vendor-client relationships quickly.
 */
public class CrossRefCommand extends Command {

    public static final String COMMAND_WORD = "crossref";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Cross-references the contact identified by the index number, "
            + "showing all other contacts that share at least one tag.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_CROSSREF_SUCCESS =
            "Cross-referencing %1$s (tags: %2$s)\n%3$d related contact(s) found!";
    public static final String MESSAGE_NO_TAGS =
            "Contact %1$s has no tags. Add tags to enable cross-referencing.";
    public static final String MESSAGE_NO_RELATED_CONTACTS =
            "No other contacts share tags with %1$s (tags: %2$s).";

    private final Index targetIndex;

    public CrossRefCommand(Index targetIndex) {
        requireNonNull(targetIndex);
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Contact> lastShownList = model.getDisplayedContactList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_CONTACT_DISPLAYED_INDEX);
        }

        Contact targetContact = lastShownList.get(targetIndex.getZeroBased());
        Set<Tag> targetTags = targetContact.getTags();

        if (targetTags.isEmpty()) {
            return new CommandResult(
                    String.format(MESSAGE_NO_TAGS, targetContact.getName()));
        }

        String tagNames = targetTags.stream()
                .map(tag -> tag.name)
                .collect(Collectors.joining(", "));

        // Filter to show contacts that share at least one tag with the target, excluding the target itself
        model.resetDisplayedContactList();
        model.filterDisplayedContactList(contact ->
                !contact.isSameContact(targetContact)
                && contact.getTags().stream().anyMatch(targetTags::contains));

        int matchCount = model.getDisplayedContactList().size();

        if (matchCount == 0) {
            // Reset to show all contacts since no matches
            model.resetDisplayedContactList();
            return new CommandResult(
                    String.format(MESSAGE_NO_RELATED_CONTACTS, targetContact.getName(), tagNames));
        }

        String feedback = String.format(MESSAGE_CROSSREF_SUCCESS,
                targetContact.getName(), tagNames, matchCount);
        model.saveSnapshot(feedback);
        return new CommandResult(feedback);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof CrossRefCommand)) {
            return false;
        }

        CrossRefCommand otherCommand = (CrossRefCommand) other;
        return targetIndex.equals(otherCommand.targetIndex);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("targetIndex", targetIndex)
                .toString();
    }
}
