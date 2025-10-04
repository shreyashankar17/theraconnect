<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Manage Users | TheraConnect Admin</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/styles.css">
    <style>
		
		body {
		    font-family: 'Segoe UI', sans-serif;
		    background-color: #f3f6fc;
		    margin: 0;
		    padding: 0;
		}
		
		
		.admin-main {
		    margin-left: 220px;
		    padding: 40px;
		    background: linear-gradient(135deg, #eef2fa, #dde7f3);
		    min-height: 100vh;
		}
 
		/*------ Filter + Search Bar ------*/
		.filter-bar {
		    display: flex;
		    justify-content: space-between;
		    align-items: center;
		    gap: 20px;
		    margin: 30px 0;
		}
 
		.filter-bar form {
		    display: flex;
		    align-items: center;
		    gap: 10px;
		}
 
		.filter-bar select,
		.filter-bar input[type="text"] {
		    padding: 10px 14px;
		    border: 1px solid #ccc;
		    border-radius: 6px;
		    font-size: 14px;
		    background-color: #fff;
		}
 
		/*------ User Table Styling ------*/
		.user-table {
		    width: 100%;
		    border-collapse: collapse;
		    background-color: #fff;
		    box-shadow: 0 4px 10px rgba(0,0,0,0.05);
		    border-radius: 8px;
		    overflow: hidden;
		}
 
		.user-table th {
		    background-color: #e3ebf7;
		    color: #333;
		    text-align: left;
		    padding: 14px;
		    font-size: 15px;
		}
 
		.user-table td {
		    padding: 12px 16px;
		    border-bottom: 1px solid #e0e0e0;
		    font-size: 14px;
		    color: #444;
		    word-break: break-word;
		}
 
		.user-table tr:hover {
		    background-color: #f9fcff;
		    transition: background-color 0.2s;
		}
 
		/*------ Action Buttons ------*/
		.action-btn {
		    margin-right: 8px;
		    padding: 6px 12px;
		    font-size: 14px;
		    border-radius: 4px;
		    border: none;
		    cursor: pointer;
		    font-weight: 500;
		    transition: background-color 0.3s ease;
		}
 
		.view-btn {
		    background-color: #1976d2;
		    color: white;
		}
 
		.role-btn {
		    background-color: #ffb300;
		    color: black;
		}
 
		.delete-btn {
		    background-color: #e53935;
		    color: white;
		}
 
		.action-btn:hover {
		    opacity: 0.9;
		}
		
		.admin-main {
		    margin-left: 0;
		    padding: 40px 60px;
		}
		
		.modal-overlay {
		    position: fixed;
		    top: 0; left: 0;
		    width: 100%; height: 100%;
		    background: rgba(0,0,0,0.4);
		    display: flex;
		    align-items: center;
		    justify-content: center;
		    z-index: 999;
		}
 
		.modal-box {
		    background: white;
		    padding: 30px;
		    border-radius: 10px;
		    text-align: center;
		    min-width: 300px;
		    box-shadow: 0 4px 10px rgba(0,0,0,0.3);
		}
 
		.role-cards {
		    display: flex;
		    gap: 15px;
		    margin: 20px 0;
		    justify-content: center;
		}
 
		.role-option {
		    padding: 20px;
		    width: 100px;
		    cursor: pointer;
		    border-radius: 8px;
		    font-weight: bold;
		    color: white;
		}
		
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
 
		.role-option.active { border: 2px solid #000; }
		.role-option.disabled { background-color: #333; cursor: not-allowed; }
 
		#PATIENT:not(.disabled) { background-color: #2196F3; }
		#DOCTOR:not(.disabled) { background-color: #4CAF50; }
		#ADMIN:not(.disabled) { background-color: #FFC107; color: #000; }
 
 
 
    </style>
</head>
<body>
 
<main class="admin-main">
	<div class="topbar-back">
			<a href="${pageContext.request.contextPath}/admin/dashboard" class="back-home-button">← Back to Home</a>	
		</div>
    <h2>Manage Users 🧍🧍</h2>
 
    <!-- 🔎 Filter + Search -->
    <div class="filter-bar">
        <form action="${pageContext.request.contextPath}/admin/users/filter" method="get">
            <label for="role">Filter by Role:</label>
			<select name="role" id="role">
			    <option value="" ${selectedRole == null || selectedRole == '' ? 'selected' : ''}>All</option>
			    <option value="PATIENT" ${selectedRole == 'PATIENT' ? 'selected' : ''}>Patient</option>
			    <option value="DOCTOR" ${selectedRole == 'DOCTOR' ? 'selected' : ''}>Doctor</option>
			    <option value="ADMIN" ${selectedRole == 'ADMIN' ? 'selected' : ''}>Admin</option>
			</select>
 
            <button type="submit" class="action-btn view-btn">Apply</button>
        </form>
 
        <form action="${pageContext.request.contextPath}/admin/users/search" method="get">
			<input type="text" name="keyword" value="${searchKeyword}" placeholder="Search name or email">
            <button type="submit" class="action-btn view-btn">Search</button>
        </form>
    </div>
 
    <!-- 📋 User Table -->
    <table class="user-table">
        <thead>
            <tr>
                <th>#</th>
                <th>Name</th>
                <th>Email</th>
                <th>Role</th>
               
                <th>Actions</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach var="user" items="${users}" varStatus="loop">
                <tr>
                    <td>${loop.index + 1}</td>
                    <td>${user.name}</td>
                    <td>${user.email}</td>
                    <td>${user.role}</td>
                  
                    <td>
                        <a href="${pageContext.request.contextPath}/admin/users/view/${user.userId}">
                            <button class="action-btn view-btn">Profile</button>
                        </a>
 
                        <!--<a href="${pageContext.request.contextPath}/admin/users/role/${user.userId}">-->
							<button class="action-btn role-btn" onclick="openRoleModal('${user.userId}', '${user.role}')">
							    Change Role
							</button>
                        <!--</a>-->
						
						<button onclick="showDeleteModal('${user.userId}')" class="action-btn delete-btn">Delete</button>
 
                        <!--<form action="${pageContext.request.contextPath}/admin/users/delete/${user.userId}" method="post" style="display:inline;" onsubmit="return confirm('Are you sure you want to delete this user?');">
                            <button type="submit" class="action-btn delete-btn">Delete</button>
                        </form>-->
                    </td>
                </tr>
            </c:forEach>
        </tbody>
    </table>
	<div id="roleModal" class="modal-overlay" style="display: none;">
	    <div class="modal-box">
	        <h3>Change Role</h3>
	       <!--<form id="roleForm" method="post">-->
			<form id="roleForm" method="post" action="${pageContext.request.contextPath}/admin/users/role/update">
	            <input type="hidden" name="userId" id="modalUserId" />
 
	            <div class="role-cards">
	                <div class="role-option" id="PATIENT" onclick="selectRole('PATIENT')">Patient</div>
	                <div class="role-option" id="DOCTOR" onclick="selectRole('DOCTOR')">Doctor</div>
	                <div class="role-option" id="ADMIN" onclick="selectRole('ADMIN')">Admin</div>
	            </div>
 
	            <input type="hidden" name="newRole" id="selectedRole" />
	            <button type="submit" class="action-btn view-btn">Confirm</button>
	            <button type="button" class="action-btn delete-btn" onclick="closeRoleModal()">Cancel</button>
	        </form>
	    </div>
	</div>
</main>	
 
 
<div id="deleteModal" class="modal-overlay" style="display: none;">
	  <div class="modal-box">
	    <span onclick="closeDeleteModal()" class="close">&times;</span>
	    <h2>Confirm Delete</h2>
	    <p>Are you sure you want to delete this user? This action cannot be undone.</p>
	    <button onclick="confirmDelete()" class="action-btn delete-btn">Yes, Delete</button>
	    <button onclick="closeDeleteModal()" class="action-btn btn-secondary">Cancel</button>
	  </div>
	</div>
 
<script>
    function openRoleModal(userId, currentRole) {
        document.getElementById("roleModal").style.display = "flex";
        document.getElementById("modalUserId").value = userId;
 
        document.querySelectorAll(".role-option").forEach(box => {
            box.classList.remove("active", "disabled");
            if (box.id === currentRole) {
                box.classList.add("disabled");
            }
        });
 
        document.getElementById("selectedRole").value = "";
    }
 
    function selectRole(role) {
        const target = document.getElementById(role);
        if (target.classList.contains("disabled")) return;
 
        document.querySelectorAll(".role-option").forEach(box => box.classList.remove("active"));
        target.classList.add("active");
 
        document.getElementById("selectedRole").value = role;
    }
 
    function closeRoleModal() {
        document.getElementById("roleModal").style.display = "none";
    }
	
	let selectedUserId;
 
	function showDeleteModal(userId) {
	    selectedUserId = userId;
	    document.getElementById('deleteModal').style.display = 'block';
	}
 
	function closeDeleteModal() {
	    document.getElementById('deleteModal').style.display = 'none';
	    selectedUserId = null;
	}
 
	function confirmDelete() {
	    const form = document.createElement('form');
	    form.method = 'POST';
	    form.action = '/admin/users/delete/' + selectedUserId;
 
	    document.body.appendChild(form);
	    form.submit();
	}
	
</script>
 
</body>
</html>