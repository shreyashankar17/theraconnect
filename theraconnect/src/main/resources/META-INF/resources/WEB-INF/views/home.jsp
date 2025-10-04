<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>TheraConnect | Home</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css">
    
    
</head>
<body>
 
    <!-- 🔝 Top Navbar -->
    <div class="top-navbar">
        <div class="nav-left">
            <a href="/" class="logo-icon">T</a>
            <span class="logo-text">TheraConnect</span>
        </div>
        <div class="nav-right">
        <a href="/">Dashboard</a>
        <a href="/about">About Us</a>
    </div>
    </div>
 
    <!-- 🏠 Hero Section -->
    <div class="main-container">
        <div class="left-panel">
            <h2>Welcome to Your Wellness Journey</h2>
            
            <div class="button-group">
  <a href="/register" class="btn secondary">Get Started</a>
    <a href="/login" class="btn">Log In</a>
            </div>
        </div>
 
        <div class="right-panel">
 
            <!-- 💙 Heart Card -->
            <div class="card-box">
                <img src="${pageContext.request.contextPath}/image/heart-icon.png" alt="Heart Icon" class="heart-icon">
                <h4>Mental Health Matters</h4>
                <p>Expert therapy at your fingertips. Feel supported every step of the way.</p>
            </div>
 
            <!-- 🌟 Daily Motivation Section -->
            <div class="quote-box-wrapper">
                
                <div class="quote-box">
                <h3>Boost Your Mood Today 💬</h3>
                    <p id="quoteText">Loading...</p>
                </div>
            </div>
 
        </div>
    </div>
 
<!-- ✨ Why Choose TheraConnect -->
<section class="feature-section">
    <h2>Why Choose TheraConnect?</h2>
    <p class="feature-subtitle">A complete care experience, designed to support you with ease, empathy, and real results.</p>
 
    <div class="feature-grid">
        <!-- 🔍 Find Therapists -->
        <div class="feature-card">
            <i class="fas fa-user-md feature-icon"></i>
            <h3>Find Therapists</h3>
            <p>Search and filter licensed mental health professionals by specialization and experience.</p>
        </div>
 
        <!-- 🗓️ Easy Booking -->
        <div class="feature-card">
            <i class="fas fa-calendar-check feature-icon"></i>
            <h3>Easy Booking</h3>
            <p>Instantly schedule sessions with availability tailored to your comfort and time.</p>
        </div>
 
        <!-- 🚨 Emergency Support -->
        <div class="feature-card">
            <i class="fas fa-phone-volume feature-icon"></i>
            <h3>Emergency Support</h3>
            <p>Access 24/7 emergency care from trusted professionals when it’s needed most.</p>
        </div>
 
        <!-- 📊 Platform Impact -->
        <div class="feature-card stats-card">
            <i class="fas fa-chart-line feature-icon"></i>
            <h3>Platform Impact</h3>
            <ul class="stats-list">
                <li><strong>500+</strong> Qualified Therapists</li>
                <li><strong>10,000+</strong> Happy Patients</li>
                <li><strong>50,000+</strong> Successful Sessions</li>
            </ul>
        </div>
    </div>
</section>
 
 
<!-- 🚀 Call to Action -->
<section class="cta-section">
    <div class="cta-wrapper">
        <h2>Take the First Step Toward Healing</h2>
        <p>Your mental wellness journey starts here. Join thousands already supported by TheraConnect.</p>
        <a href="/register" class="btn cta-button">Join Now</a>
        <a href="/findTherapists" class="btn cta-button">Find Therapist</a>
    </div>
</section>
 
<!-- 🔻 Footer -->
<footer>
    <div class="footer-top">
        <div class="footer-logo">
            <div class="logo-icon-footer">T</div>
            <div>
                <h3>TheraConnect</h3>
                <p>Empowering healthier, happier lives through accessible therapy and emotional support.</p>
            </div>
        </div>
 
        <div class="footer-links">
            <h4>Explore</h4>
            <ul>
                <li><a href="/about">About Us</a></li>
                <li><a href="/findTherapists">Find Therapists</a></li>
                <li><a>Contact</a></li>
                
            </ul>
        </div>
 
        <div class="footer-contact">
            <h4>Get in Touch</h4>
            <p>📧 theraconnect01@gmail.com</p>
            <p>📞 1-800-THERAPY</p>
            
        </div>
    </div>
 
    <div class="footer-bottom">
        <p>&copy; 2024 TheraConnect. All rights reserved. You’re not alone—we’re here to support you.</p>
    </div>
</footer>
 
	
<!-- 🧠 Quote API Script with Expanded Fallback -->
<script>
fetch("https://zenquotes.io/api/random")
    .then(response => response.json())
    .then(data => {
        const quote = data[0].q;
        const author = data[0].a;
        document.getElementById("quoteText").innerText = `"${quote}" — ${author}`;
    })
    .catch(() => {
        const fallbackQuotes = [
            `"Healing begins with connection." — Unknown`,
            `"You're allowed to be both a masterpiece and a work in progress." — Sophia Bush`,
            `"It's okay to not feel okay. What matters is that you keep showing up." — Unknown`,
            `"You carry so much love in your heart. Give some to yourself." — Unknown`,
            `"Growth is quietly happening beneath the surface." — Morgan Harper Nichols`,
            `"Let it be soft. You’re not a machine." — Nayyirah Waheed`
        ];
        const randomIndex = Math.floor(Math.random() * fallbackQuotes.length);
        document.getElementById("quoteText").innerText = fallbackQuotes[randomIndex];
    });
</script>
 
	
	
	</body>
	</html>