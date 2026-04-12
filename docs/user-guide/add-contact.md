# Adding a contact: `add`

Adds a contact to the contact list.

Format: `add n/NAME [p/PHONE_NUMBER] [e/EMAIL] [a/ADDRESS] [lc/LAST_CONTACTED] [t/TAG]…​`

<box type="tip" seamless>

**Tip:** A contact can have any number of tags (including 0). At least one of `p/PHONE_NUMBER` or `e/EMAIL` must be provided.
</box>

Examples:
* `add n/John Doe p/98765432 e/johnd@example.com a/John street, block 123, #01-01`
* `add n/Betsy Crowe t/friend e/betsycrowe@example.com a/Newgate Prison p/1234567 t/criminal`
* `add n/Alex Tan p/91234567`
* `add n/Jane Smith e/jane@example.com lc/22/Feb/2026`

![add contact]({{ baseUrl }}/images/addContact.png)

### Similar contacts
After a successful `add` command, the contact list will be reset to display every contact in the default sort order, then if there are similar contacts in the list, the contact list will be displayed to display the similar contacts.

Two contacts are similar if:

- Both contacts share the same name
- Both contacts share the same phone number
- Both contacts share the same email address

![add contact]({{ baseUrl }}/images/addContact-similar.png)
