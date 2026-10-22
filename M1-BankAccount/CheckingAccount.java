/**
 * CSC372 Module 1 Critical Thinking - Implementing a Superclass Bank Account
 * CheckingAccount: inherits from BankAccount, adds an interest rate, and allows
 * overdraft withdrawals with a $30 overdraft fee.
 */
public class CheckingAccount extends BankAccount {
    private static final double OVERDRAFT_FEE = 30.0;
    private double interestRate;   // annual interest rate, as a percentage (e.g., 1.5 = 1.5%)

    public CheckingAccount(String firstName, String lastName, int accountID, double interestRate) {
        super(firstName, lastName, accountID);
        this.interestRate = interestRate;
    }

    public double getInterestRate() { return interestRate; }
    public void setInterestRate(double interestRate) { this.interestRate = interestRate; }

    // Processes a withdrawal. Unlike BankAccount, an overdraft is allowed: the balance goes
    // negative, a $30 fee is assessed, and the resulting negative balance is displayed.
    public void processWithdrawal(double amount) {
        if (amount <= balance) {
            withdrawal(amount);   // inherited normal path
            System.out.printf("Withdrawal of $%.2f processed. Balance: %s%n", amount, formatMoney(balance));
        } else {
            balance -= amount + OVERDRAFT_FEE;
            System.out.printf("Withdrawal of $%.2f processed as an OVERDRAFT. Balance: %s. A $%.2f overdraft fee has been assessed.%n",
                    amount, formatMoney(balance), OVERDRAFT_FEE);
        }
    }

    // Displays all superclass attributes plus the interest rate.
    public void displayAccount() {
        accountSummary();   // inherited: holder, ID, balance
        System.out.printf("Interest rate:  %.2f%%%n", interestRate);
    }
}
