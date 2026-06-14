package calculator;

import util.TestHarness;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

public class CalculatorTest {

  public static void main(String[] args) {
    TestHarness.run(CalculatorTest.class);
  }

  private Calculator calculator;

  @BeforeEach
  void setUp() {
    calculator = new Calculator();
  }

  @Test
  void testAdd() {
    assertEquals(5, calculator.add(2, 3), "2 + 3 should equal 5");
    assertEquals(-1, calculator.add(-4, 3), "-4 + 3 should equal -1");
  }

  @Test
  void testSubtract() {
    assertEquals(5, calculator.subtract(10, 5), "10 - 5 should equal 5");
    assertEquals(0, calculator.subtract(0, 0), "0 - 0 should equal 0");
  }

  @Test
  void testMultiply() {
    assertEquals(20, calculator.multiply(4, 5), "4 * 5 should equal 20");
    assertEquals(-15, calculator.multiply(3, -5), "3 * -5 should equal -15");
  }

  @Test
  void testDivide() {
    assertEquals(2.5, calculator.divide(5, 2), "5 / 2 should equal 2.5");
    assertEquals(-2.0, calculator.divide(10, -5), "10 / -5 should equal -2.0");
  }

  @Test
  void testDivideByZero() {
    Exception exception = assertThrows(IllegalArgumentException.class, () -> {
      calculator.divide(10, 0);
    });
    assertEquals("Division by zero is not allowed.", exception.getMessage());
  }
}
