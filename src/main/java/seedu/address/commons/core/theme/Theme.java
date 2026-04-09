package seedu.address.commons.core.theme;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import seedu.address.commons.exceptions.IllegalValueException;

/**
 * Represents a Theme in the address book.
 */
public class Theme {
    private final String name;
    private final transient String url;

    Theme(String name, String url) {
        this.name = name;
        this.url = url;
    }

    @JsonValue
    public String getName() {
        return name;
    }

    public String getUrl() {
        return url;
    }

    /**
     * Returns a Theme object from the given theme name.
     *
     * @param name the name of the theme
     * @return the Theme object
     * @throws IllegalValueException if the theme name is invalid
     */
    @JsonCreator
    public static Theme fromName(String name) throws IllegalValueException {
        if (!Themes.contains(name)) {
            throw new IllegalValueException("Invalid theme: " + name);
        }

        Theme theme = Themes.get(name);

        return theme;
    }
}
