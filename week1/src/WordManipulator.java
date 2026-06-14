public class WordManipulator {
  String word;

  public WordManipulator(String w) {
    word = w;
  }

  private String revStr() {
    String newStr = "";
    for (int i = 0; i < word.length(); i++)
      newStr = word.charAt(i) + newStr;
    return newStr;
  }

  public boolean isPalindrome() {
    return word.equals(revStr());
  }
}
