<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.sql.*" %>
<html>
<head>
    <title>Registration Form</title>
</head>
<body>

<%
    // Process the form submission for POST requests
    if ("POST".equalsIgnoreCase(request.getMethod())) {
        String name = request.getParameter("Name");
        String mobile = request.getParameter("mobile");
        String email = request.getParameter("email");
        String password = request.getParameter("psw");
        String confirmPassword = request.getParameter("psw-repeat");

        // You can perform further validation and store the data in the database here

        // Assuming a simple validation for password matching
        if (password.equals(confirmPassword)) {
%>
            <div class="container">
                <h1>Thank you, <%= name %>! Happy Learning</h1>
            </div>
<%
        } else {
%>
            <div class="container">
                <p>Passwords do not match. Please try again.</p>
            </div>
<%
        }
    }

    // Process GET requests to display registered user data
    try {
        Class.forName("com.mysql.cj.jdbc.Driver");
        String jdbcUrl = "jdbc:mysql://192.168.138.126:3306/myDB";
        String jdbcUser = "mysql";
        String jdbcPassword = "mysql";

        Connection connection = DriverManager.getConnection(jdbcUrl, jdbcUser, jdbcPassword);

        // SQL query to select all user information
        String selectAllQuery = "SELECT * FROM web";
        try (PreparedStatement preparedStatement = connection.prepareStatement(selectAllQuery)) {
            ResultSet resultSet = preparedStatement.executeQuery();
%>
            <div class="container">
                <h1>Registered User Data</h1>
                <table border="1">
                    <tr>
                        <th>Name</th>
                        <th>Mobile</th>
                        <th>Email</th>
                    </tr>
<%
            while (resultSet.next()) {
%>
                    <tr>
                        <td><%= resultSet.getString("name") %></td>
                        <td><%= resultSet.getString("mobile") %></td>
                        <td><%= resultSet.getString("email") %></td>
                    </tr>
<%
            }
%>
                </table>
            </div>
<%
        }
    } catch (ClassNotFoundException | SQLException e) {
        e.printStackTrace();
%>
        <div class="container">
            <p>Error retrieving user data.</p>
        </div>
<%
    }
%>

<form action="<%= request.getContextPath() %>/index.jsp" method="post">
    <!-- The registration form remains the same -->
</form>

<div class="container signin">
    <p>Already have an account? <a href="#">Sign in</a>.</p>
</div>

</body>
</html>
