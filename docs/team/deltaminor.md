---
layout: page
title: deltaMinor's Project Portfolio Page
---

<img src="../images/deltaminor.png" width="200px">

[[github](http://github.com/deltaMinor)]

## Overview: B2B4U

B2B4U is a desktop contact management application made for business consultants, optimized for use via a Command Line Interface (CLI) while still having the benefits of a Graphical User Interface (GUI).
If you can type fast, B2B4U can get your human resource management tasks done faster than traditional GUI apps.

Given below are my contributions to the project.

## New Features
- Reminder system, which additionally includes a flexible time input system
  - Function: Allow users to set reminders regarding their contacts. Users will be reminded via a pop-up window during startup and the GUI itself.
  - Highlights: Users can input the time of the reminder in nearly any format(for example, `31 aug`, `August 31` or `31/08` will be parsed as the 31<sup>st</sup> of August in the current year). The user can also input text such as "sometime in July" and the reminder note will save it as such, allowing for user freedom.
  - Non-trivial pull-requests: [#99](https://github.com/AY2526S2-CS2103T-T08-1/tp/pull/99)
- Undo/Redo system
  - Function: Allow users to undo their mistake and prevent accidental loss of important data.
  - Highlights: The undo and redo actions can additionally be accessed via shortcut keys _Ctrl+Z_ and _Ctrl+Shift+Z_ respectively, allowing for faster usage.
  - Non-trivial pull-requests: [#102](https://github.com/AY2526S2-CS2103T-T08-1/tp/pull/102), [#198](https://github.com/AY2526S2-CS2103T-T08-1/tp/pull/198)
- File management system
  - Function: Allow users to store contacts in separate files for archival purposes.
  - Highlights: B2B4U has in-built commands to view the currently available files and delete them, allowing the user to have full control over their files solely through the application.
  - Non-trivial pull-requests: [#189](https://github.com/AY2526S2-CS2103T-T08-1/tp/pull/189)
- GUI overhaul
  - Changes: New contact card design, additional colour palettes, autoscroll to newly added/edited contact.
  - Rationale:
    - Give B2B4U a more unique visual identity to separate it from AB3.
    - Provide users a better experience as the new design is more user-friendly, while additional colour palettes allow for user freedom in customising their experience.
  - Non-trivial pull-requests: [#198](https://github.com/AY2526S2-CS2103T-T08-1/tp/pull/198), [#208](https://github.com/AY2526S2-CS2103T-T08-1/tp/pull/208), [#287](https://github.com/AY2526S2-CS2103T-T08-1/tp/pull/287)

### Code contribution
[[RepoSense report](https://nus-cs2103-ay2526-s2.github.io/tp-dashboard/?search=t08&sort=groupTitle&sortWithin=title&timeframe=commit&mergegroup=&groupSelect=groupByRepos&breakdown=true&checkedFileTypes=docs~functional-code~test-code~other&since=2026-02-20T00%3A00%3A00&filteredFileName=&tabOpen=true&tabType=zoom&zA=deltaMinor&zR=AY2526S2-CS2103T-T08-1%2Ftp%5Bmaster%5D&zACS=247.18897637795277&zS=2026-02-20T00%3A00%3A00&zFS=t08&zU=2026-04-07T23%3A59%3A59&zMG=false&zFTF=commit&zFGS=groupByRepos&zFR=false)]
<iframe src="https://nus-cs2103-ay2526-s2.github.io/tp-dashboard/#/widget/?search=t08&sort=groupTitle&sortWithin=title&timeframe=commit&mergegroup=&groupSelect=groupByRepos&breakdown=true&checkedFileTypes=docs~functional-code~test-code~other&since=2026-02-20T00%3A00%3A00&filteredFileName=&chartGroupIndex=0&chartIndex=3" frameBorder="0" width="800px" height="240px"></iframe>

**Project management**:

### Contribution to Documentation
- User Guide
    - Rewrote overall document hierarchy: [#280](https://github.com/AY2526S2-CS2103T-T08-1/tp/pull/280)
    - Add documentation for file management commands: [#210](https://github.com/AY2526S2-CS2103T-T08-1/tp/pull/210)
    - Add documentation for `theme` command: [#210](https://github.com/AY2526S2-CS2103T-T08-1/tp/pull/210)
- Developer's Guide
    - Update description of undo/redo commands: [#194](https://github.com/AY2526S2-CS2103T-T08-1/tp/pull/194)

### Contribution to Team-Based Tasks
- Setup project milestones
- Managed v1.3 and v1.4 releases
- Setup issue tracker for alpha release bugs
- Extensive bug-testing and providing suggestions for fixes: [#80](https://github.com/AY2526S2-CS2103T-T08-1/tp/issues/80), [#100](https://github.com/AY2526S2-CS2103T-T08-1/tp/issues/100), [#115](https://github.com/AY2526S2-CS2103T-T08-1/tp/issues/115), [#204](https://github.com/AY2526S2-CS2103T-T08-1/tp/issues/204)
- Extensive bug-fixing: [#147](https://github.com/AY2526S2-CS2103T-T08-1/tp/pull/147), [#205](https://github.com/AY2526S2-CS2103T-T08-1/tp/pull/205), [#201](https://github.com/AY2526S2-CS2103T-T08-1/tp/pull/201), [#286](https://github.com/AY2526S2-CS2103T-T08-1/tp/pull/286)
- Examples of PRs reviewed (with non-trivial review comments): [#70](https://github.com/AY2526S2-CS2103T-T08-1/tp/pull/70), [#151](https://github.com/AY2526S2-CS2103T-T08-1/tp/pull/151), [#154](https://github.com/AY2526S2-CS2103T-T08-1/tp/pull/154), [#173](https://github.com/AY2526S2-CS2103T-T08-1/tp/pull/173), [#223](https://github.com/AY2526S2-CS2103T-T08-1/tp/pull/223), [#253](https://github.com/AY2526S2-CS2103T-T08-1/tp/pull/253)
