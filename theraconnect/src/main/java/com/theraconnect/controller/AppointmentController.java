

package com.theraconnect.controller;
 
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.theraconnect.TheraconnectApplication;
import com.theraconnect.dto.AvailabilityCalendarDTO;
import com.theraconnect.enums.AppointmentStatus;
import com.theraconnect.enums.PaymentStatus;
import com.theraconnect.model.Appointment;
import com.theraconnect.model.DoctorSlotAvailability;
import com.theraconnect.model.PatientProfile;
import com.theraconnect.model.Payment;
import com.theraconnect.model.User;
import com.theraconnect.repository.AppointmentRepository;
import com.theraconnect.service.AppointmentService;
import com.theraconnect.service.DoctorSlotAvailabilityService;
import com.theraconnect.service.EmailSenderService;
import com.theraconnect.service.PatientService;
import com.theraconnect.service.PaymentService;
import com.theraconnect.service.UserService;
 
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
 
@Controller
@RequestMapping("/appointments")
public class AppointmentController {
 
    private final TheraconnectApplication theraconnectApplication;
 
	@Autowired
	private AppointmentService appointmentService;
 
	@Autowired
	private DoctorSlotAvailabilityService availabilityService;
 
	@Autowired
	private UserService userService;
	@Autowired
	AppointmentRepository appointmentRepository;

	@Autowired
	private PatientService patientService;
 
	@Autowired
	private PaymentService paymentService;
	@Autowired
	private EmailSenderService senderService;
 
    AppointmentController(TheraconnectApplication theraconnectApplication) {
        this.theraconnectApplication = theraconnectApplication;
    }
 
	@PostMapping
	public Appointment book(@RequestBody Appointment appointment) {
		return appointmentService.bookAppointment(appointment);
	}
 
	@GetMapping("/{id}")
	public Appointment get(@PathVariable Long id) {
		return appointmentService.getById(id);
	}
 
	@GetMapping("/doctor/{doctorId}")
	public List<Appointment> getByDoctor(@PathVariable Long doctorId) {
		return appointmentService.getByDoctor(doctorId);
	}
 
	@GetMapping("/patient/{patientId}")
	public List<Appointment> getByPatient(@PathVariable Long patientId) {
		return appointmentService.getByPatient(patientId);
	}
 //updates
	@GetMapping("/book")
	public String showBookingPage(@RequestParam Long doctorId, Model model) {
	    User doctor = userService.getUserById(doctorId)
	        .orElseThrow(() -> new RuntimeException("Doctor not found"));
 
	    List<AvailabilityCalendarDTO> slots = availabilityService.getCalendarSlots(doctorId);	
	    
	    
 
	    model.addAttribute("doctor", doctor);
	    model.addAttribute("slots", slots);
 
	    return "book-appointment-calendar";
	}
	
	
	
	
	
	
//	@GetMapping("/book")
//	public String showBookingPage(@RequestParam Long doctorId, Model model, HttpSession session) {
//	    User doctor = userService.getUserById(doctorId)
//	        .orElseThrow(() -> new RuntimeException("Doctor not found"));
//
//	    List<AvailabilityCalendarDTO> slots = availabilityService.getCalendarSlots(doctorId);
//
//	    // Fetch patient ID from session
//	    Long patientProfileId = (Long) session.getAttribute("patientProfileId");
//	    PatientProfile patient = patientService. getProfileById(patientProfileId); // You need to implement this
//
//	    model.addAttribute("doctor", doctor);
//	    model.addAttribute("patient", patient);
//	    model.addAttribute("slots", slots);
//
//	    return "book-appointment-calendar";
//	}

	
	
	@Autowired
	private JavaMailSender mailSender;
 
	@PostMapping("/confirm")
	public String confirmBooking(
	    @RequestParam Long slotId,
	    @RequestParam Long doctorId,
	    @RequestParam Long patientId,
	    @RequestParam String reason,
	    @RequestParam String date,
	    HttpSession session
	) {
	    // Fetch the slot
	    DoctorSlotAvailability slot = availabilityService.getAvailabilityById(slotId);
	    if (slot.isBooked()) {
	    	//return "redirect:/appointments/slot-booked";
	    	throw new RuntimeException("Slot already booked.");
	    }
 
	    // Book the slot
	    slot.setBooked(true);
	    availabilityService.save(slot);
 
	    // Get doctor & patient
	    User doctor = userService.getUserById(doctorId).orElseThrow();
	    User patient = userService.getUserById(patientId).orElseThrow();

 
	    // Create appointment
	    Appointment appointment = new Appointment();
	    appointment.setDoctor(doctor);
	    appointment.setPatient(patient);
 
        appointment.setReason(reason);
	    appointment.setSlotTime(LocalDateTime.of(slot.getDate(), slot.getStartTime()));
	    appointment.setStatus(AppointmentStatus.UPCOMING);
 
	   // appointment.setMeetingLink("https://meet.theraconnect.com/" + UUID.randomUUID());
	    String roomName = "theraconnect-appointment-" + UUID.randomUUID();
		String meetingLink = generateJitsiMeetingLink(roomName);
		appointment.setMeetingLink(meetingLink);
 
	    Appointment saved = appointmentService.bookAppointment(appointment);
	    session.setAttribute("upcomingAppointmentId", saved.getAppointmentId());
 
 
	    // Payment
	    Payment payment = new Payment();
	    payment.setAppointment(saved);
	    payment.setAmount(new BigDecimal("500"));
	    payment.setPaymentStatus(PaymentStatus.PAID);
	    payment.setTimestamp(LocalDateTime.now());
	    paymentService.processPayment(payment);
	 // Send email to patient
	    senderService.sendSimpleEmail(
	        patient.getEmail(),
	        "Appointment Confirmation",
	        "Hi " + patient.getName() + ",\n\nYour appointment with Dr. " + doctor.getName() +
	        " is confirmed.\nTime: " + slot.getDate() + " " + slot.getStartTime() +
	        "\nMeeting Link: " + appointment.getMeetingLink()
	    );
 
	    // Send email to doctor
	    senderService.sendSimpleEmail(
	        doctor.getEmail(),
	        "New Appointment Booked",
	        "Hello Dr. " + doctor.getName() + ",\n\nA new appointment has been scheduled with patient " +
	        patient.getName() + ".\nTime: " + slot.getDate() + " " + slot.getStartTime() +
	        "\nMeeting Link: " + appointment.getMeetingLink()
	    );
	    return "redirect:/appointments/payment";
	}
	@GetMapping("/payment")
	public String showPaymentPage(HttpSession session, HttpServletRequest request) {
	    Long id = (Long) session.getAttribute("upcomingAppointmentId");
	    if (id == null) return "redirect:/patient-dashboard";
	    Appointment appointment = appointmentService.getById(id);
	    request.setAttribute("appointment", appointment);
	    return "payment"; // payment.jsp
	}
	@PostMapping("/pay")
	public String processPayment(HttpSession session,
	        @RequestParam Long doctorId,
	        @RequestParam Long patientId,
	        @RequestParam Long slotId) {
 
	    Long id = (Long) session.getAttribute("upcomingAppointmentId");
	    if (id == null) return "redirect:/patient/dashboard";
 
	    Appointment appointment = appointmentService.getById(id);
 
	    DoctorSlotAvailability slot = availabilityService.getAvailabilityById(slotId);
	    User doctor = userService.getUserById(doctorId).orElseThrow();
	    User patient = userService.getUserById(patientId).orElseThrow();
	    System.out.println("Pay api is running.....");
 
	 // Send email to patient
	    senderService.sendSimpleEmail(
	        patient.getEmail(),
	        "Appointment Confirmation",
	        "Hi " + patient.getName() + ",\n\nYour appointment with Dr. " + doctor.getName() +
	        " is confirmed.\nTime: " + slot.getDate() + " " + slot.getStartTime() +
	        "\nMeeting Link: " + appointment.getMeetingLink()
	    );
 
	    // Send email to doctor
	    senderService.sendSimpleEmail(
	        doctor.getEmail(),
	        "New Appointment Booked",
	        "Hello Dr. " + doctor.getName() + ",\n\nA new appointment has been scheduled with patient " +
	        patient.getName() + ".\nTime: " + slot.getDate() + " " + slot.getStartTime() +
	        "\nMeeting Link: " + appointment.getMeetingLink()
	    );
 
	    
	 // Mark appointment as confirmed
	    appointment.setStatus(AppointmentStatus.UPCOMING);
	    appointmentService.bookAppointment(appointment);
	    // Clear session
	    session.removeAttribute("pendingAppointmentId");
 
	    return "redirect:/patient-dashboard?paymentSuccess=true";
	}

	private String generateJitsiMeetingLink(String roomName) {
		String baseUrl = "https://meet.jit.si/";
		return baseUrl + roomName;
		}
	
	
	
	
	
	
	//23 july evenning by sameer
	
//	@GetMapping("/doctor/view")
//	public String viewDoctorAppointments(HttpSession session, Model model) {
//	    Long doctorId = (Long) session.getAttribute("doctorProfileId");
//	    if (doctorId == null) {
//	        return "redirect:/login";
//	    }
//
//	    List<Appointment> appointments = appointmentService.getByDoctor(doctorId);
//	    model.addAttribute("appointments", appointments);
//	    return "doctor-appointments";
//	}
	
	
	// other imports…
 
	@GetMapping("/doctor/view")
	public String viewDoctorAppointments(HttpSession session, Model model) {
	    Long doctorId = (Long) session.getAttribute("doctorProfileId");
 
	    if (doctorId == null) {
	        System.out.println("Doctor ID not found in session.");
	        return "redirect:/login";
	    }
 
	    List<Appointment> rawAppointments = appointmentService.getByDoctor(doctorId);
	    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMM yyyy, hh:mm a");
 
	    // Format LocalDateTime to string
	    List<Map<String, Object>> appointments = new ArrayList<>();
	    for (Appointment appt : rawAppointments) {
	        Map<String, Object> item = new HashMap<>();
	        item.put("appointmentId", appt.getAppointmentId());
	        item.put("reason", appt.getReason());
	        item.put("status", appt.getStatus());
	        item.put("meetingLink", appt.getMeetingLink());
 
	        User patient = appt.getPatient();
	        item.put("patientName", (patient != null) ? patient.getName() : "Unknown");
 
	        // Format slotTime safely
	        LocalDateTime time = appt.getSlotTime();
	        item.put("formattedSlotTime", (time != null) ? time.format(formatter) : "N/A");
 
	        appointments.add(item);
	    }
 
	    model.addAttribute("appointments", appointments);
	    return "doctor-appointments";
	}
	//------DoctorUpload prescription
	@PostMapping("/doctor/upload-prescription")
    public String uploadPrescription(@RequestParam("appointmentId") Long appointmentId,
                                     @RequestParam("prescription") MultipartFile file) throws IOException {

        Optional<Appointment> optional = appointmentRepository.findById(appointmentId);
        if (optional.isPresent()) {
            Appointment appt = optional.get();
            appt.setImageData(file.getBytes());                         // Actual image bytes
            appt.setPrescriptionName(file.getOriginalFilename());       // Optional metadata
            appointmentRepository.save(appt);
        }

//        return "redirect:/doctor/view"; // Or wherever your dashboard lives
        return "redirect:/doctor-dashboard"; // ? or whatever route renders doctor-appointments.jsp

    }
	
	//patient appointment view
	@GetMapping("/patients/view")
	public String viewPatientAppointments(HttpSession session, Model model) {
		 Long patientId= (Long) session.getAttribute("patientProfileId");
 
		    if (patientId == null) {
		        System.out.println("patient ID not found in session.");
		        return "redirect:/login";
		    }
		    List<Appointment> rawAppointments = appointmentService.getByPatient(patientId);
		    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMM yyyy, hh:mm a");
		    
		    List<Map<String, Object>> appointments = new ArrayList<>();
		    
		    for (Appointment appt : rawAppointments) {
		        Map<String, Object> item = new HashMap<>();
		        item.put("appointmentId", appt.getAppointmentId());
		        item.put("reason", appt.getReason());
		        //item.put("status", appt.getStatus());
		        item.put("meetingLink", appt.getMeetingLink());
 
		        User doctor= appt.getDoctor();
		        item.put("doctorName", (doctor != null) ? doctor.getName() : "Unknown");
 
		        // Format slotTime safely
		        LocalDateTime time = appt.getSlotTime();
		        item.put("formattedSlotTime", (time != null) ? time.format(formatter) : "N/A");
		        //updated for prescription
		        item.put("hasPrescription", appt.getImageData() != null);
		        //
 
 
		        appointments.add(item);
		    }
 
		    model.addAttribute("appointments", appointments);
		    return "patient-appointments";
	}
 //download prescription
	@GetMapping("/patients/prescription/download/{appointmentId}")
	  public ResponseEntity<byte[]> downloadPrescription(@PathVariable Long appointmentId) {
	      Optional<Appointment> optional = appointmentRepository.findById(appointmentId);

	      if (optional.isEmpty()) {
	          return ResponseEntity.notFound().build();
	      }

	      Appointment appt = optional.get();
	      byte[] imageData = appt.getImageData();
	      String filename = appt.getPrescriptionName();

	      if (imageData == null || imageData.length == 0 || filename == null) {
	          return ResponseEntity.noContent().build();
	      }

	      return ResponseEntity.ok()
	          .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + filename + "\"")
	          .contentType(MediaType.valueOf("image/jpeg")) // Change to "image/png" or "application/pdf" if needed
	          .body(imageData);
	  }
	 

		@GetMapping("/slot-booked")
		public String showSlotBookedPage() {
			return "slotalready-booked"; // This corresponds to slot-booked.jsp
		}



	
	

	
}
