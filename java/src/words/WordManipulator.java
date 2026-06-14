package words;

import java.util.Arrays;

public class WordManipulator {
  private String word;

  public WordManipulator(String w) {
    this.word = w;
  }

  public String reverse() {
    String newStr = "";
    for (int i = 0; i < word.length(); i++) {
      newStr = word.charAt(i) + newStr;
    }
    return newStr;
  }

  public boolean isPalindrome() {
    return word.equals(reverse());
  }

  public int countVowels() {
    int count = 0;
    String lowerCaseWord = word.toLowerCase();
    for (int i = 0; i < lowerCaseWord.length(); i++) {
      char c = lowerCaseWord.charAt(i);
      if (c == 'a' || c == 'e' || c == 'i' || c == 'o' || c == 'u') {
        count++;
      }
    }
    return count;
  }

  public boolean isAnagram(String otherWord) {
    if (word.length() != otherWord.length()) {
      return false;
    }
    char[] w1 = word.toLowerCase().toCharArray();
    char[] w2 = otherWord.toLowerCase().toCharArray();
    Arrays.sort(w1);
    Arrays.sort(w2);
    return Arrays.equals(w1, w2);
  }

  public static void main(String[] args) {
    WordManipulator manipulator = new WordManipulator("listen");
    System.out.println("Word: " + manipulator.word);
    System.out.println("Reverse: " + manipulator.reverse());
    System.out.println("Is palindrome? " + manipulator.isPalindrome());
    System.out.println("Vowel count: " + manipulator.countVowels());
    System.out.println("Is anagram of 'silent'? " + manipulator.isAnagram("silent"));
  }
}
