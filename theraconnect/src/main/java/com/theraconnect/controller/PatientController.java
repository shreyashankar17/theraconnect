package com.theraconnect.controller;
 
import java.sql.Date;

import java.time.Instant;

import java.time.LocalDate;

import java.time.ZoneId;

import java.util.Optional;
 
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;

import org.springframework.ui.Model;

import org.springframework.web.bind.annotation.*;

import org.springframework.web.servlet.mvc.support.RedirectAttributes;
 
import com.theraconnect.model.PatientProfile;

import com.theraconnect.model.User;

import com.theraconnect.repository.PatientProfileRepository;

import com.theraconnect.repository.UserRepository;

import com.theraconnect.service.PatientService;
 
import jakarta.servlet.http.HttpSession;
 
//@RestController

@Controller

@RequestMapping("/api/patients")

public class PatientController {
 
    @Autowired

    private PatientService patientService;//changed 25 july

    @Autowired

    private UserRepository userRepository;

    //Injectiong patientRepository

    @Autowired

    private PatientProfileRepository patientProfileRepository;
 
    @GetMapping("/{id}")

    public PatientProfile getPatient(@PathVariable Long id) {

        return patientService.getProfileById(id);

    }
 
    @PutMapping("/{id}")

    public PatientProfile updatePatient(@PathVariable Long id, @RequestBody PatientProfile profile) {

        return patientService.updateProfile(id, profile);

    }

    /////22 july-----------------------------

//    @GetMapping("/myprofile")
//
//    public String showMyProfile(HttpSession session, Model model) {
//
//        String fullName = (String) session.getAttribute("fullName");
// 
//        Optional<User> optionalUser = userRepository.findByName(fullName);
//
//        if (optionalUser.isPresent()) {
//
//            User user = optionalUser.get();
//           
//            model.addAttribute("user_id", user.getUserId());
// 
//            model.addAttribute("name", user.getName());
//            
//
//            model.addAttribute("emailid", user.getEmail());
// 
//            PatientProfile profile = user.getPatientProfile();
//
//            if (profile != null) {
//
//                model.addAttribute("mobileno", profile.getContact());
//
//                model.addAttribute("gender", profile.getGender());
//
//                model.addAttribute("dob", profile.getDob());
//
//            }
//
//        }
// 
//        return "myprofile"; // Loads myprofile.jsp view
//
//    }
// 
//      
    @GetMapping("/myprofile")
    public String showMyProfile(HttpSession session, Model model) {
        String email = (String) session.getAttribute("patientEmail"); // Use a role-specific session key

        if (email == null) {
            return "redirect:/login"; // Session expired or not logged in
        }

        Optional<User> optionalUser = userRepository.findByEmail(email);

        if (optionalUser.isPresent()) {
            User user = optionalUser.get();

            model.addAttribute("user_id", user.getUserId());
            model.addAttribute("name", user.getName());
            model.addAttribute("emailid", user.getEmail());

            PatientProfile profile = user.getPatientProfile();
            if (profile != null) {
                model.addAttribute("mobileno", profile.getContact());
                model.addAttribute("gender", profile.getGender());
                model.addAttribute("dob", profile.getDob());
            }

            return "myprofile"; // JSP name
        }

        return "redirect:/login?error=notfound";
    }


    @GetMapping("/updateprofile")

    public String showProfile()

    	{                                    //18 july serves profile.jsp

    		return "updateprofile";

    	}

// ------------------------------ 

//    @GetMapping("/profile")

//    public String showProfile(Model model) {

//        // Fetch user data from DB or session

//        model.addAttribute("name", "sam");

//        model.addAttribute("email", "123@gmil111");

//        model.addAttribute("role", "PATIENT");

//

//        return "profile"; // Loads profile.jsp

//    }

// 




//    @PostMapping("/updateProfile")

//    public String updateProfile(@RequestParam String name,

//                                @RequestParam String email,

//                                @RequestParam String role,

//                                RedirectAttributes redirectAttributes) {

//

//        // ✅ Find user by email (or session ID if using login)

//        Optional<User> optionalUser = userRepository.findByEmail(email);

//        User user = optionalUser.get();

//        if (user != null) {                                     //18 july

//            user.setName(name);

//           // user.setRole(role);

//            userRepository.save(user); // ✅ Save updated user

//        }

//        

//        redirectAttributes.addFlashAttribute("name", name);

//        redirectAttributes.addFlashAttribute("role", role);

//

//        return "redirect:/patient-dashboard";

//    }


   /////////////////////////////////////////////////////////////////

    @PostMapping("/updateProfile")

    public String updateProfile(@RequestParam String name,

                                @RequestParam String email,

                                @RequestParam String contact,

                                @RequestParam String dob,

                                @RequestParam String gender,

                                RedirectAttributes redirectAttributes) {
 
        Optional<User> optionalUser = userRepository.findByEmail(email);

        if (optionalUser.isPresent()) {

            User user = optionalUser.get();

            user.setName(name);
 
            PatientProfile profile = user.getPatientProfile();

            if (profile == null) {

                profile = new PatientProfile();

               // profile.setPatientProfileId(user.getUserId()); // Link by ID

                profile.setUser(user); // Required for @MapsId

                user.setPatientProfile(profile);

            }
 
            profile.setContact(contact);                    //18 july changed

            profile.setGender(gender);
 
            try {

                LocalDate localDate = LocalDate.parse(dob); // Format: yyyy-MM-dd

                Instant instant = localDate.atStartOfDay(ZoneId.systemDefault()).toInstant();

                profile.setDob(Date.from(instant));

            } catch (Exception e) {

                System.out.println("Invalid DOB format: " + dob);

            }
 
            // ✅ Link both sides

            profile.setUser(user);

            user.setPatientProfile(profile);
 
            // ✅ Save both

           // userRepository.save(user);

            patientProfileRepository.save(profile);

        }
 
        return "redirect:/patient-dashboard";

    }
    
    
    //logout controller
    @GetMapping("/logout")
    public String logout(HttpSession session) {
       session.invalidate();


       

        return "redirect:/login";
    }
 
    



}

 