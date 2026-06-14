# File Organizer Application

A Java Swing GUI application that automatically categorizes and moves files within a target directory into specific subfolders (Documents, Images, Videos, Others) based on file extensions.

## How to Use

1. Execute the `main` method inside `FileOrganizerApp.java`.
2. Enter the absolute path of your target directory into the text field.
3. Click **Organize Now**. The UI will update with the operation status.

## Architecture & Files

- **`FileOrganizerCore.java`**: The pure business logic layer. It handles mathematical categorization and direct file system I/O operations independently of the UI.
- **`FileOrganizerApp.java`**: The presentation layer. A lightweight Swing UI that captures user input and triggers the core logic.
- **`FileOrganizerCoreTest.java`**: Unit tests for the routing logic. Utilizes JUnit 5's `@TempDir` annotation to safely test I/O operations in an isolated, ephemeral sandbox. Runs natively via the VS Code "Run" button using the `util.TestHarness`.
- **`FileOrganizerAppSystemTest.java`**: End-to-End (E2E) system test. It instantiates the UI headlessly, injects test data, simulates user clicks, and asserts that the UI and filesystem states resolve correctly.
