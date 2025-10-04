package com.theraconnect.controller;
 
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;
 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.theraconnect.enums.Role;
import com.theraconnect.model.Appointment;
import com.theraconnect.model.Payment;
import com.theraconnect.model.User;
import com.theraconnect.repository.UserRepository;
import com.theraconnect.service.AdminService;
import com.theraconnect.service.AppointmentService;
import com.theraconnect.service.PaymentService;
import com.theraconnect.service.UserService;
 
import jakarta.servlet.http.HttpSession;
@Controller
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    private AdminService dashboardService;
    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepo;
    @Autowired
    private AppointmentService appointmentService;
    @Autowired
    private PaymentService paymentService;
    @GetMapping("/dashboard")
    public String dashboard(HttpSession session, Model model) {
    	//29july
    	String adminName = (String) session.getAttribute("adminFullName");
		if (adminName == null) {
			return "redirect:/login"; // Not logged in, redirect to login
		}
		
    	long totalUsers = userService.getAllUsers().size();
        long totalDoctors = userRepo.countByRole(Role.DOCTOR);
        long totalAdmins = userRepo.countByRole(Role.ADMIN);
        long totalPatients = userRepo.countByRole(Role.PATIENT);
        model.addAttribute("fullName", session.getAttribute("fullName"));
        model.addAttribute("totalUsers", dashboardService.getTotalUsers());
        model.addAttribute("totalDoctors", dashboardService.getTotalDoctors());
        model.addAttribute("totalAppointments", dashboardService.getTotalAppointments());
        model.addAttribute("totalRevenue", dashboardService.getTotalRevenue());
        return "admin-dashboard";
    }
    @GetMapping("/users")
    public String showAllUsers(Model model) {
        List<User> users = userService.getAllUsers(); // Filtered if needed
        model.addAttribute("users", users);
        return "manage-users";
    }
    @GetMapping("/users/filter")
    public String filterByRole(@RequestParam String role, Model model) {
        List<User> filtered;
 
        if (role == null || role.trim().isEmpty()) {
            // "All" selected – fetch everything
            filtered = userService.getAllUsers();
        } else {
            try {
                filtered = userService.getUsersByRole(role);
            } catch (IllegalArgumentException e) {
                model.addAttribute("error", "Invalid role: " + role);
                filtered = userService.getAllUsers(); // fallback
            }
        }
        model.addAttribute("users", filtered);
        model.addAttribute("selectedRole", role);
 
        return "manage-users";
    }
    @GetMapping("/users/search")
    public String searchUsers(@RequestParam String keyword, Model model) {
        List<User> found = userService.searchByNameOrEmail(keyword);
        model.addAttribute("users", found);
        model.addAttribute("searchKeyword", keyword);
        return "manage-users";
    }
 
 
    @GetMapping("/users/view/{userId}")
    public String viewUserProfile(@PathVariable Long userId, Model model) {
        Optional<User> optionalUser = userService.getUserById(userId);
 
        if (optionalUser.isPresent()) {
            model.addAttribute("user", optionalUser.get());
            return "admin-view-profile";
        } else {
            model.addAttribute("error", "User not found.");
            return "redirect:/admin/users";
        }
    }
    @PostMapping("/users/role/update")
    public String changeRole(@RequestParam Long userId, @RequestParam String newRole) {
        Optional<User> optional = userService.getUserById(userId);
        if (optional.isPresent()) {
            User user = optional.get();
            Role roleEnum = Role.valueOf(newRole.toUpperCase());
            user.setRole(roleEnum);
            userService.save(user);
        }
        return "redirect:/admin/users";
    }
 
    @PostMapping("/users/delete/{userId}")
    public String deleteUser(@PathVariable Long userId) {
        try {
            userService.deleteUser(userId);
        } catch (Exception e) {
            // Optional: add model attribute for error messaging
            System.out.println("Failed to delete user: " + e.getMessage());
        }
        return "redirect:/admin/users";
    }
//////////////////////////////////////////////////////////////////

 
    @GetMapping("/appointments")
    public String getAppointmentDashboard(
    	@RequestParam(required = false) String nameSearch,
        @RequestParam(required = false) String dateSearch,
        @RequestParam(required = false) String statusFilter,
        @RequestParam(required = false) String sortOrder,
        Model model) {
 
        // 🟦 Stats: Today, Week, Month, Next 24h
        model.addAttribute("stats", appointmentService.getAppointmentStats());
 
        // 📊 Status breakdown
        model.addAttribute("statusCounts", appointmentService.getStatusBreakdown());
 
        // 🔍 Filtered + Sorted appointments
        List<Appointment> appointments = appointmentService.getFilteredAppointments(
        	    nameSearch, dateSearch, statusFilter, sortOrder);
 
 
        model.addAttribute("appointments", appointments);
 
        return "admin-appointments"; // maps to your JSP page
    }
 
    @GetMapping("/payments")
    public String viewPayments(
            @RequestParam(required = false) String nameSearch,
            @RequestParam(required = false) Long paymentIdSearch,
            @RequestParam(required = false) String statusFilter,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateSearch,
            Model model) {
 
        // 🎯 Fetch filtered payments — use entity or map if no DTO
        //List<Payment> payments = paymentService.searchPayments
    	List<Map<String, Object>> payments = paymentService.searchPayments(
                nameSearch,
                paymentIdSearch,
                statusFilter,
                dateSearch
        );
 
        // 💰 Revenue stats returned as Map<String, BigDecimal>
        Map<String, BigDecimal> revenueStats = paymentService.getRevenueStats(dateSearch);
 
        // 📊 Payment ratios as Map<String, Double>
       // Map<String, Double> paymentRatio = paymentService.getPaymentRatio();
        
        String statusStatsJson = "";
        try {
            statusStatsJson = new ObjectMapper().writeValueAsString(paymentService.getPaymentStatusCounts());
        } catch (JsonProcessingException e) {
            e.printStackTrace(); // or log properly
        }
        
        Map<String, Long> revenueChartData = paymentService.getRevenueLast7DaysChartData();
        String revenueChartJson = "";
        try {
            revenueChartJson = new ObjectMapper().writeValueAsString(revenueChartData);
        } catch (JsonProcessingException e) {
            e.printStackTrace(); // Or log it properly
        }
 
        model.addAttribute("revenue7DaysJson", revenueChartJson);
 
        
        model.addAttribute("statusStatsJson", statusStatsJson);
        model.addAttribute("payments", payments);
        model.addAttribute("revenueStats", revenueStats); // access like ${revenueStats.today}
        model.addAttribute("statusStats", paymentService.getPaymentStatusCounts());
        model.addAttribute("revenue7DaysJson",revenueChartJson);
 
        return "admin-payments";
    }
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();



    
        return "redirect:/login";
    }
 

}