<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Verify Email - TheraConnect</title>
    <style>
        body {
            font-family: 'Segoe UI', sans-serif;
            background-color: #f5f7fa;
            display: flex;
            justify-content: center;
            align-items: center;
            height: 100vh;
        }
        .container {
            background-color: #ffffff;
            padding: 40px;
            border-radius: 12px;
            box-shadow: 0 4px 12px rgba(0,0,0,0.1);
            width: 400px;
        }
        h2 {
            text-align: center;
            color: #333;
        }
        input[type="email"] {
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
        .error {
            color: red;
            text-align: center;
            margin-top: 15px;
        }
    </style>
</head>
<body>
<div class="container">
    <h2>Email Verification</h2>
    <form action="${pageContext.request.contextPath}/forgot-password" method="post">
        <input type="email" name="email" placeholder="Enter registered email" required />
        <button type="submit">Send OTP</button>
    </form>
 
    <c:if test="${not empty param.error}">
        <div class="error">
            <c:choose>
                <c:when test="${param.error == 'userNotFound'}">
                    Email not registered. Please try again.
                </c:when>
                <c:otherwise>
                    An unexpected error occurred.
                </c:otherwise>
            </c:choose>
        </div>
    </c:if>
</div>
</body>
</html>