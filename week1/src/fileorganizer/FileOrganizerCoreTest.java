package fileorganizer;

import util.TestHarness;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.io.TempDir;
import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class FileOrganizerCoreTest {

  public static void main(String[] args) {
    TestHarness.run(FileOrganizerCoreTest.class);
  }

  private FileOrganizerCore core;

  @BeforeEach
  void setUp() {
    core = new FileOrganizerCore();
  }

  @Test
  void testCategorizationLogic() {
    assertEquals("Documents", core.getCategory("report.pdf"));
    assertEquals("Images", core.getCategory("vacation.png"));
    assertEquals("Others", core.getCategory("unknown.dat"));
    assertEquals("Others", core.getCategory("file_with_no_extension"));
  }

  @Test
  void testDirectoryOrganization(@TempDir Path tempDir) throws IOException {
    // Arrange: Create dummy files in the temporary directory
    Files.createFile(tempDir.resolve("note.txt"));
    Files.createFile(tempDir.resolve("picture.jpg"));

    // Act
    boolean result = core.organizeDirectory(tempDir);

    // Assert: Ensure execution succeeded and files were routed correctly
    assertTrue(result, "Organization should report success");
    assertTrue(Files.exists(tempDir.resolve("Documents/note.txt")),
        "Text file not moved to Documents");
    assertTrue(Files.exists(tempDir.resolve("Images/picture.jpg")), "JPG not moved to Images");
  }
}
