package seedu.address.ui;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javafx.stage.Stage;

public class ReminderWindowTest extends GuiUnitTest {
    private ReminderWindow reminderWindow;

    @BeforeEach
    public void setUp() throws Exception {
        runAndWait(() -> {
            Stage stage = new Stage();
            reminderWindow = new ReminderWindow(stage, List.of());
        });
    }

    @Test
    public void setThemeTest() throws Exception {
        runAndWait(() -> {
            reminderWindow.setTheme("stubTheme");
            assert reminderWindow.getStage().getScene().getStylesheets().contains("stubTheme");
        });
    }
}
