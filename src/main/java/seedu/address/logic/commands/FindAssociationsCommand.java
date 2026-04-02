package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.stream.Collectors;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.contact.Contact;
import seedu.address.model.contact.Note;

/**
 * Finds contacts associated with a given contact by examining its note references.
 * Shows the target contact and all contacts referenced in its notes via {@code @{UUID}}.
 */
public class FindAssociationsCommand extends FindCommand {

    public static final String MESSAGE_CROSSREF_SUCCESS =
            "Cross-referencing %1$s\n%2$d associated contact(s) found!";
    public static final String MESSAGE_NO_RELATED_CONTACTS =
            "No contact references found in notes for %1$s.";

    private final Index associateIndex;

    /**
     * Creates a FindAssociationsCommand that cross-references the contact at the given index.
     */
    public FindAssociationsCommand(Index associateIndex) {
        this.associateIndex = associateIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Contact> lastShownList = model.getDisplayedContactList();

        if (associateIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_CONTACT_DISPLAYED_INDEX);
        }

        Contact targetContact = lastShownList.get(associateIndex.getZeroBased());
        UUID targetId = targetContact.getId();

        // Forward: collect UUIDs referenced in the target's notes
        Set<UUID> referencedIds = getReferencedUuids(targetContact.getNotes());

        // Reverse: collect IDs of contacts whose notes reference the target
        Set<UUID> referencingIds = model.getAddressBook().getContactList().stream()
                .filter(contact -> !contact.isSameContact(targetContact))
                .filter(contact -> getReferencedUuids(contact.getNotes()).contains(targetId))
                .map(Contact::getId)
                .collect(Collectors.toSet());

        if (referencedIds.isEmpty() && referencingIds.isEmpty()) {
            return new CommandResult(
                    String.format(MESSAGE_NO_RELATED_CONTACTS, targetContact.getName()));
        }

        model.filterDisplayedContactList(contact ->
                contact.isSameContact(targetContact)
                || referencedIds.contains(contact.getId())
                || referencingIds.contains(contact.getId()));

        int matchCount = model.getDisplayedContactList().size() - 1;

        String feedback = String.format(MESSAGE_CROSSREF_SUCCESS,
                targetContact.getName(), matchCount);
        model.saveSnapshot(feedback);
        return new CommandResult(feedback);
    }

    /**
     * Extracts all {@code @{UUID}} references from the given list of notes.
     */
    private static Set<UUID> getReferencedUuids(List<Note> notes) {
        Set<UUID> uuids = new HashSet<>();
        for (Note note : notes) {
            Matcher matcher = Note.CONTACT_REF_PATTERN.matcher(note.value);
            while (matcher.find()) {
                uuids.add(UUID.fromString(matcher.group(1)));
            }
        }
        return uuids;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof FindAssociationsCommand)) {
            return false;
        }
        FindAssociationsCommand otherCommand = (FindAssociationsCommand) other;
        return associateIndex.equals(otherCommand.associateIndex);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("associateIndex", associateIndex)
                .toString();
    }
}
