# File Categorizer Application

A simple Java Swing GUI application that recursively scans a target directory and categorizes files based on their extensions (Documents, Images, Videos, Others) without altering the filesystem.

## How to Use

1. Execute the `main` method inside `FileCategorizerApp.java`.
2. Enter the absolute path of your target directory into the text field.
3. Click **Categorize Files** to view the summary of counts and relative file paths.

## Architecture & Files

- `FileCategorizerCore.java`: Handles extension categorization and recursive directory traversal.
- `FileCategorizerApp.java`: GUI layer displaying the categorized file listing.
- `FileCategorizerCoreTest.java`: Unit tests for traversal and categorization logic.
- `FileCategorizerAppSystemTest.java`: End-to-end system tests verifying the UI integration and outputs.
