package practice.calculator;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

public class CalculatorTest {
  private Calculator calc;

  @BeforeEach
  public void setUp() {
    calc = new Calculator();
  }

  @Test
  public void testAdd() {
    assertEquals(12, calc.add(10, 2), "10 + 2 must equal to 12");
    assertEquals(6, calc.add(10, -4), "10 + (-6) must equal to 4");
  }

  @Test
  public void testSubtract() {
    assertEquals(8, calc.subtract(10, 2), "10 - 2 must equal to 8");
    assertEquals(14, calc.subtract(10, -4), "10 - (-4) must equal to 14");
    assertEquals(-6, calc.subtract(-10, -4), "(-10) - (-4) must equal to -6");
  }

  @Test
  public void testMultiply() {
    assertEquals(20, calc.multiply(10, 2), "10 * 2 must equal to 20");
    assertEquals(-40, calc.multiply(10, -4), "10 * (-4) must equal to -40");
    assertEquals(40, calc.multiply(-10, -4), "(-10) * (-4) must equal to 40");
  }

  @Test
  public void testDivide() {
    assertEquals(5, calc.divide(10, 2), "10 / 2 must equal to 5");
    assertEquals(2.5, calc.divide(5, 2), "5 / 2 must equal to 2.5");
    assertEquals(-2.5, calc.divide(-5, 2), "(-5) / 2 must equal to -2.5");
    assertEquals(-2.5, calc.divide(5, -2), "5 / (-2) must equal to -2.5");
  }

  @Test
  public void testDivideByZero() {
    Exception exception = assertThrows(IllegalArgumentException.class, () -> {
      calc.divide(10, 0);
    });
    assertEquals("Divide by zero is not allowed.", exception.getMessage());
  }
}
