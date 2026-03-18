package seedu.address.model.tag;

import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class RankedTagTest {

    private static final String INVALID_TAG_NAME = "#";
    private static final String INVALID_TAG_VALUE = "#";
    private static final String VALID_TAG_NAME = "friend";
    private static final String VALID_TAG_VALUE = "best";

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new RankedTag(null, null));
        assertThrows(NullPointerException.class, () -> new RankedTag(VALID_TAG_NAME, null));
        assertThrows(NullPointerException.class, () -> new RankedTag(null, VALID_TAG_VALUE));
    }

    @Test
    public void constructor_invalidTagName_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> new RankedTag(INVALID_TAG_NAME, VALID_TAG_VALUE));
    }

    @Test
    public void constructor_invalidTagValue_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> new RankedTag(VALID_TAG_NAME, INVALID_TAG_VALUE));
    }

    @Test
    public void isValidTagName_null_throwsNullPointerException() {
        // null tag name
        assertThrows(NullPointerException.class, () -> RankedTag.isValidTagName(null));
    }

    @Test
    public void isValidTagValue_null_throwsNullPointerException() {
        // null tag name
        assertThrows(NullPointerException.class, () -> RankedTag.isValidTagValue(null));
    }

}
