package seedu.address.ui;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javafx.stage.Stage;
import seedu.address.commons.core.theme.Themes;

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
            reminderWindow.setTheme(Themes.get("light"));
            assert reminderWindow.getStage().getScene().getStylesheets()
                    .contains(UiUtil.getUrl("LightTheme.css").toString());
        });
    }
}
