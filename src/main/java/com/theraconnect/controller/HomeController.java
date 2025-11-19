package com.theraconnect.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import jakarta.servlet.http.HttpSession;

@Controller
public class HomeController {
	@GetMapping("/")
    public String showHomePage() {
        return "home";
    }
	//about us
	
	
//	//28 july-----uncomemnt below code and cmnt above
//	@GetMapping("/")
//	public String home(HttpSession session) {
//	    if (session.getAttribute("adminFullName") != null) {
//	        return "redirect:/admin/dashboard";
//	    } else if (session.getAttribute("patientRole") != null) {
//	        return "redirect:/patient-dashboard";
//	    } else if (session.getAttribute("doctorRole") != null) {
//	        return "redirect:/doctor-dashboard";
//	    }
//	    return "redirect:/login";
//	}


}
