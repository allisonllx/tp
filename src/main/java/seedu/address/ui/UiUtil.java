package seedu.address.ui;

import static java.util.Objects.requireNonNull;

import java.net.URL;

import javafx.scene.Node;
import seedu.address.MainApp;

/**
 * Utility functions for {@code Node}.
 */
public interface UiUtil {

    /** Resource folder where CSS and FXML resource files are stored. */
    public static final String RESOURCE_FILE_FOLDER = "/view/";

    /**
     * Hides a node by setting its {@code visible} and {@code managed} attributes to {@code false}.
     */
    public static void hide(Node node) {
        node.setVisible(false);
        node.setManaged(false);
    }

    /**
     * Shows a node by setting its {@code visible} and {@code managed} attributes to {@code true}.
     */
    public static void show(Node node) {
        node.setVisible(true);
        node.setManaged(true);
    }

    /**
     * Returns the file URL for the specified file name within {@link #RESOURCE_FILE_FOLDER}.
     */
    public static URL getUrl(String fileName) {
        requireNonNull(fileName);
        String fileNameWithFolder = RESOURCE_FILE_FOLDER + fileName;
        URL fileUrl = MainApp.class.getResource(fileNameWithFolder);
        return requireNonNull(fileUrl);
    }
}
