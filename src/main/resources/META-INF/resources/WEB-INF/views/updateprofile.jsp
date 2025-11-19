<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>TheraConnect - Profile Settings</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
    <script>
      function validateForm() {
        const name = document.getElementById("name").value.trim();
        const email = document.getElementById("email").value.trim();
        const contact = document.getElementById("contact").value.trim();
        const dob = document.getElementById("dob").value;
        const gender = document.getElementById("gender").value;
 
		// Name: at least 2 characters and letters only
		const nameRegex = /^[A-Za-z\s]{2,}$/;
		if (!nameRegex.test(name)) {
		  alert("Name must contain only letters and be at least 2 characters long.");
		  return false;
		}
 
 
        // Email: basic structure check
        const emailRegex = /^[^@\s]+@[^@\s]+\.[a-z]{2,}$/i;
        if (!emailRegex.test(email)) {
          alert("Please enter a valid email address.");
          return false;
        }
 
        // Contact: 10 digits starting from 6-9
        const contactRegex = /^[6-9]\d{9}$/;
        if (!contactRegex.test(contact)) {
          alert("Contact number must be 10 digits starting with 6-9.");
          return false;
        }
 
        // DOB: must be in the past
        if (!dob) {
          alert("Please enter your Date of Birth.");
          return false;
        }
        const dobDate = new Date(dob);
        const today = new Date();
        if (dobDate >= today) {
          alert("Date of Birth must be in the past.");
          return false;
        }
 
        // Gender: validate selected value
        if (!["Male", "Female", "Other"].includes(gender)) {
          alert("Please select a valid gender.");
          return false;
        }
 
        return true;
      }
 
      window.addEventListener("DOMContentLoaded", () => {
        const contactInput = document.getElementById("contact");
        contactInput.addEventListener("input", function () {
          this.value = this.value.replace(/[^0-9]/g, ''); // only digits
        });
 
        document.querySelector("form").addEventListener("submit", function (e) {
          if (!validateForm()) {
            e.preventDefault();
          }
        });
      });
    </script>
 
</head>
<body>
 
<!-- 🧭 Navigation Bar -->
<div class="navbar">
    <div>
        <a href="/patient-dashboard">Dashboard</a>
        <a href="/api/doctors/list">Find Therapists</a>
        <a href="${pageContext.request.contextPath}/mood/view">My Emotions</a>
        <a href="/api/patients/myprofile">Profile</a>
    </div>
    <div>
        <!-- <select>
            <option selected>English</option>
            <option>Hindi</option>
        </select> -->
        <a href="/patient-dashboard">Back</a>
    </div>
</div>
 
<!-- 📝 Profile Update Form -->
<div class="container">
    <h2>Profile Settings</h2>
    <form action="/api/patients/updateProfile" method="post">
        <!-- Basic Info -->
        <label for="name">Name:</label>
        <input type="text" id="name" name="name" value="${name}">
 
        <label for="email">Email:</label>
        <input type="email" id="email" name="email" value="${email}">
 
        <!-- Additional Info -->
        <label for="contact">Contact:</label>
        <%-- <input type="text" id="contact" name="contact" value="${contact}"> --%>
        <input type="text" id="contact" name="contact"
       value="${contact}" pattern="\d{10}" maxlength="10"
       title="Please enter exactly 10 digits" required>
 
        <label for="dob">Date of Birth:</label>
        <input type="date" id="dob" name="dob" value="${dob}">
 
        <label for="gender">Gender:</label>
        <select id="gender" name="gender">
            <option value="Male" ${gender == 'Male' ? 'selected' : ''}>Male</option>
            <option value="Female" ${gender == 'Female' ? 'selected' : ''}>Female</option>
            <option value="Other" ${gender == 'Other' ? 'selected' : ''}>Other</option>
        </select>
 
        <input type="submit" value="Update Profile">
    </form>
</div>

<!-- <script>
  const contactInput = document.getElementById("contact");
 
  contactInput.addEventListener("input", function () {
    // Allow only digits
    this.value = this.value.replace(/[^0-9]/g, '');
  });
 
  document.querySelector("form").addEventListener("submit", function (e) {
    if (contactInput.value.length !== 10) {
      alert("Mobile number must be exactly 10 digits.");
      e.preventDefault(); // block form submission
    }
  });
</script> -->



 
 
</body>
</html>