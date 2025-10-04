<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Update Doctor Profile</title>
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
	background-color: #f2f6fc;
	margin: 0;
	padding: 0;
}

.form-container {
	max-width: 600px;
	margin: 60px auto;
	background: #fff;
	padding: 30px;
	border-radius: 10px;
	box-shadow: 0 8px 20px rgba(0, 0, 0, 0.08);
}

h2 {
	text-align: center;
	margin-bottom: 25px;
	color: #1f77f3;
}

label {
	font-weight: bold;
	display: block;
	margin-top: 20px;
	color: #444;
}

input, textarea {
	width: 100%;
	padding: 10px;
	margin-top: 6px;
	border-radius: 6px;
	border: 1px solid #ccc;
	box-sizing: border-box;
}

textarea {
	resize: vertical;
}

.submit-btn {
	margin-top: 30px;
	width: 100%;
	padding: 12px;
	background-color: #1f77f3;
	color: white;
	border: none;
	border-radius: 6px;
	font-size: 16px;
	cursor: pointer;
}

.submit-btn:hover {
	background-color: #155dcc;
}
</style>

</head>
<body>


	<a href="${pageContext.request.contextPath}/doctor-dashboard"
		class="back-home-button">← Back</a>


	<div class="form-container">
		<h2>Update Your Profile</h2>
		<%--         <form action="${pageContext.request.contextPath}/" method="post"> --%>
		<form
			action="${pageContext.request.contextPath}/api/doctors/updateProfile"
			method="post">

			<label for="experience">Experience:</label> <input type="number"
				id="experience" name="experience" min="1" max="70" placeholder="e.g. 8"
				title="Please enter a number " required> <label
				for="specialization">Specialization</label> <select
				name="specialization" required>
				<option value="" disabled selected>Select Specialization</option>
				<option value="psychologist">Psychologist</option>
				<option value="psychiatrist">Psychiatrist</option>
				<option value="pediatric Psychotherapist">pediatric
					Psychotherapist</option>
				<option value="Addiction Recovery Specialist">Addiction
					Recovery Specialist</option>>
				<option value="Mindfulness Therapist">Mindfulness Therapist</option>

			</select> <label for="bio">Bio:</label>
			<textarea id="bio" name="bio" rows="5"
				placeholder="Write a short bio..."></textarea>

			<button type="submit" class="submit-btn">Save Changes</button>
		</form>
	</div>
</body>
</html>

