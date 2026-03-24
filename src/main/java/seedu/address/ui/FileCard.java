package seedu.address.ui;

import java.io.File;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import seedu.address.model.timepoint.DateTimeUtil;

/**
 * A UI component that displays information of a B2B4U file.
 */
public class FileCard extends UiPart<Region> {
    private static final String FXML = "FileListCard.fxml";
    private static final ZoneId ZONE_ID = ZoneId.systemDefault();

    public final File file;

    @FXML
    private Label name;
    @FXML
    private Label details;

    /**
     * Creates a {@code FileCard} with the given file to display.
     */
    public FileCard(File file) {
        super(FXML);
        this.file = file;
        name.setText(file.getName());
        LocalDateTime lastModified = Instant.ofEpochMilli(file.lastModified()).atZone(ZONE_ID).toLocalDateTime();
        details.setText(DateTimeUtil.toDisplayString(lastModified));
    }
}
