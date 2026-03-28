---
  layout: default.md
  title: "Implementation"
  pageNav: 3
---

# **Implementation**

This section describes some noteworthy details on how certain features are implemented.

## Remove field on empty edit

### Overview

The edit command supports removing optional fields (phone, email, address, last contacted date) by supplying the field prefix with no argument. For example, `edit 1 p/` removes the phone number from the first contact.

### Implementation

The following sequence diagram shows how the edit field removal mechanism works when the user executes `edit 1 p/`:

<puml src="{{ baseUrl }}/diagrams/EditSequenceDiagram.puml" alt="EditSequenceDiagram" />

1. `EditCommandParser` detects the empty value for the `p/` prefix and sets `clearPhone = true` in the `EditContactDescriptor`.
2. `EditCommand#createEditedContact()` checks the clear flag — if `clearPhone` is true, `updatedPhone` is set to `Optional.empty()`.
3. Before applying the edit, the command validates that the resulting contact retains at least a phone number or email address.
4. The updated contact is saved to the model.

### Design considerations

**Aspect: How empty prefix values are handled:**

* **Alternative 1 (current choice):** Dedicated boolean clear flags (`clearPhone`, `clearEmail`, etc.) in `EditContactDescriptor`.
  * Pros: Explicit intent — distinguishes "remove field" from "no change". Avoids null/sentinel confusion.
  * Cons: Adds extra fields and logic to the descriptor.

* **Alternative 2:** Use a sentinel value (e.g. empty string) to represent removal.
  * Pros: Fewer fields — reuses the existing `Optional<Phone>` field.
  * Cons: Sentinel values are error-prone and harder to reason about. Validation logic in `Phone`, `Email`, etc. would need special-case handling.

## \[Proposed\] Undo/redo feature

### Proposed Implementation

The proposed undo/redo mechanism is facilitated by `VersionedAddressBook`. It extends `AddressBook` with an undo/redo history, stored internally as an `addressBookStateList` and `currentStatePointer`. Additionally, it implements the following operations:

* `VersionedAddressBook#commit()` — Saves the current address book state in its history.
* `VersionedAddressBook#undo()` — Restores the previous address book state from its history.
* `VersionedAddressBook#redo()` — Restores a previously undone address book state from its history.

These operations are exposed in the `Model` interface as `Model#commitAddressBook()`, `Model#undoAddressBook()` and `Model#redoAddressBook()` respectively.

Given below is an example usage scenario and how the undo/redo mechanism behaves at each step.

Step 1. The user launches the application for the first time. The `VersionedAddressBook` will be initialized with the initial address book state, and the `currentStatePointer` pointing to that single address book state.

<puml src="{{ baseUrl }}/diagrams/UndoRedoState0.puml" alt="UndoRedoState0" />

Step 2. The user executes `delete 5` command to delete the 5th contact in the address book. The `delete` command calls `Model#commitAddressBook()`, causing the modified state of the address book after the `delete 5` command executes to be saved in the `addressBookStateList`, and the `currentStatePointer` is shifted to the newly inserted address book state.

<puml src="{{ baseUrl }}/diagrams/UndoRedoState1.puml" alt="UndoRedoState1" />

Step 3. The user executes `add n/David …​` to add a new contact. The `add` command also calls `Model#commitAddressBook()`, causing another modified address book state to be saved into the `addressBookStateList`.

<puml src="{{ baseUrl }}/diagrams/UndoRedoState2.puml" alt="UndoRedoState2" />

<box type="info" seamless>

**Note:** If a command fails its execution, it will not call `Model#commitAddressBook()`, so the address book state will not be saved into the `addressBookStateList`.

</box>

Step 4. The user now decides that adding the contact was a mistake, and decides to undo that action by executing the `undo` command. The `undo` command will call `Model#undoAddressBook()`, which will shift the `currentStatePointer` once to the left, pointing it to the previous address book state, and restores the address book to that state.

<puml src="{{ baseUrl }}/diagrams/UndoRedoState3.puml" alt="UndoRedoState3" />


<box type="info" seamless>

**Note:** If the `currentStatePointer` is at index 0, pointing to the initial AddressBook state, then there are no previous AddressBook states to restore. The `undo` command uses `Model#canUndoAddressBook()` to check if this is the case. If so, it will return an error to the user rather
than attempting to perform the undo.

</box>

The following sequence diagram shows how an undo operation goes through the `Logic` component:

<puml src="{{ baseUrl }}/diagrams/UndoSequenceDiagram-Logic.puml" alt="UndoSequenceDiagram-Logic" />

<box type="info" seamless>

**Note:** The lifeline for `UndoCommand` should end at the destroy marker (X) but due to a limitation of PlantUML, the lifeline reaches the end of diagram.

</box>

Similarly, how an undo operation goes through the `Model` component is shown below:

<puml src="{{ baseUrl }}/diagrams/UndoSequenceDiagram-Model.puml" alt="UndoSequenceDiagram-Model" />

The `redo` command does the opposite — it calls `Model#redoAddressBook()`, which shifts the `currentStatePointer` once to the right, pointing to the previously undone state, and restores the address book to that state.

<box type="info" seamless>

**Note:** If the `currentStatePointer` is at index `addressBookStateList.size() - 1`, pointing to the latest address book state, then there are no undone AddressBook states to restore. The `redo` command uses `Model#canRedoAddressBook()` to check if this is the case. If so, it will return an error to the user rather than attempting to perform the redo.

</box>

Step 5. The user then decides to execute the command `list`. Commands that do not modify the address book, such as `list`, will usually not call `Model#commitAddressBook()`, `Model#undoAddressBook()` or `Model#redoAddressBook()`. Thus, the `addressBookStateList` remains unchanged.

<puml src="{{ baseUrl }}/diagrams/UndoRedoState4.puml" alt="UndoRedoState4" />

Step 6. The user executes `clear`, which calls `Model#commitAddressBook()`. Since the `currentStatePointer` is not pointing at the end of the `addressBookStateList`, all address book states after the `currentStatePointer` will be purged. Reason: It no longer makes sense to redo the `add n/David …​` command. This is the behavior that most modern desktop applications follow.

<puml src="{{ baseUrl }}/diagrams/UndoRedoState5.puml" alt="UndoRedoState5" />

The following activity diagram summarizes what happens when a user executes a new command:

<puml src="{{ baseUrl }}/diagrams/CommitActivityDiagram.puml" width="250" />

### Design considerations:

**Aspect: How undo & redo executes:**

* **Alternative 1 (current choice):** Saves the entire address book.
  * Pros: Easy to implement.
  * Cons: May have performance issues in terms of memory usage.

* **Alternative 2:** Individual command knows how to undo/redo by
  itself.
  * Pros: Will use less memory (e.g. for `delete`, just save the contact being deleted).
  * Cons: We must ensure that the implementation of each individual command are correct.

_{more aspects and alternatives to be added}_

## \[Proposed\] Data archiving

_{Explain here how the data archiving feature will be implemented}_
