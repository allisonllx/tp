package seedu.address.model.contact;

import static java.util.Objects.requireNonNull;

/**
 * Represents a Contact's notes in the address book.
 * Guarantees: immutable; is always valid
 */
public class Note {
    public final String value;

    /**
     * Constructs a {@code Note}.
     *
     * @param note A valid note.
     */
    public Note(String note) {
        requireNonNull(note);
        value = note;
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this || (other instanceof Note && value.equals(((Note) other).value));
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
