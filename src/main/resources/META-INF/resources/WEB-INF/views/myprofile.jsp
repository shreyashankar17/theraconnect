<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>My Profile | TheraConnect</title>
    <style>
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
        background: linear-gradient(135deg, #f0f4ff, #e1ecf2);
        font-family: 'Segoe UI', sans-serif;
        margin: 0;
        padding: 0;
    }
 
    .profile-box {
        max-width: 650px;
        margin: 60px auto;
        background: #ffffff;
        padding: 30px;
        border-radius: 12px;
        box-shadow: 0 8px 20px rgba(0, 0, 0, 0.08);
        border-left: 6px solid #1f77f3;
    }
 
    h2 {
        text-align: center;
        margin-bottom: 35px;
        color: #1f77f3;
        font-weight: 600;
        font-size: 26px;
    }
 
    .field {
        margin-bottom: 22px;
        padding: 10px;
        border-radius: 8px;
        background: #f7faff;
        border: 1px solid #dbe8ff;
    }
 
    .label {
        font-weight: bold;
        color: #444;
        margin-bottom: 6px;
    }
 
    .value {
        font-size: 17px;
        color: #222;
    }
</style>
    
</head>
<body>
<div class="topbar-back">
		<a href="${pageContext.request.contextPath}/patient-dashboard" class="back-home-button">← Back</a>	
	</div>
    <div class="profile-box">
        <h2>My Profile Details</h2>
        <%--  <div class="field"><div class="label">My Id:</div><div class="value">${user_id}</div></div> --%>
        <div class="field"><div class="label">Name:</div><div class="value">${name}</div></div>
        <div class="field"><div class="label">DOB:</div><div class="value"><fmt:formatDate value="${dob}" pattern="yyyy-MM-dd"/></div></div>
        <div class="field"><div class="label">Gender:</div><div class="value">${gender}</div></div>
        <div class="field"><div class="label">Mobile No:</div><div class="value">${mobileno}</div></div>
        <div class="field"><div class="label">Email ID:</div><div class="value">${emailid}</div></div>
    </div>
</body>
</html>