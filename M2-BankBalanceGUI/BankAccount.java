/**
 * CSC372 Module 1 Critical Thinking - Implementing a Superclass Bank Account
 * BankAccount: the superclass. Holds the account holder's name, account ID, and balance.
 *
 * Design note: a plain BankAccount does not allow overdrafts; withdrawal() refuses any
 * amount larger than the balance. CheckingAccount (the subclass) is the account type that
 * allows overdrafts and charges the $30 fee.
 */
public class BankAccount {
    private String firstName;
    private String lastName;
    private int accountID;
    protected double balance;   // protected so the subclass can apply overdraft rules directly

    // Constructor: initializes the balance to zero.
    public BankAccount() {
        balance = 0.0;
    }

    public BankAccount(String firstName, String lastName, int accountID) {
        this();
        this.firstName = firstName;
        this.lastName = lastName;
        this.accountID = accountID;
    }

    // Adds the parameter value to the existing balance.
    public void deposit(double amount) {
        balance += amount;
    }

    // Subtracts the parameter value from the existing balance; refuses an overdraft.
    public void withdrawal(double amount) {
        if (amount > balance) {
            System.out.printf("Withdrawal of $%.2f declined: insufficient funds (balance $%.2f).%n", amount, balance);
            return;
        }
        balance -= amount;
    }

    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }

    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }

    public int getAccountID() { return accountID; }
    public void setAccountID(int accountID) { this.accountID = accountID; }

    public double getBalance() { return balance; }

    // Prints all account information.
    public void accountSummary() {
        System.out.println("Account holder: " + firstName + " " + lastName);
        System.out.println("Account ID:     " + accountID);
        System.out.printf("Balance:        %s%n", formatMoney(balance));
    }

    // Formats a dollar amount so a negative balance reads -$70.00 rather than $-70.00.
    protected static String formatMoney(double amount) {
        return (amount < 0 ? "-$" : "$") + String.format("%.2f", Math.abs(amount));
    }
}
