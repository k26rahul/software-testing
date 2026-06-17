package practice.calculator;

public class Calculator {
  public int add(int a, int b) {
    return a + b;
  }

  public int subtract(int a, int b) {
    return a - b;
  }

  public int multiply(int a, int b) {
    return a * b;
  }

  public double divide(int a, int b) {
    if (b == 0) {
      throw new IllegalArgumentException("Divide by zero is not allowed.");
    }
    return (double) a / b;
  }

  public static void main(String[] args) {
    Calculator calc = new Calculator();
    System.out.println("100 + 17 = " + calc.add(100, 17));
    System.out.println("100 - 17 = " + calc.subtract(100, 17));
    System.out.println("100 * 17 = " + calc.multiply(100, 17));
    System.out.println("100 / 17 = " + calc.divide(100, 17));

    System.out.println("Below line will throw error:");
    System.out.println("100 / 0 = " + calc.divide(100, 0));
  }
}
