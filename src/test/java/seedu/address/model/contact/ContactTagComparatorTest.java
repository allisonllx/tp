package seedu.address.model.contact;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.ContactBuilder;

public class ContactTagComparatorTest {
    private static final Contact JOHN = new ContactBuilder()
        .withName("John Smith")
        .withPhone("94455028").build();
    private static final Contact JANE = new ContactBuilder()
        .withName("Jane Doe")
        .withPhone("94455029")
        .withTags("friends", "contractor").build();
    private static final Contact TOM = new ContactBuilder()
        .withName("Tom D Harry")
        .withPhone("94455030")
        .withTags("friends:1").build();
    private static final Contact MAX = new ContactBuilder()
        .withName("Maximus Primus Secundus")
        .withPhone("94455031")
        .withTags("friends:3").build();
    private static final ContactTagComparator ASC_COMPARATOR =
        new ContactTagComparator("friends", ContactComparator.Order.ASCENDING);
    private static final ContactTagComparator DESC_COMPARATOR =
        new ContactTagComparator("friends", ContactComparator.Order.DESCENDING);

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new ContactTagComparator(null, null));
    }

    @Test
    public void compare_equal_returnsZero() {
        assertEquals(0, ASC_COMPARATOR.compare(JOHN, JOHN));
        assertEquals(0, ASC_COMPARATOR.compare(JANE, JANE));
        assertEquals(0, ASC_COMPARATOR.compare(TOM, TOM));
        assertEquals(0, ASC_COMPARATOR.compare(MAX, MAX));
        assertEquals(0, DESC_COMPARATOR.compare(JOHN, JOHN));
        assertEquals(0, DESC_COMPARATOR.compare(JANE, JANE));
        assertEquals(0, DESC_COMPARATOR.compare(TOM, TOM));
        assertEquals(0, DESC_COMPARATOR.compare(MAX, MAX));
    }

    @Test
    public void compare_noTag_isCorrect() {
        // JOHN has no "friends" tag, so is ranked lower than everyone else regardless of order
        assertTrue(DESC_COMPARATOR.compare(JOHN, JANE) > 0);
        assertTrue(ASC_COMPARATOR.compare(JOHN, JANE) > 0);
        assertTrue(DESC_COMPARATOR.compare(JANE, JOHN) < 0);
        assertTrue(ASC_COMPARATOR.compare(JANE, JOHN) < 0);

        assertTrue(DESC_COMPARATOR.compare(JOHN, TOM) > 0);
        assertTrue(ASC_COMPARATOR.compare(JOHN, TOM) > 0);
        assertTrue(DESC_COMPARATOR.compare(TOM, JOHN) < 0);
        assertTrue(ASC_COMPARATOR.compare(TOM, JOHN) < 0);

        assertTrue(DESC_COMPARATOR.compare(JOHN, MAX) > 0);
        assertTrue(ASC_COMPARATOR.compare(JOHN, MAX) > 0);
        assertTrue(DESC_COMPARATOR.compare(MAX, JOHN) < 0);
        assertTrue(ASC_COMPARATOR.compare(MAX, JOHN) < 0);
    }

    @Test
    public void compare_noRank_isCorrect() {
        // JANE has "friends" tag but no rank, so is ranked lower than TOM and MAX regardless of order
        assertTrue(DESC_COMPARATOR.compare(JANE, TOM) > 0);
        assertTrue(ASC_COMPARATOR.compare(JANE, TOM) > 0);
        assertTrue(DESC_COMPARATOR.compare(TOM, JANE) < 0);
        assertTrue(ASC_COMPARATOR.compare(TOM, JANE) < 0);

        assertTrue(DESC_COMPARATOR.compare(JANE, MAX) > 0);
        assertTrue(ASC_COMPARATOR.compare(JANE, MAX) > 0);
        assertTrue(DESC_COMPARATOR.compare(MAX, JANE) < 0);
        assertTrue(ASC_COMPARATOR.compare(MAX, JANE) < 0);
    }

    @Test
    public void compare_lessRank_returnsPositive() {
        assertTrue(DESC_COMPARATOR.compare(TOM, MAX) > 0);
        assertTrue(ASC_COMPARATOR.compare(TOM, MAX) < 0);
        assertTrue(DESC_COMPARATOR.compare(MAX, TOM) < 0);
        assertTrue(ASC_COMPARATOR.compare(MAX, TOM) > 0);
    }

    @Test
    public void hashCode_sameTagAndOrder_returnsSameHashCode() {
        ContactTagComparator comparator1 = new ContactTagComparator("friends", ContactComparator.Order.ASCENDING);
        ContactTagComparator comparator2 = new ContactTagComparator("friends", ContactComparator.Order.ASCENDING);
        ContactTagComparator comparator3 = new ContactTagComparator("friends", ContactComparator.Order.DESCENDING);
        assertEquals(comparator1.hashCode(), comparator2.hashCode());
        assertNotEquals(comparator1.hashCode(), comparator3.hashCode());
    }

    @Test
    public void equals() {
        ContactTagComparator comparator1 = new ContactTagComparator("friends", ContactComparator.Order.ASCENDING);
        ContactTagComparator comparator2 = new ContactTagComparator("friends", ContactComparator.Order.ASCENDING);
        ContactTagComparator comparator3 = new ContactTagComparator("friends", ContactComparator.Order.DESCENDING);
        ContactTagComparator comparator4 = new ContactTagComparator("contractor", ContactComparator.Order.ASCENDING);

        // same object -> returns true
        assertEquals(comparator1, comparator1);

        // same values -> returns true
        assertEquals(comparator1, comparator2);

        // different order -> returns false
        assertNotEquals(comparator1, comparator3);

        // different tag -> returns false
        assertNotEquals(comparator1, comparator4);

        // different type -> returns false
        assertNotEquals(comparator1, 1);

        // null -> returns false
        assertNotEquals(comparator1, null);
    }
}
