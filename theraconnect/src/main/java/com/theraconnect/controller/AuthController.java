package com.theraconnect.controller;
 
import com.theraconnect.enums.Role;
import com.theraconnect.model.User;
import com.theraconnect.repository.AppointmentRepository;
import com.theraconnect.repository.UserRepository;
import com.theraconnect.service.EmailSenderService;
import com.theraconnect.service.UserService;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Optional;
 
@Controller
public class AuthController {
 
    @Autowired
    private UserRepository userRepository;
 
    @Autowired
    private AppointmentRepository appointmentRepository;
    
    @Autowired
    private EmailSenderService senderService;
    
    @Autowired
    private UserService userService;
 
    // 🔐 Registration Page
    @GetMapping("/register")
    public String showRegisterPage() {
        return "register";
    }
 
    // 🔓 Login Page
    @GetMapping("/login")
    public String showLoginPage() {
        return "login";
    }
 
//    // ✅ Login Submission Handler
//    @PostMapping("/login")
//    public String login(@RequestParam String username,
//                        @RequestParam String password,
//                        HttpSession session) {
// 
//        String cleanEmail = username.trim().toLowerCase();
//        String cleanPassword = password.trim();
// 
//        // 🔐 Hardcoded Admin Credentials (no signup required)
//        if ("admin@theraconnect.com".equalsIgnoreCase(cleanEmail) &&
//            "S3cuReAdm!nP@ss2024".equals(cleanPassword)) {
// 
//            session.setAttribute("fullName", "System Admin");
//            session.setAttribute("role", "ADMIN");
//            return "redirect:/admin-dashboard";
//        }
// 
//        // 🔍 Standard User Login (Patient or Doctor)
//        Optional<User> optionalUser = userRepository.findByEmail(cleanEmail);
// 
//        if (optionalUser.isPresent()) {
//            User user = optionalUser.get();
//            
//            //new session change 
//            session.setAttribute("loggedUser", user); // store in session
//
// 
//            if (user.getPassword().trim().equals(cleanPassword)) {
//                session.setAttribute("fullName", user.getName());
//                session.setAttribute("role", user.getRole().toString());
// 
//                switch (user.getRole()) {
//                    case PATIENT:
//   
//                    	  session.setAttribute("patientProfileId", user.getPatientProfile().getPatientProfileId());
//                          session.setAttribute("fullName", user.getName());
//                        return "redirect:/patient-dashboard";
//                    case DOCTOR:
//
//session.setAttribute("doctorProfileId", user.getDoctorProfile().getDoctorProfileId());
//
//                        return "redirect:/doctor-dashboard";
//                }
//            }
//        }
// 
//        // 🚫 Failed Login
//        return "redirect:/login?error=true";
//    }
    
    //27july
    @PostMapping("/login")
    public String login(@RequestParam String username,
                        @RequestParam String password,
                        HttpSession session) {
    	
    	
    	//session.setMaxInactiveInterval(10); // 10 seconds
    	
    	
    	//28july session login

//if (session.getAttribute("loggedUser") != null || 
//        session.getAttribute("adminFullName") != null) {
//        return "redirect:/"; // Already logged in, redirect to appropriate dashboard
//    }


        String cleanEmail = username.trim().toLowerCase();
        String cleanPassword = password.trim();

        // 🔐 Admin Login
        if ("admin@theraconnect.com".equalsIgnoreCase(cleanEmail) &&
            "S3cuReAdm!nP@ss2024".equals(cleanPassword)) {

            session.setAttribute("adminFullName", "System Admin");
            session.setAttribute("role", "ADMIN");
            return "redirect:/admin/dashboard";
        }

        // 🔍 Standard User Login
        Optional<User> optionalUser = userRepository.findByEmail(cleanEmail);

        if (optionalUser.isPresent()) {
            User user = optionalUser.get();

            if (user.getPassword().trim().equals(cleanPassword)) {
                session.setAttribute("loggedUser", user); // generic reference

                switch (user.getRole()) {
                    case PATIENT:
                        session.setAttribute("fullName", user.getName());

                        session.setAttribute("patientEmail", user.getEmail());

                        session.setAttribute("patientRole", "PATIENT");
                        session.setAttribute("patientProfileId", user.getPatientProfile().getPatientProfileId());
                        return "redirect:/patient-dashboard";

                    case DOCTOR:
                        session.setAttribute("doctorFullName", user.getName());
                        session.setAttribute("doctorRole", "DOCTOR");
                        session.setAttribute("doctorProfileId", user.getDoctorProfile().getDoctorProfileId());
                        return "redirect:/doctor-dashboard";
                }
            }
        }

        // ❌ Invalid login
        return "redirect:/login?error=true";
    }

    
 
    // 🩺 Patient Dashboard Route
    @GetMapping("/patient-dashboard")
    public String showPatientDashboard(HttpSession session) {
    	 User user = (User) session.getAttribute("loggedUser");
    	if (user == null || user.getRole() != Role.PATIENT) {
    		 return "redirect:/login";
    		 
    	 }
        return "patient-dashboard";
    }
 
    // 👨‍⚕️ Doctor Dashboard Route
    @GetMapping("/doctor-dashboard")
    public String showDoctorDashboard(HttpSession session) {
    	
    	//28july
    	
    	User user = (User) session.getAttribute("loggedUser");
   	 if (user == null || user.getRole() != Role.DOCTOR) {
   		 return "redirect:/login";
   		
   	 }
    	//
        return "doctor-dashboard";
    }
 
    // 🛡️ Admin Dashboard Route
    @GetMapping("/admin-dashboard")
    public String showAdminDashboard(Model model, HttpSession session) {
    	
//28july
		String adminName = (String) session.getAttribute("adminFullName");
		if (adminName == null) {
			return "redirect:/login"; // Not logged in, redirect to login
		}
//
        long userCount = userRepository.count();
        long therapistCount = userRepository.countByRole(Role.DOCTOR);
        long appointmentCount = appointmentRepository.count();
 
        model.addAttribute("userCount", userCount);
        model.addAttribute("therapistCount", therapistCount);
        model.addAttribute("appointmentCount", appointmentCount);
 
        //String adminName = (String) session.getAttribute("fullName");
        model.addAttribute("adminName", adminName != null ? adminName : "Admin");
 
        return "admin-dashboard";
    }
    
    
    
    
    //24july
    @GetMapping("/email-verification")
    public String showEmailVerificationForm() {
        return "email-verification"; // this matches the JSP file name
    }
    
    @GetMapping("/forgot-password")
    public String showForgotPasswordForm() {
        return "email-verification"; // name of your JSP file
    }
 
    
    @PostMapping("/forgot-password")
    public String sendOtp(@RequestParam String email, HttpSession session) {
//    	User user = userService.getUserByEmail(email)
//                .orElseThrow(() -> new RuntimeException("User not found"));
 
//        if (user == null) {
//            return "redirect:/forgot-password?error=userNotFound";
//        }
    	User user = userService.getUserByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
 
        
        
        
        int otpInt = 100000 + new java.util.Random().nextInt(900000); // generates 6-digit OTP
        String otp = String.valueOf(otpInt);
 
        //String otp = otpGenerator.generate(); // e.g., 6-digit random code
        session.setAttribute("resetOtp", otp);
        session.setAttribute("resetOtpExpiry", LocalDateTime.now().plusSeconds(60));
        session.setAttribute("resetEmail", email);
 
        senderService.sendSimpleEmail(email, "TheraConnect Password Reset OTP", "Your OTP is: " + otp);
 
        return "verify-otp";
    }
 
    	
    @PostMapping("/verify-otp")
    public String verifyOtp(@RequestParam String otp, HttpSession session) {
        String sessionOtp = (String) session.getAttribute("resetOtp");
        LocalDateTime expiry = (LocalDateTime) session.getAttribute("resetOtpExpiry");
 
        if (sessionOtp != null && expiry != null &&
            otp.equals(sessionOtp) && LocalDateTime.now().isBefore(expiry)) {
            return "reset-password";
        }
 
        return "redirect:/verify-otp?error=invalidOrExpired";
    }
 
    @PostMapping("/resend-otp")
    public String resendOtp(HttpSession session) {
        String email = (String) session.getAttribute("resetEmail");
        if (email == null) {
            return "redirect:/email-verification?error=sessionExpired";
        }
 
        int otpInt = 100000 + new java.security.SecureRandom().nextInt(900000);
        String newOtp = String.valueOf(otpInt);
 
        session.setAttribute("resetOtp", newOtp);
        session.setAttribute("resetOtpExpiry", LocalDateTime.now().plusSeconds(60));
 
        senderService.sendSimpleEmail(email, "TheraConnect OTP", "Your new OTP is: " + newOtp);
 
        return "redirect:/verify-otp";
    }
 
 
    @PostMapping("/reset-password")
    public String resetPassword(@RequestParam String newPassword,
                                @RequestParam String confirmPassword,
                                HttpSession session) {
        if (!newPassword.equals(confirmPassword)) {
            return "redirect:/reset-password?error=mismatch";
        }
 
        String email = (String) session.getAttribute("resetEmail");
//        User user = userService.getUserByEmail(email);
        User user = userService.getUserByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));	
 
        user.setPassword(newPassword); // ⚠️ Not hashed
        userService.save(user);
 
        session.removeAttribute("resetOtp");
        session.removeAttribute("resetEmail");
        session.removeAttribute("resetOtpExpiry");
 
        return "redirect:/login?resetSuccess=true";
    }
    
 
    
    //about us 
    @GetMapping("/about")
    public String showAboutPage() {
        return "about-us"; // this loads about-us.jsp
    }
    
    @GetMapping("/findTherapists")
    public String showTherapistList() {
        return "findTherapists";
    }
 
}