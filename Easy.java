import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

class EmployeeDataFetcher {

    // Database connection parameters
    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/your_database_name";
    private static final String USERNAME = "your_username";
    private static final String PASSWORD = "your_password";

    public static void main(String[] args) {
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;

        try {
            // 1. Load the MySQL JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            // 2. Establish connection to the database
            System.out.println("Connecting to database...");
            connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
            System.out.println("Connection established successfully!");

            // 3. Create a statement
            statement = connection.createStatement();

            // 4. Execute query to fetch all records from Employee table
            String sql = "SELECT EmpID, Name, Salary FROM Employee";
            resultSet = statement.executeQuery(sql);

            // 5. Process and display the results
            System.out.println("\nEmployee Details:");
            System.out.println("--------------------------------------------------");
            System.out.printf("%-10s %-20s %-10s\n", "EmpID", "Name", "Salary");
            System.out.println("--------------------------------------------------");

            // Iterate through the result set and display each record
            while (resultSet.next()) {
                int empId = resultSet.getInt("EmpID");
                String name = resultSet.getString("Name");
                double salary = resultSet.getDouble("Salary");

                System.out.printf("%-10d %-20s $%-10.2f\n", empId, name, salary);
            }
            System.out.println("--------------------------------------------------");

        } catch (ClassNotFoundException e) {
            System.err.println("MySQL JDBC Driver not found");
            e.printStackTrace();
        } catch (SQLException e) {
            System.err.println("Database connection error");
            e.printStackTrace();
        } finally {
            // 6. Close all resources
            try {
                if (resultSet != null) resultSet.close();
                if (statement != null) statement.close();
                if (connection != null) connection.close();
                System.out.println("\nDatabase connection closed");
            } catch (SQLException e) {
                System.err.println("Error closing database resources");
                e.printStackTrace();
            }
        }
    }
}

// Add Dependencies in you maven and build it!
//<dependency>
//    <groupId>mysql</groupId>
//    <artifactId>mysql-connector-java</artifactId>
//    <version>8.0.28</version> <!-- Use the latest version available -->
//</dependency>


