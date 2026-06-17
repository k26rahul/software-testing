# System Metrics REST API

A lightweight HTTP server using `com.sun.net.httpserver` that exposes machine metrics via JSON endpoints.

## Usage

Run `MetricsServerApp.java` and issue GET requests to:

- `http://localhost:8080/api/health`
- `http://localhost:8080/api/metrics`

## Files

- **`MetricsCore.java`**
  - `getHealthStatus()` - returns a JSON health status string
  - `getSystemMetrics()` - returns JSON with processor count and memory usage
  - `main(String[])` - entry point, prints health and metrics to stdout

- **`MetricsServerApp.java`**
  - `MetricsServerApp()` - constructor, initializes a `MetricsCore` instance
  - `start(int)` - starts the HTTP server on the given port, registers `/api/health` and `/api/metrics` routes
  - `stop()` - stops the server
  - `main(String[])` - entry point, starts the server on port 8080

- **`MetricsCoreTest.java`**: JUnit 5 unit tests for `MetricsCore` methods.

- **`MetricsServerAppSystemTest.java`**: E2E integration tests that boot the server on an ephemeral port and issue real HTTP requests.
