package seedu.address.model.contact;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.timepoint.TimePoint;
import seedu.address.testutil.TypicalContacts;

public class NoteTest {
    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Note(null));
    }

    @Test
    public void isReminderTest() {
        assertFalse(new Note("Reminder note").isReminder());
        assertTrue(new Note("Reminder note", TimePoint.of("timeString")).isReminder());
        assertTrue(new Note("Reminder note", TimePoint.of(LocalDate.of(2025, 10, 25))).isReminder());
        assertTrue(new Note("Reminder note", TimePoint.of(LocalDateTime.of(2025, 10, 25, 15, 35))).isReminder());
    }

    @Test
    public void hasDueReminderTest() {
        int dueDayThresholdAfter = Note.DUE_PERIOD_DAYS - 1;
        int dueDayThresholdBefore = Note.DUE_PERIOD_DAYS + 1;
        assertFalse(new Note("Reminder note").hasDueReminder());
        assertFalse(new Note("Reminder note", TimePoint.of("timeString")).hasDueReminder());
        assertTrue(
                new Note(
                        "Reminder note",
                        TimePoint.of(LocalDate.now().plusDays(dueDayThresholdAfter))).hasDueReminder());
        assertFalse(
                new Note(
                        "Reminder note",
                        TimePoint.of(LocalDate.now().plusDays(dueDayThresholdBefore))).hasDueReminder());
        assertTrue(
                new Note(
                        "Reminder note",
                        TimePoint.of(LocalDateTime.now().plusDays(dueDayThresholdAfter))).hasDueReminder());
        assertFalse(
                new Note(
                        "Reminder note",
                        TimePoint.of(LocalDateTime.now().plusDays(dueDayThresholdBefore))).hasDueReminder());
    }

    @Test
    public void jsonTest() {
        assertEquals(new Note("sample"), Note.fromJsonString(new Note("sample").toJsonString()));
        assertEquals(
                new Note("reminder", TimePoint.of("time")),
                Note.fromJsonString(new Note("reminder", TimePoint.of("time")).toJsonString()));
        assertEquals(
                new Note("reminder", TimePoint.of(LocalDate.of(2026, 7, 7))),
                Note.fromJsonString(new Note("reminder", TimePoint.of(LocalDate.of(2026, 7, 7))).toJsonString()));
        assertEquals(
                new Note("reminder", TimePoint.of(LocalDateTime.of(2026, 7, 7, 13, 30))),
                Note.fromJsonString(
                        new Note("reminder", TimePoint.of(LocalDateTime.of(2026, 7, 7, 13, 30))).toJsonString()));
    }

    @Test
    public void equals() {
        Note notes = new Note("To meet on February");

        // same values -> returns true
        assertTrue(notes.equals(new Note("To meet on February")));

        // same object -> returns true
        assertTrue(notes.equals(notes));

        // null -> returns false
        assertFalse(notes.equals(null));

        // different types -> returns false
        assertFalse(notes.equals(5.0f));

        // different values -> returns false
        assertFalse(notes.equals(new Note("Likes ice cream")));

        // same text, different timePoint -> returns false
        Note noteWithTime = new Note("To meet on February", TimePoint.of(LocalDate.of(2026, 3, 1)));
        Note noteWithDiffTime = new Note("To meet on February", TimePoint.of(LocalDate.of(2026, 4, 1)));
        assertFalse(noteWithTime.equals(noteWithDiffTime));

        // same text, one with timePoint one without -> returns false
        assertFalse(notes.equals(noteWithTime));
        assertFalse(noteWithTime.equals(notes));

        // same text and same timePoint -> returns true
        assertTrue(noteWithTime.equals(new Note("To meet on February", TimePoint.of(LocalDate.of(2026, 3, 1)))));
    }

    @Test
    public void hashcode() {
        Note note1 = new Note("Hello");
        Note note2 = new Note("Hello");
        Note noteWithTime1 = new Note("Hello", TimePoint.of(LocalDate.of(2026, 3, 1)));
        Note noteWithTime2 = new Note("Hello", TimePoint.of(LocalDate.of(2026, 3, 1)));

        // same values -> same hashcode
        assertEquals(note1.hashCode(), note2.hashCode());
        assertEquals(noteWithTime1.hashCode(), noteWithTime2.hashCode());
    }

    @Test
    public void hasContactReferences_withReference_returnsTrue() {
        UUID id = UUID.fromString("550e8400-e29b-41d4-a716-446655440000");
        Note note = new Note("worked with @{" + id + "}");
        assertTrue(note.hasContactReferences());
    }

    @Test
    public void hasContactReferences_withoutReference_returnsFalse() {
        Note note = new Note("just a normal note");
        assertFalse(note.hasContactReferences());
    }

    @Test
    public void dereferenceContact_replacesUuidWithName() {
        UUID id = UUID.fromString("550e8400-e29b-41d4-a716-446655440000");
        Note note = new Note("worked with @{" + id + "}");
        Note result = note.dereferenceContact(id, "Alice");
        assertEquals("worked with Alice", result.value);
        assertFalse(result.hasContactReferences());
    }

    @Test
    public void dereferenceContact_preservesTimePoint() {
        UUID id = UUID.fromString("550e8400-e29b-41d4-a716-446655440000");
        TimePoint<?> tp = TimePoint.of(LocalDate.of(2025, 6, 15));
        Note note = new Note("worked with @{" + id + "}", tp);
        Note result = note.dereferenceContact(id, "Bob");
        assertEquals("worked with Bob", result.value);
        assertEquals(Optional.of(tp), result.timePoint);
    }

    @Test
    public void dereferenceContact_differentUuid_noChange() {
        UUID id1 = UUID.fromString("550e8400-e29b-41d4-a716-446655440000");
        UUID id2 = UUID.fromString("660e8400-e29b-41d4-a716-446655440000");
        Note note = new Note("worked with @{" + id1 + "}");
        Note result = note.dereferenceContact(id2, "Alice");
        assertEquals(note.value, result.value);
    }

    @Test
    public void formatContactReferencesForDisplay_nullDisplayed_returnsOriginal() {
        UUID id = UUID.fromString("550e8400-e29b-41d4-a716-446655440000");
        String text = "x @{" + id + "} y";
        assertEquals(text, Note.formatContactReferencesForDisplay(text, null, null));
    }

    @Test
    public void formatContactReferencesForDisplay_uuidInDisplayedList_showsNameAndIndex() {
        List<Contact> list = TypicalContacts.getTypicalContacts();
        Contact second = list.get(1);
        String text = "met @{" + second.getId() + "}";
        assertEquals("met " + second.getName().fullName + " (@2)",
                Note.formatContactReferencesForDisplay(text, list, list));
    }

    @Test
    public void formatContactReferencesForDisplay_uuidNotInDisplayed_usesNameFromAll() {
        List<Contact> all = TypicalContacts.getTypicalContacts();
        Contact second = all.get(1);
        List<Contact> displayed = List.of(all.get(0));
        String text = "met @{" + second.getId() + "}";
        assertEquals("met " + second.getName().fullName,
                Note.formatContactReferencesForDisplay(text, displayed, all));
    }

    @Test
    public void toString_test() {
        Note note = new Note("Meeting notes", TimePoint.of(LocalDateTime.of(2025, 12, 25, 10, 30)));
        String expected = "seedu.address.model.contact.Note{" + "value=Meeting notes, "
                + "timePoint=Optional[10:30, Dec 25, 2025]}";
        assertEquals(expected, note.toString());
    }
}
