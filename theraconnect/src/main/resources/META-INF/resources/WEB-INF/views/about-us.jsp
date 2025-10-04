<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>About Us | TheraConnect</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
<a href="${pageContext.request.contextPath}/" class="back-home-button">← Back to Home</a>
 
<!-- 🔝 Sticky Back Button -->
<a href="${pageContext.request.contextPath}/" class="back-home-button">← Back to Home</a>
 
<!-- 🌟 Hero -->
<div class="about-hero">
    <h1>TheraConnect</h1>
    <p class="tagline">Because every mind deserves care, connection, and hope 💙</p>
</div>
 
<!-- 📚 Page Sections -->
<div class="about-content">
    <section class="fade-in">
        <h2>Our Mission</h2>
        <p>
            TheraConnect was born to bridge the emotional gap between people seeking support and the professionals ready to offer it.
            For those feeling hesitant to walk into a therapist’s office — we're your quiet space to begin healing.
        </p>
        <p class="section-quote">“Healing begins when compassion meets courage.”</p>
    </section>
 
    <section class="fade-in">
        <h2>What We Believe</h2>
        <ul>
            <li>🌿 Therapy should feel safe and stigma-free</li>
            <li>🌈 Mental wellness is a journey — not a destination</li>
            <li>💻 Technology can build trust, not just convenience</li>
            <li>💬 Every feeling is valid and worth listening to</li>
        </ul>
        <p class="section-quote">“You’re allowed to be both a masterpiece and a work in progress.”</p>
    </section>
 
    <section class="fade-in">
        <h2>Why We Exist</h2>
        <p>
            Because life gets overwhelming. Because you shouldn’t have to justify your feelings. Because emotional wellbeing isn’t a luxury — it’s essential.
            TheraConnect is for those navigating silent battles, seeking clarity, or simply needing to be heard.
        </p>
        <p class="section-quote">“It’s okay to ask for help. That’s how healing starts.”</p>
    </section>
 
    <section class="fade-in">
        <h2>You’re Not Alone</h2>
        <p>
            Whether you're anxious, unsure, or quietly struggling — we're here. Not as a replacement for in-person care, but as a gentle nudge toward self-awareness,
            empathy, and connection. Therapy should be approachable, and we’re proud to make that true.
        </p>
        <p class="section-quote">“One small step toward support can change your life.”</p>
    </section>
</div>
 
<!-- 🌿 Scroll Animation Script -->
<script>
    const observer = new IntersectionObserver((entries) => {
        entries.forEach(entry => {
            if (entry.isIntersecting) {
                entry.target.classList.add("visible");
            }
        });
    });
 
    const faders = document.querySelectorAll(".fade-in");
    faders.forEach(el => observer.observe(el));
</script>
 
</body>
</html>
 