package seedu.address.ui;

import java.util.List;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.stage.Modality;
import javafx.stage.Stage;
import seedu.address.commons.core.theme.Theme;

/**
 * Alerts the user of the deletion of a file containing data and requests for confirmation.
 */
public class DeleteFileAlert extends UiPart<Stage> {
    private static final String FXML = "ConfirmationAlert.fxml";
    private static final String ALERT_FORMAT = "%s still has %d contacts.\nAre you sure you want to delete it?";

    @FXML
    private Label alertLabel;
    @FXML
    private Button yesButton;
    @FXML
    private Button noButton;

    private ButtonType result = ButtonType.CANCEL;

    /**
     * Creates a new DeleteFileAlert window.
     * @param root Stage to use as the root of the DeleteFileAlert window.
     * @param alertText Text to display to user.
     * @param theme Theme to be used for this alert window.
     */
    public DeleteFileAlert(Stage root, String alertText, Theme theme) {
        super(FXML, root);

        List<String> essentialStylesheets = root.getScene().getStylesheets();
        String[] stylesheets = new String[essentialStylesheets.size() + 1];
        essentialStylesheets.toArray(stylesheets);
        stylesheets[stylesheets.length - 1] = theme.getUrl();
        root.getScene().getStylesheets().setAll(stylesheets);

        alertLabel.setText(alertText);
    }

    /**
     * Creates a new DeleteFileAlert window.
     * @param fileName Name of file to be deleted.
     * @param contactCount Number of contacts within file to be deleted.
     */
    public DeleteFileAlert(String fileName, int contactCount, Theme theme) {
        this(new Stage(), String.format(ALERT_FORMAT, fileName, contactCount), theme);
    }

    /**
     * Shows the alert window.
     */
    public void show() {
        getRoot().initModality(Modality.APPLICATION_MODAL);
        getRoot().showAndWait();
        getRoot().centerOnScreen();
    }

    /**
     * Hides the alert window.
     */
    public void hide() {
        getRoot().hide();
    }

    @FXML
    private void yesSelected() {
        result = ButtonType.YES;
        hide();
    }

    @FXML
    private void noSelected() {
        result = ButtonType.NO;
        hide();
    }

    /**
     * Returns the result of the confirmation.
     */
    public ButtonType getResult() {
        return result;
    }
}
