<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
 
<html>
<head>
    <title>Emotion Log | TheraConnect</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
    <script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
</head>
<body class="view-body">

<a href="${pageContext.request.contextPath}/patient-dashboard" class="back-home-button">← Back</a>
 
<div class="view-container">
    <h2>How Are You Feeling Today?</h2>
<form action="${pageContext.request.contextPath}/mood/log" method="post">
    <input type="hidden" name="moodLevel" id="moodLevelInput" />
 
    <div class="emoji-select">
        <button type="button" onclick="setMood(5)">😃 Very Happy</button>
        <button type="button" onclick="setMood(4)">🙂 Happy</button>
        <button type="button" onclick="setMood(3)">😐 Neutral</button>
        <button type="button" onclick="setMood(2)">😞 Sad</button>
        <button type="button" onclick="setMood(1)">😢 Very Sad</button>
    </div>
 
    <button type="submit" class="log-btn">Log Emotion</button>
</form>
<c:if test="${not empty justLogged}">
    <div class="success-popup">Emotion logged for today! 🫶</div>
    <div class="motivation-text">
        <c:choose>
            <c:when test="${justLogged == 1}">
                We’re here for you. Stay strong and take one step at a time 🤗
            </c:when>
            <c:when test="${justLogged == 2}">
                Neutral days are okay — take a moment to breathe and reflect.
            </c:when>
            <c:when test="${justLogged == 3}">
                Glad to hear you’re feeling okay. Keep going 💪
            </c:when>
            <c:when test="${justLogged == 4}">
                You're doing great! Celebrate small wins 🌟
            </c:when>
            <c:when test="${justLogged == 5}">
                Amazing! Your positive energy is inspiring 🎉
            </c:when>
        </c:choose>
    </div>
</c:if>
 
 
<div id="moodMessage" class="mood-message"></div>
<script>
function setMood(level) {
    document.getElementById("moodLevelInput").value = level;
    document.getElementById("moodMessage").innerText =
        "Selected: " + level + " 🌿 (Click below to log)";
}
</script>
 
    <script>
function setMood(level) {
    document.getElementById("moodLevelInput").value = level;
    document.getElementById("moodMessage").innerText = "Selected mood: " + level;
}
</script>
 
    
</div>
</form>
 
    <h2>Your Mood Progress</h2>
 
 
<canvas id="moodChart" width="700" height="300"></canvas>
<script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
 
 
<script>
const ctx = document.getElementById('moodChart').getContext('2d');
const moodChart = new Chart(ctx, {
    type: 'line',
    data: {
        labels: [], // empty initially
        datasets: [{
            label: 'Mood Level',
            data: [],
            borderColor: '#4A90E2',
            backgroundColor: 'rgba(74,144,226,0.2)',
            fill: true,
            tension: 0.3
        }]
    },
    options: {
        scales: {
            y: {
                min: 1,
                max: 5,
                ticks: {
                    stepSize: 1,
                    callback: value => {
                        const map = {
                            1: "😢 Very Sad",
                            2: "😞 Sad",
                            3: "😐 Neutral",
                            4: "🙂 Happy",
                            5: "😃 Very Happy"
                        };
                        return map[value] || value;
                    }
                }
            }
        }
    }
});
</script>
 
<!-- 🔹 Refresh Chart on Page Load -->
<script>
function refreshGraph() {
    fetch("${pageContext.request.contextPath}/api/mood/data")
    .then(response => response.json())
    .then(data => {
        moodChart.data.labels = data.labels;
        moodChart.data.datasets[0].data = data.levels;
        moodChart.update();
    });
}
window.onload = refreshGraph;
</script>
 
<!-- 🔹 Mood message display -->
<div id="moodMessage" class="mood-message"></div>
 
<!-- 🔹 Log mood without redirect -->
<script>
function logMood(level) {
    fetch("${pageContext.request.contextPath}/mood/log", {
        method: "POST",
        headers: { "Content-Type": "application/x-www-form-urlencoded" },
        body: "moodLevel=" + level
    })
    .then(response => {
        if (response.ok) {
            document.getElementById("moodMessage").innerText = "Mood logged! Refreshing chart... 🌈";
            refreshGraph();
        } else {
            document.getElementById("moodMessage").innerText = "Oops! Submission failed.";
        }
    });
}
</script>
 
</body>
</html>