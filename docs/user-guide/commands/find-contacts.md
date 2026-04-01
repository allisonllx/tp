### Finding contacts: `find`

Finds contacts whose fields match the specified search criteria.

Format: `find [KEYWORD]… [n/NAME] [p/PHONE] [e/EMAIL] [a/ADDRESS] [t/TAG]…`
or: `find @INDEX` to find contacts associated with the contact at INDEX

* The search is case-insensitive. e.g. `hans` will match `Hans`.
* Unprefixed `KEYWORD`s search across all fields (name, phone, email, address, notes, tags) using partial matching. Each keyword must appear somewhere in the contact.
* Prefixed searches (`n/`, `p/`, `e/`, `a/`) filter by the specified field using partial matching.
* `t/TAG` filters by tag using **exact** matching (e.g. `t/friend` will not match a tag named `friends`).
* All search conditions are combined with **AND** logic — only contacts satisfying **every** condition are returned.
* At least one search condition must be provided.
* `find @INDEX` performs a **bidirectional** cross-reference lookup on the contact at the given index:
  * It finds all contacts that are **referenced by** the target contact's notes (via `@INDEX` references).
  * It also finds all contacts whose notes **reference** the target contact.
  * This allows you to see all associations regardless of which direction the reference was made.

Examples:
* `find John` returns contacts containing `john` in any field.
* `find n/Alex` returns contacts with `Alex` in their name.
* `find p/94` returns contacts with `94` in their phone number.
* `find a/street t/friends` returns contacts that have `street` in their address **and** the exact tag `friends`.
* `find @1` shows all contacts associated with the 1st contact — both contacts referenced in their notes and contacts that reference them.
* If Contact 1's notes contain `@2` (a reference to Contact 2), both `find @1` and `find @2` will show the association between them.

![find contacts]({{ baseUrl }}/images/findContacts.png)
