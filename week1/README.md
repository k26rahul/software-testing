# JUnit 5 & 4 Testing Sandbox (No Build Tool)

This repository is a lightweight, flat-directory Java testing environment using JUnit 5 (Jupiter) and JUnit 4 (Vintage). It explicitly bypasses standard build tools (Maven/Gradle) to provide a fast, isolated sandbox for learning and testing software logic.

## Directory Architecture

- **`src/`**: Contains all raw Java source code (`.java`), including application logic and test classes.
- **`src/util/`**: Contains `TestHarness.java`, the custom execution engine that powers the self-executing tests.
- **Packages (Subfolders)**: If you organize files into subfolders inside `src/` (e.g., `src/calculator/`), include the corresponding package declaration (`package calculator;`) at the top of those `.java` files.
- **`bin/`**: Output directory for compiled bytecode (`.class`). VS Code automatically handles compilation and routes files here.
- **`lib/`**: Stores external dependency `.jar` files.
- **`.vscode/`**: Contains `settings.json` configuration binding `src`, `bin`, and `lib` to the IDE's language server.

---

## Dependency Setup

This architecture supports two dependency setups. Choose the one that fits your needs. Execute the commands in a PowerShell terminal at the root of the `week1` folder.

### Primary Approach: Modular JARs (Recommended)

Downloads the individual, decoupled JUnit modular libraries.

- **Pros:** Full functionality. Terminal execution, custom test harness, and the VS Code Test Runner extension (visual play buttons) will work flawlessly for **both** JUnit 4 and JUnit 5.

```powershell
$baseUrl = "https://repo1.maven.org/maven2"

# JUnit 5 (Jupiter) Core & Execution Engine
Invoke-WebRequest -Uri "$baseUrl/org/junit/jupiter/junit-jupiter-api/5.10.2/junit-jupiter-api-5.10.2.jar" -OutFile "lib\junit-jupiter-api.jar"
Invoke-WebRequest -Uri "$baseUrl/org/junit/jupiter/junit-jupiter-engine/5.10.2/junit-jupiter-engine-5.10.2.jar" -OutFile "lib\junit-jupiter-engine.jar"
Invoke-WebRequest -Uri "$baseUrl/org/junit/platform/junit-platform-commons/1.10.2/junit-platform-commons-1.10.2.jar" -OutFile "lib\junit-platform-commons.jar"
Invoke-WebRequest -Uri "$baseUrl/org/junit/platform/junit-platform-engine/1.10.2/junit-platform-engine-1.10.2.jar" -OutFile "lib\junit-platform-engine.jar"
Invoke-WebRequest -Uri "$baseUrl/org/junit/platform/junit-platform-launcher/1.10.2/junit-platform-launcher-1.10.2.jar" -OutFile "lib\junit-platform-launcher.jar"
Invoke-WebRequest -Uri "$baseUrl/org/apiguardian/apiguardian-api/1.1.2/apiguardian-api-1.1.2.jar" -OutFile "lib\apiguardian-api.jar"
Invoke-WebRequest -Uri "$baseUrl/org/opentest4j/opentest4j/1.3.0/opentest4j-1.3.0.jar" -OutFile "lib\opentest4j.jar"

# JUnit 4 (Vintage) Bridge & Legacy APIs
Invoke-WebRequest -Uri "$baseUrl/org/junit/vintage/junit-vintage-engine/5.10.2/junit-vintage-engine-5.10.2.jar" -OutFile "lib\junit-vintage-engine.jar"
Invoke-WebRequest -Uri "$baseUrl/junit/junit/4.13.2/junit-4.13.2.jar" -OutFile "lib\junit-4.13.2.jar"
Invoke-WebRequest -Uri "$baseUrl/org/hamcrest/hamcrest-core/1.3/hamcrest-core-1.3.jar" -OutFile "lib\hamcrest-core.jar"

```

_(Note: After downloading, run `Java: Clean Workspace` from the VS Code Command Palette to refresh the IDE)._

### Alternative Approach: Standalone "Fat" JAR

Downloads a single, monolithic file containing the entire testing platform.

- **Pros:** Single file download. Terminal execution and the custom test harness work for both versions.
- **Cons:** The VS Code Test Runner extension (visual play buttons) will **only** work for legacy JUnit 4 files, not JUnit 5.

```powershell
Invoke-WebRequest -Uri "https://repo1.maven.org/maven2/org/junit/platform/junit-platform-console-standalone/1.10.2/junit-platform-console-standalone-1.10.2.jar" -OutFile "lib\junit.jar"

```

---

## Test Execution Methods

### 1. VS Code Native Runner (Visual Play Buttons)

If you are using the **Primary (Modular)** setup, VS Code's Java extension will natively parse both `@org.junit.jupiter.api.Test` (v5) and `@org.junit.Test` (v4).

- Open any test class.
- Click the **Run Test** green play icon next to the line numbers.
- View test results directly in the VS Code sidebar.

### 2. Self-Executing Tests (IDE execution without terminal)

This approach utilizes the custom `util.TestHarness` facade, allowing test classes to execute themselves directly via a standard `main` method. Works for both JUnit 4 and 5, under both dependency setups.

1. Import the engine: `import util.TestHarness;`
2. Add a main method passing the target class:

```java
public static void main(String[] args) {
    TestHarness.run(CalculatorTest.class);
}

```

3. Click the visual **Run** button hovering over the `main` method. The test output will print cleanly to the IDE terminal.

### 3. Direct Terminal Commands

You can bypass the IDE entirely and execute tests via the command line. Ensure you run these from the root of the `week1` directory.

_Note: Use the exact class name for flat files, or the fully qualified class name (e.g., `calculator.CalculatorTest`) for packaged files._

**If using the Primary Setup (Modular JARs):**
Because there is no standalone runner, you execute the self-executing `main` method while specifying the `lib` folder in the classpath.

```powershell
java -cp "bin;lib\*" calculator.CalculatorTest

```

**If using the Alternative Setup (Standalone JAR):**
You invoke the standalone console runner built into the `junit.jar` file.

```powershell
java -jar lib\junit.jar execute -cp "bin" -c calculator.CalculatorTest

```
