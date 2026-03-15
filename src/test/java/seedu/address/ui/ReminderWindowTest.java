package seedu.address.ui;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.model.contact.Contact;
import seedu.address.model.contact.Reminder;
import seedu.address.model.contact.TimePoint;
import seedu.address.testutil.ContactBuilder;

public class ReminderWindowTest {
    private static final Contact REMINDERLESS_CONTACT = new ContactBuilder().build();
    private static final Reminder EXPIRED_REMINDER =
            new Reminder("expired", TimePoint.of(LocalDate.now().minusDays(1)));
    private static final Contact EXPIRED_CONTACT =
            new ContactBuilder().withReminders(EXPIRED_REMINDER).build();
    private static final Reminder TOMORROW_REMINDER =
            new Reminder("tomorrow", TimePoint.of(LocalDate.now().plusDays(1)));
    private static final Contact TOMORROW_CONTACT =
            new ContactBuilder().withReminders(TOMORROW_REMINDER).build();
    private static final Reminder NOT_DUE_REMINDER =
            new Reminder(
                    "still far away",
                    TimePoint.of(LocalDate.now().plusDays(ReminderWindow.DUE_PERIOD_DAYS + 10)));
    private static final Contact NOT_DUE_CONTACT =
            new ContactBuilder().withReminders(NOT_DUE_REMINDER).build();
    private static final Contact MIX_DUE_CONTACT1 =
            new ContactBuilder().withReminders(EXPIRED_REMINDER, TOMORROW_REMINDER).build();
    private static final Contact MIX_DUE_CONTACT2 =
            new ContactBuilder().withReminders(NOT_DUE_REMINDER, TOMORROW_REMINDER).build();
    private static final Contact MIX_NOT_DUE_CONTACT =
            new ContactBuilder().withReminders(EXPIRED_REMINDER, NOT_DUE_REMINDER).build();
    private static final Contact MIX_DUE_CONTACT3 =
            new ContactBuilder().withReminders(EXPIRED_REMINDER, TOMORROW_REMINDER, NOT_DUE_REMINDER).build();

    @Test
    public void singleContactTest() {
        assertFalse(ReminderWindow.hasDueReminders(List.of(REMINDERLESS_CONTACT)));
        assertFalse(ReminderWindow.hasDueReminders(List.of(EXPIRED_CONTACT)));
        assertTrue(ReminderWindow.hasDueReminders(List.of(TOMORROW_CONTACT)));
        assertFalse(ReminderWindow.hasDueReminders(List.of(NOT_DUE_CONTACT)));
        assertTrue(ReminderWindow.hasDueReminders(List.of(MIX_DUE_CONTACT1)));
        assertTrue(ReminderWindow.hasDueReminders(List.of(MIX_DUE_CONTACT2)));
        assertFalse(ReminderWindow.hasDueReminders(List.of(MIX_NOT_DUE_CONTACT)));
        assertTrue(ReminderWindow.hasDueReminders(List.of(MIX_DUE_CONTACT3)));
    }

    @Test
    public void multipleContactsTest() {
        assertFalse(ReminderWindow.hasDueReminders(List.of(REMINDERLESS_CONTACT, EXPIRED_CONTACT)));
        assertFalse(ReminderWindow.hasDueReminders(
                List.of(REMINDERLESS_CONTACT, EXPIRED_CONTACT, NOT_DUE_CONTACT, MIX_NOT_DUE_CONTACT)));
        assertTrue(ReminderWindow.hasDueReminders(List.of(NOT_DUE_CONTACT, TOMORROW_CONTACT)));
        assertTrue(ReminderWindow.hasDueReminders(List.of(MIX_DUE_CONTACT1, MIX_DUE_CONTACT2)));
        assertTrue(ReminderWindow.hasDueReminders(List.of(MIX_NOT_DUE_CONTACT, MIX_DUE_CONTACT3, TOMORROW_CONTACT)));
    }
}
