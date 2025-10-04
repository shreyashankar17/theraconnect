<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>TheraConnect - Profile Settings</title>
    <style>
        * { box-sizing: border-box; margin: 0; padding: 0; }
        body { font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif; background-color: #f0f2f5; }
 
        .navbar {
            background-color: #2e7d32;
            padding: 15px 30px;
            color: white;
            display: flex;
            justify-content: space-between;
            align-items: center;
        }
        .navbar a {
            color: white;
            margin: 0 12px;
            text-decoration: none;
            font-weight: bold;
        }
        .navbar select {
            padding: 5px;
            border-radius: 4px;
            border: none;
        }
 
        .container {
            max-width: 600px;
            margin: 40px auto;
            background-color: white;
            padding: 30px;
            border-radius: 10px;
            box-shadow: 0 4px 12px rgba(0,0,0,0.1);
        }
 
        h2 {
            margin-bottom: 20px;
            color: #333;
        }
 
        label {
            display: block;
            margin-top: 15px;
            font-weight: 600;
            color: #555;
        }
 
        input[type="text"],
        input[type="email"],
        input[type="date"],
        select {
            width: 100%;
            padding: 10px;
            margin-top: 8px;
            border: 1px solid #ccc;
            border-radius: 6px;
            font-size: 14px;
        }
 
        input[type="submit"] {
            margin-top: 20px;
            background-color: #2e7d32;
            color: white;
            padding: 12px 20px;
            border: none;
            border-radius: 6px;
            font-size: 16px;
            cursor: pointer;
            transition: background-color 0.3s ease;
        }
 
        input[type="submit"]:hover {
            background-color: #1b5e20;
        }
    </style>
</head>
<body>
 
<div class="navbar">
    <div>
        <a href="dashboard.jsp">Dashboard</a>
        <a href="findTherapists.jsp">Find Therapists</a>
        <a href="emotions.jsp">My Emotions</a>
        <a href="profile.jsp">Profile</a>
    </div>
    <div>
        <select>
            <option selected>English</option>
            <option>Hindi</option>
        </select>
        <a href="logout.jsp">Logout</a>
    </div>
</div>
 
<div class="container">
    <h2>Profile Settings</h2>
    <form action="/api/patients/updateProfile" method="post">
        <!-- Basic Info -->
        <label for="name">Name:</label>
        <input type="text" id="name" name="name" value="${name}">
 
      <label for="email">Email:</label>
<input type="email" id="email" name="email" value="${email}">
 
        <!-- Additional Info -->
        <label for="contact">Contact:</label>
        <input type="text" id="contact" name="contact" value="${contact}">
 
        <label for="dob">Date of Birth:</label>
        <input type="date" id="dob" name="dob" value="${dob}">
 
        <label for="gender">Gender:</label>
        <select id="gender" name="gender">
            <option value="Male" ${gender == 'Male' ? 'selected' : ''}>Male</option>
            <option value="Female" ${gender == 'Female' ? 'selected' : ''}>Female</option>
            <option value="Other" ${gender == 'Other' ? 'selected' : ''}>Other</option>
        </select>
 
        <input type="submit" value="Update Profile">
    </form>
</div>
 
</body>
</html>  