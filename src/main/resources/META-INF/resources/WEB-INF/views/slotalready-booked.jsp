<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Slot Already Booked</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
    <div class="message-container">
        <h2>Slot Already Booked</h2>
        <p>Sorry, the selected slot has already been booked by another patient.</p>
        <form action="${pageContext.request.contextPath}/appointments/book?doctorId=<%= session.getAttribute("doctorProfileId") %>" method="get">
            <button type="submit">OK</button>
        </form>
    </div>
</body>
</html>
