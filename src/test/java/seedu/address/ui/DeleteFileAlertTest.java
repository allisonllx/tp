package seedu.address.ui;

import org.junit.jupiter.api.Test;

import javafx.stage.Stage;

public class DeleteFileAlertTest extends GuiUnitTest {
    private DeleteFileAlert alert;

    @Test
    public void construct_success() throws Exception {
        runAndWait(() -> {
            Stage stage = new Stage();
            alert = new DeleteFileAlert(stage, "Message", "stubTheme");
        });
    }
}
