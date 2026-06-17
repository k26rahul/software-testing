# File Categorizer Application

A simple Java Swing GUI application that recursively scans a target directory and categorizes files based on their extensions (Documents, Images, Videos, Others) without altering the filesystem.

## How to Use

Run `FileCategorizerApp.java`, enter an absolute directory path, and click **Categorize Files**.

## Files

- **`FileCategorizerCore.java`**
  - `getCategory(String)` - returns the category (`Documents`, `Images`, `Videos`, `Others`) for a given filename based on extension
  - `categorizeDirectory(Path)` - recursively walks a directory and returns a map of category to list of relative file paths; throws `IllegalArgumentException` if the path is invalid
  - `generateSummary(Map)` - formats the categorized map into a printable summary string

- **`FileCategorizerApp.java`**
  - `FileCategorizerApp()` - constructor, initializes `FileCategorizerCore` and builds the Swing UI
  - `main(String[])` - entry point, launches the window on the EDT via `SwingUtilities.invokeLater`

- **`FileCategorizerCoreTest.java`**: JUnit 5 unit tests for traversal and categorization logic.

- **`FileCategorizerAppSystemTest.java`**: End-to-end system tests verifying UI integration and outputs.

