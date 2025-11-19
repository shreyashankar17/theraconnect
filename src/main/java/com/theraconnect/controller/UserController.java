package com.theraconnect.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.theraconnect.enums.Role;
import com.theraconnect.model.DoctorProfile;
import com.theraconnect.model.PatientProfile;
import com.theraconnect.model.User;
import com.theraconnect.repository.DoctorProfileRepository;
import com.theraconnect.repository.PatientProfileRepository;
import com.theraconnect.repository.UserRepository;
import com.theraconnect.service.UserService;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/api/users")
public class UserController {

	@Autowired
    private UserService userService;
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private PatientProfileRepository patientProfileRepo;
	
	@Autowired
	private DoctorProfileRepository doctorProfileRepo;
	


    @GetMapping
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/{id}")
    public User getUserById(@PathVariable Long id) {
        Optional<User> user = userService.getUserById(id);
        return user.orElse(null);
    }

    @GetMapping("/email")
    public User getUserByEmail(@RequestParam String email) {
        Optional<User> user = userService.getUserByEmail(email);
        return user.orElse(null);
    }
    

//    @PostMapping
//    public User createUser(@RequestBody User user) {
//        return userService.createUser(user);
//    }
//    
    

    @PostMapping("/registration")
    public String registerUser(@ModelAttribute User user,Model model) {
    	if (userRepository.findFirstByEmail(user.getEmail()).isPresent()) {
              	
//       	 model.addAttribute("emailExists", true);
//           model.addAttribute("error", "An account with this email already exists.");
           
//new
			model.addAttribute("emailExists", true);
			model.addAttribute("popupMessage", "Email already exists. Please use a different one.");

           
           return "register"; // 👈 redisplay the form with error
       }
        User savedUser = userService.createUser(user);
        
        //Insert Empty Profile Based On the role 
        if(user.getRole() == Role.PATIENT) {
        	PatientProfile profile = new PatientProfile();
        	profile.setUser(savedUser);
        	patientProfileRepo.save(profile);
        }else if(user.getRole()==Role.DOCTOR) {
        	DoctorProfile profile = new DoctorProfile();
        	profile.setUser(savedUser);
        	doctorProfileRepo.save(profile);
        }
        return "home"; // or wherever you want to land after registration
    }


    @DeleteMapping("/{id}")
    public String deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return "User deleted";
    }

}
