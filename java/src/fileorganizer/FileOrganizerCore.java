package fileorganizer;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

public class FileOrganizerCore {

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

  public boolean organizeDirectory(Path sourceDir) throws IOException {
    File folder = sourceDir.toFile();
    File[] files = folder.listFiles();

    if (files == null || files.length == 0)
      return false;

    boolean movedAny = false;
    for (File file : files) {
      if (file.isFile()) {
        String category = getCategory(file.getName());
        Path targetDir = sourceDir.resolve(category);

        if (!Files.exists(targetDir)) {
          Files.createDirectory(targetDir);
        }

        Path targetFile = targetDir.resolve(file.getName());
        Files.move(file.toPath(), targetFile, StandardCopyOption.REPLACE_EXISTING);
        movedAny = true;
      }
    }
    return movedAny;
  }

  public static void main(String[] args) {
    FileOrganizerCore core = new FileOrganizerCore();
    System.out.println("sample.pdf -> " + core.getCategory("sample.pdf"));
    System.out.println("photo.png -> " + core.getCategory("photo.png"));
    System.out.println("movie.mp4 -> " + core.getCategory("movie.mp4"));
    System.out.println("unknown.xyz -> " + core.getCategory("unknown.xyz"));
  }
}
