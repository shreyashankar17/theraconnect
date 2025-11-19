<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Register | TheraConnect</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>

<div class="register-container">
    <h2>Create Your TheraConnect Account</h2>
    <p>Start your journey toward emotional wellness</p>

    <!-- Popup for email already exists -->
    <c:if test="${emailExists}">
        <div id="popupBox" class="popup-overlay">
            <div class="popup-content">
                <c:out value="${popupMessage}" default="Email already exists. Please use a different one."/>
            </div>
        </div>

        <script>
            setTimeout(function() {
                document.getElementById("popupBox").style.display = "none";
            }, 5000);
        </script>
    </c:if>

    <form action="/api/users/registration" method="post" class="register-form">
        <!-- <div class="form-group">
            <label for="name">Full Name</label>
            <input type="text" id="name" name="name" placeholder="Enter your full name" required>
        </div> -->
        
<div class="form-group">
    <label for="name">Full Name</label>
    <input 
        type="text" 
        id="name" 
        name="name" 
        placeholder="Enter your full name" 
        required 
        pattern="^[A-Za-z\s]+$" 
        title="Please enter only letters and spaces. No numbers or special characters."
    >
</div>
        

        <div class="form-group">
            <label for="email">Email Address</label>
            <input type="email" id="email" name="email" placeholder="Enter your email" required>
        </div>

        <div class="form-group">
            <label for="password">Password</label>
            <input type="password"
                   id="password"
                   name="password"
                   placeholder="Create a password"
                   required
                   pattern="(?=.*[A-Z])(?=.*[@]).{8,}"
                   title="Password must be at least 8 characters, include one uppercase letter and '@' symbol">
        </div>

        <div class="form-group">
            <label for="confirmPassword">Confirm Password</label>
            <input type="password"
                   id="confirmPassword"
                   name="confirmPassword"
                   placeholder="Confirm your password"
                   required
                   oninput="this.setCustomValidity(this.value !== document.getElementById('password').value ? 'Passwords do not match' : '')">
        </div>

        <div class="form-group">
            <label for="role">I am a:</label>
            <select id="role" name="role" required>
                <option value="" disabled selected>Select your role</option>
                <option value="PATIENT">Patient</option>
                <option value="DOCTOR">Doctor</option>
            </select>
        </div>

        <button type="submit" class="btn">Create Account</button>
    </form>
</div>

</body>
</html>
