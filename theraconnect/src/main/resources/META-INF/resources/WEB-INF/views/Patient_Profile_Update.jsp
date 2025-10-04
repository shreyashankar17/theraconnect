/WEB-INF/views/patient/update-profile.jsp
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Update Patient Profile | TheraConnect</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
    <div class="form-container">
        <h2>Update Your Profile</h2>
        <form action="${pageContext.request.contextPath}/patients/profile" method="post">
            <input type="hidden" name="patientProfileId" value="${profile.patientProfileId}" />

            <div class="form-group">
                <label for="dob">Date of Birth:</label>
                <input type="date" name="dob" id="dob" value="${profile.dob}" required />
            </div>

            <div class="form-group">
                <label for="gender">Gender:</label>
                <select name="gender" id="gender" required>
                    <option value="MALE" ${profile.gender == 'MALE' ? 'selected' : ''}>Male</option>
                    <option value="FEMALE" ${profile.gender == 'FEMALE' ? 'selected' : ''}>Female</option>
                    <option value="OTHER" ${profile.gender == 'OTHER' ? 'selected' : ''}>Other</option>
                </select>
            </div>

            <div class="form-group">
                <label for="contact">Contact Number:</label>
                <input type="text" name="contact" id="contact" value="${profile.contact}" required />
            </div>

            <button type="submit" class="btn">Update Profile</button>
        </form>
    </div>
</body>
</html>