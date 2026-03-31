package seedu.address.commons.core;

import java.awt.Point;
import java.io.Serializable;
import java.util.Objects;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.ui.UiUtil;

/**
 * A Serializable class that contains the GUI settings.
 * Guarantees: immutable.
 */
public class GuiSettings implements Serializable {

    private static final double DEFAULT_HEIGHT = 600;
    private static final double DEFAULT_WIDTH = 740;

    private final double windowWidth;
    private final double windowHeight;
    private final Point windowCoordinates;

    private final String themeUrl;

    /**
     * Constructs a {@code GuiSettings} with the default height, width and position.
     */
    public GuiSettings() {
        windowWidth = DEFAULT_WIDTH;
        windowHeight = DEFAULT_HEIGHT;
        windowCoordinates = null; // null represent no coordinates
        themeUrl = UiUtil.getUrl("DarkTheme.css").toString();
    }

    /**
     * Constructs a {@code GuiSettings} with the specified height, width and theme.
     */
    public GuiSettings(double windowWidth, double windowHeight, String themeUrl) {
        this.windowWidth = windowWidth;
        this.windowHeight = windowHeight;
        windowCoordinates = null;
        this.themeUrl = themeUrl;
    }

    /**
     * Constructs a {@code GuiSettings} with the specified height, width, position and theme.
     */
    public GuiSettings(double windowWidth, double windowHeight, int xPosition, int yPosition, String themeUrl) {
        this.windowWidth = windowWidth;
        this.windowHeight = windowHeight;
        windowCoordinates = new Point(xPosition, yPosition);
        this.themeUrl = themeUrl;
    }

    public double getWindowWidth() {
        return windowWidth;
    }

    public double getWindowHeight() {
        return windowHeight;
    }

    public Point getWindowCoordinates() {
        return windowCoordinates != null ? new Point(windowCoordinates) : null;
    }

    public String getThemeUrl() {
        return themeUrl;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof GuiSettings)) {
            return false;
        }

        GuiSettings otherGuiSettings = (GuiSettings) other;
        return windowWidth == otherGuiSettings.windowWidth
                && windowHeight == otherGuiSettings.windowHeight
                && Objects.equals(windowCoordinates, otherGuiSettings.windowCoordinates);
    }

    @Override
    public int hashCode() {
        return Objects.hash(windowWidth, windowHeight, windowCoordinates, themeUrl);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("windowWidth", windowWidth)
                .add("windowHeight", windowHeight)
                .add("windowCoordinates", windowCoordinates)
                .add("theme", themeUrl)
                .toString();
    }
}
