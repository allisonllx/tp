package seedu.address.ui;

import java.io.IOException;
import java.util.List;
import java.util.UUID;
import java.util.logging.Logger;

import javafx.beans.value.ChangeListener;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TextInputControl;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.core.Themes;
import seedu.address.logic.Logic;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.contact.Contact;

/**
 * The Main Window. Provides the basic application layout containing
 * a menu bar and space where other JavaFX elements can be placed.
 */
public class MainWindow extends UiPart<Stage> {

    private static final String FXML = "MainWindow.fxml";

    private final Logger logger = LogsCenter.getLogger(getClass());

    private Stage primaryStage;
    private Logic logic;

    private String[] stylesheets;

    // Independent Ui parts residing in this Ui container
    private ContactListPanel contactListPanel;
    private ContactDetailPanel contactDetailPanel;
    private FileListPanel fileListPanel;
    private ResultDisplay resultDisplay;
    private HelpWindow helpWindow;
    private ReminderWindow reminderWindow = null;
    private StatusBarFooter statusBarFooter;

    /** Listener to keep the split pane divider at the right edge when the detail panel is hidden */
    private final ChangeListener<Number> splitPaneListener;

    /** The UUID of the contact currently shown in the detail panel, or null if none. */
    private UUID viewedContactId;

    @FXML
    private StackPane commandBoxPlaceholder;

    @FXML
    private MenuItem undoMenuItem;

    @FXML
    private MenuItem redoMenuItem;

    @FXML
    private MenuItem helpMenuItem;

    @FXML
    private StackPane contactListPanelPlaceholder;

    @FXML
    private StackPane contactDetailPanelPlaceholder;

    @FXML
    private StackPane fileListPanelPlaceholder;

    @FXML
    private VBox viewPanelContainer;

    @FXML
    private SplitPane splitPane;

    @FXML
    private StackPane resultDisplayPlaceholder;

    @FXML
    private StackPane statusbarPlaceholder;

    /**
     * Creates a {@code MainWindow} with the given {@code Stage} and {@code Logic}.
     */
    public MainWindow(Stage primaryStage, Logic logic) {
        super(FXML, primaryStage);

        // Set dependencies
        this.primaryStage = primaryStage;
        this.logic = logic;

        List<String> essentialStylesheets = primaryStage.getScene().getStylesheets();
        stylesheets = new String[essentialStylesheets.size() + 1];
        essentialStylesheets.toArray(stylesheets);

        // Configure the UI
        setWindowDefaultSize(logic.getGuiSettings());

        setAccelerators();

        helpWindow = new HelpWindow();

        // Set up the split pane listener to keep the divider at the right edge when the detail panel is hidden
        splitPaneListener = (obs, oldVal, newVal) -> {
            if (!viewPanelContainer.isVisible()) {
                splitPane.setDividerPositions(1.0);
            }
        };

        splitPane.widthProperty().addListener(splitPaneListener);
        splitPane.getDividers().get(0).positionProperty().addListener(splitPaneListener);
    }

    public Stage getPrimaryStage() {
        return primaryStage;
    }

    private void setAccelerators() {
        setAccelerator(helpMenuItem, KeyCombination.valueOf("F1"));
        setAccelerator(undoMenuItem, new KeyCodeCombination(KeyCode.Z, KeyCombination.SHORTCUT_DOWN));
        setAccelerator(redoMenuItem,
                new KeyCodeCombination(KeyCode.Z, KeyCombination.SHIFT_DOWN, KeyCombination.SHORTCUT_DOWN));
    }

    /**
     * Sets the accelerator of a MenuItem.
     * @param keyCombination the KeyCombination value of the accelerator
     */
    private void setAccelerator(MenuItem menuItem, KeyCombination keyCombination) {
        menuItem.setAccelerator(keyCombination);

        /*
         * TODO: the code below can be removed once the bug reported here
         * https://bugs.openjdk.java.net/browse/JDK-8131666
         * is fixed in later version of SDK.
         *
         * According to the bug report, TextInputControl (TextField, TextArea) will
         * consume function-key events. Because CommandBox contains a TextField, and
         * ResultDisplay contains a TextArea, thus some accelerators (e.g F1) will
         * not work when the focus is in them because the key event is consumed by
         * the TextInputControl(s).
         *
         * For now, we add following event filter to capture such key events and open
         * help window purposely so to support accelerators even when focus is
         * in CommandBox or ResultDisplay.
         */
        getRoot().addEventFilter(KeyEvent.KEY_PRESSED, event -> {
            if (event.getTarget() instanceof TextInputControl && keyCombination.match(event)) {
                menuItem.getOnAction().handle(new ActionEvent());
                event.consume();
            }
        });
    }

    /**
     * Fills up all the placeholders of this window.
     */
    void fillInnerParts() {
        contactListPanel = new ContactListPanel(logic.getDisplayedContactList(),
                logic.getAddressBook().getContactList());
        contactListPanelPlaceholder.getChildren().add(contactListPanel.getRoot());

        contactDetailPanel = new ContactDetailPanel(logic.getAddressBook().getContactList());
        contactDetailPanelPlaceholder.getChildren().add(contactDetailPanel.getRoot());

        try {
            fileListPanel = new FileListPanel(logic.getAddressBookFilePath().getParent());
            fileListPanelPlaceholder.getChildren().add(fileListPanel.getRoot());
        } catch (IOException e) {
            logger.info("An error occurred while setting up file list: " + e);
        }

        // Initially hide the detail panel
        hideViewPanel();

        resultDisplay = new ResultDisplay();
        resultDisplayPlaceholder.getChildren().add(resultDisplay.getRoot());

        statusBarFooter = new StatusBarFooter(logic.getAddressBookFilePath());
        statusbarPlaceholder.getChildren().add(statusBarFooter.getRoot());

        CommandBox commandBox = new CommandBox(this::executeCommand);
        commandBoxPlaceholder.getChildren().add(commandBox.getRoot());

        if (logic.getDisplayedContactList().stream().anyMatch(Contact::hasDueReminders)) {
            reminderWindow = new ReminderWindow(logic.getDisplayedContactList());
            reminderWindow.setTheme(logic.getTheme());
            reminderWindow.show();
        }

        undoMenuItem.setDisable(!logic.modelCanUndo());
        redoMenuItem.setDisable(!logic.modelCanRedo());
    }

    /**
     * Shows the contact detail panel.
     */
    private void showContactDetail() {
        UiUtil.show(viewPanelContainer);
        UiUtil.show(contactDetailPanelPlaceholder);
        UiUtil.hide(fileListPanelPlaceholder);
        splitPane.setDividerPositions(0.6);
    }

    /**
     * Shows the file list panel.
     */
    private void showFileList() {
        UiUtil.show(viewPanelContainer);
        UiUtil.hide(contactDetailPanelPlaceholder);
        UiUtil.show(fileListPanelPlaceholder);
        splitPane.setDividerPositions(0.6);
    }

    /**
     * Hides the contact detail panel.
     */
    private void hideViewPanel() {
        UiUtil.hide(viewPanelContainer);
        splitPane.setDividerPositions(1.0);
        viewedContactId = null;
    }

    /**
     * Refreshes the contact detail panel with the latest data for the currently viewed contact.
     * If the contact is no longer in the filtered list (e.g. deleted or filtered out),
     * the detail panel is hidden.
     */
    private void refreshContactDetailPanel() {
        Contact updatedContact = logic.getDisplayedContactList().stream()
                .filter(c -> c.getId().equals(viewedContactId))
                .findFirst()
                .orElse(null);

        if (updatedContact != null) {
            contactDetailPanel.setContact(updatedContact);
        } else {
            contactDetailPanel.clearContact();
            hideViewPanel();
        }
    }

    /**
     * Sets the default size based on {@code guiSettings}.
     */
    private void setWindowDefaultSize(GuiSettings guiSettings) {
        primaryStage.setHeight(guiSettings.getWindowHeight());
        primaryStage.setWidth(guiSettings.getWindowWidth());
        if (guiSettings.getWindowCoordinates() != null) {
            primaryStage.setX(guiSettings.getWindowCoordinates().getX());
            primaryStage.setY(guiSettings.getWindowCoordinates().getY());
        }
    }

    /**
     * Sets the theme of the MainWindow.
     * @param theme The desired theme.
     */
    public void setTheme(String theme) {
        stylesheets[stylesheets.length - 1] = Themes.get(theme);
        primaryStage.getScene().getStylesheets().setAll(stylesheets);
        helpWindow.setTheme(theme);
        if (reminderWindow != null) {
            reminderWindow.setTheme(theme);
        }
    }

    /**
     * Executes a "help" command.
     */
    @FXML
    private void handleHelp() throws CommandException, ParseException {
        executeCommand("help");
    }

    @FXML
    private void handleHelpAdd() throws CommandException, ParseException {
        executeCommand("help add");
    }

    @FXML
    private void handleHelpClear() throws CommandException, ParseException {
        executeCommand("help clear");
    }

    @FXML
    private void handleHelpClose() throws CommandException, ParseException {
        executeCommand("help close");
    }

    @FXML
    private void handleHelpDelete() throws CommandException, ParseException {
        executeCommand("help delete");
    }

    @FXML
    private void handleHelpEdit() throws CommandException, ParseException {
        executeCommand("help edit");
    }

    @FXML
    private void handleHelpFile() throws CommandException, ParseException {
        executeCommand("help file");
    }

    @FXML
    private void handleHelpFind() throws CommandException, ParseException {
        executeCommand("help find");
    }

    @FXML
    private void handleHelpList() throws CommandException, ParseException {
        executeCommand("help list");
    }

    @FXML
    private void handleHelpNote() throws CommandException, ParseException {
        executeCommand("help note");
    }

    @FXML
    private void handleHelpSort() throws CommandException, ParseException {
        executeCommand("help sort");
    }

    @FXML
    private void handleHelpTheme() throws CommandException, ParseException {
        executeCommand("help theme");
    }

    @FXML
    private void handleHelpView() throws CommandException, ParseException {
        executeCommand("help view");
    }

    /**
     * Opens the help window or focuses on it if it's already opened.
     */
    @FXML
    public void showHelpWindow() {
        if (!helpWindow.isShowing()) {
            helpWindow.show();
        } else {
            helpWindow.focus();
        }
    }

    void show() {
        setTheme(logic.getTheme());

        primaryStage.show();
    }

    /**
     * Closes the application.
     */
    @FXML
    private void handleExit() {
        GuiSettings guiSettings = new GuiSettings(primaryStage.getWidth(), primaryStage.getHeight(),
                (int) primaryStage.getX(), (int) primaryStage.getY(), logic.getTheme());
        logic.setGuiSettings(guiSettings);
        helpWindow.hide();
        primaryStage.hide();
        if (reminderWindow != null) {
            if (reminderWindow.isShowing()) {
                reminderWindow.hide();
            }
        }
        fileListPanel.shutdownExecutorService();
    }

    /**
     * Executes an "undo" command.
     */
    @FXML
    private void handleUndo() throws CommandException, ParseException {
        executeCommand("undo");
    }

    /**
     * Executes a "redo" command.
     */
    @FXML
    private void handleRedo() throws CommandException, ParseException {
        executeCommand("redo");
    }

    /**
     * Executes a "view files" command.
     */
    @FXML
    private void handleViewFiles() throws CommandException, ParseException {
        executeCommand("view files");
    }

    /**
     * Executes a "close view" command.
     */
    @FXML
    private void handleCloseView() throws CommandException, ParseException {
        executeCommand("close view");
    }

    /**
     * Sets the theme to dark mode.
     */
    @FXML
    private void toggleDarkTheme() throws CommandException, ParseException {
        executeCommand("theme dark");
    }

    /**
     * Sets the theme to light mode.
     */
    @FXML
    private void toggleLightTheme() throws CommandException, ParseException {
        executeCommand("theme light");
    }

    /**
     * Sets the theme to reading mode.
     */
    @FXML
    private void toggleBookTheme() throws CommandException, ParseException {
        executeCommand("theme book");
    }

    /**
     * Sets the theme to sakura.
     */
    @FXML
    private void toggleSakuraTheme() throws CommandException, ParseException {
        executeCommand("theme sakura");
    }

    public ContactListPanel getContactListPanel() {
        return contactListPanel;
    }

    /**
     * Returns the UUID of the currently viewed contact, for testing.
     */
    UUID getViewedContactId() {
        return viewedContactId;
    }

    /**
     * Sets the UUID of the currently viewed contact, for testing.
     */
    void setViewedContactId(UUID id) {
        this.viewedContactId = id;
    }

    /**
     * Executes the command and returns the result.
     *
     * @see seedu.address.logic.Logic#execute(String)
     */
    CommandResult executeCommand(String commandText) throws CommandException, ParseException {
        try {
            CommandResult commandResult = logic.execute(commandText);
            logger.info("Result: " + commandResult.getFeedbackToUser());
            resultDisplay.setFeedbackToUser(commandResult.getFeedbackToUser());

            if (commandResult.isShowHelp()) {
                commandResult.getHelpInfo().ifPresent(helpWindow::setHelpInfo);
                showHelpWindow();
            }

            if (commandResult.isExit()) {
                handleExit();
            }

            if (commandResult.isHideViewPanel()) {
                contactDetailPanel.clearContact();
                hideViewPanel();
            } else if (commandResult.isShowContactDetail()) {
                commandResult.getContactToView().ifPresent(contact -> {
                    contactDetailPanel.setContact(contact);
                    viewedContactId = contact.getId();
                    showContactDetail();
                });
            } else if (viewedContactId != null) {
                refreshContactDetailPanel();
            }

            if (commandResult.isShowFileList()) {
                showFileList();
            }

            if (commandResult.hasScrollToIndex()) {
                contactListPanel.scrollToIndex(commandResult.getScrollToIndex());
            }

            if (!logic.getTheme().equals(stylesheets[stylesheets.length - 1])) {
                setTheme(logic.getTheme());
            }
            statusBarFooter.updateSaveLocation(logic.getAddressBookFilePath());
            undoMenuItem.setDisable(!logic.modelCanUndo());
            redoMenuItem.setDisable(!logic.modelCanRedo());

            return commandResult;
        } catch (CommandException | ParseException e) {
            logger.info("An error occurred while executing command: " + commandText);
            resultDisplay.setFeedbackToUser(e.getMessage());
            throw e;
        }
    }
}
