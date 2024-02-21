package com.example;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/main")
public class MainServlet extends HttpServlet {
    // Update these values with your database details
    private static final String JDBC_URL = "jdbc:mysql://192.168.138.126:3306/myDB";
    private static final String JDBC_USER = "mysql";
    private static final String JDBC_PASSWORD = "mysql";

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Your existing code for inserting data into the database
        // ...

        // You can add a call to fetch and display data here
        displayUserData(response.getWriter());
    }

    private void displayUserData(PrintWriter out) {
        try {
            // Load the JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Establish a connection
            try (Connection connection = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD)) {
                // Perform database operations to retrieve data
                String sql = "SELECT name, mobile, email FROM web";

                try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                    // Execute the query
                    ResultSet resultSet = preparedStatement.executeQuery();

                    // Display the retrieved data
                    out.println("<h2>Users in the Database</h2>");
                    out.println("<table border='1'>");
                    out.println("<tr><th>Name</th><th>Mobile</th><th>Email</th></tr>");

                    while (resultSet.next()) {
                        String name = resultSet.getString("name");
                        String mobile = resultSet.getString("mobile");
                        String email = resultSet.getString("email");

                        out.println("<tr><td>" + name + "</td><td>" + mobile + "</td><td>" + email + "</td></tr>");
                    }

                    out.println("</table>");
                }
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            out.println("Error: " + e.getMessage());
        }
    }
}
