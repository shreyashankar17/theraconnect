<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
 
 
<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
    <title>Add Doctor Availability</title>
    <script>
        // Disable past dates
        window.onload = function () {
            const today = new Date().toISOString().split('T')[0];
            document.getElementById("date").setAttribute("min", today);
        };
    </script>
</head>
<body>

<div class="topbar-back">
		<a href="${pageContext.request.contextPath}/doctor-dashboard" class="back-home-button">← Back</a>	
	</div>
 
 
 
<div class="slot-form-container">
    <h2>Add Availability Slot</h2>
 
    <form action="${pageContext.request.contextPath}/api/availability/add" method="post">
        <!-- Doctor ID fetched automatically -->
        <input type="hidden" name="doctorId" value="${doctor.doctorProfileId}" /><br/>
 
        <label>Date:</label>
        <input type="date" id="date" name="date" required /><br/>
 
       Start Time:</label>
        <select name="startTime" required>
            <c:forEach var="hour" begin="9" end="17">
                <option value="${hour < 10 ? '0' : ''}${hour}:00:00">${hour}:00</option>
                <option value="${hour < 10 ? '0' : ''}${hour}:30:00">${hour}:30</option>
            </c:forEach>
        </select><br/>
 
        <label>End Time:</label>
        <select name="endTime" required>
            <c:forEach var="hour" begin="9" end="17">
                <option value="${hour < 10 ? '0' : ''}${hour}:30:00">${hour}:30</option>
                <option value="${hour+1 < 10 ? '0' : ''}${hour+1}:00:00">${hour+1}:00</option>
            </c:forEach>
        </select><br/>
 
        <label>Is Booked:</label>
        <select name="isBooked" required>
            <option value="false">False</option>
            <option value="true">True</option>
        </select><br/>
 
        <button type="submit">Add Slot</button>
    </form>
    </div>
</body>
</html>
 