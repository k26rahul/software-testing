package words;

import util.TestHarness;
import org.junit.Test;
import static org.junit.Assert.*;

public class WordManipulatorTest {

  public static void main(String[] args) {
    TestHarness.run(WordManipulatorTest.class);
  }

  @Test
  public void testPalindrome() {
    WordManipulator w1 = new WordManipulator("radar");
    assertTrue(w1.isPalindrome());

    WordManipulator w2 = new WordManipulator("hello");
    assertFalse(w2.isPalindrome());
  }

  @Test
  public void testReverse() {
    WordManipulator w = new WordManipulator("java");
    assertEquals("avaj", w.reverse());
  }

  @Test
  public void testCountVowels() {
    WordManipulator w = new WordManipulator("engineering");
    assertEquals(5, w.countVowels());

    WordManipulator w2 = new WordManipulator("rhythm");
    assertEquals(0, w2.countVowels());
  }

  @Test
  public void testIsAnagram() {
    WordManipulator w = new WordManipulator("listen");
    assertTrue(w.isAnagram("silent"));
    assertFalse(w.isAnagram("apple"));
  }
}
