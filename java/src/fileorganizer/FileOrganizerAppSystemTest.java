package fileorganizer;

import util.TestHarness;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.io.TempDir;
import static org.junit.jupiter.api.Assertions.*;

import java.nio.file.Files;
import java.nio.file.Path;

public class FileOrganizerAppSystemTest {

  public static void main(String[] args) {
    TestHarness.run(FileOrganizerAppSystemTest.class);
  }

  @Test
  void testFullUIExecutionFlow(@TempDir Path tempDir) throws Exception {
    // Arrange: Create a temporary file to guarantee a successful run
    Files.createFile(tempDir.resolve("data.docx"));

    FileOrganizerApp app = new FileOrganizerApp();

    // Assert initial state
    assertEquals("Status: Awaiting input.", app.statusLabel.getText());

    // Act: Inject the safe TempDir path and simulate a user click
    app.pathInput.setText(tempDir.toString());
    app.organizeButton.doClick();

    // Assert: Verify the UI successfully updated its status label
    assertEquals("Status: Organization Complete!", app.statusLabel.getText());
    assertTrue(Files.exists(tempDir.resolve("Documents/data.docx")));
  }
}
