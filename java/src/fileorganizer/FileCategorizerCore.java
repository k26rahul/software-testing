package fileorganizer;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Stream;

/**
 * Handles the logic of identifying categories for files and recursively
 * scanning directories to group files by their categories.
 */
public class FileCategorizerCore {

  public String getCategory(String fileName) {
    String extension = getExtension(fileName);
    return switch (extension) {
      case "txt", "pdf", "docx" -> "Documents";
      case "jpg", "png", "gif" -> "Images";
      case "mp4", "mkv" -> "Videos";
      default -> "Others";
    };
  }

  private String getExtension(String fileName) {
    int lastIndex = fileName.lastIndexOf('.');
    if (lastIndex > 0 && lastIndex < fileName.length() - 1) {
      return fileName.substring(lastIndex + 1).toLowerCase();
    }
    return "";
  }

  public Map<String, List<String>> categorizeDirectory(Path sourceDir) throws IOException {
    Map<String, List<String>> result = new TreeMap<>();
    result.put("Documents", new ArrayList<>());
    result.put("Images", new ArrayList<>());
    result.put("Videos", new ArrayList<>());
    result.put("Others", new ArrayList<>());

    if (!Files.exists(sourceDir) || !Files.isDirectory(sourceDir)) {
      throw new IllegalArgumentException("Directory does not exist or is not a directory");
    }

    try (Stream<Path> paths = Files.walk(sourceDir)) {
      paths.filter(Files::isRegularFile).forEach(path -> {
        String relativePath = sourceDir.relativize(path).toString();
        String category = getCategory(path.getFileName().toString());
        result.get(category).add(relativePath);
      });
    }

    return result;
  }

  public String generateSummary(Map<String, List<String>> categorizedFiles) {
    StringBuilder sb = new StringBuilder();
    sb.append("File Categorization Summary:\n");
    sb.append("============================\n");
    categorizedFiles.forEach((category, files) -> {
      sb.append(category).append(" (Count: ").append(files.size()).append("):\n");
      if (files.isEmpty()) {
        sb.append("  (No files)\n");
      } else {
        for (String file : files) {
          sb.append("  - ").append(file).append("\n");
        }
      }
      sb.append("\n");
    });
    return sb.toString();
  }
}
