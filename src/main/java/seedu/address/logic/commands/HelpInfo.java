package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.Objects;

/**
 * Represents help information to be displayed in the help window.
 * Contains a message describing the command and a URL to the relevant User Guide page.
 */
public class HelpInfo {

    public static final String USER_GUIDE_BASE_URL =
            "https://ay2526s2-cs2103t-t08-1.github.io/tp/user-guide/";

    /** Default help info pointing to the main User Guide page. */
    public static final HelpInfo DEFAULT = new HelpInfo(
            "Refer to the user guide for more information.",
            USER_GUIDE_BASE_URL + "index.html");

    private final String message;
    private final String url;

    /**
     * Constructs a {@code HelpInfo} with the given message and URL.
     */
    public HelpInfo(String message, String url) {
        requireNonNull(message);
        requireNonNull(url);
        this.message = message;
        this.url = url;
    }

    public String getMessage() {
        return message;
    }

    public String getUrl() {
        return url;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof HelpInfo)) {
            return false;
        }
        HelpInfo otherInfo = (HelpInfo) other;
        return message.equals(otherInfo.message) && url.equals(otherInfo.url);
    }

    @Override
    public int hashCode() {
        return Objects.hash(message, url);
    }

    @Override
    public String toString() {
        return "HelpInfo{message=" + message + ", url=" + url + "}";
    }
}
