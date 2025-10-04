<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>



<script>
	    function openUploadModal(appointmentId) {
	        document.getElementById("appointmentIdInput").value = appointmentId;
	        document.getElementById("uploadModal").style.display = "flex";
	    }
 
	    function closeUploadModal() {
	        document.getElementById("uploadModal").style.display = "none";
	    }
	</script>



<meta charset="UTF-8">
<title>Your Appointments | TheraConnect</title>
<%--     <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css"> --%>
<style>
/* 🧭 Sticky Back to Home Button */
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
	background-color: #f4f9ff;
	margin: 0;
	padding: 0;
	text-align: center;
}

.container {
	display: inline-block;
	text-align: left;
	max-width: 1000px;
	margin: 50px auto;
	background-color: #ffffff;
	padding: 40px;
	border-radius: 12px;
	box-shadow: 0 8px 20px rgba(0, 0, 0, 0.08);
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
<a href="${pageContext.request.contextPath}/doctor-dashboard" class="back-home-button">← Back</a>
	<div class="container">
		<h2>Your Upcoming Appointments</h2>

		<c:if test="${empty appointments}">
			<div class="empty-message">No appointments scheduled yet. ��</div>
		</c:if>

		<c:if test="${not empty appointments}">
			<table>
				<thead>
					<tr>
						<th>ID</th>
						<th>Patient Name</th>
						<th>Reason</th>
						<th>Slot Time</th>
						<th>Meeting Link</th>
						<th>Prescription</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach var="appt" items="${appointments}">
						<tr>
							<td>${appt.appointmentId}</td>
							<td>${appt.patientName}</td>
							<td>${appt.reason}</td>
							<td>${appt.formattedSlotTime}</td>
							<td><a class="link-btn" href="${appt.meetingLink}"
								target="_blank">Join</a></td>
							<td><a href="#"
								onclick="openUploadModal(${appt.appointmentId})">Upload</a></td>

						</tr>

					</c:forEach>
				</tbody>
			</table>


		</c:if>
	</div>
	<div id="uploadModal"
		style="display: none; position: fixed; top: 0; left: 0; width: 100%; height: 100%; background-color: rgba(0, 0, 0, 0.4); z-index: 9999; justify-content: center; align-items: center;">
		<div
			style="background: white; padding: 30px; border-radius: 12px; width: 400px; position: relative;">
			<h3 style="margin-top: 0;">Upload Prescription</h3>

			<form id="prescriptionForm"
				action="/appointments/doctor/upload-prescription" method="post"
				enctype="multipart/form-data">
				<form action="/doctor/upload-prescription" method="post"...>

					<input type="hidden" id="appointmentIdInput" name="appointmentId"
						value="" /> <input type="file" name="prescription"
						accept="image/*" required style="margin-top: 10px;" /> <br>
					<br>
					<button type="submit"
						style="background-color: #1f77f3; color: white; padding: 10px 20px; border: none; border-radius: 5px;">Upload</button>
					<button type="button" onclick="closeUploadModal()"
						style="margin-left: 10px;">Cancel</button>
				</form>
		</div>
	</div>

</body>
</html>