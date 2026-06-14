# JUnit 5 & 4 Testing Sandbox (No Build Tool)

This repository is a lightweight, flat-directory Java testing environment using JUnit 5 (Jupiter) and JUnit 4 (Vintage). It explicitly bypasses standard build tools (Maven/Gradle) and complex enterprise directory structures to provide a fast, isolated sandbox for learning and testing software logic.

## Directory Architecture

- **`src/`**: Contains all raw Java source code (`.java`), including application logic and test classes.
- **`src/util/`**: Contains `TestHarness.java`, the custom execution engine that powers the self-executing tests.
- **Packages (Subfolders)**: If you organize files into subfolders inside `src/` (e.g., `src/calculator/`), you must include the corresponding package declaration (e.g., `package calculator;`) at the top of those `.java` files.
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

You can invoke the JUnit execution engine directly via the command line.

Execute this command from the root of the `week1` directory.

- **For flat files:** Use the exact class name.

```powershell
java -jar lib\junit.jar execute -cp "bin" -c CalculatorTest

```

- **For packaged files (subfolders):** Use the fully qualified class name (package + class).

```powershell
java -jar lib\junit.jar execute -cp "bin" -c calculator.CalculatorTest

```

### Approach 2: Self-Executing Tests (JUnit 4 & 5)

This approach utilizes a custom Facade pattern, allowing each test class to execute itself directly via the VS Code "Run" button, completely avoiding the terminal.

1. Open your target test class file (e.g., `CalculatorTest.java`).
2. Import the custom test engine at the top of the file:

```java
import util.TestHarness;

```

3. Include a standard `main` method that passes the current class to the harness:

```java
public static void main(String[] args) {
    TestHarness.run(CalculatorTest.class);
}

```

4. Click the visual **Run** button hovering directly over the `main` method in your editor.

**Note on Architecture:** The complex JUnit Platform Launcher API logic is fully encapsulated inside `src/util/TestHarness.java`. This file acts as the central execution engine. It generates concise, inline terminal output for passed/failed tests and requires zero manual modification.

### Approach 3: VS Code Native Runner (JUnit 4 Only)

Because the downloaded standalone `.jar` includes the JUnit Vintage engine, this architecture fully supports legacy JUnit 4 tests out of the box. If you write a test using the older JUnit 4 annotations (e.g., `import org.junit.Test;`), you can bypass the terminal and the custom harness entirely.

1. Open your JUnit 4 test class file.
2. Click the visual **Run Test** (green play button) that automatically appears next to the line numbers in the editor.
3. View the test execution status via the checkmarks in the editor and the VS Code Test Explorer sidebar.

**Note on Packages:** This native runner requires zero manual configuration for packages. The IDE's language server automatically parses subfolders, resolves the namespaces, and displays the execution buttons.
