import org.junit.Test;
import static org.junit.Assert.assertTrue;

public class WordManipulatorTest {
  @Test
  public void testPalindrome() {
    String word = "radar";
    WordManipulator w = new WordManipulator(word);
    assertTrue(w.isPalindrome());
  }
}
