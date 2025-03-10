import java.sql.*;
import java.util.Scanner;

class ProductManagementSystem {
    // Database connection parameters
    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/inventory";
    private static final String USERNAME = "your_username";
    private static final String PASSWORD = "your_password";

    private static Connection connection = null;
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        try {
            // Load the MySQL JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Establish connection to the database
            System.out.println("Connecting to database...");
            connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
            connection.setAutoCommit(false); // Disable auto-commit for transaction control
            System.out.println("Connection established successfully!");

            boolean exit = false;

            while (!exit) {
                displayMenu();
                int choice = getIntInput("Enter your choice: ");

                try {
                    switch (choice) {
                        case 1:
                            createProduct();
                            break;
                        case 2:
                            readAllProducts();
                            break;
                        case 3:
                            updateProduct();
                            break;
                        case 4:
                            deleteProduct();
                            break;
                        case 5:
                            exit = true;
                            System.out.println("Exiting application. Goodbye!");
                            break;
                        default:
                            System.out.println("Invalid choice. Please try again.");
                    }
                } catch (SQLException e) {
                    System.err.println("Error occurred during database operation: " + e.getMessage());
                    try {
                        System.out.println("Rolling back transaction...");
                        connection.rollback();
                    } catch (SQLException ex) {
                        System.err.println("Error during rollback: " + ex.getMessage());
                    }
                }
            }

        } catch (ClassNotFoundException e) {
            System.err.println("MySQL JDBC Driver not found: " + e.getMessage());
        } catch (SQLException e) {
            System.err.println("Database connection error: " + e.getMessage());
        } finally {
            // Close database resources
            if (connection != null) {
                try {
                    connection.close();
                    System.out.println("Database connection closed");
                } catch (SQLException e) {
                    System.err.println("Error closing database connection: " + e.getMessage());
                }
            }
            scanner.close();
        }
    }

    private static void displayMenu() {
        System.out.println("\n===== PRODUCT MANAGEMENT SYSTEM =====");
        System.out.println("1. Add New Product");
        System.out.println("2. View All Products");
        System.out.println("3. Update Product");
        System.out.println("4. Delete Product");
        System.out.println("5. Exit");
        System.out.println("=====================================");
    }

    // CREATE operation
    private static void createProduct() throws SQLException {
        System.out.println("\n--- ADD NEW PRODUCT ---");

        String productName = getStringInput("Enter Product Name: ");
        double price = getDoubleInput("Enter Price: ");
        int quantity = getIntInput("Enter Quantity: ");

        String sql = "INSERT INTO Product (ProductName, Price, Quantity) VALUES (?, ?, ?)";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, productName);
            statement.setDouble(2, price);
            statement.setInt(3, quantity);

            int rowsInserted = statement.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("Product added successfully!");
                connection.commit();
            } else {
                System.out.println ("Failed to add product. No rows affected.");
            }
        }
    }

    // READ operation
    private static void readAllProducts() throws SQLException {
        System.out.println("\n--- VIEW ALL PRODUCTS ---");

        String sql = "SELECT * FROM Product";

        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {

            while (resultSet.next()) {
                int productId = resultSet.getInt("ProductID");
                String productName = resultSet.getString("ProductName");
                double price = resultSet.getDouble("Price");
                int quantity = resultSet.getInt("Quantity");

                System.out.printf("ID: %d, Name: %s, Price: %.2f, Quantity: %d%n", productId, productName, price, quantity);
            }
        }
    }

    // UPDATE operation
    private static void updateProduct() throws SQLException {
        System.out.println("\n--- UPDATE PRODUCT ---");

        int productId = getIntInput("Enter Product ID to update: ");
        String productName = getStringInput("Enter new Product Name: ");
        double price = getDoubleInput("Enter new Price: ");
        int quantity = getIntInput("Enter new Quantity: ");

        String sql = "UPDATE Product SET ProductName = ?, Price = ?, Quantity = ? WHERE ProductID = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, productName);
            statement.setDouble(2, price);
            statement.setInt(3, quantity);
            statement.setInt(4, productId);

            int rowsUpdated = statement.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("Product updated successfully!");
                connection.commit();
            } else {
                System.out.println("Failed to update product. No rows affected.");
            }
        }
    }

    // DELETE operation
    private static void deleteProduct() throws SQLException {
        System.out.println("\n--- DELETE PRODUCT ---");

        int productId = getIntInput("Enter Product ID to delete: ");

        String sql = "DELETE FROM Product WHERE ProductID = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, productId);

            int rowsDeleted = statement.executeUpdate();
            if (rowsDeleted > 0) {
                System.out.println("Product deleted successfully!");
                connection.commit();
            } else {
                System.out.println("Failed to delete product. No rows affected.");
            }
        }
    }

    private static int getIntInput(String prompt) {
        System.out.print(prompt);
        return scanner.nextInt();
    }

    private static double getDoubleInput(String prompt) {
        System.out.print(prompt);
        return scanner.nextDouble();
    }

    private static String getStringInput(String prompt) {
        System.out.print(prompt);
        return scanner.next();
    }
}
}