package seedu.address.model.contact;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

public class ReminderTest {
    @Test
    public void equals() {
        Reminder firstReminder = new Reminder("Christmas meeting", TimePoint.of(LocalDate.of(2026, 12, 20)));

        // same values -> returns true
        assertTrue(firstReminder.equals(new Reminder("Christmas meeting", TimePoint.of(LocalDate.of(2026, 12, 20)))));

        // same object -> returns true
        assertTrue(firstReminder.equals(firstReminder));

        // null -> returns false
        assertFalse(firstReminder.equals(null));

        // different types -> returns false
        assertFalse(firstReminder.equals(5.0f));

        // different note -> returns false
        assertFalse(firstReminder.equals(new Reminder("General meeting", TimePoint.of(LocalDate.of(2026, 12, 20)))));

        // different timePoint -> returns false
        assertFalse(firstReminder.equals(new Reminder("Christmas meeting", TimePoint.of(LocalDate.of(2026, 12, 24)))));

        // different values -> returns false
        assertFalse(firstReminder.equals(new Reminder("General meeting", TimePoint.of(LocalDate.of(2026, 11, 19)))));
    }
}
