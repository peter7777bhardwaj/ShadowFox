import java.util.Scanner;

public class Scical {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean exit = false;

        while (!exit) {
            System.out.println("\n==== Enhanced Console-based Calculator ====");
            System.out.println("1. Basic Arithmetic Operations");
            System.out.println("2. Scientific Calculations");
            System.out.println("3. Unit Conversions");
            System.out.println("4. Exit");
            System.out.print("Choose an option: ");

            int choice = scanner.nextInt();
            
            switch (choice) {
                case 1:
                    basicArithmetic(scanner);
                    break;
                case 2:
                    scientificCalculations(scanner);
                    break;
                case 3:
                    unitConversions(scanner);
                    break;
                case 4:
                    exit = true;
                    System.out.println("Exiting the calculator. Goodbye!");
                    break;
                default:
                    System.out.println("Invalid choice, please try again.");
            }
        }
        scanner.close();
    }

    // Method for basic arithmetic
    public static void basicArithmetic(Scanner scanner) {
        System.out.println("\n-- Basic Arithmetic Operations --");
        System.out.print("Enter first number: ");
        double num1 = scanner.nextDouble();
        System.out.print("Enter second number: ");
        double num2 = scanner.nextDouble();
        System.out.print("Enter operation (+, -, *, /): ");
        char operation = scanner.next().charAt(0);
        
        double result = 0;
        boolean valid = true;

        switch (operation) {
            case '+':
                result = num1 + num2;
                break;
            case '-':
                result = num1 - num2;
                break;
            case '*':
                result = num1 * num2;
                break;
            case '/':
                if (num2 != 0) {
                    result = num1 / num2;
                } else {
                    System.out.println("Error: Division by zero is undefined.");
                    valid = false;
                }
                break;
            default:
                System.out.println("Invalid operation.");
                valid = false;
        }

        if (valid) {
            System.out.println("Result: " + result);
        }
    }

    // Method for scientific calculations
    public static void scientificCalculations(Scanner scanner) {
        System.out.println("\n-- Scientific Calculations --");
        System.out.println("1. Sine");
        System.out.println("2. Cosine");
        System.out.println("3. Tangent");
        System.out.println("4. Logarithm");
        System.out.print("Choose a function: ");
        int choice = scanner.nextInt();

        System.out.print("Enter the value: ");
        double value = scanner.nextDouble();
        double result = 0;

        switch (choice) {
            case 1:
                result = Math.sin(Math.toRadians(value));
                System.out.println("sin(" + value + ") = " + result);
                break;
            case 2:
                result = Math.cos(Math.toRadians(value));
                System.out.println("cos(" + value + ") = " + result);
                break;
            case 3:
                result = Math.tan(Math.toRadians(value));
                System.out.println("tan(" + value + ") = " + result);
                break;
            case 4:
                if (value > 0) {
                    result = Math.log(value);
                    System.out.println("log(" + value + ") = " + result);
                } else {
                    System.out.println("Error: Logarithm for non-positive values is undefined.");
                }
                break;
            default:
                System.out.println("Invalid choice.");
        }
    }

    // Method for unit conversions
    public static void unitConversions(Scanner scanner) {
        System.out.println("\n-- Unit Conversions --");
        System.out.println("1. Celsius to Fahrenheit");
        System.out.println("2. Fahrenheit to Celsius");
        System.out.println("3. Kilometers to Miles");
        System.out.println("4. Miles to Kilometers");
        System.out.println("5. Currency Converter (USD, EUR, INR)");
        System.out.print("Choose a conversion: ");
        int choice = scanner.nextInt();

        double result = 0;
        switch (choice) {
            case 1:
                System.out.print("Enter Celsius: ");
                double celsius = scanner.nextDouble();
                result = (celsius * 9/5) + 32;
                System.out.println(celsius + "°C = " + result + "°F");
                break;
            case 2:
                System.out.print("Enter Fahrenheit: ");
                double fahrenheit = scanner.nextDouble();
                result = (fahrenheit - 32) * 5/9;
                System.out.println(fahrenheit + "°F = " + result + "°C");
                break;
            case 3:
                System.out.print("Enter Kilometers: ");
                double kilometers = scanner.nextDouble();
                result = kilometers * 0.621371;
                System.out.println(kilometers + " km = " + result + " miles");
                break;
            case 4:
                System.out.print("Enter Miles: ");
                double miles = scanner.nextDouble();
                result = miles * 1.60934;
                System.out.println(miles + " miles = " + result + " km");
                break;
            case 5:
                currencyConverter(scanner);
                break;
            default:
                System.out.println("Invalid choice.");
        }
    }

    // Method for currency conversion
    public static void currencyConverter(Scanner scanner) {
        System.out.println("\n-- Currency Converter --");
        System.out.println("1. USD to EUR");
        System.out.println("2. USD to INR");
        System.out.println("3. EUR to USD");
        System.out.println("4. EUR to INR");
        System.out.println("5. INR to USD");
        System.out.println("6. INR to EUR");
        System.out.print("Choose a conversion option: ");
        int choice = scanner.nextInt();

        System.out.print("Enter amount: ");
        double amount = scanner.nextDouble();
        double result = 0;

        // Predefined conversion rates (for simplicity)
        final double USD_TO_EUR = 0.94;
        final double USD_TO_INR = 83.0;
        final double EUR_TO_USD = 1.06;
        final double EUR_TO_INR = 88.0;
        final double INR_TO_USD = 0.012;
        final double INR_TO_EUR = 0.011;

        switch (choice) {
            case 1:
                result = amount * USD_TO_EUR;
                System.out.println(amount + " USD = " + result + " EUR");
                break;
            case 2:
                result = amount * USD_TO_INR;
                System.out.println(amount + " USD = " + result + " INR");
                break;
            case 3:
                result = amount * EUR_TO_USD;
                System.out.println(amount + " EUR = " + result + " USD");
                break;
            case 4:
                result = amount * EUR_TO_INR;
                System.out.println(amount + " EUR = " + result + " INR");
                break;
            case 5:
                result = amount * INR_TO_USD;
                System.out.println(amount + " INR = " + result + " USD");
                break;
            case 6:
                result = amount * INR_TO_EUR;
                System.out.println(amount + " INR = " + result + " EUR");
                break;
            default:
                System.out.println("Invalid choice.");
        }
    }
}
