import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// Class representing an inventory item
class Item {
    private String name;
    private int quantity;
    private double price;

    public Item(String name, int quantity, double price) {
        this.name = name;
        this.quantity = quantity;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public int getQuantity() {
        return quantity;
    }

    public double getPrice() {
        return price;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return name + " (Qty: " + quantity + ", Price: $" + price + ")";
    }
}

// Main class for Inventory Management System
public class Utkarsh1 {
    private final List<Item> inventory = new ArrayList<>(); // Inventory list
    private final JTextArea outputArea = new JTextArea(10, 30); // Area to display output
    private final JTextField nameField = new JTextField(15);
    private final JTextField quantityField = new JTextField(5);
    private final JTextField priceField = new JTextField(10);
    private final JTextField searchField = new JTextField(15);
    private final JTextField usernameField = new JTextField(15);
    private final JPasswordField passwordField = new JPasswordField(15);
    private final JCheckBox showPasswordCheckBox = new JCheckBox("Show Password");

    private final Map<String, String> userCredentials = new HashMap<>(); // Store user credentials
    private boolean isAuthenticated = false; // To track user authentication
    private JButton logoutButton; // Logout button

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Utkarsh1::createAndShowGUI);
    }

    private static void createAndShowGUI() {
        JFrame frame = new JFrame("Inventory Management System");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new GridBagLayout()); // Use GridBagLayout for better control

        Utkarsh1 app = new Utkarsh1();
        app.createLoginComponents(frame);

        frame.setSize(400, 400);
        frame.setVisible(true);
    }

    // Create login components
    private void createLoginComponents(JFrame frame) {
        frame.getContentPane().removeAll(); // Clear previous components
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5); // Add some padding

        gbc.gridx = 0; gbc.gridy = 0; // Username label
        frame.add(new JLabel("Username:"), gbc);
        
        gbc.gridx = 1; // Username field
        frame.add(usernameField, gbc);
        
        gbc.gridx = 0; gbc.gridy = 1; // Password label
        frame.add(new JLabel("Password:"), gbc);
        
        gbc.gridx = 1; // Password field
        frame.add(passwordField, gbc);
        
        gbc.gridx = 1; gbc.gridy = 2; // Show password checkbox
        frame.add(showPasswordCheckBox, gbc);
        showPasswordCheckBox.addActionListener(e -> togglePasswordVisibility());

        gbc.gridx = 0; gbc.gridy = 3; // Login button
        JButton loginButton = new JButton("Login");
        loginButton.addActionListener(this::loginUser);
        frame.add(loginButton, gbc);

        gbc.gridx = 1; // Signup button
        JButton signupButton = new JButton("Signup");
        signupButton.addActionListener(this::signupUser);
        frame.add(signupButton, gbc);

        outputArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(outputArea);
        gbc.gridx = 0; gbc.gridy = 4; gbc.gridwidth = 2; // Output area
        frame.add(scrollPane, gbc);

        frame.revalidate(); // Refresh the frame
        frame.repaint(); // Repaint the frame
    }

    // Toggle password visibility
    private void togglePasswordVisibility() {
        if (showPasswordCheckBox.isSelected()) {
            passwordField.setEchoChar((char) 0); // Show password
        } else {
            passwordField.setEchoChar('•'); // Hide password
        }
    }

    // Method to log in an existing user
    private void loginUser(ActionEvent e) {
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());

        if (username.isEmpty() || password.isEmpty()) {
            outputArea.append("Please enter both username and password!\n");
            return;
        }

        if (userCredentials.containsKey(username)) {
            if (userCredentials.get(username).equals(password)) {
                isAuthenticated = true;
                outputArea.append("Login successful!\n");
                clearLoginFields();
                createInventoryComponents(); // Show inventory components after login
            } else {
                outputArea.append("Invalid password!\n");
            }
        } else {
            outputArea.append("User does not exist. Please sign up first.\n");
        }
    }

    // Method to sign up a new user
    private void signupUser(ActionEvent e) {
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());

        if (username.isEmpty() || password.isEmpty()) {
            outputArea.append("Please enter both username and password!\n");
            return;
        }

        if (userCredentials.containsKey(username)) {
            outputArea.append("Username already exists. Please choose a different username.\n");
        } else {
            userCredentials.put(username, password);
            outputArea.append("Signup successful! You can now login.\n");
            clearLoginFields();
        }
    }

    // Create inventory components after successful login
    private void createInventoryComponents() {
        JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(outputArea);
        frame.getContentPane().removeAll(); // Clear existing components
        outputArea.setText(""); // Clear previous messages
        outputArea.setEditable(true); // Allow editing

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5); // Add some padding

        // Adding inventory input fields and buttons
        gbc.gridx = 0; gbc.gridy = 0; // Item Name label
        frame.add(new JLabel("Item Name:"), gbc);
        
        gbc.gridx = 1; // Item Name field
        frame.add(nameField, gbc);
        
        gbc.gridx = 0; gbc.gridy = 1; // Quantity label
        frame.add(new JLabel("Quantity:"), gbc);
        
        gbc.gridx = 1; // Quantity field
        frame.add(quantityField, gbc);
        
        gbc.gridx = 0; gbc.gridy = 2; // Price label
        frame.add(new JLabel("Price:"), gbc);
        
        gbc.gridx = 1; // Price field
        frame.add(priceField, gbc);

        gbc.gridx = 0; gbc.gridy = 3; // Add Item button
        JButton addButton = new JButton("Add Item");
        addButton.addActionListener(this::addItem);
        frame.add(addButton, gbc);

        gbc.gridx = 1; // Update Item button
        JButton updateButton = new JButton("Update Item");
        updateButton.addActionListener(this::updateItem);
        frame.add(updateButton, gbc);

        gbc.gridx = 0; gbc.gridy = 4; // Delete Item button
        JButton deleteButton = new JButton("Delete Item");
        deleteButton.addActionListener(this::deleteItem);
        frame.add(deleteButton, gbc);

        gbc.gridx = 1; // Search label
        frame.add(new JLabel("Search by name:"), gbc);
        
        gbc.gridx = 0; gbc.gridy = 5; // Search field
        frame.add(searchField, gbc);
        
        gbc.gridx = 1; // Search button
        JButton searchButton = new JButton("Search Item");
        searchButton.addActionListener(this::searchItem);
        frame.add(searchButton, gbc);

        gbc.gridx = 0; gbc.gridy = 6; // Logout button
        logoutButton = new JButton("Logout");
        logoutButton.addActionListener(this::logoutUser);
        frame.add(logoutButton, gbc);

        // Output area to display the inventory
        outputArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(outputArea);
        gbc.gridx = 0; gbc.gridy = 7; gbc.gridwidth = 2; // Output area
        frame.add(scrollPane, gbc);

        // Notify for low inventory
        notifyLowInventory();

        frame.revalidate(); // Refresh the frame
        frame.repaint(); // Repaint the frame
    }

    // Method to log out the user
    private void logoutUser(ActionEvent e) {
        isAuthenticated = false;
        outputArea.append("Logged out successfully!\n");

        // Reset GUI to show login components again
        JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(outputArea);
        frame.getContentPane().removeAll(); // Clear existing components
        createLoginComponents(frame);
    }

    // Method to add an item to the inventory
    private void addItem(ActionEvent e) {
        String name = nameField.getText();
        String quantityText = quantityField.getText();
        String priceText = priceField.getText();

        if (name.isEmpty() || quantityText.isEmpty() || priceText.isEmpty()) {
            outputArea.append("Please fill in all fields!\n");
            return;
        }

        try {
            int quantity = Integer.parseInt(quantityText);
            double price = Double.parseDouble(priceText);
            inventory.add(new Item(name, quantity, price));
            outputArea.append("Added: " + name + "\n");
            clearFields();
        } catch (NumberFormatException ex) {
            outputArea.append("Invalid quantity or price!\n");
        }
    }

    // Method to update an item in the inventory
    private void updateItem(ActionEvent e) {
        String name = nameField.getText();
        String quantityText = quantityField.getText();
        String priceText = priceField.getText();

        if (name.isEmpty() || quantityText.isEmpty() || priceText.isEmpty()) {
            outputArea.append("Please fill in all fields!\n");
            return;
        }

        for (Item item : inventory) {
            if (item.getName().equalsIgnoreCase(name)) {
                try {
                    item.setQuantity(Integer.parseInt(quantityText));
                    item.setPrice(Double.parseDouble(priceText));
                    outputArea.append("Updated: " + item + "\n");
                    clearFields();
                    return;
                } catch (NumberFormatException ex) {
                    outputArea.append("Invalid quantity or price!\n");
                    return;
                }
            }
        }
        outputArea.append("Item not found.\n");
    }

    // Method to delete an item from the inventory
    private void deleteItem(ActionEvent e) {
        String name = nameField.getText();

        if (name.isEmpty()) {
            outputArea.append("Please enter an item name to delete!\n");
            return;
        }

        for (Item item : inventory) {
            if (item.getName().equalsIgnoreCase(name)) {
                inventory.remove(item);
                outputArea.append("Deleted: " + name + "\n");
                clearFields();
                return;
            }
        }
        outputArea.append("Item not found.\n");
    }

    // Method to search for an item in the inventory
    private void searchItem(ActionEvent e) {
        String name = searchField.getText();
        for (Item item : inventory) {
            if (item.getName().equalsIgnoreCase(name)) {
                outputArea.append("Found: " + item + "\n");
                return;
            }
        }
        outputArea.append("Item not found.\n");
    }

    // Notify if low inventory items exist
    private void notifyLowInventory() {
        for (Item item : inventory) {
            if (item.getQuantity() < 5) {
                outputArea.append("Low inventory alert for: " + item.getName() + "\n");
            }
        }
    }

    // Clear input fields
    private void clearFields() {
        nameField.setText("");
        quantityField.setText("");
        priceField.setText("");
        searchField.setText("");
    }

    // Clear login fields
    private void clearLoginFields() {
        usernameField.setText("");
        passwordField.setText("");
        showPasswordCheckBox.setSelected(false);
        passwordField.setEchoChar('•'); // Hide password
    }
}
