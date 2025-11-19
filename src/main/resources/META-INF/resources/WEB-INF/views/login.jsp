<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Login | TheraConnect</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
 
 <a href="${pageContext.request.contextPath}/" class="back-home-button">← Back to Home</a>
 
    <div class="login-wrapper">
        <div class="login-box">
            <h2>Welcome Back</h2>
            <p class="subtitle">Sign in to your TheraConnect account</p>
 
            <form class="login-form" action="/login" method="post">
                <div class="form-group">
                    <label for="email">Email Address</label>
                    <input type="email" name="username" id="email" placeholder="Enter your email" required required autocomplete="off" />
                </div>
 
                <div class="form-group">
                    <label for="password">Password</label>
                    <input type="password" name="password" id="password" placeholder="Enter your password" required />
                </div>
 
                <button type="submit" class="btn">LogIn </button>
            </form>
 
            <div class="login-options">
                <a href="/register">Don't have an account? Sign up here</a>
                <a href="/forgot-password">Forgot Password?</a>
            </div>
        </div>
    </div>
 
</body>
</html>