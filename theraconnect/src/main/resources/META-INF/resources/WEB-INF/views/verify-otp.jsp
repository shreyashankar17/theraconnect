<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Verify OTP - TheraConnect</title>
    <style>
        body {
            font-family: 'Segoe UI', sans-serif;
            background-color: #f0f4f8;
            display: flex;
            justify-content: center;
            align-items: center;
            height: 100vh;
        }
        .container {
            background-color: #fff;
            padding: 40px;
            border-radius: 12px;
            box-shadow: 0 6px 16px rgba(0,0,0,0.1);
            width: 400px;
        }
        h2 {
            text-align: center;
            color: #333;
        }
        input[type="text"] {
            width: 100%;
            padding: 12px;
            margin-top: 20px;
            border: 1px solid #ccc;
            border-radius: 6px;
        }
        button {
            width: 100%;
            padding: 12px;
            margin-top: 20px;
            background-color: #0066cc;
            border: none;
            color: white;
            font-size: 16px;
            border-radius: 6px;
            cursor: pointer;
        }
        button:hover {
            background-color: #004a99;
        }
        #timer {
            text-align: center;
            margin-top: 10px;
            color: #888;
        }
        #resend {
            background-color: #ff9800;
            margin-top: 10px;
            display: block;
            width: 100%;
        }
        .error {
            color: red;
            text-align: center;
            margin-top: 15px;
        }
    </style>
</head>
<body>
<div class="container">
    <h2>Enter OTP</h2>
 
    <form action="${pageContext.request.contextPath}/verify-otp" method="post">
        <input type="text" name="otp" placeholder="Enter 6-digit OTP" required />
        <button type="submit">Verify OTP</button>
    </form>
 
    <p id="timer">Time remaining: 60s</p>
    <form action="${pageContext.request.contextPath}/resend-otp" method="post">
        <button type="submit" id="resend" disabled>Resend OTP</button>
    </form>
 
    <c:if test="${not empty param.error}">
        <div class="error">
            <c:choose>
                <c:when test="${param.error == 'invalidOrExpired'}">
                    Invalid or expired OTP. Please try again.
                </c:when>
                <c:otherwise>
                    Something went wrong.
                </c:otherwise>
            </c:choose>
        </div>
    </c:if>
</div>
 
<script>
    let seconds = 60;
    const timerDisplay = document.getElementById("timer");
    const resendBtn = document.getElementById("resend");
 
    const countdown = setInterval(() => {
        seconds--;
        timerDisplay.textContent = "Time remaining: " + seconds + "s";
        if (seconds <= 0) {
            clearInterval(countdown);
            resendBtn.disabled = false;
            timerDisplay.textContent = "OTP expired. You may resend.";
        }
    }, 1000);
</script>
</body>
</html>