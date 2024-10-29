import java.io.*;
import java.util.*;

class Contact {
    String name;
    String phoneNumber;
    String email;

    public Contact(String name, String phoneNumber, String email) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.email = email;
    }

    public String toString() {
        return String.format("Name: %s, Phone: %s, Email: %s", name, phoneNumber, email);
    }
}

public class ContactManager {
    private ArrayList<Contact> contacts;
    private Scanner scanner;
    private final String filePath = "contacts.csv";

    public ContactManager() {
        contacts = new ArrayList<>();
        scanner = new Scanner(System.in);
        loadContactsFromFile(); // Load contacts at startup
    }

    // ANSI escape codes for colors
    private static final String RESET = "\u001B[0m";
    private static final String BLUE = "\u001B[34m";
    private static final String GREEN = "\u001B[32m";
    private static final String RED = "\u001B[31m";
    private static final String CYAN = "\u001B[36m";

    private boolean isPhoneNumberUnique(String phoneNumber) {
        for (Contact contact : contacts) {
            if (contact.phoneNumber.equals(phoneNumber)) {
                return false;
            }
        }
        return true;
    }

    private boolean isValidEmail(String email) {
        return email.matches("^[A-Za-z0-9+_.-]+@(.+)$");
    }

    private void loadContactsFromFile() {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] details = line.split(",");
                contacts.add(new Contact(details[0], details[1], details[2]));
            }
            System.out.println(GREEN + "Contacts loaded from file." + RESET);
        } catch (IOException e) {
            System.out.println(RED + "No previous contacts found. Starting fresh!" + RESET);
        }
    }

    public void saveContactsToFile() {
        try (PrintWriter pw = new PrintWriter(new FileWriter(filePath))) {
            for (Contact contact : contacts) {
                pw.println(contact.name + "," + contact.phoneNumber + "," + contact.email);
            }
            System.out.println(GREEN + "Contacts saved to file!" + RESET);
        } catch (IOException e) {
            System.out.println(RED + "Error saving contacts to file." + RESET);
        }
    }

    public void addContact() {
        String name, phoneNumber, email;

        System.out.print(CYAN + "Enter Name: " + RESET);
        name = scanner.nextLine();

        // Validate phone number (must be exactly 10 digits and unique)
        while (true) {
            System.out.print(CYAN + "Enter Phone Number (10 digits, unique): " + RESET);
            phoneNumber = scanner.nextLine();
            if (phoneNumber.matches("\\d{10}") && isPhoneNumberUnique(phoneNumber)) {
                break;
            } else if (!phoneNumber.matches("\\d{10}")) {
                System.out.println(RED + "Invalid phone number. It must contain exactly 10 digits." + RESET);
            } else {
                System.out.println(RED + "Phone number already exists. Please enter a unique phone number." + RESET);
            }
        }

        // Validate email
        while (true) {
            System.out.print(CYAN + "Enter Email (valid format): " + RESET);
            email = scanner.nextLine();
            if (isValidEmail(email)) {
                break;
            } else {
                System.out.println(RED + "Invalid email format. Please try again." + RESET);
            }
        }

        contacts.add(new Contact(name, phoneNumber, email));
        System.out.println(GREEN + "Contact added successfully!" + RESET);
        saveContactsToFile();  // Save contacts after adding
    }

    public void viewContacts() {
        if (contacts.isEmpty()) {
            System.out.println(RED + "No contacts found." + RESET);
        } else {
            System.out.println(BLUE + "--- Contact List ---" + RESET);
            for (int i = 0; i < contacts.size(); i++) {
                System.out.println(BLUE + (i + 1) + ". " + contacts.get(i) + RESET);
            }
        }
    }

    public void searchContact() {
        System.out.print(CYAN + "Enter the name to search: " + RESET);
        String searchName = scanner.nextLine().toLowerCase();
        boolean found = false;

        for (Contact contact : contacts) {
            if (contact.name.toLowerCase().contains(searchName)) {
                System.out.println(GREEN + contact + RESET);
                found = true;
            }
        }

        if (!found) {
            System.out.println(RED + "No contact found with the name: " + searchName + RESET);
        }
    }

    public void updateContact() {
        viewContacts();
        System.out.print(CYAN + "Enter the number of the contact to update: " + RESET);
        int index = scanner.nextInt();
        scanner.nextLine();  // Consume newline

        if (index > 0 && index <= contacts.size()) {
            Contact contact = contacts.get(index - 1);

            System.out.print(CYAN + "Enter new Name (leave blank to keep the same): " + RESET);
            String newName = scanner.nextLine();
            if (!newName.isEmpty()) {
                contact.name = newName;
            }

            System.out.print(CYAN + "Enter new Phone Number (leave blank to keep the same): " + RESET);
            String newPhone = scanner.nextLine();
            if (!newPhone.isEmpty()) {
                contact.phoneNumber = newPhone;
            }

            System.out.print(CYAN + "Enter new Email (leave blank to keep the same): " + RESET);
            String newEmail = scanner.nextLine();
            if (!newEmail.isEmpty()) {
                contact.email = newEmail;
            }

            System.out.println(GREEN + "Contact updated successfully!" + RESET);
            saveContactsToFile();  // Save after updating
        } else {
            System.out.println(RED + "Invalid contact number." + RESET);
        }
    }

    public void deleteContact() {
        viewContacts();
        System.out.print(CYAN + "Enter the number of the contact to delete: " + RESET);
        int index = scanner.nextInt();
        scanner.nextLine();  // Consume newline

        if (index > 0 && index <= contacts.size()) {
            contacts.remove(index - 1);
            System.out.println(GREEN + "Contact deleted successfully!" + RESET);
            saveContactsToFile();  // Save after deleting
        } else {
            System.out.println(RED + "Invalid contact number." + RESET);
        }
    }

    public void sortContacts() {
        if (contacts.isEmpty()) {
            System.out.println(RED + "No contacts to sort." + RESET);
            return;
        }

        contacts.sort(Comparator.comparing(contact -> contact.name.toLowerCase()));
        System.out.println(GREEN + "Contacts sorted by name." + RESET);
    }

    public void displayMenu() {
        System.out.println("\n" + BLUE + "--- Contact Management System ---" + RESET);
        System.out.println("1. Add Contact");
        System.out.println("2. View Contacts");
        System.out.println("3. Search Contact");
        System.out.println("4. Update Contact");
        System.out.println("5. Delete Contact");
        System.out.println("6. Sort Contacts by Name");
        System.out.println("7. Exit");
        System.out.print(CYAN + "Choose an option: " + RESET);
    }

    public static void main(String[] args) {
        ContactManager manager = new ContactManager();
        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        while (running) {
            manager.displayMenu();
            int choice = scanner.nextInt();
            scanner.nextLine();  // Consume newline

            switch (choice) {
                case 1:
                    manager.addContact();
                    break;
                case 2:
                    manager.viewContacts();
                    break;
                case 3:
                    manager.searchContact();
                    break;
                case 4:
                    manager.updateContact();
                    break;
                case 5:
                    manager.deleteContact();
                    break;
                case 6:
                    manager.sortContacts();
                    break;
                case 7:
                    System.out.print(CYAN + "Are you sure you want to exit? (y/n): " + RESET);
                    String confirmation = scanner.nextLine().toLowerCase();
                    if (confirmation.equals("y")) {
                        running = false;
                        System.out.println(GREEN + "Exiting..." + RESET);
                    } else {
                        System.out.println(RED + "Exit cancelled." + RESET);
                    }
                    break;
                default:
                    System.out.println(RED + "Invalid option. Please try again." + RESET);
            }
        }

        scanner.close();
    }
}
