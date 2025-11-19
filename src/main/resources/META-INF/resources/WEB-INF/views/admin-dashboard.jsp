<%-- <%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
 
<%
    String adminName = session != null ? (String) session.getAttribute("adminFullName") : "Admin";

%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Admin Dashboard | TheraConnect</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
 
<!-- 🔙 Back Arrow + 🔓 Logout -->
<div class="topbar-back">
    <button onclick="goBack()" class="back-icon">&#8592;</button>
</div>
<div class="topbar">
    <a href="/logout" class="topbar-logout">Logout</a>
</div>
 
<!-- 🧭 Sidebar Navigation -->
<aside class="sidebar">
    <div class="sidebar-logo">
        <a href="/" class="logo-link">T</a>
        <span>Admin Panel</span>
    </div>
    <nav class="sidebar-nav">
        <a href="#top" class="nav-link">Dashboard</a>
        <a href="${pageContext.request.contextPath}/admin/users" class="nav-link">Manage Users</a>
        <!--<a href="${pageContext.request.contextPath}/admin/doctors" class="nav-link">Doctors</a>-->
        <a href="${pageContext.request.contextPath}/admin/appointments" class="nav-link">Appointments</a>
        <a href="${pageContext.request.contextPath}/admin/payments" class="nav-link">Payments</a>
        <!--<a href="${pageContext.request.contextPath}/admin/emergencies" class="nav-link">Emergency Logs</a>-->
        <a href="${pageContext.request.contextPath}/admin/reports" class="nav-link">Reports</a>
    </nav>
    <div class="sidebar-footer">
        <a href="/admin/logout" class="logout-link">Logout</a>
    </div>
</aside>
 
<!-- 🧠 Main Dashboard Content -->
<main class="patient-main" id="top">
    <h2>Welcome, <span class="username"><%= adminName %></span> 🛡️</h2>
    <p class="center-message">Overview of TheraConnect Platform</p>
 
    <div class="card-grid">
		<div class="card blue">
		    <h3>Total Users</h3>
		    <p>${totalUsers} registered users</p>
		</div>
 
		<div class="card green">
		    <h3>Registered Doctors</h3>
		    <p>${totalDoctors} professionals onboarded</p>
		</div>
 
		<div class="card orange">
		    <h3>Total Appointments</h3>
		    <p>${totalAppointments} booked sessions</p>
		</div>
 
		<div class="card red">
		    <h3>Total Revenue</h3>
		    <p><fmt:formatNumber value="${totalRevenue}" type="currency" currencySymbol="₹"/></p>
		</div>
 
    </div>
 
    <section class="dashboard-boxes">
        <div class="appointments-section">
            <h3>Recent Activity</h3>
            <ul>
                <li>New registration: Sarah Johnson, 5 minutes ago</li>
                <li>Emergency resolved: Dr. Green, 12 mins ago</li>
                <li>Payment: Michael Chen, 18 mins ago</li>
            </ul>
        </div>
 
        <div class="checkins-section">
            <h3>System Health</h3>
            <ul>
                <li>Uptime: 99.9%</li>
                <li>Response Time: 152ms</li>
                <li>Sessions Active: 47</li>
                <li>Error Rate: 0.01%</li>
            </ul>
        </div>
    </section>
</main>
 
<script>
function goBack() {
    window.history.back();
}
</script>
</body>
</html> --%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
 
<%
    String adminName = session != null ? (String) session.getAttribute("adminFullName") : "Admin";
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Admin Dashboard | TheraConnect</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
 
<!-- 🔙 Back Arrow + 🔓 Logout -->
<div class="topbar-back">
    <button onclick="goBack()" class="back-icon">&#8592;</button>
</div>
<div class="topbar">
    <a href="/logout" class="topbar-logout">Logout</a>
</div>
 
<!-- 🧭 Sidebar Navigation -->
<aside class="sidebar">
    <div class="sidebar-logo">
        <a href="/" class="logo-link">T</a>
        <span>Admin Panel</span>
    </div>
    <nav class="sidebar-nav">
        <a href="#top" class="nav-link">Dashboard</a>
        <a href="${pageContext.request.contextPath}/admin/users" class="nav-link">Manage Users</a>
        <!--<a href="${pageContext.request.contextPath}/admin/doctors" class="nav-link">Doctors</a>-->
        <a href="${pageContext.request.contextPath}/admin/appointments" class="nav-link">Appointments</a>
        <a href="${pageContext.request.contextPath}/admin/payments" class="nav-link">Payments</a>
        <!--<a href="${pageContext.request.contextPath}/admin/emergencies" class="nav-link">Emergency Logs</a>-->
      <%--   <a href="${pageContext.request.contextPath}/admin/reports" class="nav-link">Reports</a> --%>
    </nav>
    <div class="sidebar-footer">
        <a href="/admin/logout" class="logout-link">Logout</a>
    </div>
</aside>
 
<!-- 🧠 Main Dashboard Content -->
<main class="patient-main" id="top">
    <h2>Welcome, <span class="username"><%= adminName %></span> 🛡️</h2>
    <p class="center-message">Overview of TheraConnect Platform</p>
 
    <div class="card-grid">
		<div class="card blue">
		    <h3>Total Users</h3>
		    <p>${totalUsers} registered users</p>
		</div>
 
		<div class="card green">
		    <h3>Registered Doctors</h3>
		    <p>${totalDoctors} professionals onboarded</p>
		</div>
 
		<div class="card orange">
		    <h3>Total Appointments</h3>
		    <p>${totalAppointments} booked sessions</p>
		</div>
 
		<div class="card red">
		    <h3>Total Revenue</h3>
		    <p><fmt:formatNumber value="${totalRevenue}" type="currency" currencySymbol="₹"/></p>
		</div>
 
    </div>
 
    <section class="dashboard-boxes">
        <div class="appointments-section">
            <h3>Recent Activity</h3>
            <ul>
                <li>New registration: Sarah Johnson, 5 minutes ago</li>
                <li>Emergency resolved: Dr. Green, 12 mins ago</li>
                <li>Payment: Michael Chen, 18 mins ago</li>
            </ul>
        </div>
 
        <div class="checkins-section">
            <h3>System Health</h3>
            <ul>
                <li>Uptime: 99.9%</li>
                <li>Response Time: 152ms</li>
                <li>Sessions Active: 47</li>
                <li>Error Rate: 0.01%</li>
            </ul>
        </div>
    </section>
</main>
 
<script>
function goBack() {
    window.history.back();
}
</script>
</body>
</html>