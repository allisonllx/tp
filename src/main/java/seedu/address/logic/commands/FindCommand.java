package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.contact.Contact;
import seedu.address.model.tag.Tag;

/**
 * Finds and lists contacts whose fields match the given keywords.
 * Keyword matching is case-insensitive.
 * Also supports {@code find @INDEX} to cross-reference a contact's tags,
 * showing all other contacts that share at least one tag.
 */
public class FindCommand extends Command {

    public static final String COMMAND_WORD = "find";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all contacts whose fields match "
            + "the specified keywords (case-insensitive) and displays them as a list with index numbers.\n"
            + "Parameters: [" + PREFIX_NAME + "NAME_KEYWORDS] "
            + "[" + PREFIX_PHONE + "PHONE_KEYWORDS] "
            + "[" + PREFIX_EMAIL + "EMAIL_KEYWORDS] "
            + "[" + PREFIX_ADDRESS + "ADDRESS_KEYWORDS] "
            + "[" + PREFIX_TAG + "TAG]...\n"
            + "Or: " + COMMAND_WORD + " @INDEX to find contacts associated with the contact at INDEX\n"
            + "Examples: " + COMMAND_WORD + " "
            + PREFIX_NAME + "John Doe "
            + PREFIX_EMAIL + "john@example.com "
            + PREFIX_TAG + "friend\n"
            + COMMAND_WORD + " @1";

    public static final String MESSAGE_CROSSREF_SUCCESS =
            "Cross-referencing %1$s (tags: %2$s)\n%3$d associated contact(s) found!";
    public static final String MESSAGE_NO_TAGS =
            "Contact %1$s has no tags. Add tags to enable cross-referencing.";
    public static final String MESSAGE_NO_RELATED_CONTACTS =
            "No other contacts share tags with %1$s (tags: %2$s).";

    private final Optional<Predicate<Contact>> predicate;
    private final Optional<Index> associateIndex;

    /**
     * Creates a FindCommand that filters contacts by the given predicate.
     */
    public FindCommand(Predicate<Contact> predicate) {
        this.predicate = Optional.of(predicate);
        this.associateIndex = Optional.empty();
    }

    /**
     * Creates a FindCommand that cross-references the contact at the given index.
     */
    public FindCommand(Index associateIndex) {
        this.predicate = Optional.empty();
        this.associateIndex = Optional.of(associateIndex);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (associateIndex.isPresent()) {
            return executeCrossRef(model);
        }

        model.resetDisplayedContactList();
        model.filterDisplayedContactList(predicate.get());

        String feedback =
                String.format(Messages.MESSAGE_CONTACTS_LISTED_OVERVIEW, model.getDisplayedContactList().size());
        model.saveSnapshot(feedback);
        return new CommandResult(feedback);
    }

    /**
     * Executes cross-reference mode: finds all contacts sharing at least one tag
     * with the contact at the specified index.
     */
    private CommandResult executeCrossRef(Model model) throws CommandException {
        List<Contact> lastShownList = model.getDisplayedContactList();
        Index index = associateIndex.get();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_CONTACT_DISPLAYED_INDEX);
        }

        Contact targetContact = lastShownList.get(index.getZeroBased());
        Set<Tag> targetTags = targetContact.getTags();

        if (targetTags.isEmpty()) {
            return new CommandResult(
                    String.format(MESSAGE_NO_TAGS, targetContact.getName()));
        }

        String tagNames = targetTags.stream()
                .map(tag -> tag.name)
                .collect(Collectors.joining(", "));

        model.resetDisplayedContactList();
        model.filterDisplayedContactList(contact ->
                !contact.isSameContact(targetContact)
                && contact.getTags().stream().anyMatch(targetTags::contains));

        int matchCount = model.getDisplayedContactList().size();

        if (matchCount == 0) {
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

        // instanceof handles nulls
        if (!(other instanceof FindCommand)) {
            return false;
        }

        FindCommand otherFindCommand = (FindCommand) other;
        return predicate.equals(otherFindCommand.predicate)
                && associateIndex.equals(otherFindCommand.associateIndex);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("predicate", predicate)
                .add("associateIndex", associateIndex)
                .toString();
    }
}
