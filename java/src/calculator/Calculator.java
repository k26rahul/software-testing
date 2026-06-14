package calculator;

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
      throw new IllegalArgumentException("Division by zero is not allowed.");
    }
    return (double) a / b;
  }

  public static void main(String[] args) {
    Calculator calculator = new Calculator();
    System.out.println("2 + 3 = " + calculator.add(2, 3));
    System.out.println("10 - 5 = " + calculator.subtract(10, 5));
    System.out.println("4 * 5 = " + calculator.multiply(4, 5));
    System.out.println("5 / 2 = " + calculator.divide(5, 2));
  }
}
