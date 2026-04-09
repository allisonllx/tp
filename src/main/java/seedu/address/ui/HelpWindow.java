package seedu.address.ui;

import java.util.List;
import java.util.logging.Logger;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.stage.Stage;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.core.theme.Theme;
import seedu.address.logic.commands.HelpInfo;

/**
 * Controller for a help page
 */
public class HelpWindow extends UiPart<Stage> {

    public static final String USER_GUIDE_URL = HelpInfo.USER_GUIDE_BASE_URL + "index.html";
    public static final String HELP_MESSAGE = "Refer to the user guide: " + USER_GUIDE_URL;

    private static final Logger logger = LogsCenter.getLogger(HelpWindow.class);
    private static final String FXML = "HelpWindow.fxml";

    private Stage stage;
    private String[] stylesheets;
    private String currentUrl = USER_GUIDE_URL;

    @FXML
    private Button copyButton;

    @FXML
    private Label helpMessage;

    /**
     * Creates a new HelpWindow.
     *
     * @param root Stage to use as the root of the HelpWindow.
     */
    public HelpWindow(Stage root) {
        super(FXML, root);
        stage = root;

        List<String> essentialStylesheets = stage.getScene().getStylesheets();
        stylesheets = new String[essentialStylesheets.size() + 1];
        essentialStylesheets.toArray(stylesheets);

        helpMessage.setText(HELP_MESSAGE);
    }

    /**
     * Creates a new HelpWindow.
     */
    public HelpWindow() {
        this(new Stage());
    }

    /**
     * Updates the help window content with the given {@code HelpInfo}.
     */
    public void setHelpInfo(HelpInfo helpInfo) {
        currentUrl = helpInfo.getUrl();
        helpMessage.setText(helpInfo.getMessage() + "\n\nUser Guide: " + currentUrl);
    }

    /**
     * Shows the help window.
     *
     * @throws IllegalStateException <ul>
     *                               <li>
     *                               if this method is called on a thread other than the JavaFX Application Thread.
     *                               </li>
     *                               <li>
     *                               if this method is called during animation or layout processing.
     *                               </li>
     *                               <li>
     *                               if this method is called on the primary stage.
     *                               </li>
     *                               <li>
     *                               if {@code dialogStage} is already showing.
     *                               </li>
     *                               </ul>
     */
    public void show() {
        logger.fine("Showing help page about the application.");
        getRoot().show();
        getRoot().centerOnScreen();
    }

    /**
     * Returns true if the help window is currently being shown.
     */
    public boolean isShowing() {
        return getRoot().isShowing();
    }

    /**
     * Hides the help window.
     */
    public void hide() {
        getRoot().hide();
    }

    /**
     * Focuses on the help window.
     */
    public void focus() {
        getRoot().requestFocus();
    }

    /**
     * Copies the current URL to the clipboard.
     */
    @FXML
    private void copyUrl() {
        final Clipboard clipboard = Clipboard.getSystemClipboard();
        final ClipboardContent url = new ClipboardContent();
        url.putString(currentUrl);
        clipboard.setContent(url);
    }

    /**
     * Sets the theme of the HelpWindow.
     * @param theme The desired theme.
     */
    public void setTheme(Theme theme) {
        stylesheets[stylesheets.length - 1] = theme.getUrl();
        stage.getScene().getStylesheets().setAll(stylesheets);
    }
}
