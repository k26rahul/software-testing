package fileorganizer;

import util.TestHarness;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.io.TempDir;
import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;

public class FileCategorizerCoreTest {

  public static void main(String[] args) {
    TestHarness.run(FileCategorizerCoreTest.class);
  }

  private FileCategorizerCore core;

  @BeforeEach
  void setUp() {
    core = new FileCategorizerCore();
  }

  @Test
  void testCategorizationLogic() {
    assertEquals("Documents", core.getCategory("report.pdf"));
    assertEquals("Images", core.getCategory("vacation.png"));
    assertEquals("Others", core.getCategory("unknown.dat"));
    assertEquals("Others", core.getCategory("file_with_no_extension"));
  }

  @Test
  void testRecursiveDirectoryCategorization(@TempDir Path tempDir) throws IOException {
    Path docDir = tempDir.resolve("subfolder");
    Files.createDirectory(docDir);
    Files.createFile(tempDir.resolve("picture.jpg"));
    Files.createFile(docDir.resolve("note.txt"));

    Map<String, List<String>> result = core.categorizeDirectory(tempDir);

    assertEquals(1, result.get("Images").size());
    assertTrue(result.get("Images").contains("picture.jpg"));

    assertEquals(1, result.get("Documents").size());
    String relativeDocPath = result.get("Documents").get(0).replace('\\', '/');
    assertEquals("subfolder/note.txt", relativeDocPath);

    assertTrue(Files.exists(tempDir.resolve("picture.jpg")));
    assertTrue(Files.exists(docDir.resolve("note.txt")));
  }
}
