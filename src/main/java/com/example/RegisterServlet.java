import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/RegisterServlet")
public class RegisterServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        String name = request.getParameter("Name");
        String mobile = request.getParameter("mobile");
        String email = request.getParameter("email");
        String password = request.getParameter("psw");

        try {
            // Load the JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Establish a connection
            String jdbcUrl = "jdbc:mysql://192.168.138.126:3308/myDB";
            String dbUser = "root";
            String dbPassword = "idrbt";
            Connection connection = DriverManager.getConnection(jdbcUrl, dbUser, dbPassword);

            // Create the users table if not exists
            createTable(connection);

            // Insert data into the users table
            insertData(connection, name, mobile, email, password, out);

        } catch (ClassNotFoundException | SQLException e) {
            out.println("<h1>Error: " + e.getMessage() + "</h1>");
        }
    }

    private void createTable(Connection connection) throws SQLException {
        String createTableSQL = "CREATE TABLE IF NOT EXISTS users (" +
                "id INT AUTO_INCREMENT PRIMARY KEY," +
                "name VARCHAR(255) NOT NULL," +
                "mobile VARCHAR(20) NOT NULL," +
                "email VARCHAR(255) NOT NULL," +
                "password VARCHAR(255) NOT NULL)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(createTableSQL)) {
            preparedStatement.executeUpdate();
            System.out.println("Table created successfully.");
        }
    }

    private void insertData(Connection connection, String name, String mobile, String email, String password, PrintWriter out) {
        try {
            String insertSQL = "INSERT INTO users (name, mobile, email, password) VALUES (?, ?, ?, ?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(insertSQL)) {
                preparedStatement.setString(1, name);
                preparedStatement.setString(2, mobile);
                preparedStatement.setString(3, email);
                preparedStatement.setString(4, password);

                // Execute the statement
                int rowsAffected = preparedStatement.executeUpdate();

                if (rowsAffected > 0) {
                    out.println("<h1>Registration successful!</h1>");
                } else {
                    out.println("<h1>Registration failed. Please try again.</h1>");
                }
            }
        } catch (SQLException e) {
            out.println("<h1>Error: " + e.getMessage() + "</h1>");
        }
    }
}
