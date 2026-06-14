import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

public class CalculatorTest {

  private Calculator calculator;

  @BeforeEach
  void setUp() {
    calculator = new Calculator(); // Instantiated before each test
  }

  @Test
  void testAdd() {
    int result = calculator.add(2, 3);
    assertEquals(5, result, "2 + 3 should equal 5");
  }

  @Test
  void testDivideByZero() {
    assertThrows(IllegalArgumentException.class, () -> {
      calculator.divide(10, 0);
    }, "Division by zero should throw IllegalArgumentException");
  }
}
