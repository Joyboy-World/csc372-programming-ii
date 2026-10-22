/**
 * CSC372 Module 1 Critical Thinking - Implementing a Superclass Bank Account
 * BankAccountTest: exercises BankAccount and CheckingAccount through six test cases.
 */
public class BankAccountTest {
    public static void main(String[] args) {
        System.out.println("=== Test 1: BankAccount deposit and withdrawal ===");
        BankAccount savings = new BankAccount("Theo", "Thomas", 1001);
        savings.deposit(500.00);
        savings.withdrawal(200.00);
        savings.accountSummary();                       // expected balance: $300.00

        System.out.println();
        System.out.println("=== Test 2: BankAccount refuses an overdraft ===");
        savings.withdrawal(1000.00);                    // declined; balance unchanged
        savings.accountSummary();                       // expected balance: $300.00

        System.out.println();
        System.out.println("=== Test 3: CheckingAccount normal withdrawal and displayAccount ===");
        CheckingAccount checking = new CheckingAccount("Theo", "Thomas", 2001, 1.5);
        checking.deposit(100.00);
        checking.processWithdrawal(40.00);              // expected balance: $60.00
        checking.displayAccount();                      // includes the 1.50% interest rate

        System.out.println();
        System.out.println("=== Test 4: CheckingAccount overdraft with $30 fee ===");
        checking.processWithdrawal(100.00);             // 60 - 100 - 30 = -$70.00, fee assessed
        checking.displayAccount();

        System.out.println();
        System.out.println("=== Test 5: Setters, getters, and recovery deposit ===");
        checking.setLastName("Thomas-Jones");
        checking.setInterestRate(2.0);
        checking.deposit(100.00);                       // -70 + 100 = $30.00
        System.out.println("Holder: " + checking.getFirstName() + " " + checking.getLastName()
                + " | ID: " + checking.getAccountID() + " | Rate: " + checking.getInterestRate() + "%");
        System.out.printf("Balance via getBalance(): $%.2f%n", checking.getBalance());

        System.out.println();
        System.out.println("=== Test 6: Polymorphism - withdrawal() through a BankAccount reference ===");
        BankAccount plain = savings;                    // static type BankAccount, runtime BankAccount
        BankAccount overdraftable = checking;           // static type BankAccount, runtime CheckingAccount
        plain.withdrawal(1000.00);                      // BankAccount.withdrawal: declined
        overdraftable.withdrawal(100.00);               // CheckingAccount.withdrawal override: overdraft + fee
        overdraftable.accountSummary();                 // 30 - 100 - 30 = -$100.00
    }
}
