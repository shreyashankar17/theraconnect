<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Doctor Profile</title>
    <%--  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css"> --%>
    <style >
    
    /* 🧭 Sticky Back to Home Button */
.back-home-button {
    position: fixed;
    top: 20px;
    left: 20px;
    background-color: #0077cc;
    color: white;
    padding: 10px 20px;
    border: none;
    border-radius: 8px;
    font-weight: bold;
    cursor: pointer;
    text-decoration: none;
    box-shadow: 0 4px 12px rgba(0, 0, 0, 0.2);
    z-index: 999;
    transition: background-color 0.3s ease;
}
 
.back-home-button:hover {
    background-color: #005fa3;
}
        body {
            background-color: #f5f9ff;
            font-family: 'Segoe UI', sans-serif;
        }
        .profile-container {
            max-width: 650px;
            margin: 60px auto;
            background-color: #fff;
            padding: 35px;
            border-radius: 12px;
            box-shadow: 0 8px 20px rgba(0, 0, 0, 0.08);
        }
        h2 {
            text-align: center;
            color: #1f77f3;
            margin-bottom: 30px;
        }
        .field {
            margin-bottom: 20px;
        }
        .label {
            font-weight: bold;
            color: #333;
        }
        .value {
            font-size: 16px;
            color: #222;
            margin-top: 5px;
        }
        
        
 
    </style>
</head>
<body>

		<a href="${pageContext.request.contextPath}/doctor-dashboard" class="back-home-button">← Back</a>	
	

    <div class="profile-container">
        <h2>Doctor Profile</h2>
 
        <div class="field">
            <div class="label">Name:</div>
            <div class="value">${name}</div>
        </div>
 
        <div class="field">
            <div class="label">Specialization:</div>
            <div class="value">${specialization}</div>
        </div>
 
        <div class="field">
            <div class="label">Experience:</div>
            <div class="value">${experience}</div>
        </div>
 
        <div class="field">
            <div class="label">Bio:</div>
            <div class="value">${bio}</div>
        </div>
    </div>
</body>
</html>