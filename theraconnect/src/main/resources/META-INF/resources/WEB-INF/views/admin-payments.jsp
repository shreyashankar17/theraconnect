<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <title>Admin - Payments</title>
    <link rel="stylesheet" href="your-centralized.css">
    <style>
        body {
            font-family: 'Segoe UI', sans-serif;
            background-color: #f4f6f9;
            margin: 0;
            padding: 20px;
            color: #333;
        }
 
        .container {
            max-width: 1200px;
            margin: auto;
        }
 
        .revenue-cards {
            display: flex;
            gap: 8px;
            margin-bottom: 30px;
        }
		
 
 
        .card {
            background: white;
            padding: 18px;
            border-radius: 8px;
            flex: 1;
            box-shadow: 0 2px 8px rgba(0,0,0,0.06);
            text-align: center;
            font-size: 18px;
            font-weight: 600;
			margin-top: 30px;
        }
 
        .revenue-summary {
            display: flex;
            justify-content: space-between;
            align-items: center;
            margin-bottom: 30px;
        }
 
        .success-pending-chart {
            background-color: #ffffff;
            padding: 14px 20px;
            border-radius: 8px;
            box-shadow: 0 1px 6px rgba(0,0,0,0.05);
            font-size: 16px;
            font-weight: 500;
        }
 
        .filter-bar {
            display: flex;
            flex-wrap: wrap;
            gap: 10px;
            margin-bottom: 25px;
        }
 
        .filter-bar input,
        .filter-bar select {
            padding: 8px 12px;
            border: 1px solid #ccc;
            border-radius: 6px;
            min-width: 160px;
        }
 
        .filter-bar button {
            padding: 8px 16px;
            background-color: #28a745;
            color: white;
            border: none;
            border-radius: 6px;
            cursor: pointer;
            font-weight: bold;
        }
 
        .filter-bar button:hover {
            background-color: #218838;
        }
 
        .payments-table {
            width: 100%;
            border-collapse: collapse;
            background-color: white;
            border-radius: 8px;
            overflow: hidden;
            box-shadow: 0 2px 6px rgba(0,0,0,0.05);
        }
 
        .payments-table th,
        .payments-table td {
            padding: 12px 16px;
            text-align: left;
            border-bottom: 1px solid #eee;
        }
 
        .payments-table th {
            background-color: #f0f4f8;
            font-weight: 600;
        }
 
        .payments-table tr:hover {
            background-color: #f9fcff;
        }
		
		.back-home-button {
						    top: 35px;
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
						
						.payments-heading {
						    text-align: center;
						    font-size: 34px;
						    font-weight: 600;
							margin-top: -10px;
						    margin-bottom: 10px;
						}
						
						#paymentStatusLegend div::before {
						  content: '';
						  display: inline-block;
						  width: 10px;
						  height: 10px;
						  margin-right: 6px;
						  border-radius: 50%;
						  background-color: currentColor;
						}
						
 
 
 
 
    </style>
</head>
<body>
    <div class="container">
		
		<div class="topbar-back">
								<a href="${pageContext.request.contextPath}/admin/dashboard" class="back-home-button">← Back to Home</a>	
							</div>
					
							<h2 class="payments-heading">Payments</h2>
 
        <!-- 💳 Revenue Cards -->
		<div class="top-analytics" style="display: flex; gap: 20px; margin-bottom: 30px;">
 
		    <!-- 🧾 Revenue Cards Vertically -->
		    <div class="revenue-cards-vertical" >
		        <div class="card">Today: ₹<strong>${revenueStats.today}</strong></div>
		        <div class="card">This Week: ₹<strong>${revenueStats.week}</strong></div>
		        <div class="card">This Month: ₹<strong>${revenueStats.month}</strong></div>
		    </div>
 
		    <!-- 📈 Line Graph -->
		    <div class="dashboard-card" style="flex: 2; padding: 20px;">
		        <h5>Revenue Over Last 7 Days</h5>
		        <canvas id="sevenDayRevenueChart" height="100"></canvas>
		    </div>
 
		    <!-- 🥧 Pie Chart -->
		    <div class="dashboard-card" style="width: 240px;">
		        <h5>Payment Status Overview</h5>
		        <canvas id="paymentPieChart" width="240" height="240"></canvas>
				<div id="paymentStatusLegend" style="margin-top: 12px; font-weight: 500;"></div>
		    </div>
		</div>
 
 
        <!-- 🔍 Unified Filter Bar -->
        <form action="/admin/payments" method="get" class="filter-bar">
            <input type="text" name="nameSearch" placeholder="Doctor or Patient Name" value="${param.nameSearch}">
            <input type="text" name="paymentIdSearch" placeholder="Payment ID" value="${param.paymentIdSearch}">
            <input type="date" name="dateSearch" value="${param.dateSearch}">
			<select name="statusFilter">
			    <option value="">All Status</option>
			    <option value="PAID">Paid</option>
			    <option value="PENDING">Pending</option>
			    <option value="FAILED">Failed</option>
			</select>
 
            <button type="submit">Search</button>
        </form>
 
        <!-- 📋 Payment History Table -->
        <table class="payments-table">
            <thead>
                <tr>
                    <th>Payment ID</th>
                    <th>Paid From</th>
                    <th>Paid To</th>
                    <th>Amount (₹)</th>
                    <th>Status</th>
                    <th>Date</th>
                    <th>Time</th>
                    <th>Appointment ID</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="pay" items="${payments}">
                    <tr>
						<c:set var="ts" value="${pay['timestamp']}" />
						<td>${pay["payment_id"]}</td>
						<td>${pay["patient_name"]}</td>
						<td>${pay["doctor_name"]}</td>
						<td>${pay["amount"]}</td>
						<td>${pay["payment_status"]}</td>
						<td><fmt:formatDate value="${ts}" pattern="dd-MMM-yyyy" /></td>
						<td><fmt:formatDate value="${ts}" pattern="HH:mm a" /></td>
						<td>${pay["appointment_id"]}</td>
 
                    </tr>
                </c:forEach>
            </tbody>
        </table>
 
    </div>
	
	<script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
	
	<script>
	const statusStats = JSON.parse('${statusStatsJson}'); // Already mapped
	</script>
	
	<script>
	  const ctx = document.getElementById('paymentPieChart').getContext('2d');
 
	  const pieData = {
	    labels: Object.keys(statusStats),
	    datasets: [{
	      data: Object.values(statusStats),
	      backgroundColor:['#FFC107','#4CAF50','#F44336']
	    }]
	  };
 
	  new Chart(ctx, {
	    type: 'pie',
	    data: pieData,
	    options: {
	      plugins: {
	        legend: { position: 'bottom' },
	        tooltip: {
	          callbacks: {
	            label: function(context) {
	              const value = context.raw;
	              const total = context.dataset.data.reduce((a, b) => a + b, 0);
	              const percent = ((value / total) * 100).toFixed(1);
	              return `${context.label}: ${value} (${percent}%)`;
	            }
	          }
	        }
	      }
	    }
	  });
	</script>
	
	<script>
	    const revenueStats = JSON.parse('${revenue7DaysJson}');
	    const ctxLine = document.getElementById('sevenDayRevenueChart').getContext('2d');
 
	    const lineChart = new Chart(ctxLine, {
	        type: 'line',
	        data: {
	            labels: Object.keys(revenueStats),
	            datasets: [{
	                label: 'Daily Revenue (₹)',
	                data: Object.values(revenueStats),
	                borderColor: '#3b82f6',
	                backgroundColor: 'rgba(59, 130, 246, 0.2)',
	                fill: true,
	                tension: 0.3,
	                pointRadius: 4
	            }]
	        },
	        options: {
	            scales: {
					y: {
					  beginAtZero: true,
					  grid: { color: '#eee' },
					  ticks: {
					    callback: value => '₹' + value.toLocaleString()
					  }
					},
					y: {
					  beginAtZero: true,
					  grid: { color: '#eee' },
					  ticks: {
					    callback: value => '₹' + value.toLocaleString()
					  }
					}
	            },
	            plugins: {
	                legend: { display: false },
	                tooltip: {
	                    callbacks: {
	                        label: context => `₹${context.raw.toLocaleString()}`
	                    }
	                }
	            }
	        }
	    });
	</script>
 
 
	
</body>
</html>
 