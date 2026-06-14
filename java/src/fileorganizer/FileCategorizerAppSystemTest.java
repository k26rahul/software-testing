package fileorganizer;

import util.TestHarness;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.io.TempDir;
import static org.junit.jupiter.api.Assertions.*;

import java.nio.file.Files;
import java.nio.file.Path;

public class FileCategorizerAppSystemTest {

  public static void main(String[] args) {
    TestHarness.run(FileCategorizerAppSystemTest.class);
  }

  @Test
  void testFullUIExecutionFlow(@TempDir Path tempDir) throws Exception {
    Files.createFile(tempDir.resolve("data.docx"));

    FileCategorizerApp app = new FileCategorizerApp();

    assertEquals("Status: Awaiting input.", app.statusLabel.getText());

    app.pathInput.setText(tempDir.toString());
    app.categorizeButton.doClick();

    assertEquals("Status: Categorization Complete!", app.statusLabel.getText());
    assertTrue(app.resultArea.getText().contains("Documents (Count: 1):"));
    assertTrue(app.resultArea.getText().contains("- data.docx"));

    assertTrue(Files.exists(tempDir.resolve("data.docx")));
  }
}
