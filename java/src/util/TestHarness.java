package util;

import org.junit.platform.launcher.Launcher;
import org.junit.platform.launcher.LauncherDiscoveryRequest;
import org.junit.platform.launcher.core.LauncherDiscoveryRequestBuilder;
import org.junit.platform.launcher.core.LauncherFactory;
import org.junit.platform.launcher.TestExecutionListener;
import org.junit.platform.launcher.TestIdentifier;
import org.junit.platform.engine.TestExecutionResult;
import static org.junit.platform.engine.discovery.DiscoverySelectors.selectClass;

public class TestHarness {

  public static void run(Class<?> targetTestClass) {
    LauncherDiscoveryRequest request =
        LauncherDiscoveryRequestBuilder.request().selectors(selectClass(targetTestClass)).build();

    Launcher launcher = LauncherFactory.create();

    TestExecutionListener methodListener = new TestExecutionListener() {
      @Override
      public void executionFinished(TestIdentifier testIdentifier,
          TestExecutionResult testExecutionResult) {
        if (testIdentifier.isTest()) {
          boolean passed = testExecutionResult.getStatus() == TestExecutionResult.Status.SUCCESSFUL;
          // ANSI color codes are pure ASCII bytes - no encoding issues on any terminal.
          String label = passed ? "\033[32m[PASS]\033[0m" : "\033[31m[FAIL]\033[0m";
          System.out.println(label + " " + testIdentifier.getDisplayName());

          if (!passed) {
            testExecutionResult.getThrowable().ifPresent(throwable -> {
              System.out.println("       Reason: " + throwable.getMessage());
            });
          }
        }
      }
    };

    launcher.registerTestExecutionListeners(methodListener);

    System.out.println("=== Executing Tests for: " + targetTestClass.getSimpleName() + " ===\n");
    launcher.execute(request);
    System.out.println("\n=== Test Run Complete ===");
  }
}
