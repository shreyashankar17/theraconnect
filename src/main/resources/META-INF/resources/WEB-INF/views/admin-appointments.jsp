<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Admin - Appointments</title>
    <link rel="stylesheet" href="your-centralized.css">
	<style>
		
		body {
		    font-family: 'Segoe UI', sans-serif;
		    background-color: #f7f9fc;
		    margin: 0;
		    padding: 20px;
		    color: #2c3e50;
		}
 
		.container {
		    max-width: 1200px;
		    margin: auto;
		}
 
		.dashboard-cards {
		    display: flex;
		    gap: 20px;
		    margin-bottom: 30px;
			margin-top: 30px;
		}
 
		.card {
		    background: white;
		    padding: 20px;
		    border-radius: 8px;
		    flex: 1;
		    box-shadow: 0 2px 8px rgba(0,0,0,0.06);
		    text-align: center;
		    font-weight: 600;
		    font-size: 18px;
		    color: #34495e;
		}
 
		.status-row {
		    display: flex;
		    gap: 20px;
		    margin-bottom: 25px;
		    font-size: 16px;
		}
 
		.status-row span {
		    background-color: #ecf0f1;
		    padding: 10px 15px;
		    border-radius: 6px;
		}
 
		.filter-bar {
		    display: flex;
		    gap: 10px;
		    margin-bottom: 20px;
		}
 
		.filter-bar input,
		.filter-bar select {
		    padding: 8px 10px;
		    border: 1px solid #ccc;
		    border-radius: 6px;
		}
 
		.filter-bar button {
		    padding: 8px 14px;
		    background-color: #3498db;
		    color: white;
		    border: none;
		    border-radius: 6px;
		    cursor: pointer;
		    transition: background 0.2s ease;
		}
 
		.filter-bar button:hover {
		    background-color: #2980b9;
/		}
 
		.appointments-table {
		    width: 100%;
		    border-collapse: collapse;
		    background-color: white;
		    box-shadow: 0 2px 6px rgba(0,0,0,0.05);
		    border-radius: 8px;
		    overflow: hidden;
		}
 
		.appointments-table th,
		.appointments-table td {
		    padding: 12px 16px;
		    text-align: left;
		    border-bottom: 1px solid #ecf0f1;
		}
 
		.appointments-table th {
		    background-color: #f0f4f8;
		    font-weight: 600;
		    color: #2c3e50;
		}
 
		.appointments-table tr:hover {
		    background-color: #f9fcff;
		}
		
		.back-home-button {
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
 
		
	</style>
    <script src="your-js-library.js"></script>
</head>
<body>
	
    <div class="container">
		
		<div class="topbar-back">
						<a href="${pageContext.request.contextPath}/admin/dashboard" class="back-home-button">← Back to Home</a>	
					</div>
 
        <!-- 🟦 Stat Cards Overview -->
        <div class="dashboard-cards">
            <div class="card">Appointments Today: <strong>${stats.today}</strong></div>
            <div class="card">This Week: <strong>${stats.week}</strong></div>
            <div class="card">This Month: <strong>${stats.month}</strong></div>
            <div class="card">Upcoming (24h): <strong>${stats.upcoming}</strong></div>
        </div>
 
		<!-- 🧮 Status Breakdown -->
		<div class="status-row">
		    <span>Booked: <strong>${statusCounts.booked}</strong></span>
		    <span>Upcoming: <strong>${statusCounts.upcoming}</strong></span>
		    <span>Completed: <strong>${statusCounts.completed}</strong></span>
		</div>
 
 
        <!-- 🔍 Filter + Sort Controls -->
		<!-- 🔍 Unified Filter Bar -->
		<form action="/admin/appointments" method="get" class="filter-bar">
 
		    <!-- 👤 Search by Name -->
		    <input type="text" name="nameSearch" placeholder="Doctor or Patient Name">
 
		    <!-- 📅 Date Filter -->
		    <input type="date" name="dateSearch">
 
		    <!-- 🩺 Status Filter -->
			<!-- 🩺 Status Filter -->
			<select name="statusFilter">
			    <option value="">All Status</option>
			    <option value="COMPLETED">Completed</option>
			    <option value="UPCOMING">Upcoming</option>
			</select>
 
 
		    <!-- ↕ Sort Dropdown -->
		    <select name="sortOrder">
		        <option value="">Sort by Time</option>
		        <option value="asc">Ascending</option>
		        <option value="desc">Descending</option>
		    </select>
 
		    <!-- 🔍 Single Search Trigger -->
		    <button type="submit">Search</button>
		</form>
 
 
        <!-- 📋 Appointment Table -->
        <table class="appointments-table">
            <thead>
                <tr>
                    <th>Appointment ID</th>
                    <th>Doctor Name</th>
                    <th>Patient Name</th>
                    <th>Slot Date</th>
                    <th>Slot Time</th>
                    <th>Status</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="appt" items="${appointments}">
                    <tr>
                        <td>${appt.appointmentId}</td>
                        <td>${appt.doctor.name}</td>
                        <td>${appt.patient.name}</td>
						<td>${appt.slotTime.toLocalDate()}</td>
						<td>${appt.slotTime.toLocalTime()}</td>
                        <td>${appt.status}</td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
 
    </div>
</body>
</html>