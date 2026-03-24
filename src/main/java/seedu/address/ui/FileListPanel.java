package seedu.address.ui;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;

/**
 * Panel containing the list of B2B4U.
 */
public class FileListPanel extends UiPart<Region> {
    private static final String FXML = "FileListPanel.fxml";
    private static final ExecutorService EXECUTOR_SERVICE = Executors.newSingleThreadExecutor();

    private final WatchService watchService;
    private final ObservableList<File> fileList = FXCollections.observableArrayList();

    @FXML
    private ListView<File> fileListView;

    /**
     * Creates a {@code FileListPanel} with the given directory.
     */
    public FileListPanel(Path directory) throws IOException {
        super(FXML);
        File[] existingFiles = directory.toFile().listFiles();
        if (existingFiles != null) {
            fileList.addAll(existingFiles);
        }

        watchService = FileSystems.getDefault().newWatchService();
        directory.register(watchService,
                StandardWatchEventKinds.ENTRY_CREATE,
                StandardWatchEventKinds.ENTRY_MODIFY,
                StandardWatchEventKinds.ENTRY_DELETE);
        EXECUTOR_SERVICE.submit(() -> {
            try {
                while (!Thread.currentThread().isInterrupted()) {
                    WatchKey key = watchService.take(); // blocks until an event occurs

                    for (WatchEvent<?> event : key.pollEvents()) {
                        WatchEvent.Kind<?> kind = event.kind();

                        if (kind == StandardWatchEventKinds.OVERFLOW) continue;

                        Path changed = directory.resolve((Path) event.context());
                        File changedFile = changed.toFile();

                        // All ObservableList updates MUST happen on the JavaFX thread
                        Platform.runLater(() -> {
                            if (kind == StandardWatchEventKinds.ENTRY_CREATE) {
                                fileList.add(changedFile);
                            } else if (kind == StandardWatchEventKinds.ENTRY_DELETE) {
                                fileList.removeIf(f -> f.equals(changedFile));
                            } else if (kind == StandardWatchEventKinds.ENTRY_MODIFY) {
                                int index = fileList.indexOf(changedFile);
                                if (index >= 0) {
                                    fileList.set(index, changedFile); // triggers update event
                                }
                            }
                        });
                    }

                    if (!key.reset()) break; // directory no longer accessible
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });

        fileListView.setItems(fileList);
        fileListView.setCellFactory(listView -> new FileListPanel.FileListViewCell());

        // Refresh all visible cells when any contact changes (e.g. name edit)
        // so that note references (@{UUID}) resolve to the updated name.
        fileList.addListener((ListChangeListener<File>) change -> {
            fileListView.refresh();
        });
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code File} using a {@code FileCard}.
     */
    class FileListViewCell extends ListCell<File> {
        @Override
        protected void updateItem(File file, boolean empty) {
            super.updateItem(file, empty);

            if (empty || file == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new FileCard(file).getRoot());
            }
        }
    }
}
