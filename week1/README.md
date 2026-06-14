# JUnit 5 & 4 Testing Sandbox (No Build Tool)

This repository is a lightweight, flat-directory Java testing environment using JUnit 5 (Jupiter) and JUnit 4 (Vintage). It explicitly bypasses standard build tools (Maven/Gradle) and complex enterprise directory structures to provide a fast, isolated sandbox for learning and testing software logic.

## Directory Architecture

- **`src/`**: Contains all raw Java source code (`.java`), including application logic, test classes, and custom test harness files.
- **`bin/`**: The output directory for compiled bytecode (`.class`). VS Code automatically handles compilation and routes files here.
- **`lib/`**: Stores external dependencies. It strictly contains the JUnit 5 Standalone Console `.jar` file.
- **`.vscode/`**: Contains `settings.json` configuration that binds the `src`, `bin`, and `lib` paths to the IDE's underlying Java engine.

## Dependency Setup

If the JUnit executable is ever accidentally deleted, open Windows PowerShell at the root of the `week1` folder and execute the following command to restore it to the `lib` directory:

```powershell
Invoke-WebRequest -Uri "https://repo1.maven.org/maven2/org/junit/platform/junit-platform-console-standalone/1.10.2/junit-platform-console-standalone-1.10.2.jar" -OutFile "lib\junit.jar"

```

## Test Execution Methods

This architecture supports three distinct methods for executing tests.

### Approach 1: Terminal Command (JUnit 4 & 5)

You can invoke the JUnit execution engine directly via the command line. To test a different file, simply replace `CalculatorTest` with your target class name.

Execute this command from the root of the `week1` directory:

```powershell
java -jar lib\junit.jar execute -cp "bin" -c CalculatorTest

```

### Approach 2: Visual Test Harness (JUnit 4 & 5)

This approach utilizes a custom Facade pattern to run tests via the VS Code "Run" button, avoiding the terminal entirely.

1. Open `src/TestHarness.java`.
2. Edit the single execution line to target your desired test class:

```java
TestRunnerHelper.run(CalculatorTest.class);

```

3. Click the **Run** button hovering over the `main` method.

**Note on Architecture:** The complex JUnit Platform Launcher API logic is fully encapsulated inside `src/TestRunnerHelper.java`. This helper file acts as the underlying execution engine. It generates concise, inline terminal output for passed/failed tests and requires zero manual modification.

### Approach 3: VS Code Native Runner (JUnit 4 Only)

Because the downloaded standalone `.jar` includes the JUnit Vintage engine, this architecture fully supports legacy JUnit 4 tests out of the box. If you write a test using the older JUnit 4 annotations (e.g., `import org.junit.Test;`), you can bypass the terminal and the custom harness entirely.

1. Open your JUnit 4 test class file.
2. Click the visual **Run Test** (green play button) that automatically appears next to the line numbers in the editor.
3. View the test execution status via the checkmarks in the editor and the VS Code Test Explorer sidebar.
