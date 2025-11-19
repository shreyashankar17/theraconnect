<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Your Appointments | TheraConnect</title>
    <style>
    
    .back-home-button {
    position: fixed;
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
    
    
        body {
            font-family: 'Segoe UI', sans-serif;
            background-color: #eef3fc;
            margin: 0;
            padding: 0;
            display: flex;
            justify-content: center;
            align-items: flex-start;
            min-height: 100vh;
        }
        .container {
            width: 100%;
            max-width: 1000px;
            margin: 60px auto;
            background: #fff;
            padding: 40px;
            border-radius: 12px;
            box-shadow: 0 8px 20px rgba(0,0,0,0.08);
        }
        h2 {
            text-align: center;
            color: #1f77f3;
            margin-bottom: 30px;
        }
        table {
            width: 100%;
            border-collapse: collapse;
            margin-top: 10px;
        }
        th, td {
            padding: 14px 12px;
            border-bottom: 1px solid #ddd;
            text-align: center;
        }
        th {
            background-color: #eef5ff;
            color: #333;
        }
        td {
            color: #444;
        }
        tr:hover {
            background-color: #f9fcff;
        }
        .link-btn {
            color: #1a75d2;
            text-decoration: none;
            font-weight: bold;
        }
        .link-btn:hover {
            text-decoration: underline;
        }
        .empty-message {
            text-align: center;
            color: #888;
            font-style: italic;
            padding: 20px;
        }
    </style>
</head>
<body>
<!-- new 27 july -->

<div class="back-home-button">
<a href="${pageContext.request.contextPath}/patient-dashboard" class="back-home-button">← Back</a>	
	</div>


<div class="container">
    <h2>Your Booked Appointments</h2>
 
    <c:if test="${empty appointments}">
        <div class="empty-message">No appointments found. 🗓️</div>
    </c:if>
 
    <c:if test="${not empty appointments}">
        <table>
            <thead>
                <tr>
                    <th>ID</th>
                    <th>Doctor</th>
                    <th>Reason</th>
                    <th>Time</th>
                    <th>Meeting</th>
                    <th>Prescription</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="appt" items="${appointments}">
                    <tr>
                        <td>${appt.appointmentId}</td>
                        <td>${appt.doctorName}</td>
                        <td>${appt.reason}</td>
                        <td>${appt.formattedSlotTime}</td>
                        <td>
                            <a class="link-btn" href="${appt.meetingLink}" target="_blank">Join</a>
                        </td>
                        <td>
                            <c:choose>
                                <c:when test="${appt.hasPrescription}">
                                    <a class="link-btn"
                                       href="${pageContext.request.contextPath}/appointments/patients/prescription/download/${appt.appointmentId}"
                                       target="_blank">Download</a>
                                </c:when>
                                <c:otherwise>
                                    <span style="color: #aaa;">Not Available</span>
                                </c:otherwise>
                            </c:choose>
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </c:if>
</div>
</body>
</html>
 