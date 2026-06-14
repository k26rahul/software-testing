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
          System.out.println(" -> " + testIdentifier.getDisplayName() + " ["
              + testExecutionResult.getStatus() + "]");

          if (testExecutionResult.getStatus() == TestExecutionResult.Status.FAILED) {
            testExecutionResult.getThrowable().ifPresent(throwable -> {
              System.out.println("      Reason: " + throwable.getMessage());
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
