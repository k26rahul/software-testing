package sysmetrics;

public class MetricsCore {

  public String getHealthStatus() {
    return "{\"status\": \"UP\"}";
  }

  public String getSystemMetrics() {
    Runtime runtime = Runtime.getRuntime();
    long totalMemory = runtime.totalMemory() / (1024 * 1024);
    long freeMemory = runtime.freeMemory() / (1024 * 1024);
    long usedMemory = totalMemory - freeMemory;
    int processors = runtime.availableProcessors();

    // Manual JSON formatting to avoid external dependencies like Jackson/Gson
    return String.format("{\"processors\": %d, \"memory_total_mb\": %d, \"memory_used_mb\": %d}",
        processors, totalMemory, usedMemory);
  }

  public static void main(String[] args) {
    MetricsCore metrics = new MetricsCore();
    System.out.println("Health Status: " + metrics.getHealthStatus());
    System.out.println("System Metrics: " + metrics.getSystemMetrics());
  }
}
