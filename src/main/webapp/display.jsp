<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.sql.*" %>
<html>
<head>
    <title>Registered User Data</title>
</head>
<body>

<%
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

</body>
</html>
