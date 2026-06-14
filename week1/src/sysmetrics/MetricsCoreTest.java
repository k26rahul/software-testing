package sysmetrics;

import util.TestHarness;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

public class MetricsCoreTest {

  public static void main(String[] args) {
    TestHarness.run(MetricsCoreTest.class);
  }

  private MetricsCore core;

  @BeforeEach
  void setUp() {
    core = new MetricsCore();
  }

  @Test
  void testHealthStatus() {
    assertEquals("{\"status\": \"UP\"}", core.getHealthStatus(), "Health status JSON is malformed");
  }

  @Test
  void testSystemMetricsFormat() {
    String metrics = core.getSystemMetrics();

    // Assert it looks like valid JSON and contains the expected keys
    assertTrue(metrics.startsWith("{") && metrics.endsWith("}"), "Should be a JSON object");
    assertTrue(metrics.contains("\"processors\":"), "Missing processors key");
    assertTrue(metrics.contains("\"memory_total_mb\":"), "Missing memory_total_mb key");
    assertTrue(metrics.contains("\"memory_used_mb\":"), "Missing memory_used_mb key");
  }
}
