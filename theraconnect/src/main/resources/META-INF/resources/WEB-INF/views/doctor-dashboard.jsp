<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%
String doctorName = session != null ? (String) session.getAttribute("doctorFullName") : "Doctor";
 
Long doctorId = session != null ? (Long) session.getAttribute("doctorProfileId") : null;
%>
 
 
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Doctor Dashboard | TheraConnect</title>
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
 
 
	<aside class="sidebar">
		<div class="sidebar-logo">
			<a href="/" class="logo-link">T</a> <span>Therepists Portal</span>
		</div>
 
		<nav class="sidebar-nav">
			<a href="#top" class="nav-link">Dashboard</a> <a
				href="${pageContext.request.contextPath}/api/availability/addslotform?doctorId=<%= doctorId %>">
 
				Add Available Slot </a>
				<!-- <a href="#Prescriptions" class="nav-link">Prescriptions</a> -->
			<a
				href="${pageContext.request.contextPath}/api/doctors/updateprofiledoctor"
				class="nav-link">Update Profile</a> <a
				href="${pageContext.request.contextPath}/api/doctors/myprofiledoctor"
				class="nav-link">Profile</a>
		</nav>
		<div class="sidebar-footer">
			<a href="/api/doctors/logout" class="logout-link">Logout</a>
		</div>
	</aside>
 
	<!-- 👋 Greeting Section -->
	<div class="doctor-wrapper">
		<div class="dashboard-content">
			<h2>
 
				Welcome back, <span class="username"><%=doctorName%></span> 👨‍⚕️
			</h2>
			<p class="center-message">Your care impact starts here.</p>
 
			<!-- 📊 Dashboard Cards -->
			<div class="card-grid">
 
				<!-- Card 1: Today's Appointments -->
				<div class="doctor-card blue">
					<h3>Today's Appointments</h3>
					<p>appointments upcomming</p>
					<a href="/appointments/doctor/view" class="btn">View Schedule</a>
				</div>
 
				<!-- Card 2: Add Available Slot -->
				<div class="doctor-card green">
					<h3>Add Available Slot</h3>
					<p>Set availability for your patients</p>
					<a
						href="${pageContext.request.contextPath}/api/availability/addslotform?doctorId=<%= doctorId %>"
						class="btn"> Add Slot </a>
 
 
				</div>
 
				<!-- Card 3: Total Earnings -->
				<div class="doctor-card gray">
					<h3>Total Earnings</h3>
					<p>
						<strong>Rs.1000</strong> Earned till Now
					</p>
					<!-- No button needed -->
				</div>
 
			</div>
 
 
		</div>
	</div>
 
 
	<div class="top-right-logout">
		<a href="/api/doctors/logout" class="logout-btn">Logout</a>
	</div>
 
 
 
</body>
</html>