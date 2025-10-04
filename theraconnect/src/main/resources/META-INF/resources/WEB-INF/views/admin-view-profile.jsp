<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>User Profile | TheraConnect Admin</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/styles.css">
    <style>
        .profile-container {
            margin-left: 220px;
            padding: 40px;
            background-color: #fff;
            border-radius: 8px;
            box-shadow: 0 4px 12px rgba(0,0,0,0.05);
            max-width: 600px;
        }
 
        .profile-container h2 {
            color: #1976d2;
            margin-bottom: 20px;
        }
 
        .profile-item {
            margin-bottom: 16px;
        }
 
        .profile-label {
            font-weight: bold;
            color: #333;
        }
    </style>
</head>
<body>
 
<main class="profile-container">
    <h2>${user.name}'s Profile ��</h2>
 
    <div class="profile-item">
        <span class="profile-label">Email:</span> ${user.email}
    </div>
 
    <div class="profile-item">
        <span class="profile-label">Role:</span> ${user.role}
    </div>
 
    <c:if test="${user.role == 'DOCTOR'}">
        <div class="profile-item">
            <span class="profile-label">Doctor Profile ID:</span> ${user.doctorProfile.doctorProfileId}
        </div>
    </c:if>
 
    <c:if test="${user.role == 'PATIENT'}">
        <div class="profile-item">
            <span class="profile-label">Patient Profile ID:</span> ${user.patientProfile.patientProfileId}
        </div>
    </c:if>
 
    <a href="${pageContext.request.contextPath}/admin/users">
        <button class="action-btn view-btn">Back to Users</button>
    </a>
</main>
 
</body>
</html>