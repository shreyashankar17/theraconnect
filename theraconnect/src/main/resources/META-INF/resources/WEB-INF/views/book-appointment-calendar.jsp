<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Book Appointment | TheraConnect</title>
<link
	href="https://cdn.jsdelivr.net/npm/fullcalendar@6.1.8/index.global.min.css"
	rel="stylesheet">
<script
	src="https://cdn.jsdelivr.net/npm/fullcalendar@6.1.8/index.global.min.js"></script>
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>

	<div class="calendar-container">
		<h2>Book Appointment with Dr. ${doctor.name}</h2>
		<div id="calendar"></div>

		<form id="appointmentForm"
			action="${pageContext.request.contextPath}/appointments/confirm"
			method="post" class="booking-form">
			<%-- <input type="hidden" name="doctorId" value="${doctor.userId}" />
			<input type="hidden" name="patientId" value="${patient.userId}" /> --%>

			<label>Doctor ID:</label> <input type="text" name="doctorId"
				value="${doctor.userId}" readonly /> <label>Patient ID:</label> <input
				type="text" name="patientId" value="<%= session.getAttribute("patientProfileId") %>"readonly>





			<label>Slot ID:</label> <input type="text" name="slotId" id="slotId" readonly
				required /> <label>Date & Time:</label> <input type="text"
				name="date" id="slotDate" readonly required /> <label>Reason:</label>
			<input type="text" name="reason" id="reason"
				placeholder="Enter reason for visit" required /> <label>Payment
				Method:</label> <select name="paymentMethod" id="paymentMethod" required>
				<option value="">Select</option>
				<option value="WalletCARD">Card</option>
			
			</select>

			<p id="slotInfo" class="slot-note"></p>

			<button type="submit" id="bookBtn" disabled>Proceed to
				Payment</button>
		</form>

	</div>

	<script>
const slots = [
    <c:forEach var="slot" items="${slots}">
    {
        id: "${slot.id}",
        title: "Available",
        start: "${slot.start}",
        end: "${slot.end}"
    },
    </c:forEach>
];
 
document.addEventListener('DOMContentLoaded', function () {
    const calendarEl = document.getElementById('calendar');
 
    const calendar = new FullCalendar.Calendar(calendarEl, {
        initialView: 'timeGridWeek',
        height: 450,
        events: slots,
        eventClick: function (info) {
            const slotId = info.event.id;
            const slotDate = info.event.start.toLocaleString();
 
            document.getElementById('slotId').value = slotId;
            document.getElementById('slotDate').value = slotDate;
            document.getElementById('slotInfo').innerText = "Selected Slot: " + slotDate;
            document.getElementById('bookBtn').disabled = false;
        }
    });
 
    calendar.render();
});
</script>
</body>
</html>


