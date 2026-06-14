# System Metrics REST API

A lightweight backend HTTP server built entirely with native Java networking libraries (`com.sun.net.httpserver`). It exposes machine health, memory usage, and processor availability via JSON endpoints.

## How to Use

1. Execute the `main` method inside `MetricsServerApp.java`. The server will bind to `localhost:8080`.
2. Issue HTTP GET requests to the available endpoints:
   - `http://localhost:8080/api/health`
   - `http://localhost:8080/api/metrics`

## Architecture & Files

- **`MetricsCore.java`**: The pure logic layer. Interfaces with the Java `Runtime` class to calculate hardware metrics and manually format them into JSON strings. Unaware of network protocols.
- **`MetricsServerApp.java`**: The application layer. Manages the HTTP server lifecycle (start/stop) and routes incoming URI requests to the core logic.
- **`MetricsCoreTest.java`**: Unit tests that validate the string formatting and mathematical calculations of the hardware metrics in total isolation. Runs natively via the VS Code "Run" button using the `util.TestHarness`.
- **`MetricsServerAppSystemTest.java`**: End-to-End (E2E) network integration test. It dynamically boots the server on an ephemeral port (8085) using JUnit lifecycle hooks (`@BeforeEach`/`@AfterEach`), issues real network requests using `java.net.http.HttpClient`, and asserts the HTTP status codes and JSON response bodies.
