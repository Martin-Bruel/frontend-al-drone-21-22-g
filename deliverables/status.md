## Sommaire

* [Status](#status)
* [Répartition des points](#répartition-des-points)

## Status
| Date | Planned | Achieved | Blockers/Risk | 🟩🟧🟥 Flag |
| :--- | :--- | :--- | :--- | :---: |
| 02-11-21<br />(week 44) | <ul><li>Report</li></ul>| <ul><li>Deliveries by big drones</li><li>Cancel deliveries too far away</li><li>Prepare Demo</li><li>Improve Logs for Demo</li><li>Fix the sending of notifications when the drone delivers and is disconnected</li></ul> | | 🟩 |
| 26-10-21<br />(week 43) | <ul><li>Deliveries by big drones</li><li>Warehouse Notification lost drone</li><li>Cancel deliveries too far away</li><li>Prepare Demo</li><li>Report</li></ul> | <ul><li>Scenario in CI</li><li>Resilient Notifications</li></ul> | <ul><li>Hard to test architecture (need to add some routes just for testing)</li></ul> | 🟩 |
| 19-10-21<br />(week 42) | <ul><li>Deliveries by drones (one drone <-> one package)</li><li>Simple Scheduling && Flight Plan</li><li>Important Drone Tracking</li><li>Basic Notifications</li><li>CI End to End </li></ul> | <ul><li>Deliveries by drones (one drone <-> one package)</li><li>Simple Scheduling && Flight Plan</li><li>Important Drone Tracking</li><li>Basic Notifications</li></ul> | <ul><li>In-memory Database</li><li>Persistence Issues : error while testing with some os</li><li>Persistence Issues : difficulties with Cascading</li></ul> | 🟩 |
| 12-10-21<br />(week 41) | <ul><li>Correction Epics/Sprints</li><li>Review architectural justifications</li><li>Projet Setup</li><li>First delivery by drone</li></ul> | <ul><li>Correction Epics/Sprints</li><li>Review architectural justifications</li><li>Projet Setup</li></ul> | <ul><li>Technological risk: our technological choices are heavy and will require time for configuration</li></ul> | 🟧 |
| 05-10-21<br />(week 40) | <ul><li>Add Personas to Users</li><li>Create Epics, US, Acceptance criteria</li><br /><li>Create component diagram</li><li>Create architecture diagram</li><li>Plan roadmap</li><li>Choose our technologies</li></ul> | <ul><li>Add Personas to Users</li><li>Create Epics, US, Acceptance criteria</li><br /><li>Create component diagram</li><li>Create architecture diagram</li><li>Plan roadmap</li><li>Choose our technologies</li></ul> | <ul><li>Manage the timeout for considering drones lost</li><li>Scheduling that is only triggered when a drone returns and not at regular intervals.</li><li>The truck can overload when there are too many drones</li><li>Data persistence between modules</li><li>Simulate the synchronisation between the drones and the truck : Physical contrainst of the POC (can't use ethernet cables for demo)</li></ul> | 🟩 |
| 28-09-21<br />(week 39)| <ul><li>Define users and scope</li><li>Description scenario</li><li>Identify risk</li></ul> | <ul><li>Define users and scope</li><li>Description scenario</li><li>Identify risk</li></ul> | | 🟩 |




## Répartition des points
| Bruel Martin | Esteve Thibaut | Lebrisse David | Meulle Nathan | Ushaka Kevin |
|:------------:|:--------------:|:--------------:|:-------------:|:------------:|
|     100      |      100       |      100       |      100      |     100      |
