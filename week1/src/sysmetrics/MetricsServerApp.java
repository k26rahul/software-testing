package sysmetrics;

import com.sun.net.httpserver.HttpServer;
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;

public class MetricsServerApp {
  private HttpServer server;
  private final MetricsCore core;

  public MetricsServerApp() {
    this.core = new MetricsCore();
  }

  public void start(int port) throws IOException {
    server = HttpServer.create(new InetSocketAddress(port), 0);

    // Endpoint 1: /api/health
    server.createContext("/api/health", exchange -> {
      String response = core.getHealthStatus();
      exchange.getResponseHeaders().set("Content-Type", "application/json");
      exchange.sendResponseHeaders(200, response.getBytes().length);
      try (OutputStream os = exchange.getResponseBody()) {
        os.write(response.getBytes());
      }
    });

    // Endpoint 2: /api/metrics
    server.createContext("/api/metrics", exchange -> {
      String response = core.getSystemMetrics();
      exchange.getResponseHeaders().set("Content-Type", "application/json");
      exchange.sendResponseHeaders(200, response.getBytes().length);
      try (OutputStream os = exchange.getResponseBody()) {
        os.write(response.getBytes());
      }
    });

    server.setExecutor(null);
    server.start();
    System.out.println("API Server started on port: " + port);
  }

  public void stop() {
    if (server != null) {
      server.stop(0);
      System.out.println("API Server stopped.");
    }
  }

  public static void main(String[] args) throws IOException {
    MetricsServerApp app = new MetricsServerApp();
    app.start(8080); // Default production port
  }
}
