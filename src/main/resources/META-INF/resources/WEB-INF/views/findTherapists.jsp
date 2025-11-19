<%@ page import="java.sql.*" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>TheraConnect | Find Therapists</title>
    <link rel="stylesheet" href="css/style.css">
</head>
<body class="find-therapist">
    <div class="therapist-container">
        <h1>Available Therapists</h1>
        <div class="card-grid">
            <%
                try {
                    Class.forName("com.mysql.cj.jdbc.Driver");
                    Connection con = DriverManager.getConnection(
                        "jdbc:mysql://localhost:3306/theraconnect",
                        "root", // <-- Make sure your MySQL user/password are correct
                        "root"
                    );
                    
                    String query = "SELECT u.user_id, u.name, dp.specialization " +
                                   "FROM USERS u " +
                                   "JOIN doctor_profile dp ON u.user_id = dp.doctor_profile_id";
                                   
                    PreparedStatement ps = con.prepareStatement(query);
                    ResultSet rs = ps.executeQuery();
 
                    while (rs.next()) {
                        String name = rs.getString("name");
                        String specialization = rs.getString("specialization");
            %>
                <div class="therapist-card">
                    <div class="card-avatar">👨‍⚕️</div>
                    <h3><%= name %></h3>
                    <p>Specialization: <%= specialization %></p>
                </div>
            <%
                    }
 
                    rs.close();
                    ps.close();
                    con.close();
                } catch (Exception e) {
                    out.println("<p>Error loading therapists.</p>");
                    e.printStackTrace();
                }
            %>
        </div>
    </div>
</body>
</html>
 