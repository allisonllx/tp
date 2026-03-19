package seedu.address.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.tag.RankedTag;
import seedu.address.model.tag.Tag;

/**
 * Jackson-friendly version of {@link Tag}.
 */
class JsonAdaptedTag {

    private final String name;
    private final String rank;

    /**
     * Constructs a {@code JsonAdaptedTag} with the given {@code name} and {@code rank}.
     */
    @JsonCreator
    public JsonAdaptedTag(String[] tagParams) {
        if (tagParams.length == 0) {
            throw new IllegalArgumentException("Tag parameters cannot be empty");
        }

        if (tagParams.length > 2) {
            throw new IllegalArgumentException("Cannot have more than 2 tag parameters");
        }

        this.name = tagParams[0];

        if (tagParams.length == 2) {
            this.rank = tagParams[1];
        } else {
            this.rank = null;
        }
    }

    /**
     * Converts a given {@code Tag} into this class for Jackson use.
     */
    public JsonAdaptedTag(Tag source) {
        name = source.name;

        if (source instanceof RankedTag) {
            rank = ((RankedTag) source).rank;
        } else {
            rank = null;
        }
    }

    @JsonValue
    public String[] getTagParams() {
        return rank != null ? new String[] { name, rank } : new String[] { name };
    }

    /**
     * Converts this Jackson-friendly adapted tag object into the model's {@code Tag} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted tag.
     */
    public Tag toModelType() throws IllegalValueException {
        if (!Tag.isValidTagName(name)) {
            throw new IllegalValueException(Tag.MESSAGE_CONSTRAINTS);
        }

        boolean isRankedTag = rank != null;

        if (!isRankedTag) {
            return new Tag(name);
        }

        if (!RankedTag.isValidTagValue(rank)) {
            throw new IllegalValueException(RankedTag.MESSAGE_CONSTRAINTS);
        }

        return new RankedTag(name, rank);
    }

}
