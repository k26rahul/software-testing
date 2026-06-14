package bankaccount;

import util.TestHarness;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

// Demonstrates: @Test, @BeforeAll, @AfterAll, @BeforeEach, @AfterEach,
//               @DisplayName, @Disabled, @Nested, @RepeatedTest, @Tag
@DisplayName("BankAccount Test Suite")
public class BankAccountTest {

  public static void main(String[] args) {
    TestHarness.run(BankAccountTest.class);
  }

  // Shared across all tests in the class - must be static.
  private static int totalTestsRun;

  private BankAccount alice;
  private BankAccount bob;

  @BeforeAll
  static void initSuite() {
    totalTestsRun = 0;
    System.out.println("[Suite] BankAccount test suite starting.");
  }

  @AfterAll
  static void tearDownSuite() {
    System.out.println("[Suite] Tests completed. Total run: " + totalTestsRun);
  }

  @BeforeEach
  void setUp() {
    alice = new BankAccount("Alice", "ACC-001", 1000.0);
    bob = new BankAccount("Bob", "ACC-002", 500.0);
    totalTestsRun++;
  }

  @AfterEach
  void tearDown() {
    System.out.println("[Test] Finished. Alice balance: " + alice.getBalance());
  }

  // ── Construction ─────────────────────────────────────────────────────────

  @Test
  @DisplayName("Account is created with the correct initial state")
  void testAccountCreation() {
    assertEquals("Alice", alice.getAccountHolder());
    assertEquals("ACC-001", alice.getAccountNumber());
    assertEquals(1000.0, alice.getBalance());
    assertFalse(alice.isFrozen());
    assertFalse(alice.getTransactionHistory().isEmpty());
  }

  @Test
  @DisplayName("Constructor rejects blank holder name")
  void testConstructorRejectsBlankHolder() {
    assertThrows(IllegalArgumentException.class,
        () -> new BankAccount("", "ACC-X", 100.0));
  }

  @Test
  @DisplayName("Constructor rejects negative initial balance")
  void testConstructorRejectsNegativeBalance() {
    assertThrows(IllegalArgumentException.class,
        () -> new BankAccount("Carol", "ACC-Y", -50.0));
  }

  // ── Deposit ──────────────────────────────────────────────────────────────

  @Nested
  @DisplayName("Deposit operations")
  class DepositTests {

    @Test
    @DisplayName("Deposit increases balance correctly")
    void testDepositIncreasesBalance() {
      alice.deposit(500.0);
      assertEquals(1500.0, alice.getBalance());
    }

    @Test
    @DisplayName("Deposit records transaction in history")
    void testDepositRecordsHistory() {
      alice.deposit(200.0);
      assertTrue(alice.getTransactionHistory().stream()
          .anyMatch(entry -> entry.contains("Deposited: 200.0")));
    }

    @Test
    @DisplayName("Deposit of zero or negative amount is rejected")
    void testDepositRejectsNonPositiveAmount() {
      assertThrows(IllegalArgumentException.class, () -> alice.deposit(0));
      assertThrows(IllegalArgumentException.class, () -> alice.deposit(-100.0));
    }
  }

  // ── Withdrawal ───────────────────────────────────────────────────────────

  @Nested
  @DisplayName("Withdrawal operations")
  class WithdrawalTests {

    @Test
    @DisplayName("Withdrawal reduces balance correctly")
    void testWithdrawalReducesBalance() {
      alice.withdraw(400.0);
      assertEquals(600.0, alice.getBalance());
    }

    @Test
    @DisplayName("Withdrawal of more than balance throws InsufficientFunds")
    void testWithdrawalExceedingBalance() {
      IllegalStateException ex = assertThrows(IllegalStateException.class,
          () -> alice.withdraw(2000.0));
      assertEquals("Insufficient funds.", ex.getMessage());
    }

    @Test
    @DisplayName("Withdrawal of zero or negative amount is rejected")
    void testWithdrawalRejectsNonPositiveAmount() {
      assertThrows(IllegalArgumentException.class, () -> alice.withdraw(0));
      assertThrows(IllegalArgumentException.class, () -> alice.withdraw(-50.0));
    }

    @Test
    @DisplayName("Exact balance withdrawal leaves zero balance")
    void testWithdrawExactBalance() {
      alice.withdraw(1000.0);
      assertEquals(0.0, alice.getBalance());
    }
  }

  // ── Transfer ─────────────────────────────────────────────────────────────

  @Test
  @DisplayName("Transfer moves funds between two accounts accurately")
  void testTransfer() {
    alice.transfer(bob, 300.0);
    assertEquals(700.0, alice.getBalance());
    assertEquals(800.0, bob.getBalance());
  }

  @Test
  @DisplayName("Transfer to same account is rejected")
  void testTransferToSelf() {
    assertThrows(IllegalArgumentException.class, () -> alice.transfer(alice, 100.0));
  }

  @Test
  @DisplayName("Transfer to null target is rejected")
  void testTransferToNull() {
    assertThrows(IllegalArgumentException.class, () -> alice.transfer(null, 100.0));
  }

  // ── Interest ─────────────────────────────────────────────────────────────

  @Test
  @DisplayName("Interest is calculated and applied correctly")
  void testApplyInterest() {
    // 1000 * 0.05 * 2 = 100 interest => 1100 total
    alice.applyInterest(0.05, 2);
    assertEquals(1100.0, alice.getBalance(), 0.001);
  }

  @Test
  @DisplayName("Negative interest rate is rejected")
  void testNegativeInterestRate() {
    assertThrows(IllegalArgumentException.class, () -> alice.applyInterest(-0.05, 1));
  }

  @Test
  @DisplayName("Zero years for interest is rejected")
  void testZeroYearsForInterest() {
    assertThrows(IllegalArgumentException.class, () -> alice.applyInterest(0.05, 0));
  }

  // ── Freeze / Unfreeze ────────────────────────────────────────────────────

  @Nested
  @DisplayName("Account freeze operations")
  class FreezeTests {

    @Test
    @DisplayName("Frozen account blocks deposits")
    void testFrozenBlocksDeposit() {
      alice.freeze();
      assertTrue(alice.isFrozen());
      assertThrows(IllegalStateException.class, () -> alice.deposit(100.0));
    }

    @Test
    @DisplayName("Frozen account blocks withdrawals")
    void testFrozenBlocksWithdrawal() {
      alice.freeze();
      assertThrows(IllegalStateException.class, () -> alice.withdraw(100.0));
    }

    @Test
    @DisplayName("Unfreezing restores normal operations")
    void testUnfreezeRestoresAccess() {
      alice.freeze();
      alice.unfreeze();
      assertFalse(alice.isFrozen());
      assertDoesNotThrow(() -> alice.deposit(100.0));
    }
  }

  // ── Transaction History ──────────────────────────────────────────────────

  @Test
  @DisplayName("Transaction history is unmodifiable from outside")
  void testHistoryIsUnmodifiable() {
    assertThrows(UnsupportedOperationException.class,
        () -> alice.getTransactionHistory().add("Fake entry"));
  }

  @Test
  @DisplayName("History grows with each operation")
  void testHistoryGrowsWithOperations() {
    int initialSize = alice.getTransactionHistory().size();
    alice.deposit(100.0);
    alice.withdraw(50.0);
    assertEquals(initialSize + 2, alice.getTransactionHistory().size());
  }

  // ── Repeated Test ────────────────────────────────────────────────────────

  @RepeatedTest(3)
  @DisplayName("Balance is consistent after repeated deposits and withdrawals")
  void testBalanceConsistencyRepeated(RepetitionInfo info) {
    alice.deposit(100.0);
    alice.withdraw(100.0);
    // Balance should always return to 1000.0 regardless of repetition.
    assertEquals(1000.0, alice.getBalance(),
        "Repetition " + info.getCurrentRepetition() + ": balance mismatch");
  }

  // ── Disabled ─────────────────────────────────────────────────────────────

  @Test
  @Disabled("Compound interest not yet implemented - placeholder for future feature")
  @DisplayName("Compound interest calculation")
  void testCompoundInterest() {
    // Will be enabled once compoundInterest() is added to BankAccount.
    fail("Not implemented yet.");
  }

  // ── Tag ──────────────────────────────────────────────────────────────────

  @Test
  @Tag("smoke")
  @DisplayName("Smoke: account is usable end-to-end")
  void testEndToEndSmokeFlow() {
    alice.deposit(500.0);
    alice.withdraw(200.0);
    alice.transfer(bob, 100.0);
    alice.applyInterest(0.10, 1);
    // 1000 + 500 - 200 - 100 = 1200, then 1200 * 0.10 * 1 = 120 interest => 1320
    assertEquals(1320.0, alice.getBalance(), 0.001);
    assertEquals(600.0, bob.getBalance(), 0.001);
  }
}
