import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

// Enum for Account Types
enum AccountType {
    CHECKING, SAVINGS, FIXED_DEPOSIT
}

// Customer class to hold personal details
class Customer {
    private final String name;
    private final String email;
    private final String usn; // Unique Serial Number

    public Customer(String name, String email, String usn) {
        this.name = name;
        this.email = email;
        this.usn = usn;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getUsn() {
        return usn;
    }
}

// Utkarsh class for bank account management
public class Utkarsh {
    private double balance;
    private final AccountType accountType;
    private final double interestRate;
    private final List<String> transactionHistory = new ArrayList<>();
    private final String accountNumber; // Randomly generated account number
    private final Customer customer;

    // Constructor for different account types with interest rates
    public Utkarsh(double initialBalance, AccountType accountType, Customer customer) {
        if (initialBalance < 0) throw new IllegalArgumentException("Initial balance cannot be negative.");

        this.balance = initialBalance;
        this.accountType = accountType;
        this.customer = customer;
        this.accountNumber = generateAccountNumber(); // Generate account number

        // Set interest rate based on account type
        this.interestRate = switch (accountType) {
            case CHECKING -> 0.0;
            case SAVINGS -> 0.02;
            case FIXED_DEPOSIT -> 0.05;
        };
    }

    // Method to generate a random account number
    private String generateAccountNumber() {
        Random rand = new Random();
        return "AC" + String.format("%06d", rand.nextInt(1000000)); // Account number format: ACxxxxxx
    }

    public double getBalance() {
        return balance;
    }

    public AccountType getAccountType() {
        return accountType;
    }

    public List<String> getTransactionHistory() {
        return Collections.unmodifiableList(transactionHistory);
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public Customer getCustomer() {
        return customer;
    }

    // Method to deposit amount
    public void deposit(double amount) {
        if (amount <= 0) throw new IllegalArgumentException("Deposit amount must be positive.");
        balance += amount;
        transactionHistory.add("Deposit: $" + amount);
    }

    // Method to withdraw amount
    public void withdraw(double amount) {
        if (amount <= 0) throw new IllegalArgumentException("Withdrawal amount must be positive.");
        if (amount > balance) throw new IllegalArgumentException("Insufficient funds.");
        balance -= amount;
        transactionHistory.add("Withdrawal: $" + amount);
    }

    // Method to apply interest
    public void applyInterest() {
        if (accountType != AccountType.CHECKING) {
            double interest = balance * interestRate;
            balance += interest;
            transactionHistory.add("Interest Applied: $" + interest);
        }
    }

    // Main method to demonstrate functionality
    public static void main(String[] args) {
        SwingUtilities.invokeLater(Utkarsh::createAndShowGUI);
    }

    // Create and show GUI
    private static void createAndShowGUI() {
        JFrame frame = new JFrame("Bank Account Management System");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 500);
        frame.setLayout(new GridLayout(8, 1));

        // Components
        JTextField nameField = new JTextField("Enter your name");
        JTextField emailField = new JTextField("Enter your email");
        JTextField usnField = new JTextField("Enter your unique USN");
        JTextField balanceField = new JTextField("Enter initial balance");
        JComboBox<AccountType> accountTypeComboBox = new JComboBox<>(AccountType.values());
        JTextField amountField = new JTextField("Enter amount for deposit/withdrawal");
        JTextArea outputArea = new JTextArea();
        outputArea.setEditable(false);
        JButton createAccountButton = new JButton("Create Account");
        JButton depositButton = new JButton("Deposit");
        JButton withdrawButton = new JButton("Withdraw");
        JButton applyInterestButton = new JButton("Apply Interest");

        // Create an instance variable to hold the account
        Utkarsh[] account = {null}; // Using an array to make it mutable within lambda

        // Adding components to the frame
        frame.add(nameField);
        frame.add(emailField);
        frame.add(usnField);
        frame.add(balanceField);
        frame.add(accountTypeComboBox);
        frame.add(amountField);
        frame.add(createAccountButton);
        frame.add(depositButton);
        frame.add(withdrawButton);
        frame.add(applyInterestButton);
        frame.add(new JScrollPane(outputArea));

        // Creating account button action
        createAccountButton.addActionListener(e -> {
            try {
                String name = nameField.getText();
                String email = emailField.getText();
                String usn = usnField.getText();
                double initialBalance = Double.parseDouble(balanceField.getText());
                AccountType accountType = (AccountType) accountTypeComboBox.getSelectedItem();

                // Validate USN uniqueness
                if (isUsnUnique(usn)) {
                    Customer customer = new Customer(name, email, usn);
                    account[0] = new Utkarsh(initialBalance, accountType, customer);
                    outputArea.append("Account created for " + name + " (" + usn + ") with balance $" + initialBalance + " and account number " + account[0].getAccountNumber() + "\n");
                } else {
                    outputArea.append("USN must be unique. Please enter a different USN.\n");
                }
            } catch (NumberFormatException ex) {
                outputArea.append("Invalid initial balance. Please enter a valid number.\n");
            }
        });

        // Deposit button action
        depositButton.addActionListener(e -> {
            try {
                double amount = Double.parseDouble(amountField.getText());
                if (account[0] != null) {
                    account[0].deposit(amount);
                    outputArea.append("Deposited: $" + amount + " to account " + account[0].getAccountNumber() + "\n");
                } else {
                    outputArea.append("Please create an account first.\n");
                }
            } catch (NumberFormatException ex) {
                outputArea.append("Invalid amount. Please enter a valid number.\n");
            } catch (IllegalArgumentException ex) {
                outputArea.append(ex.getMessage() + "\n");
            }
        });

        // Withdraw button action
        withdrawButton.addActionListener(e -> {
            try {
                double amount = Double.parseDouble(amountField.getText());
                if (account[0] != null) {
                    account[0].withdraw(amount);
                    outputArea.append("Withdrew: $" + amount + " from account " + account[0].getAccountNumber() + "\n");
                } else {
                    outputArea.append("Please create an account first.\n");
                }
            } catch (NumberFormatException ex) {
                outputArea.append("Invalid amount. Please enter a valid number.\n");
            } catch (IllegalArgumentException ex) {
                outputArea.append(ex.getMessage() + "\n");
            }
        });

        // Apply interest button action
        applyInterestButton.addActionListener(e -> {
            if (account[0] != null) {
                account[0].applyInterest();
                outputArea.append("Interest applied. New balance: $" + account[0].getBalance() + " for account " + account[0].getAccountNumber() + "\n");
            } else {
                outputArea.append("Please create an account first.\n");
            }
        });

        frame.setVisible(true);
    }

    // Check if USN is unique
    private static boolean isUsnUnique(String usn) {
        // You can implement a logic to check the uniqueness of the USN.
        // For simplicity, assume all USNs are unique in this example.
        return true; // In a real application, check against existing USNs in the database.
    }
}
