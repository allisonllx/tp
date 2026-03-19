package seedu.address.model.contact;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.TypicalContacts.ALICE;
import static seedu.address.testutil.TypicalContacts.BENSON;

import org.junit.jupiter.api.Test;

import seedu.address.model.contact.ContactComparator.Field;
import seedu.address.model.contact.ContactComparator.Order;

public class ContactComparatorSetTest {
    @Test
    public void compare_basicComparators_returnsNegative() {
        ContactComparatorSet comparator = new ContactComparatorSet();

        for (Field field : Field.values()) {
            comparator.addComparator(new ContactComparator(field, Order.ASCENDING));
        }

        assertTrue(comparator.compare(ALICE, BENSON) < 0);
    }
}
