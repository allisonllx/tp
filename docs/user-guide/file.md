# Open file: `file open/`

Changes the contact list file being accessed to a different one in the data subfolder.

Format: `file open/FILE_NAME`

* Accesses the file named `FILE_NAME`.json in the data subfolder.
* If `FILE_NAME`.json does not already exist, it will be created with an empty contact list.
* `FILE_NAME` can only contain **Alphanumeric characters and/or the underscore '_' character**.

Examples:
* If new_file.json does not exist, `file open/new_file` will create new_file.json and allow immediate access to it.

![Open file]({{ baseUrl }}/images/file-open.png)

# Deleting a file: `file delete/`

Deletes the specified file from the data subfolder.

Format: `file delete/FILE_NAME`

* Deletes the file named `FILE_NAME`.json in the data subfolder.
* `FILE_NAME`.json **must exist in the data subfolder**.
* `FILE_NAME`.json **cannot be the currently accessed file**.
* If `FILE_NAME`.json is not empty, an alert window will pop up and require confirmation of deletion.

Examples:
* If empty.json does not contain any contacts, `file delete/empty` will delete said file without issue.

![Delete empty file]({{ baseUrl }}/images/deleteEmptyFile.png)

* If oldContactList.json contains at least one contact, `file delete/oldContactList` will trigger an alert window to appear and prompt for confirmation.
  
![Delete file]({{ baseUrl }}/images/deleteFile.png)
