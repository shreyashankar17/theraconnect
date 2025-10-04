<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Confirm Payment - TheraConnect</title>
    <style>
        body {
            font-family: 'Segoe UI', sans-serif;
            background-color: #f5f7fa;
            padding: 40px;
        }
        .payment-container {
            background-color: #ffffff;
            max-width: 500px;
            margin: 0 auto;
            border-radius: 15px;
            padding: 30px;
            box-shadow: 0 0 10px rgba(0,0,0,0.15);
        }
        h2 {
            color: #333;
            text-align: center;
        }
        .info {
            margin-bottom: 20px;
        }
        .info p {
            font-size: 16px;
            margin: 8px 0;
        }
        .amount {
            font-size: 20px;
            color: #0066cc;
            font-weight: bold;
        }
        button {
            width: 100%;
            background-color: #0066cc;
            color: white;
            font-size: 16px;
            padding: 12px;
            border: none;
            border-radius: 8px;
            cursor: pointer;
            margin-top: 20px;
        }
        button:hover {
            background-color: #004a99;
        }
    </style>
</head>
<body>
<div class="payment-container">
    <h2>Confirm Your Payment</h2>
    <div class="info">
        <p><strong>Doctor:</strong> ${appointment.doctor.name}</p>
        <p><strong>Slot Time:</strong> ${appointment.slotTime}</p>
        <p class="amount">Amount: ₹500</p>
    </div>
 
    <!-- Razorpay Script -->
    <script src="https://checkout.razorpay.com/v1/checkout.js"></script>
    <script>
        function triggerPayment() {
            fetch('<c:url value="/api/payments/create-order?amount=500&currency=INR" />', {
                method: 'POST'
            })
            .then(response => response.json())
            .then(order => {
                const options = {
                    key: "rzp_test_xRHYO2piLxfOuj", // Replace with your Razorpay key
                    amount: order.amount,
                    currency: order.currency,
                    name: "TheraConnect",
                    description: "Consultation Payment",
                    order_id: order.id,
                    handler: function (response) {
                        //alert("Payment Successful! Payment ID: " + response.razorpay_payment_id);
                        // Optional: Post this info to backend for storing transaction
						fetch('<c:url value="/appointments/pay" />', {
						                            method: 'POST',
						                            headers: {
						                                'Content-Type': 'application/x-www-form-urlencoded'
						                            },
						                            body: new URLSearchParams({
						                                doctorId: '${appointment.doctor.userId}',
						                                patientId: '${appointment.patient.userId}',
						                                slotId: '${slotId}'
						                            })
						                        })
						                        .then(() => {
						                            alert("Payment confirmed and appointment booked!");
						                            window.location.href = '<c:url value="/patient-dashboard?paymentSuccess=true" />';
						                        })
						                        .catch(err => console.error("Error posting payment details:", err));
                    },
                    theme: {
                        color: "#3399cc"
                    }
                };
                const rzp = new Razorpay(options);
                rzp.open();
            })
            .catch(err => console.error("Payment error: ", err));
        }
    </script>
 
    <!-- Replace form with direct JS payment trigger -->
    <button onclick="triggerPayment()">Pay ₹500</button>
</div>
</body>
</html>