/**
 * CSC372 Module 2 Critical Thinking - Creating a GUI Bank Balance Application
 * BankBalanceGUI: a Swing window over the Module 1 BankAccount class.
 * The user enters a starting balance, views it in a JPanel by selecting a JButton,
 * deposits and withdraws through the same window, and sees the remaining balance on exit.
 */
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

public class BankBalanceGUI extends JFrame implements ActionListener {
    private final BankAccount account = new BankAccount("Theo", "Thomas", 1001);
    private boolean balanceSet = false;

    private final JTextField startField = new JTextField(10);
    private final JTextField amountField = new JTextField(10);
    private final JLabel balanceLabel = new JLabel("Balance: not set", SwingConstants.CENTER);

    private final JButton setButton = new JButton("Set Balance");
    private final JButton showButton = new JButton("Show Balance");
    private final JButton depositButton = new JButton("Deposit");
    private final JButton withdrawButton = new JButton("Withdraw");
    private final JButton exitButton = new JButton("Exit");

    public BankBalanceGUI() {
        super("Bank Balance");

        // Input panel: starting balance, then an amount for deposits and withdrawals.
        JPanel inputPanel = new JPanel(new GridLayout(2, 3, 8, 8));
        inputPanel.add(new JLabel("Starting balance ($):"));
        inputPanel.add(startField);
        inputPanel.add(setButton);
        inputPanel.add(new JLabel("Amount ($):"));
        inputPanel.add(amountField);
        inputPanel.add(new JLabel());

        // Balance panel: the JPanel the balance is displayed in.
        JPanel balancePanel = new JPanel(new BorderLayout());
        balancePanel.setBorder(BorderFactory.createTitledBorder("Account Balance"));
        balancePanel.add(balanceLabel, BorderLayout.CENTER);

        // Button panel: one ActionListener (this class) handles every button.
        JPanel buttonPanel = new JPanel(new GridLayout(1, 4, 8, 8));
        for (JButton button : new JButton[] {showButton, depositButton, withdrawButton, exitButton}) {
            buttonPanel.add(button);
            button.addActionListener(this);
        }
        setButton.addActionListener(this);

        JPanel content = new JPanel(new BorderLayout(10, 10));
        content.setBorder(BorderFactory.createEmptyBorder(12, 12, 12, 12));
        content.add(inputPanel, BorderLayout.NORTH);
        content.add(balancePanel, BorderLayout.CENTER);
        content.add(buttonPanel, BorderLayout.SOUTH);
        setContentPane(content);

        // Closing the window with the X goes through the same exit summary as the Exit button.
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                exitWithSummary();
            }
        });

        setSize(520, 240);
        setLocationRelativeTo(null);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();
        if (source == exitButton) {
            exitWithSummary();
            return;
        }
        if (source == setButton) {
            Double start = readAmount(startField, "starting balance");
            if (start != null) {
                account.deposit(start);            // a new BankAccount starts at $0.00
                balanceSet = true;
                startField.setEditable(false);
                setButton.setEnabled(false);
                balanceLabel.setText("Starting balance set. Select Show Balance to view it.");
            }
            return;
        }
        if (!balanceSet) {
            showError("Enter a starting balance and select Set Balance first.");
            return;
        }
        if (source == showButton) {
            showBalance("Current balance");
            return;
        }
        Double amount = readAmount(amountField, "amount");
        if (amount == null) {
            return;
        }
        if (source == depositButton) {
            account.deposit(amount);
            showBalance("Deposited " + BankAccount.formatMoney(amount) + ". Balance");
        } else if (amount > account.getBalance()) {
            showError("Withdrawal of " + BankAccount.formatMoney(amount)
                    + " declined: insufficient funds.");
        } else {
            account.withdrawal(amount);
            showBalance("Withdrew " + BankAccount.formatMoney(amount) + ". Balance");
        }
        amountField.setText("");
    }

    // Parses a positive dollar amount from a field; shows an error and returns null otherwise.
    private Double readAmount(JTextField field, String name) {
        try {
            double value = Double.parseDouble(field.getText().trim());
            if (value > 0) {
                return value;
            }
            showError("The " + name + " must be greater than zero.");
        } catch (NumberFormatException ex) {
            showError("The " + name + " must be a number, for example 250.00.");
        }
        return null;
    }

    private void showBalance(String prefix) {
        balanceLabel.setText(prefix + ": " + BankAccount.formatMoney(account.getBalance()));
    }

    private void showError(String message) {
        JOptionPane.showMessageDialog(this, message, "Input error", JOptionPane.ERROR_MESSAGE);
    }

    private void exitWithSummary() {
        JOptionPane.showMessageDialog(this,
                "Remaining balance: " + BankAccount.formatMoney(account.getBalance()),
                "Goodbye", JOptionPane.INFORMATION_MESSAGE);
        dispose();
        System.exit(0);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new BankBalanceGUI().setVisible(true));
    }
}
