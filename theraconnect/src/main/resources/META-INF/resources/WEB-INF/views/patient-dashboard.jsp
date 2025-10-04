<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String fullName = session != null ? (String) session.getAttribute("fullName") : "Guest";
  // Long patientId = session != null ? (Long) session.getAttribute("patientProfileId") : null;
    
%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Patient Dashboard | TheraConnect</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>

 
    <!-- 🔹 Sidebar -->
    <aside class="sidebar">
        <div class="sidebar-logo">
            <a href="/" class="logo-link">T</a>
            <span>Patient Portal</span>
        </div>
        <nav class="sidebar-nav">
            <a href="#top" class="nav-link">Dashboard</a>
            <a href="${pageContext.request.contextPath}/api/doctors/list"  class="nav-link">Find Therapists</a>
  
            <a href="/appointments/patients/view" class="nav-link">My Appointments</a>
            <a href="${pageContext.request.contextPath}/mood/view" class="nav-link">My Emotions</a>
            <a href="/api/patients/updateprofile" class="nav-link">Update Profile</a>
            <a href="/api/patients/myprofile" class="nav-link">My profile</a>
            <div class="whatsapp-icon">
			  <a href="https://wa.me/91XXXXXXXXXX?text=Hi%20Doctor%2C%20I%20need%20help" target="_blank">
			    <img src="image/wh.png" alt="">
				<span class="chat-label"><b>Chat Now </b></span>
			  </a>
			</div>
        </nav>
        <div class="sidebar-footer">
            <a href="/api/patients/logout" class="logout-link">Logout</a>
        </div>
    </aside>
 
    <!-- 🧠 Main Content -->
    <main class="patient-main" id="top">
        <h2>Welcome, <span class="username"><%= fullName %></span> 🌼</h2>
     
        <p class="center-message">Your mental wellness journey starts here.</p>
        
 
        <!-- 🔸 Cards -->
        <div class="card-grid">
            <div class="card blue">
                <h3>Find a Therapist</h3>
                <p>Explore qualified professionals for support.</p>
               <%--  <a href="${pageContext.request.contextPath}/api/doctors/list" class="btn">Search Now</a> --%>
                <a href="${pageContext.request.contextPath}/api/doctors/list"  class="btn">Find Therapists</a>
               
            </div>
 
            <div class="card green">
                <h3>Log Emotions</h3>
                <p>Track your feelings and reflect your progress.</p>
                <a href="${pageContext.request.contextPath}/mood/view" class="btn">Log Today</a>
            </div>
 
            <div class="card red">
                <h3>Emergency Support</h3>
                <p>Immediate help when needed most.</p>
                <a href="https://wa.me/91XXXXXXXXXX?text=Hi%20Doctor%2C%20I%20need%20help" class="btn secondary">Get Help Now</a>
            </div>
        </div>
 
<%--         <!-- 🔍 Therapist Search -->
        <section id="therapistSearch" class="search-section">
            <h3>Find Therapists by Specialization</h3>
            <form action="/searchTherapists" method="get" class="search-form">
                <input type="text" name="name" placeholder="Therapist Name">
                <select name="specialization">
                    <option value="">Select Specialization</option>
                    <option value="Anxiety">Anxiety</option>
                    <option value="Depression">Depression</option>
                    <option value="Trauma">Trauma</option>
                    <option value="Couples Therapy">Couples Therapy</option>
                </select>
             <!--    <button type="submit" class="btn">Search</button> -->
                <a href="${pageContext.request.contextPath}/api/doctors/list" class="btn">Search Now</a>
            </form>
        </section> --%>
 
        <!-- 🔗 Appointments + Emotion Check-ins -->
        <section class="dashboard-boxes">
            <!-- 📅 Appointments -->
            <div class="appointments-section" id="appointments">
                <h3>Upcoming Appointments</h3>
                <p class="info-note">Your booked appointments will appear here once confirmed.</p>
                <a href="/appointments/patients/view" class="btn">View Appointments</a>
            </div>
 
            <!-- 😄 Mood Check-ins -->
            <div class="checkins-section" id="emotions">
                <h3>How Are You Feeling Today?</h3>
                <form class="mood-form">
                    <label for="moodScale">Select Mood:</label>
                    <div class="emoji-scale">
                        <button type="submit" onclick="showMood(1)">😞</button>
                        <button type="submit" onclick="showMood(2)">😐</button>
                        <button type="submit" onclick="showMood(3)">🙂</button>
                        <button type="submit" onclick="showMood(4)">😊</button>
                        <button type="submit" onclick="showMood(5)">😄</button>
                    </div>
                    <div id="moodMessage" class="mood-message"></div>
                </form>
                <a href="${pageContext.request.contextPath}/mood/view" class="btn">View Progress</a>
 
            </div>
        </section>
    </main>
 
<!-- ❤️ Mood JS -->
<script>
function showMood(level) {
    event.preventDefault();
    const msg = document.getElementById("moodMessage");
    const messages = {
        1: "We’re here for you. Stay strong and take one step at a time 🤗",
        2: "Neutral days are okay — take a moment to breathe and reflect.",
        3: "Glad to hear you’re feeling okay. Keep going 💪",
        4: "You're doing great! Celebrate small wins 🌟",
        5: "Amazing! Your positive energy is inspiring 🎉"
    };
    msg.innerText = messages[level];
}
</script>
</body>
<div class="topbar">
    <a href="/api/patients/logout" class="topbar-logout">Logout</a>
</div>
</html>