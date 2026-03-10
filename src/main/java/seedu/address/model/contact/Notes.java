package seedu.address.model.contact;

import static java.util.Objects.requireNonNull;

/**
 * Represents a Contact's notes in the address book.
 * Guarantees: immutable; is always valid
 */
public class Notes {
    public final String value;

    /**
     * Constructs a {@code Notes}.
     *
     * @param notes A valid notes.
     */
    public Notes(String notes) {
        requireNonNull(notes);
        value = notes;
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this || (other instanceof Notes && value.equals(((Notes) other).value));
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
