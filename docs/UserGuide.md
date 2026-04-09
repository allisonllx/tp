---
  layout: default.md
  title: "User Guide"
  pageNav: 3
---

# B2B4U User Guide

Business to Business for You (B2B4U) is a **desktop app for managing contacts, optimized for use via a Command Line Interface** (CLI) while still having the benefits of a Graphical User Interface (GUI). If you can type fast, B2B4U can get your contact management tasks done faster than traditional GUI apps.

## Table of Contents

- [B2B4U User Guide](#b2b4u-user-guide)
  - [Table of Contents](#table-of-contents)
  - [Quick start](#quick-start)
  - [Features](#features)
    - [Command Format](#command-format)
    - [Adding contacts](#adding-contacts)
    - [Editing contacts](#editing-contacts)
    - [Deleting contacts](#deleting-contacts)
      - [Delete a specific contact](#delete-a-specific-contact)
      - [Clear all contacts](#clear-all-contacts)
    - [Viewing a contact](#viewing-a-contact)
    - [Managing notes for a contact](#managing-notes-for-a-contact)
      - [Reminders](#reminders)
    - [Filtering and sorting the contact list](#filtering-and-sorting-the-contact-list)
    - [Undo and redo](#undo-and-redo)
    - [Setting the theme](#setting-the-theme)
    - [Saving data](#saving-data)
    - [Maintaining separate data files](#maintaining-separate-data-files)
    - [Editing the data file directly](#editing-the-data-file-directly)
    - [Exiting B2B4U](#exiting-b2b4u)
  - [FAQ](#faq)
  - [Known Issues](#known-issues)
  - [Command summary](#command-summary)

--------------------------------------------------------------------------------------------------------------------

## Quick start

1. Ensure **Java `17` or later is installed** on your device.<br>
   *Mac users:* Ensure you have the exact JDK version specified [here](https://se-education.org/guides/tutorials/javaInstallationMac.html).

2. Download the latest `B2B4U.jar` file from [here](https://github.com/AY2526S2-CS2103T-T08-1/tp/releases).

3. Copy the file to the folder you want to use as the _home directory_ for your B2B4U application.

4. Run the application:
   - Option 1: Double-click the `B2B4U.jar` file.
   - Option 2: Open a terminal, navigate (using `cd` command) to the folder containing the `B2B4U.jar` file, and run:
     ```
     java -jar B2B4U.jar
     ```
5. Within a few seconds, a GUI similar to the one below should appear. The application comes with **sample data** preloaded.

   ![Ui]({{ baseUrl }}/images/Ui.png)

6. Type the command in the command box and press Enter to execute it. e.g. typing **`help`** and pressing Enter will open the help window.<br>
   Some example commands you can try:

    * `list` : Lists all contacts.

    * `add n/John Doe p/98765432 e/johnd@example.com a/John street, block 123, #01-01` : Adds a contact named `John Doe` to the contact list.

    * `view 1` : Opens the detail panel for the 1st contact.

    * `close view` : Closes the contact detail panel.

    * `delete 3` : Deletes the 3rd contact shown in the current list.

    * `clear` : Deletes all contacts.

    * `exit` : Exits the app.

7. Refer to [Features]({{ baseUrl }}/UserGuide.html#features) for a full list of commands, or each command's subpages for additional details.

--------------------------------------------------------------------------------------------------------------------

## Features

### Command Format

<box type="info" seamless>

**Notes about the command format:**<br>

* Words in `SCREAMING_SNAKE_CASE` are the parameters to be supplied by the user.<br>
  e.g. in `add n/NAME`, `NAME` is a parameter which can be used as `add n/John Doe`.

* Items in square brackets are optional.<br>
  e.g. `n/NAME [t/TAG]` can be used as `n/John Doe t/friend` or as `n/John Doe`.

* Items with `…`​ after them can be used multiple times including zero times.<br>
  e.g. `[t/TAG]…​` can be used as ` ` (i.e. 0 times), `t/friend`, `t/friend t/family` etc.

* Prefixed parameters (e.g. `t/TAG`, `open/FILE`) can be in any order, however the order affects results in the `sort` command.<br>
  e.g. if the command specifies `n/NAME p/PHONE`, `p/PHONE n/NAME` is also acceptable.

* Extraneous parameters for commands that do not take in parameters (such as `list`, `exit` and `clear`) will fail to execute.<br>
  e.g. if the command specifies `exit 123`, it will fail.

* If you are using a PDF version of this document, be careful when copying and pasting commands that span multiple lines as spaces around line breaks may be lost when copied into the application.

* If a command is not recognized, an error message will be displayed.<br>
  ![unknown command]({{ baseUrl }}/images/unknownCommand.png)
  </box>


### Adding contacts

To add a new contact, use the [`add` command]({{ baseUrl }}/user-guide/add-contact.html).

Format: `add n/NAME [p/PHONE] [e/EMAIL] [a/ADDRESS] [lc/LAST_CONTACTED] [t/TAG]…​`

* At least one of `p/PHONE` or `e/EMAIL` must be provided.
* Names are standardized to Title Case.
* After adding, if there are similar contacts, the list will filter to display them.

Examples:
* `add n/John Doe p/98765432 e/johnd@example.com a/John street, block 123, #01-01`
* `add n/Alex Tan p/91234567`

### Editing contacts

To edit an existing contact, use the [`edit` command]({{ baseUrl }}/user-guide/edit-contact.html).

Format: `edit INDEX [n/NAME] [p/PHONE] [e/EMAIL] [a/ADDRESS] [lc/LAST_CONTACTED] [t/TAG]…​`

* At least one optional field must be provided.
* When editing tags, the existing tags are replaced entirely (not cumulative).

Examples:
* `edit 1 p/91234567 e/johndoe@example.com`
* `edit 2 n/Betsy Crower t/`

### Deleting contacts

- To delete a specific displayed contact, use the [`delete INDEX` command]({{ baseUrl }}/user-guide/delete-contact.html).
- To delete all contacts at once, use the [`clear` command]({{ baseUrl }}/user-guide/clear-contacts.html).

#### Delete a specific contact

Format: `delete INDEX`

- `INDEX` refers to the index number shown in the displayed contact list.
- The index must be a positive integer (e.g. 1, 2, 3…).

Examples:

- `delete 3` — deletes the 3rd contact in the current list.

#### Clear all contacts

Format: `clear`

<box type="warning" seamless>
Caution: <code>clear</code> permanently removes all contacts. This action can be undone using the <code>undo</code> command.
</box>

### Viewing a contact

To view the details of a contact, use the [`view` command]({{ baseUrl }}/user-guide/view.html).

Format: `view INDEX`

- `INDEX` refers to the index number shown in the displayed contact list.
- The index must be a positive integer (e.g. 1, 2, 3…).

Examples:

- `view 1` — opens the detail panel for the 1st contact.

To close the detail panel, use the [`close view` command]({{ baseUrl }}/user-guide/close-view.html).

Format: `close view`

### Managing notes for a contact

To manage notes and reminders for a contact, use the [`note` command]({{ baseUrl }}/user-guide/notes.html).

* [**Add a note:** `note INDEX NOTE [on/TIME]`]({{ baseUrl }}/user-guide/notes.html#add-a-note) — appends a note to the contact. Including `on/TIME` turns it into a [reminder]({{ baseUrl }}/user-guide/notes.html#reminders).
* [**Edit a note:** `note INDEX el/NOTE_INDEX NEW_NOTE [on/TIME]`]({{ baseUrl }}/user-guide/notes.html#edit-a-specific-note) — replaces a specific note.
* [**Remove a specific note:** `note INDEX cl/NOTE_INDEX`]({{ baseUrl }}/user-guide/notes.html#remove-a-specific-note) — removes the note at that position.
* [**Remove first N notes:** `note INDEX c/LINES_TO_REMOVE`]({{ baseUrl }}/user-guide/notes.html#remove-the-first-n-notes) — removes the first N notes, where N = `LINES_TO_REMOVE`.
* [**Clear all notes:** `note INDEX ca/`]({{ baseUrl }}/user-guide/notes.html#clear-all-notes) — removes all notes from the contact.

Notes support **contact references** using the `@INDEX` syntax, which creates a bidirectional association between the two contacts. This means both contacts will appear when searching for either one using `find @INDEX`.

#### Reminders

B2B4U can remind you of upcoming events, as every contact with a reminder in its notes will gain a `Reminder` tag which turns red once the reminder is due in 7 days.
During this period, a window will also pop up to remind you of the upcoming event every time you start B2B4U.

![reminder]({{ baseUrl }}/images/reminder.png)

### Filtering and sorting the contact list

By default, B2B4U will display every contact within the contact list, sorted to show contacts with the most urgent reminders first, followed by those with the most recently contacted date.
Beyond that, B2B4U also provides commands to filter and sort the contact list to quickly find contacts that fit specific criteria when managing a large contact list.

- To filter, use the [`find` command]({{ baseUrl }}/user-guide/find-contacts.html) with keywords or field-specific prefixes (e.g. `find n/Alex t/friends`). Use `find @INDEX` to find contacts associated with the contact at that index.
- To remove contact filters, use the [`find` command without any keywords]({{ baseUrl }}/user-guide/find-contacts.html#clearing-filters).
- To sort, use the [`sort` command]({{ baseUrl }}/user-guide/sort-contacts.html) with field prefixes followed by `asc` or `desc` to specify the [sort direction]({{ baseUrl }}/user-guide/sort-contacts.html#sort-order-by-field) (e.g. `sort n/asc` to sort by name ascending, `sort lc/desc` to sort by last contacted descending).
- To reset the sort order, use the [`sort` command without any keywords]({{ baseUrl }}/user-guide/sort-contacts.html#resetting-sort-order).

Active filters and sort orders are preserved independently. Running `sort` will not clear your current filter, and vice versa. Note that the following commands may also change the active filter or sort order:

- A [`list` command]({{ baseUrl }}/user-guide/list-contacts.html) will display every contact in the default sort order.
- An [`add` command]({{ baseUrl }}/user-guide/add-contact.html#similar-contacts) will reset the sort order, and may filter to display only similar contacts.

### Undo and redo

B2B4U allows you to undo and redo commands to prevent data loss due to mistakes.

- To undo the last command, use the [`undo` command]({{ baseUrl }}/user-guide/undo-command.html).
- To redo the last command, use the [`redo` command]({{ baseUrl }}/user-guide/redo-command.html).

### Setting the theme

B2B4U features a variety of color palettes (referred to as 'themes') to customize your experience.

To change to a different theme, use the [`theme THEME_NAME` command]({{ baseUrl }}/user-guide/set-theme.html).

Available themes:
- [Dark mode: `dark`]({{ baseUrl }}/user-guide/set-theme.html#dark-mode-dark)
- [Light mode: `light`]({{ baseUrl }}/user-guide/set-theme.html#light-mode-light)
- [Reading mode: `book`]({{ baseUrl }}/user-guide/set-theme.html#reading-mode-book)
- [Sakura theme: `sakura`]({{ baseUrl }}/user-guide/set-theme.html#sakura-theme-sakura)
- [Grass theme: `grass`]({{ baseUrl }}/user-guide/set-theme.html#grass-theme-grass)
- [Techcore theme: `tech`]({{ baseUrl }}/user-guide/set-theme.html#techcore-tech)
- [Jirai Kei theme: `jirai`]({{ baseUrl }}/user-guide/set-theme.html#jirai-kei-jirai)

### Saving data

B2B4U data is saved to the hard disk automatically after any command that changes the data.
There is no need to save manually.

### Maintaining separate data files

B2B4U allows you to maintain multiple separate data files.
This is useful if you want to maintain separate contact lists for different purposes (e.g. work vs personal contacts).
All data files must be placed in the data folder: `[JAR file location]/data/`.

- To open a specific data file, use the [`file open/FILE_NAME` command]({{ baseUrl }}/user-guide/file.html#open-file-file-open).
- To delete a specific data file, use the [`file delete/FILE_NAME` command]({{ baseUrl }}/user-guide/file.html#deleting-a-file-file-delete).
- To view a list of all available data files, use the [`view files` command]({{ baseUrl }}/user-guide/view.html#viewing-available-files-view-files).
- To close the file list panel, use the [`close view` command]({{ baseUrl }}/user-guide/close-view.html). The file list is displayed in the same panel used to view contacts.


### Editing the data file directly

By default, B2B4U data is saved automatically as JSON files within the `[JAR file location]/data` folder.
Advanced users are welcome to update data directly by editing that data file.

<box type="warning" seamless>

**Caution:**
If your changes to the data file make its format invalid, B2B4U will discard all data and start with an empty data file at the next run. Hence, it is recommended to make a backup of the file before editing it.<br>
Furthermore, certain edits can cause the B2B4U application to behave in unexpected ways (e.g., if a value entered is outside the acceptable range). Therefore, edit the data file only if you are confident that you can update it correctly.
</box>

### Exiting B2B4U

To exit B2B4U, use the [`exit` command]({{ baseUrl }}/user-guide/exit-program.html).

--------------------------------------------------------------------------------------------------------------------

## FAQ

**Q**: How do I transfer my data to another computer?<br>
**A**: Install the app in the other computer and overwrite the empty data file it creates with the file that contains the data of your previous B2B4U home folder.

--------------------------------------------------------------------------------------------------------------------

## Known Issues

1. **When using multiple screens**, if you move the application to a secondary screen, and later switch to using only the primary screen, the GUI will open off-screen. The remedy is to delete the `preferences.json` file created by the application before running the application again.
2. **If you minimize the Help Window** and then run the `help` command (or use the `Help` menu, or the keyboard shortcut `F1`) again, the original Help Window will remain minimized, and no new Help Window will appear. The remedy is to manually restore the minimized Help Window.

--------------------------------------------------------------------------------------------------------------------

## Command summary

| Action                                 | Format         | Parameters                                                                                                                    | Examples                                                                                           |
| -------------------------------------- | -------------- | ----------------------------------------------------------------------------------------------------------------------------- | -------------------------------------------------------------------------------------------------- |
| **Help**                               | `help`         | `[COMMAND]`                                                                                                                   | `help add`                                                                                         |
| **Add contact**                        | `add`          | `n/NAME [p/PHONE] [e/EMAIL] [a/ADDRESS] [lc/LAST_CONTACTED] [t/TAG]…​`                                                        | `add n/James Ho p/22224444 e/jamesho@example.com a/123, Clementi Rd, 1234665 t/friend t/colleague` |
| **Edit contact**                       | `edit`         | `INDEX [n/NAME] [p/PHONE] [e/EMAIL] [a/ADDRESS] [lc/LAST_CONTACTED] [t/TAG]…​`                                                | `edit 2 n/James Lee e/jameslee@example.com`                                                        |
| **Delete contact**                     | `delete`       | `INDEX`                                                                                                                       | `delete 3`                                                                                         |
| **Clear contacts**                     | `clear`        |                                                                                                                               |                                                                                                    |
| **Note (add)**                         | `note`         | `INDEX NOTE [on/TIME]`                                                                                                        | `note 1 To meet in February on/15 Apr`                                                             |
| **Note (edit)**                        | `note`         | `INDEX el/NOTE_INDEX NEW_NOTE [on/TIME]`                                                                                      | `note 1 el/1 Updated note text.`                                                                   |
| **Note (remove specific)**             | `note`         | `INDEX cl/NOTE_INDEX`                                                                                                         | `note 1 cl/2`                                                                                      |
| **Note (remove)**                      | `note`         | `INDEX c/LINES_TO_REMOVE`                                                                                                     | `note 1 c/2`                                                                                       |
| **Note (clear)**                       | `note`         | `INDEX ca/`                                                                                                                   | `note 1 ca/`                                                                                       |
| **List contacts**                      | `list`         |                                                                                                                               |                                                                                                    |
| **Find contacts**                      | `find`         | `[KEYWORD]… [n/NAME] [p/PHONE] [e/EMAIL] [a/ADDRESS] [t/TAG]…`                                                                | `find n/James t/friends`                                                                           |
| **Find contacts (by association)**     | `find`         | `@INDEX`                                                                                                                      | `find @1`                                                                                          |
| **Remove filters**                     | `find`         |                                                                                                                               |                                                                                                    |
| **Sort contacts**                      | `sort`         | `[n/asc \| desc] [p/asc \| desc] [e/asc \| desc] [a/asc \| desc] [lu/asc \| desc] [lc/asc \| desc] [t/TAG_NAME:asc \| desc]…` | `sort n/asc t/friends:desc`                                                                        |
| **Remove sort (default last updated)** | `sort`         |                                                                                                                               |                                                                                                    |
| **Undo**                               | `undo`         |                                                                                                                               |                                                                                                    |
| **Redo**                               | `redo`         |                                                                                                                               |                                                                                                    |
| **View contact**                       | `view`         | `INDEX`                                                                                                                       | `view 1`                                                                                           |
| **Close view**                         | `close view`   |                                                                                                                               |                                                                                                    |
| **Open file**                          | `file open/`   | `FILE_NAME`                                                                                                                   | `file open/newContactList`                                                                         |
| **Delete file**                        | `file delete/` | `FILE_NAME`                                                                                                                   | `file delete/OldContactList`                                                                       |
| **View list of available files**       | `view files`   |                                                                                                                               |                                                                                                    |
| **Change theme**                       | `theme`        | `THEME_NAME`                                                                                                                  | `theme sakura`                                                                                     |
