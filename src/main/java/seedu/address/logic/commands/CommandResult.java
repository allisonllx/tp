package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.Objects;
import java.util.Optional;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.contact.Contact;

/**
 * Represents the result of a command execution.
 */
public class CommandResult {

    private final String feedbackToUser;

    /** Help information to be shown to the user, or null if no help window needed. */
    private final HelpInfo helpInfo;

    /** The application should exit. */
    private final boolean exit;

    /** Contact to view in detail panel. */
    private final Contact contactToView;

    /** Whether to hide the view panel. */
    private final boolean hideViewPanel;

    /** Whether to hide the view panel. */
    private final boolean showFileList;

    /** Index of contact to scroll to. */
    private final Optional<Index> scrollToIndex;

    /**
     * Constructs a {@code CommandResult} with all fields specified.
     */
    public CommandResult(String feedbackToUser, HelpInfo helpInfo, boolean exit,
            Contact contactToView, boolean hideViewPanel, boolean showFileList, Optional<Index> scrollToIndex) {
        this.feedbackToUser = requireNonNull(feedbackToUser);
        this.helpInfo = helpInfo;
        this.exit = exit;
        this.contactToView = contactToView;
        this.hideViewPanel = hideViewPanel;
        this.showFileList = showFileList;
        this.scrollToIndex = scrollToIndex;
    }

    /**
     * Constructs a {@code CommandResult} with the specified {@code feedbackToUser},
     * and other fields set to their default value.
     */
    public CommandResult(String feedbackToUser) {
        this(feedbackToUser, null, false, null, false, false, Optional.empty());
    }

    public String getFeedbackToUser() {
        return feedbackToUser;
    }

    public boolean isShowHelp() {
        return helpInfo != null;
    }

    /**
     * Returns the help info, if any.
     */
    public Optional<HelpInfo> getHelpInfo() {
        return Optional.ofNullable(helpInfo);
    }

    public boolean isExit() {
        return exit;
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

    /**
     * Returns true if the contact detail panel should be hidden.
     */
    public boolean isHideViewPanel() {
        return hideViewPanel;
    }

    /**
     * Returns true if the file list should be shown.
     */
    public boolean isShowFileList() {
        return showFileList;
    }

    /**
     * Returns true if the contact list should scroll to a certain contact.
     */
    public boolean hasScrollToIndex() {
        return scrollToIndex.isPresent();
    }

    public Index getScrollToIndex() {
        assert scrollToIndex.isPresent();
        return scrollToIndex.get();
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
                && Objects.equals(helpInfo, otherCommandResult.helpInfo)
                && exit == otherCommandResult.exit
                && Objects.equals(contactToView, otherCommandResult.contactToView)
                && hideViewPanel == otherCommandResult.hideViewPanel
                && showFileList == otherCommandResult.showFileList
                && scrollToIndex.equals(otherCommandResult.scrollToIndex);
    }

    @Override
    public int hashCode() {
        return Objects.hash(feedbackToUser, helpInfo, exit, contactToView, hideViewPanel, showFileList, scrollToIndex);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("feedbackToUser", feedbackToUser)
                .add("helpInfo", helpInfo)
                .add("exit", exit)
                .add("contactToView", contactToView)
                .add("hideViewPanel", hideViewPanel)
                .add("showFileList", showFileList)
                .add("scrollToIndex", scrollToIndex.isPresent() ? scrollToIndex.get() : "null")
                .toString();
    }
}

class HelpCommandResult extends CommandResult {
    /**
     * Constructs a {@code CommandResult} with the specified {@code HelpInfo},
     * which will trigger the help window to open with the given info.
     */
    public HelpCommandResult(String feedbackToUser, HelpInfo helpInfo) {
        super(feedbackToUser, helpInfo, false, null, false, false, Optional.empty());
    }
}

class ExitCommandResult extends CommandResult {
    /**
     * Constructs a {@code CommandResult} with the specified {@code feedbackToUser},
     * {@code exit} set to {@code true},
     * and other fields set to their default value.
     */
    public ExitCommandResult(String feedbackToUser) {
        super(feedbackToUser, null, true, null, false, false, Optional.empty());
    }
}

class ViewContactCommandResult extends CommandResult {
    /**
     * Constructs a {@code CommandResult} with the specified {@code feedbackToUser},
     * {@code contactToView} set to the given contact,
     * and other fields set to their default value.
     */
    public ViewContactCommandResult(String feedbackToUser, Contact contactToView) {
        super(feedbackToUser, null, false, contactToView, false, false, Optional.empty());
    }
}

class CloseViewPanelCommandResult extends CommandResult {
    /**
     * Constructs a {@code CommandResult} with the specified {@code feedbackToUser},
     * {@code hideViewPanel} set to {@code true},
     * and other fields set to their default value.
     */
    public CloseViewPanelCommandResult(String feedbackToUser) {
        super(feedbackToUser, null, false, null, true, false, Optional.empty());
    }
}

class ViewFilesCommandResult extends CommandResult {
    /**
     * Constructs a {@code CommandResult} with the specified {@code feedbackToUser},
     * {@code showFileList} set to {@code true},
     * and other fields set to their default value.
     */
    public ViewFilesCommandResult(String feedbackToUser) {
        super(feedbackToUser, null, false, null, false, true, Optional.empty());
    }
}

class ScrollToIndexCommandResult extends CommandResult {
    /**
     * Constructs a {@code CommandResult} with the specified {@code feedbackToUser} and {@code scrollToIndex}
     * and other fields set to their default value.
     */
    public ScrollToIndexCommandResult(String feedbackToUser, Index scrollToIndex) {
        super(feedbackToUser, null, false, null, false, false, Optional.of(scrollToIndex));
    }
}
