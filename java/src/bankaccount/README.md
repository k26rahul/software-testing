# Bank Account

A beginner Java application demonstrating a simple bank account system, paired with a comprehensive JUnit 5 test suite that covers all common test decorators.

## Application - `BankAccount.java`

The `BankAccount` class models a basic bank account with the following operations:

| Method | Description |
|---|---|
| `deposit(amount)` | Adds funds - rejects zero or negative amounts |
| `withdraw(amount)` | Removes funds - rejects non-positive amounts and overdrafts |
| `transfer(target, amount)` | Moves funds to another account - rejects self-transfers and null targets |
| `applyInterest(rate, years)` | Applies simple interest: `balance * rate * years` |
| `freeze()` | Locks the account, blocking all deposits and withdrawals |
| `unfreeze()` | Restores normal account access |
| `getTransactionHistory()` | Returns an unmodifiable log of all operations |

Every operation records an entry in the transaction history. The constructor validates the account holder name, account number, and initial balance.

## Tests - `BankAccountTest.java`

The test file demonstrates every common JUnit 5 decorator:

| Decorator | Usage |
|---|---|
| `@BeforeAll` | One-time suite setup - initializes the test counter and prints a start message |
| `@AfterAll` | One-time suite teardown - prints total number of tests run |
| `@BeforeEach` | Creates fresh `alice` and `bob` accounts before each test |
| `@AfterEach` | Logs the current balance after each test completes |
| `@Test` | All standard individual test methods |
| `@Nested` | Groups related tests into `DepositTests`, `WithdrawalTests`, and `FreezeTests` |
| `@DisplayName` | Human-readable labels on the class, nested classes, and individual methods |
| `@RepeatedTest` | Runs a deposit-then-withdraw cycle 3 times using `RepetitionInfo` |
| `@Disabled` | Marks a compound interest test as skipped - pending future implementation |
| `@Tag` | Tags the end-to-end smoke test with `"smoke"` for selective test runs |

## Test Coverage

- **Construction** - valid creation, blank holder name, negative initial balance
- **Deposit** - balance increase, history recording, non-positive amount rejection
- **Withdrawal** - balance reduction, insufficient funds, non-positive amount, exact balance
- **Transfer** - correct fund movement, self-transfer rejection, null target rejection
- **Interest** - correct calculation, negative rate rejection, zero years rejection
- **Freeze/Unfreeze** - blocked deposit, blocked withdrawal, restored access after unfreeze
- **Transaction History** - unmodifiable from outside, grows with each operation
- **End-to-end smoke** - full flow combining deposit, withdraw, transfer, and interest
