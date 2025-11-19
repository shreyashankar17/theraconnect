//package com.theraconnect.controller;
// 
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.*;
//import org.springframework.web.servlet.mvc.support.RedirectAttributes;
// 
//import com.theraconnect.model.DoctorProfile;
//import com.theraconnect.model.User;
//import com.theraconnect.service.DoctorService;
// 
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpSession;
// 
//import java.util.List;
//import java.util.Optional;
//import com.theraconnect.repository.UserRepository;
//import com.theraconnect.repository.DoctorProfileRepository;
// 
//@Controller
//@RequestMapping("/api/doctors")
//public class DoctorController {
// 
//    @Autowired
//    private DoctorService doctorService;
//    //23 july--
//    @Autowired
//    private UserRepository userRepository;
//    @Autowired
//    private DoctorProfileRepository  doctorProfileRepository;
//    
//  
//    //-----
//    
// 
////    @GetMapping
////    public List<DoctorProfile> getAllDoctors() {
////        return doctorService.getAllDoctors();
////    }
// 
//    @GetMapping("/{id}")
//    public DoctorProfile getDoctor(@PathVariable Long id) {
//        return doctorService.getDoctorById(id);
//    }
// 
//    @GetMapping("/list")
//    public String showDoctorList(Model model) {
//        List<DoctorProfile> doctors = doctorService.getAllDoctors();
//        model.addAttribute("doctors", doctors);
//        return "doctor-list"; // This should be a Thymeleaf template
//    }
//    
//    
//    
//    
//    
//    //------------23 july
//    
//    //get mapping doctor
//    @GetMapping("/updateprofiledoctor")
//    public String showDoctorProfileUpdatePage() {
//        return "updateprofiledoctor"; // Loads updateprofiledoctor.jsp
//    }
//    
//    //post mapping
//    @PostMapping("/updateProfile")
//    public String updateDoctorProfile(
//            @RequestParam String experience,
//            @RequestParam String specialization,
//            @RequestParam String bio,
//            HttpSession session,
//            RedirectAttributes redirectAttributes) {
// 
//        String fullName = (String) session.getAttribute("fullName"); // Or use email if preferred
// 
//        Optional<User> optionalUser = userRepository.findByName(fullName); // Can switch to findByEmail()
//        if (optionalUser.isPresent()) {
//            User user = optionalUser.get();
// 
//            DoctorProfile profile = user.getDoctorProfile();
//            if (profile == null) {
//                profile = new DoctorProfile();
//                profile.setUser(user);
//                user.setDoctorProfile(profile);
//            }
// 
//            profile.setExperience(experience);
//            profile.setSpecialization(specialization);
//            profile.setBio(bio);
// 
//            // Save the profile
//            doctorProfileRepository.save(profile);
// 
//            redirectAttributes.addFlashAttribute("success", true);
//        }
// 
//        return "redirect:/doctor-dashboard";
//    }
// 
//    
//    // myprofile section
//    @GetMapping("/myprofiledoctor")
//    public String showMyProfileDoctor(HttpSession session,Model model)
//    {
//    	String fullname=(String)session.getAttribute("fullName");
//    	Optional<User> optionalUser=userRepository.findByName(fullname);
//    	if(optionalUser.isPresent())
//    	{
//    		User user=optionalUser.get();
//    		model.addAttribute("name",user.getName());
//    	}
//    	User user=optionalUser.get();
//    	 DoctorProfile profile = user.getDoctorProfile();
//    	 if(profile !=null)
//    	 {
//    		 model.addAttribute("experience",profile.getExperience());
//    		 model.addAttribute("specialization",profile.getSpecialization());
//    		 model.addAttribute("bio",profile.getBio());
//    	 }
//    	
//    	
//    	return "myprofiledoctor";
//    	
//    }
//    
// 
//    
// 
//    
//    	
//    	
//    	
//    
//    
//    
//    
//    //--------------
//    
////    //Logout of Doctor profile
////    @GetMapping("/logout")
////    public String logout(HttpSession session) {
////        session.invalidate();
////        return "redirect:/login";
////    }
//    
//
// 
//    
//}
package com.theraconnect.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.theraconnect.model.DoctorProfile;
import com.theraconnect.model.User;
import com.theraconnect.service.DoctorService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import java.util.List;
import java.util.Optional;
import com.theraconnect.repository.UserRepository;
import com.theraconnect.repository.DoctorProfileRepository;
@Controller
@RequestMapping("/api/doctors")
public class DoctorController {
    @Autowired
    private DoctorService doctorService;
    //23 july--
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private DoctorProfileRepository  doctorProfileRepository;

    //-----

//    @GetMapping
//    public List<DoctorProfile> getAllDoctors() {
//        return doctorService.getAllDoctors();
//    }
    @GetMapping("/{id}")
    public DoctorProfile getDoctor(@PathVariable Long id) {
        return doctorService.getDoctorById(id);
    }
    @GetMapping("/list")
    public String showDoctorList(Model model) {
        List<DoctorProfile> doctors = doctorService.getAllDoctors();
        model.addAttribute("doctors", doctors);
        return "doctor-list"; // This jsp page
    }


    //------------23 july
    //get mapping doctor
    @GetMapping("/updateprofiledoctor")
    public String showDoctorProfileUpdatePage() {
        return "updateprofiledoctor"; // Loads updateprofiledoctor.jsp
    }
    //post mapping
    @PostMapping("/updateProfile")
    public String updateDoctorProfile(
            @RequestParam String experience,
            @RequestParam String specialization,
            @RequestParam String bio,
            HttpSession session,
            RedirectAttributes redirectAttributes) {
        String fullName = (String) session.getAttribute("doctorFullName"); // Or use email if preferred
        Optional<User> optionalUser = userRepository.findByName(fullName); // Can switch to findByEmail()
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            DoctorProfile profile = user.getDoctorProfile();
            if (profile == null) {
                profile = new DoctorProfile();
                profile.setUser(user);
                user.setDoctorProfile(profile);
            }
            profile.setExperience(experience);
            profile.setSpecialization(specialization);
            profile.setBio(bio);
            // Save the profile
            doctorProfileRepository.save(profile);
            redirectAttributes.addFlashAttribute("success", true);
        }
        return "redirect:/doctor-dashboard";
    }

    // myprofile section
    @GetMapping("/myprofiledoctor")
    public String showMyProfileDoctor(HttpSession session,Model model)
    {
    	String fullname=(String)session.getAttribute("doctorFullName");
    	Optional<User> optionalUser=userRepository.findByName(fullname);
    	if(optionalUser.isPresent())
    	{
    		User user=optionalUser.get();
    		model.addAttribute("name",user.getName());
    	}
    	User user=optionalUser.get();
    	 DoctorProfile profile = user.getDoctorProfile();
    	 if(profile !=null)
    	 {
    		 model.addAttribute("experience",profile.getExperience());
    		 model.addAttribute("specialization",profile.getSpecialization());
    		 model.addAttribute("bio",profile.getBio());
    	 }

    	return "myprofiledoctor";
    }




    //--------------

	@GetMapping("/logout")
	public String logoutDoctor(HttpSession session) {
		session.invalidate(); // or your logout logic
   

		return "redirect:/login"; // or wherever you want to redirect
	}
	
	


 

}
 