package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.Objects;
import java.util.Optional;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.contact.Contact;

/**
 * Represents the result of a command execution.
 */
public class CommandResult {

    private final String feedbackToUser;

    /** Help information should be shown to the user. */
    private final boolean showHelp;

    /** The application should exit. */
    private final boolean exit;

    /** Contact to view in detail panel. */
    private final Contact contactToView;

    /** Number of steps to shift forward or backward. */
    private final int snapshotSteps;

    /**
     * Constructs a {@code CommandResult} with the specified {@code stepsToShift}.
     */
    public CommandResult(String feedbackToUser, int snapshotSteps) {
        this(feedbackToUser, false, false, null, snapshotSteps);
    }

    /**
     * Constructs a {@code CommandResult} with the specified fields.
     */
    public CommandResult(String feedbackToUser, boolean showHelp, boolean exit) {
        this(feedbackToUser, showHelp, exit, null, 0);
    }

    /**
     * Constructs a {@code CommandResult} with the specified fields without the number of snapshots to move
     */
    public CommandResult(String feedbackToUser, boolean showHelp, boolean exit, Contact contactToView) {
        this(feedbackToUser, showHelp, exit, contactToView, 0);
    }

    /**
     * Constructs a {@code CommandResult} with the specified fields including contact to view.
     */
    public CommandResult(
            String feedbackToUser, boolean showHelp, boolean exit, Contact contactToView, int snapshotSteps) {
        this.feedbackToUser = requireNonNull(feedbackToUser);
        this.showHelp = showHelp;
        this.exit = exit;
        this.contactToView = contactToView;
        this.snapshotSteps = snapshotSteps;
    }

    /**
     * Constructs a {@code CommandResult} with the specified {@code feedbackToUser},
     * and other fields set to their default value.
     */
    public CommandResult(String feedbackToUser) {
        this(feedbackToUser, false, false, null, 0);
    }

    public String getFeedbackToUser() {
        return feedbackToUser;
    }

    public boolean isShowHelp() {
        return showHelp;
    }

    public boolean isExit() {
        return exit;
    }

    public int getSnapshotSteps() {
        return snapshotSteps;
    }

    /**
     * Returns the contact to view, if any.
     */
    public Optional<Contact> getContactToView() {
        return Optional.ofNullable(contactToView);
    }

    /**
     * Returns true if widget should show contact details.
     */
    public boolean isShowContactDetail() {
        return contactToView != null;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof CommandResult)) {
            return false;
        }

        CommandResult otherCommandResult = (CommandResult) other;
        return feedbackToUser.equals(otherCommandResult.feedbackToUser)
                && showHelp == otherCommandResult.showHelp
                && exit == otherCommandResult.exit
                && Objects.equals(contactToView, otherCommandResult.contactToView);
    }

    @Override
    public int hashCode() {
        return Objects.hash(feedbackToUser, showHelp, exit, contactToView);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("feedbackToUser", feedbackToUser)
                .add("showHelp", showHelp)
                .add("exit", exit)
                .add("contactToView", contactToView)
                .toString();
    }

}
