import org.junit.platform.launcher.Launcher;
import org.junit.platform.launcher.LauncherDiscoveryRequest;
import org.junit.platform.launcher.core.LauncherDiscoveryRequestBuilder;
import org.junit.platform.launcher.core.LauncherFactory;
import org.junit.platform.launcher.TestExecutionListener;
import org.junit.platform.launcher.TestIdentifier;
import org.junit.platform.engine.TestExecutionResult;
import static org.junit.platform.engine.discovery.DiscoverySelectors.selectClass;

public class TestRunnerHelper {

  public static void run(Class<?> targetTestClass) {
    // 1. Build the discovery request
    LauncherDiscoveryRequest request =
        LauncherDiscoveryRequestBuilder.request().selectors(selectClass(targetTestClass)).build();

    Launcher launcher = LauncherFactory.create();

    // 2. Custom listener for concise inline output
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

    // 3. Register ONLY our custom listener
    launcher.registerTestExecutionListeners(methodListener);

    // 4. Execute the tests
    System.out.println("=== Executing Tests for: " + targetTestClass.getSimpleName() + " ===\n");
    launcher.execute(request);
    System.out.println("\n=== Test Run Complete ===");
  }
}
