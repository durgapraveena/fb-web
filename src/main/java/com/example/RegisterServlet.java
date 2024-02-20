import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DatabaseSetup {

    // JDBC URL, username, and password of MySQL server
    private static final String JDBC_URL = "jdbc:mysql://192.168.138.126:3306/myDB";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "idrbt";

    // JDBC variables for opening, closing, and managing connection
    private static Connection connection;

    // JDBC variable for executing SQL queries
    private static PreparedStatement preparedStatement;

    public static void main(String[] args) {
        try {
            // Step 1: Establishing a connection
            connection = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASSWORD);

            // Step 2: Create a table
            createTable();

            // Step 3: Insert data into the table
            insertData();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // Close resources in the finally block to ensure they are released even if an exception occurs
            try {
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private static void createTable() throws SQLException {
        String createTableSQL = "CREATE TABLE IF NOT EXISTS users (" +
                "id INT AUTO_INCREMENT PRIMARY KEY," +
                "name VARCHAR(255) NOT NULL," +
                "mobile VARCHAR(20) NOT NULL," +
                "email VARCHAR(255) NOT NULL," +
                "password VARCHAR(255) NOT NULL)";

        preparedStatement = connection.prepareStatement(createTableSQL);
        preparedStatement.executeUpdate();
        System.out.println("Table created successfully.");
    }

    private static void insertData() throws SQLException {
        String insertSQL = "INSERT INTO users (name, mobile, email, password) VALUES (?, ?, ?, ?)";

        // Sample data
        String name = "John Doe";
        String mobile = "1234567890";
        String email = "john.doe@example.com";
        String password = "securepassword";

        preparedStatement = connection.prepareStatement(insertSQL);
        preparedStatement.setString(1, name);
        preparedStatement.setString(2, mobile);
        preparedStatement.setString(3, email);
        preparedStatement.setString(4, password);

        int rowsAffected = preparedStatement.executeUpdate();

        if (rowsAffected > 0) {
            System.out.println("Data inserted successfully.");
        } else {
            System.out.println("Failed to insert data.");
        }
    }
}
