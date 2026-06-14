package bankaccount;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class BankAccount {

  private final String accountHolder;
  private final String accountNumber;
  private double balance;
  private boolean frozen;
  private final List<String> transactionHistory;

  public BankAccount(String accountHolder, String accountNumber, double initialBalance) {
    if (accountHolder == null || accountHolder.isBlank()) {
      throw new IllegalArgumentException("Account holder name cannot be empty.");
    }
    if (accountNumber == null || accountNumber.isBlank()) {
      throw new IllegalArgumentException("Account number cannot be empty.");
    }
    if (initialBalance < 0) {
      throw new IllegalArgumentException("Initial balance cannot be negative.");
    }
    this.accountHolder = accountHolder;
    this.accountNumber = accountNumber;
    this.balance = initialBalance;
    this.frozen = false;
    this.transactionHistory = new ArrayList<>();
    transactionHistory.add("Account opened with balance: " + initialBalance);
  }

  public void deposit(double amount) {
    if (frozen) {
      throw new IllegalStateException("Cannot deposit into a frozen account.");
    }
    if (amount <= 0) {
      throw new IllegalArgumentException("Deposit amount must be positive.");
    }
    balance += amount;
    transactionHistory.add("Deposited: " + amount + " | Balance: " + balance);
  }

  public void withdraw(double amount) {
    if (frozen) {
      throw new IllegalStateException("Cannot withdraw from a frozen account.");
    }
    if (amount <= 0) {
      throw new IllegalArgumentException("Withdrawal amount must be positive.");
    }
    if (amount > balance) {
      throw new IllegalStateException("Insufficient funds.");
    }
    balance -= amount;
    transactionHistory.add("Withdrew: " + amount + " | Balance: " + balance);
  }

  // Transfers funds from this account to the target account.
  public void transfer(BankAccount target, double amount) {
    if (target == null) {
      throw new IllegalArgumentException("Target account cannot be null.");
    }
    if (target == this) {
      throw new IllegalArgumentException("Cannot transfer to the same account.");
    }
    this.withdraw(amount);
    target.deposit(amount);
    transactionHistory.add("Transferred: " + amount + " to " + target.getAccountNumber());
  }

  // Applies simple interest: balance * rate * years.
  public void applyInterest(double annualRate, int years) {
    if (annualRate < 0) {
      throw new IllegalArgumentException("Interest rate cannot be negative.");
    }
    if (years <= 0) {
      throw new IllegalArgumentException("Years must be a positive integer.");
    }
    double interest = balance * annualRate * years;
    balance += interest;
    transactionHistory.add("Interest applied: " + interest + " | Balance: " + balance);
  }

  public void freeze() {
    frozen = true;
    transactionHistory.add("Account frozen.");
  }

  public void unfreeze() {
    frozen = false;
    transactionHistory.add("Account unfrozen.");
  }

  public double getBalance() {
    return balance;
  }

  public String getAccountHolder() {
    return accountHolder;
  }

  public String getAccountNumber() {
    return accountNumber;
  }

  public boolean isFrozen() {
    return frozen;
  }

  public List<String> getTransactionHistory() {
    return Collections.unmodifiableList(transactionHistory);
  }

  public static void main(String[] args) {
    BankAccount alice = new BankAccount("Alice", "ACC-001", 1000.0);
    BankAccount bob = new BankAccount("Bob", "ACC-002", 500.0);

    alice.deposit(200.0);
    alice.withdraw(150.0);
    alice.transfer(bob, 300.0);
    alice.applyInterest(0.05, 2);

    System.out.println("Alice's balance: " + alice.getBalance());
    System.out.println("Bob's balance:   " + bob.getBalance());
    System.out.println("\nAlice's transaction history:");
    alice.getTransactionHistory().forEach(System.out::println);
  }
}
