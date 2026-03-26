---
  layout: default.md
  title: "Implementation"
  pageNav: 3
---

# **Implementation**

This section describes some noteworthy details on how certain features are implemented.

## Undo/redo feature

The undo/redo mechanism is facilitated by `Snapshot`. It stores key information regarding a `Model`, stored internally as an `List<Pair<String, Snapshot>>` named `snapshots` and an `int snapshotPosition` is used to indicate the `Snapshot` being used. Additionally, `Model` implements the following methods:

* `saveSnapshot(String description)` — Saves the current `Model` state with a name for user reference.
* `moveSnapshot(int offset)` — Moves the `Model` by `offset` number of snapshots in its history.


Given below is an example usage scenario and how the undo/redo mechanism behaves at each step.

Step 1. The user launches the application for the first time. The `ModelManager`, the instantiable version of `Model`, will be initialized with the initial `snapshotPosition` of `0`, and a singular `snapshot` in `snapshots` representing the `ModelManager`'s current state.

<puml src="/diagrams/UndoRedoState0.puml" alt="UndoRedoState0" />

Step 2. The user executes `add n/John …​` command to add a new contact. The `add` command calls `Model#saveSnapshot(feedback)`, where `feedback` is the string in the `CommandResult` from executing the `AddCommand`, "New contact added: John…​", thus a `snapshot` of the `ModelManager` after the `add n/John…​` command executes to be saved in `snapshot`, and the `snapshotPosition` is incremented.

<puml src="/diagrams/UndoRedoState1.puml" alt="UndoRedoState1" />

Step 3. The user executes `delete 7` to delete the 7th contact which happens to be the most recently added contact. The `delete` command also calls `Model#saveSnapshot(feedback)`, causing another `snapshot` to be saved into `snapshots`.

<puml src="/diagrams/UndoRedoState2.puml" alt="UndoRedoState2" />

<box type="info" seamless>

**Note:** If a command fails its execution, it will not call `Model#saveSnapshot(feedback)`, so no new `snapshot` will be created.

</box>

Step 4. The user now decides that deleting the contact was a mistake, and decides to undo that action by executing the `undo` command. The `undo` command will call `Model#moveSnapshot(-1)`, which will decrement `snapshotPosition`, and restores `ModelManager` with data given by the `snapshotPosition`-th `snapshot`.

<puml src="/diagrams/UndoRedoState3.puml" alt="UndoRedoState3" />


<box type="info" seamless>

**Note:** If `snapshotPosition` is 0, pointing to the initial `Model`'s `snapshot`, then there are no previous `snapshot` to restore to. In this case an `IndexOutOfBoundsException` will be thrown.

</box>

The following sequence diagram shows how an undo operation goes through the `Logic` component:

<puml src="/diagrams/UndoSequenceDiagram-Logic.puml" alt="UndoSequenceDiagram-Logic" />

<box type="info" seamless>

**Note:** The lifeline for `UndoCommand` should end at the destroy marker (X) but due to a limitation of PlantUML, the lifeline reaches the end of diagram.

</box>

Similarly, how an undo operation goes through the `Model` component is shown below:

<puml src="/diagrams/UndoSequenceDiagram-Model.puml" alt="UndoSequenceDiagram-Model" />

The `redo` command does the opposite — it calls `Model#moveSnapshot(1)`. If `snapshotPosition` is less than `snapshots.size() - 1`, `snapshotPosition` is incremented, then the `snapshotPosition`-th `snapshot` is retrieved to restores the `ModelManager` to the state it represents.

<box type="info" seamless>

**Note:** If the `currentStatePointer` is at index `addressBookStateList.size() - 1`, pointing to the latest address book state, then there are no undone AddressBook states to restore. The `redo` command uses `Model#canRedoAddressBook()` to check if this is the case. If so, it will return an error to the user rather than attempting to perform the redo.

</box>

Step 5. The user then decides to execute the command `list`. Commands that do not modify the address book, such as `list`, will usually not call `Model#saveSnapshot()` or `Model#moveSnapshot()`. Thus, the `snapshotPosition` remains unchanged.

<puml src="/diagrams/UndoRedoState4.puml" alt="UndoRedoState4" />

Step 6. The user executes `clear`, which calls `Model#saveSnapshot()`. Since the `snapshotPosition` is not equal to `snapshots.size() - 1`, all snapshots after the `snapshotPosition`-th `snapshot` will be purged. Reason: It no longer makes sense to redo the `delate 7` command. This is the behavior that most modern desktop applications follow.

<puml src="/diagrams/UndoRedoState5.puml" alt="UndoRedoState5" />

The following activity diagram summarizes what happens when a user executes a new command:

<puml src="/diagrams/CommitActivityDiagram.puml" width="250" />

### Design considerations:

**Aspect: How undo & redo executes:**

* **Alternative 1 (current choice):** Saves a compact copy of the model.
  * Pros: Easy to implement.
  * Cons: May have performance issues in terms of memory usage even with reduced object size.

* **Alternative 2:** Save an 'undo/redo' version of each command.
  * Pros: Will use less memory (e.g. for `delete`, just save the contact being deleted).
  * Cons: We must ensure that the implementation of each individual command are correct.

_{more aspects and alternatives to be added}_

## \[Proposed\] Data archiving

_{Explain here how the data archiving feature will be implemented}_
