<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.sql.*" %>
<html>
<head>
    <title>Registration Form</title>
</head>
<body>

<%
    // Process the form submission
    if ("POST".equalsIgnoreCase(request.getMethod())) {
        String name = request.getParameter("Name");
        String mobile = request.getParameter("mobile");
        String email = request.getParameter("email");
        String password = request.getParameter("psw");
        String confirmPassword = request.getParameter("psw-repeat");

        // You can perform further validation and store the data in the database here

        // Assuming a simple validation for password matching
        if (password.equals(confirmPassword)) {
            // Perform database operations to retrieve user information
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                String jdbcUrl = "jdbc:mysql://192.168.138.126:3306/myDB";
                String jdbcUser = "mysql";
                String jdbcPassword = "mysql";

                Connection connection = DriverManager.getConnection(jdbcUrl, jdbcUser, jdbcPassword);

                // SQL query to select user information
                String selectQuery = "SELECT * FROM web WHERE email = ?";
                try (PreparedStatement preparedStatement = connection.prepareStatement(selectQuery)) {
                    preparedStatement.setString(1, email);

                    ResultSet resultSet = preparedStatement.executeQuery();

                    if (resultSet.next()) {
%>
                        <div class="container">
                            <h1>Thank you, <%= name %>! Happy Learning</h1>
                            <p>Your Information:</p>
                            <ul>
                                <li>Name: <%= resultSet.getString("name") %></li>
                                <li>Mobile: <%= resultSet.getString("mobile") %></li>
                                <li>Email: <%= resultSet.getString("email") %></li>
                            </ul>
                        </div>
<%
                    }
                }
            } catch (ClassNotFoundException | SQLException e) {
                e.printStackTrace();
%>
                <div class="container">
                    <p>Error retrieving user information.</p>
                </div>
<%
            }
        } else {
%>
            <div class="container">
                <p>Passwords do not match. Please try again.</p>
            </div>
<%
        }
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

