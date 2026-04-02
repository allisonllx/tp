---
  layout: default.md
    title: "Design"
    pageNav: 3
---

# **Design**

## Architecture

<puml src="{{ baseUrl }}/diagrams/ArchitectureDiagram.puml" width="280" />

The ***Architecture Diagram*** given above explains the high-level design of the App.

Given below is a quick overview of main components and how they interact with each other.

**Main components of the architecture**

**`Main`** (consisting of classes [
`Main`](https://github.com/AY2526S2-CS2103T-T08-1/tp/blob/master/src/main/java/seedu/address/Main.java) and [
`MainApp`](https://github.com/AY2526S2-CS2103T-T08-1/tp/blob/master/src/main/java/seedu/address/MainApp.java)) is in
charge of the app launch and shut down.

* At app launch, it initializes the other components in the correct sequence, and connects them up with each other.
* At shut down, it shuts down the other components and invokes cleanup methods where necessary.

The bulk of the app's work is done by the following four components:

* [**`UI`**](#ui-component): The UI of the App.
* [**`Logic`**](#logic-component): The command executor.
* [**`Model`**](#model-component): Holds the data of the App in memory.
* [**`Storage`**](#storage-component): Reads data from, and writes data to, the hard disk.

[**`Commons`**](#common-classes) represents a collection of classes used by multiple other components.

**How the architecture components interact with each other**

The *Sequence Diagram* below shows how the components interact with each other for the scenario where the user issues
the command `delete 1`.

<puml src="{{ baseUrl }}/diagrams/ArchitectureSequenceDiagram.puml" width="574" />

Each of the four main components (also shown in the diagram above),

* defines its *API* in an `interface` with the same name as the Component.
* implements its functionality using a concrete `{Component Name}Manager` class (which follows the corresponding API
  `interface` mentioned in the previous point.

For example, the `Logic` component defines its API in the `Logic.java` interface and implements its functionality using
the `LogicManager.java` class which follows the `Logic` interface. Other components interact with a given component
through its interface rather than the concrete class (reason: to prevent outside component's being coupled to the
implementation of a component), as illustrated in the (partial) class diagram below.

<puml src="{{ baseUrl }}/diagrams/ComponentManagers.puml" width="300" />

The sections below give more details of each component.

## UI component

The **API** of this component is specified in [
`Ui.java`](https://github.com/AY2526S2-CS2103T-T08-1/tp/blob/master/src/main/java/seedu/address/ui/Ui.java)

<puml src="{{ baseUrl }}/diagrams/UiClassDiagram.puml" alt="Structure of the UI Component"/>

The UI consists of a `MainWindow` that is made up of parts e.g.`CommandBox`, `ResultDisplay`, `ContactListPanel`,
`StatusBarFooter` etc. All these, including the `MainWindow`, inherit from the abstract `UiPart` class which captures
the commonalities between classes that represent parts of the visible GUI.

The `UI` component uses the JavaFx UI framework. The layout of these UI parts are defined in matching `.fxml` files that
are in the `src/main/resources/view` folder. For example, the layout of the [
`MainWindow`](https://github.com/AY2526S2-CS2103T-T08-1/tp/blob/master/src/main/java/seedu/address/ui/MainWindow.java)
is specified in [
`MainWindow.fxml`](https://github.com/AY2526S2-CS2103T-T08-1/tp/blob/master/src/main/resources/view/MainWindow.fxml)

The `UI` component,

* executes user commands using the `Logic` component.
* listens for changes to `Model` data so that the UI can be updated with the modified data.
* keeps a reference to the `Logic` component, because the `UI` relies on the `Logic` to execute commands.
* depends on some classes in the `Model` component, as it displays `Contact` object residing in the `Model`.

## Logic component

**API** : [
`Logic.java`](https://github.com/AY2526S2-CS2103T-T08-1/tp/blob/master/src/main/java/seedu/address/logic/Logic.java)

Here's a (partial) class diagram of the `Logic` component:

<puml src="{{ baseUrl }}/diagrams/LogicClassDiagram.puml" width="550"/>

The sequence diagram below illustrates the interactions within the `Logic` component, taking `execute("delete 1")` API
call as an example.

<puml src="{{ baseUrl }}/diagrams/DeleteSequenceDiagram.puml" alt="Interactions Inside the Logic Component for the `delete 1` Command" />

<box type="info" seamless>

**Note:** The lifeline for `DeleteCommandParser` should end at the destroy marker (X) but due to a limitation of
PlantUML, the lifeline continues till the end of diagram.
</box>

How the `Logic` component works:

1. When `Logic` is called upon to execute a command, it is passed to an `AddressBookParser` object which in turn creates
   a parser that matches the command (e.g., `DeleteCommandParser`) and uses it to parse the command.
1. This results in a `Command` object (more precisely, an object of one of its subclasses e.g., `DeleteCommand`) which
   is executed by the `LogicManager`.
1. The command can communicate with the `Model` when it is executed (e.g. to delete a contact).<br>
   Note that although this is shown as a single step in the diagram above (for simplicity), in the code it can take
   several interactions (between the command object and the `Model`) to achieve.
1. The result of the command execution is encapsulated as a `CommandResult` object which is returned back from `Logic`.

Here are the other classes in `Logic` (omitted from the class diagram above) that are used for parsing a user command:

<puml src="{{ baseUrl }}/diagrams/ParserClasses.puml" width="600"/>

How the parsing works:

* When called upon to parse a user command, the `AddressBookParser` class creates an `XYZCommandParser` (`XYZ` is a
  placeholder for the specific command name e.g., `AddCommandParser`) which uses the other classes shown above to parse
  the user command and create a `XYZCommand` object (e.g., `AddCommand`) which the `AddressBookParser` returns back as a
  `Command` object.
* All `XYZCommandParser` classes (e.g., `AddCommandParser`, `DeleteCommandParser`, ...) inherit from the `Parser`
  interface so that they can be treated similarly where possible e.g, during testing.

## Model component

**API** : [
`Model.java`](https://github.com/AY2526S2-CS2103T-T08-1/tp/blob/master/src/main/java/seedu/address/model/Model.java)

<puml src="{{ baseUrl }}/diagrams/ModelClassDiagram.puml" width="520" />


The `Model` component,

* stores the contact list data i.e., all `Contact` objects (which are contained in a `UniqueContactList` object).
* stores the currently 'selected' `Contact` objects (e.g., results of a search query) as a separate _displayed_ list
  which is exposed to outsiders as an unmodifiable `ObservableList<Contact>` that can be 'observed' e.g. the UI can be
  bound to this list so that the UI automatically updates when the data in the list change.
* stores a `UserPrefs` object that represents the user’s preferences. This is exposed to the outside as a
  `ReadOnlyUserPrefs` object.
* does not depend on any of the other three components (as the `Model` represents data entities of the domain, they
  should make sense on their own without depending on other components)

Each `Contact` also carries:

* a stable **`UUID`** (persisted in JSON), used when notes store cross-references to other contacts; commands that
  resolve `@INDEX` in note text use the **displayed** contact list at parse time.
* optional **`LastContacted`** for when the contact was last reached (flexible user phrasing, e.g. relative or absolute
  dates).
* **`LastUpdated`**, always present (`LocalDateTime`), typically set to “now” when a contact is added or edited through
  the usual code paths; used for ordering and display logic.
* a list of **`Note`** values: plain text plus an optional reminder; after parsing, stored text may contain `@{UUID}`
  cross-references to other contacts. Note command feedback uses `Messages.formatNoteOutput` (which uses
  `Contact.getNotesString()`); the GUI can resolve references when rendering (e.g. `NoteLabel`).
* a set of **`Tag`** instances; in practice some entries are **`RankedTag`** (a subclass of `Tag`) for ranked friend
  tags—the class diagram shows only `Tag` to keep the overview simple.

**Implementation detail (omitted from the model diagram):** both **`LastContacted`** and optional note reminders are
represented using **`TimePoint`** from `seedu.address.commons.core.timepoint` for parsing and comparing those time
phrases.

`JsonAdaptedContact` persists `lastUpdated`, `lastContacted`, and `notes` alongside the other contact fields.

<box type="info" seamless>

**Note:** The diagram below is an **alternative** (arguably more OOP) design: a central `Tag` list on `AddressBook` that
`Contact` references, so each unique tag exists once. **The running app does not use this structure**—`AddressBook` only
contains a `UniqueContactList`, and each `Contact` owns its own `Tag` instances as in the main model diagram above.

<puml src="{{ baseUrl }}/diagrams/BetterModelClassDiagram.puml" width="450" />

</box>

## Storage component

**API** : [
`Storage.java`](https://github.com/AY2526S2-CS2103T-T08-1/tp/blob/master/src/main/java/seedu/address/storage/Storage.java)

<puml src="{{ baseUrl }}/diagrams/StorageClassDiagram.puml" width="550" />

The `Storage` component,

* saves both contact list data and user preference data in JSON format, and reads them back into corresponding
  objects.
* inherits from both `AddressBookStorage` and `UserPrefStorage`, which means it can be treated as either one (if only
  the functionality of only one is needed).
* depends on some classes in the `Model` component (because the `Storage` component's job is to save/retrieve objects
  that belong to the `Model`)

## Common classes

Classes used by multiple components are in the `seedu.address.commons` package.
