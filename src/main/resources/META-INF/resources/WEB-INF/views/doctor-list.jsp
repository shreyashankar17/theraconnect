<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="com.theraconnect.model.DoctorProfile" %>
<%@ page import="com.theraconnect.model.User" %>
 
<%
    List<DoctorProfile> doctors = (List<DoctorProfile>) request.getAttribute("doctors");
    //Long patientId = session != null ? (Long) session.getAttribute("patientProfileId") : null;
    
%>
 
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Available Therapists</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
<a href="${pageContext.request.contextPath}/patient-dashboard" class="back-home-button">← Back</a>

    <h2>Available Therapists</h2>
   
    <div class="therapist-list">
    
        <%
            if (doctors != null) {
                for (DoctorProfile doctor : doctors) {
                    User user = doctor.getUser();
        %>
        <div class="therapist-card">
            <h3><%= user.getName() %></h3>
            <p><strong>Specialization:</strong> <%= doctor.getSpecialization() %></p>
            <p><strong>Experience:</strong> <%= doctor.getExperience() %></p>
            <p><strong>Bio:</strong> <%= doctor.getBio() %></p>
           <%--  <a href="/bookAppointment?doctorId=<%= doctor.getDoctorProfileId() %>" class="btn">Book Appointment</a> --%>
           <a href="${pageContext.request.contextPath}/appointments/book?doctorId=<%= doctor.getDoctorProfileId() %>" class="btn">Book Appointment</a>
        </div>
        <%
                }
            } else {
        %>
        <p>No doctors found.</p>
        <%
            }
        %>
    </div>
</body>
</html>
 
 