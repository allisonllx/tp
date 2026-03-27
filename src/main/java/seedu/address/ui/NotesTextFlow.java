package seedu.address.ui;

import javafx.collections.ObservableList;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import seedu.address.model.contact.Contact;
import seedu.address.model.contact.Note;

import java.util.List;
import java.util.UUID;
import java.util.regex.Matcher;

public class NotesTextFlow extends TextFlow {
    private double maxHeight;

    public NotesTextFlow(List<Note> notes, ObservableList<Contact> contacts) {
        super();
        for (Note note : notes) {
            boolean isReminder = note.isReminder();
            if (isReminder) {
                Text reminderHeader = new Text("Reminder: ");
                reminderHeader.getStyleClass().add("text-flow-label");
                reminderHeader.getStyleClass().add("bold");
                getChildren().add(reminderHeader);
            }
            String noteText = note.value;
            Matcher matcher = Note.CONTACT_REF_PATTERN.matcher(noteText);
            int lastEnd = 0;

            while (matcher.find()) {
                // Add plain text before this match
                if (matcher.start() > lastEnd) {
                    Text plainText = new Text(noteText.substring(lastEnd, matcher.start()));
                    plainText.getStyleClass().add("text-flow-label");
                    getChildren().add(plainText);
                }

                // Resolve UUID to contact name
                String uuidStr = matcher.group(1);
                UUID refId = UUID.fromString(uuidStr);
                String displayName = contacts.stream()
                        .filter(c -> c.getId().equals(refId))
                        .map(c -> c.getName().fullName)
                        .findFirst()
                        .orElse("@" + uuidStr);

                Text refText = new Text(displayName);
                refText.getStyleClass().add("contact-reference");
                refText.setStyle("-fx-font-size: 13px;");
                getChildren().add(refText);

                lastEnd = matcher.end();
            }

            // Add remaining plain text
            if (lastEnd < noteText.length()) {
                Text plainText = new Text(noteText.substring(lastEnd));
                plainText.getStyleClass().add("text-flow-label");
                getChildren().add(plainText);
            }

            if (isReminder) {
                Text reminderSeparator = new Text(" on ");
                reminderSeparator.getStyleClass().add("text-flow-label");
                reminderSeparator.getStyleClass().add("italic");
                getChildren().add(reminderSeparator);
                Text time = new Text(note.timePoint.toString());
                time.getStyleClass().add("text-flow-label");
                getChildren().add(time);
            }
            getChildren().add(new Text("\n"));
        }
        getChildren().remove(getChildren().size() - 1);
    }

    public void setNewMaxHeight(double maxHeight) {
        this.maxHeight = maxHeight;
        setClip(new Rectangle(Double.MAX_VALUE, maxHeight));
    }


    @Override
    protected double computeMaxHeight(double width) {
        return maxHeight;
    }

    @Override
    protected double computePrefHeight(double width) {
        return Math.min(super.computePrefHeight(width), maxHeight);
    }
}
