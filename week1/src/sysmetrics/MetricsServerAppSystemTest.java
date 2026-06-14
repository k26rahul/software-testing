package sysmetrics;

import util.TestHarness;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class MetricsServerAppSystemTest {

  public static void main(String[] args) {
    TestHarness.run(MetricsServerAppSystemTest.class);
  }

  private MetricsServerApp app;
  private HttpClient client;
  private final int TEST_PORT = 8085; // Use a different port to avoid conflicts

  @BeforeEach
  void setUp() throws Exception {
    app = new MetricsServerApp();
    app.start(TEST_PORT); // Boot up the server before every test
    client = HttpClient.newHttpClient();
  }

  @AfterEach
  void tearDown() {
    app.stop(); // Ensure the port is freed after every test
  }

  @Test
  void testHealthEndpointIntegration() throws Exception {
    // Arrange
    HttpRequest request = HttpRequest.newBuilder()
        .uri(URI.create("http://localhost:" + TEST_PORT + "/api/health")).GET().build();

    // Act
    HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

    // Assert
    assertEquals(200, response.statusCode());
    assertEquals("application/json", response.headers().firstValue("Content-Type").orElse(""));
    assertEquals("{\"status\": \"UP\"}", response.body());
  }

  @Test
  void testMetricsEndpointIntegration() throws Exception {
    // Arrange
    HttpRequest request = HttpRequest.newBuilder()
        .uri(URI.create("http://localhost:" + TEST_PORT + "/api/metrics")).GET().build();

    // Act
    HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

    // Assert
    assertEquals(200, response.statusCode());
    assertTrue(response.body().contains("\"memory_total_mb\""));
  }
}
