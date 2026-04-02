---
  layout: default.md
  title: "User Guide"
  pageNav: 3
---

# B2B4U User Guide

Business to Business for You (B2B4U) is a **desktop app for managing contacts, optimized for use via a  Line Interface** (CLI) while still having the benefits of a Graphical User Interface (GUI). If you can type fast, B2B4U can get your contact management tasks done faster than traditional GUI apps.

--------------------------------------------------------------------------------------------------------------------

## Quick start

1. Ensure you have Java `17` or above installed in your Computer.<br>
   **Mac users:** Ensure you have the precise JDK version prescribed [here](https://se-education.org/guides/tutorials/javaInstallationMac.html).

1. Download the latest `.jar` file from [here](https://github.com/AY2526S2-CS2103T-T08-1/tp/releases).

1. Copy the file to the folder you want to use as the _home folder_ for your B2B4U application.

1. Double-click the B2B4U.jar file.<br>
   _Alternatively,_ open a command terminal, `cd` into the folder you put the jar file in, and use the `java -jar B2B4U.jar` command to run the application.<br>
   A GUI similar to the below should appear in a few seconds. Note how the app contains some sample data.<br>
   ![Ui]({{ baseUrl }}/images/Ui.png)

1. Type the command in the command box and press Enter to execute it. e.g. typing **`help`** and pressing Enter will open the help window.<br>
   Some example commands you can try:

    * `list` : Lists all contacts.

    * `add n/John Doe p/98765432 e/johnd@example.com a/John street, block 123, #01-01` : Adds a contact named `John Doe` to the contact list.

    * `view 1` : Opens the detail panel for the 1st contact.

    * `close view` : Closes the contact detail panel.

    * `delete 3` : Deletes the 3rd contact shown in the current list.

    * `clear` : Deletes all contacts.

    * `exit` : Exits the app.

1. Refer to [Features]({{ baseUrl }}/UserGuide.html#features) for details of each command.

--------------------------------------------------------------------------------------------------------------------

## Features

<box type="info" seamless>

**Notes about the command format:**<br>

* Words in `SCREAMING_SNAKE_CASE` are the parameters to be supplied by the user.<br>
  e.g. in `add n/NAME`, `NAME` is a parameter which can be used as `add n/John Doe`.

* Items in square brackets are optional.<br>
  e.g `n/NAME [t/TAG]` can be used as `n/John Doe t/friend` or as `n/John Doe`.

* Items with `…`​ after them can be used multiple times including zero times.<br>
  e.g. `[t/TAG]…​` can be used as ` ` (i.e. 0 times), `t/friend`, `t/friend t/family` etc.

* Parameters can be in any order.<br>
  e.g. if the command specifies `n/NAME p/PHONE_NUMBER`, `p/PHONE_NUMBER n/NAME` is also acceptable.

* Extraneous parameters for commands that do not take in parameters (such as `help`, `list`, `exit` and `clear`) will be ignored.<br>
  e.g. if the command specifies `help 123`, it will be interpreted as `help`.

* If you are using a PDF version of this document, be careful when copying and pasting commands that span multiple lines as space characters surrounding line-breaks may be omitted when copied over to the application.

* If a command is not recognised, an error message will be displayed.<br>
  ![unknown command]({{ baseUrl }}/images/unknownCommand.png)
  </box>

### Commands
<!-- TODO: Add high-level overview of functionality brought by commands -->

### Saving data

B2B4U data is saved to the hard disk automatically after any command that changes the data.
There is no need to save manually.

### Editing the data file

B2B4U data is saved automatically as a JSON file `[JAR file location]/data/addressbook.json`.
Advanced users are welcome to update data directly by editing that data file.

<box type="warning" seamless>

**Caution:**
If your changes to the data file makes its format invalid, B2B4U will discard all data and start with an empty data file at the next run.  Hence, it is recommended to make a backup of the file before editing it.<br>
Furthermore, certain edits can cause the B2B4U application to behave in unexpected ways (e.g., if a value entered is outside the acceptable range). Therefore, edit the data file only if you are confident that you can update it correctly.
</box>

### Separate data files

B2B4U allows you to maintain multiple separate data files.
This is useful if you want to maintain separate contact lists for different purposes (e.g. work vs personal contacts).
All data files must be placed in the data folder: `[JAR file location]/data/`.

- To view a list of all available data files, use the `view files` command.
- To open a specific data file, use the `file open/FILE_NAME` command.
- To delete a specific data file, use the `file delete/FILE_NAME` command.

For more information, refer to the [Command summary](#command-summary) at the end of this document,
or the specific pages for [viewing]({{ baseUrl }}/user-guide/view.html) and [managing]({{ baseUrl }}/user-guide/file.html) files.

--------------------------------------------------------------------------------------------------------------------

## FAQ & Known Issues

### FAQ

**Q**: How do I transfer my data to another Computer?<br>
**A**: Install the app in the other computer and overwrite the empty data file it creates with the file that contains the data of your previous B2B4U home folder.

--------------------------------------------------------------------------------------------------------------------

### Known Issues

1. **When using multiple screens**, if you move the application to a secondary screen, and later switch to using only the primary screen, the GUI will open off-screen. The remedy is to delete the `preferences.json` file created by the application before running the application again.
2. **If you minimize the Help Window** and then run the `help` command (or use the `Help` menu, or the keyboard shortcut `F1`) again, the original Help Window will remain minimized, and no new Help Window will appear. The remedy is to manually restore the minimized Help Window.

--------------------------------------------------------------------------------------------------------------------

## Command summary

| Action                            | Format         | Parameters                                                                                                                    | Examples                                                                                           |
|-----------------------------------|----------------|-------------------------------------------------------------------------------------------------------------------------------|----------------------------------------------------------------------------------------------------|
| **Help**                          | `help`         | `[COMMAND]`                                                                                                                   | `help add`                                                                                         |
| **Add contact**                   | `add`          | `n/NAME [p/PHONE_NUMBER] [e/EMAIL] [a/ADDRESS] [lc/LAST_CONTACTED] [t/TAG]…​`                                                 | `add n/James Ho p/22224444 e/jamesho@example.com a/123, Clementi Rd, 1234665 t/friend t/colleague` |
| **Edit contact**                  | `edit`         | `INDEX [n/NAME] [p/PHONE_NUMBER] [e/EMAIL] [a/ADDRESS] [lc/LAST_CONTACTED] [t/TAG]…​`                                         | `edit 2 n/James Lee e/jameslee@example.com`                                                        |
| **Delete contact**                | `delete`       | `INDEX`                                                                                                                       | `delete 3`                                                                                         |
| **Clear contacts**                | `clear`        |                                                                                                                               |                                                                                                    |
| **Note (add)**                    | `note`         | `INDEX NOTE [on/TIME]`                                                                                                        | `note 1 To meet in February on/15 Apr`                                                             |
| **Note (edit)**                   | `note`         | `INDEX el/NOTE_INDEX NEW_NOTE [on/TIME]`                                                                                      | `note 1 el/1 Updated note text.`                                                                   |
| **Note (remove specific)**        | `note`         | `INDEX cl/NOTE_INDEX`                                                                                                         | `note 1 cl/2`                                                                                      |
| **Note (remove)**                 | `note`         | `INDEX c/LINES_TO_REMOVE`                                                                                                     | `note 1 c/2`                                                                                       |
| **Note (clear)**                  | `note`         | `INDEX ca/`                                                                                                                   | `note 1 ca/`                                                                                       |
| **List contacts**                 | `list`         |                                                                                                                               |                                                                                                    |
| **Find contacts**                 | `find`         | `[KEYWORD]… [n/NAME] [p/PHONE] [e/EMAIL] [a/ADDRESS] [t/TAG]…`                                                                | `find n/James t/friends`                                                                           |
| **Find contacts(by association)** | `find`         | `@INDEX`                                                                                                                      | `find @1`                                                                                          |
| **Remove filters**                | `find`         |                                                                                                                               |                                                                                                    |
| **Sort contacts**                 | `sort`         | `[n/asc \| desc] [p/asc \| desc] [e/asc \| desc] [a/asc \| desc] [lu/asc \| desc] [lc/asc \| desc] [t/TAG_NAME:asc \| desc]…` | `sort n/asc t/friends:desc`                                                                        |
| **Sort by last updated**          | `sort`         |                                                                                                                               |                                                                                                    |
| **Undo**                          | `undo`         |                                                                                                                               |                                                                                                    |
| **Redo**                          | `redo`         |                                                                                                                               |                                                                                                    |
| **View contact**                  | `view`         | `INDEX`                                                                                                                       | `view 1`                                                                                           |
| **Close view**                    | `close view`   |                                                                                                                               |                                                                                                    |
| **Open file**                     | `file open/`   | `FILE_NAME`                                                                                                                   | `file open/newContactList`                                                                         |
| **Delete file**                   | `file delete/` | `FILE_NAME`                                                                                                                   | `file delete/OldContactList`                                                                       |
| **View list of available files**  | `view files`   |                                                                                                                               |                                                                                                    |
| **Change theme**                  | `theme`        | `THEME_NAME`                                                                                                                  | `theme sakura`                                                                                     |
