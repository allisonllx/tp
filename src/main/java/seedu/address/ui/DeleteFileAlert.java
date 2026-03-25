package seedu.address.ui;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

public class DeleteFileAlert extends Alert {
    public DeleteFileAlert(String fileName, int contactCount) {
        super(
                Alert.AlertType.CONFIRMATION,
                String.format("%s still has %d contacts.\nAre you sure you want to delete it?", fileName, contactCount),
                ButtonType.YES, ButtonType.NO);
    }
}
