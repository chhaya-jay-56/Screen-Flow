package com.example.Spring_2.Controller;

import com.example.Spring_2.Entity_client.user_manage;

import com.example.Spring_2.Service_Client.LoginService;
import com.example.Spring_2.Service_Client.OtpService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.servlet.http.HttpSession;
import kong.unirest.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.*;


@org.springframework.stereotype.Controller
public class Controller {

    @Autowired
    private LoginService loginService;

    @Autowired
    private OtpService otpService;

    @PersistenceContext
    private EntityManager entityManager;

    private static final String PRODUCT_IMAGES = "Uploads/";

    // Store OTP and generation time
    public final Map<String, OtpDetails> otpStore = new HashMap<>();

    private boolean isLoggedIn(HttpSession session) {
        return session.getAttribute("username") != null;
    }
    @GetMapping("/sign_up")
    public String add_user(Model model){
        user_manage newUser = new user_manage();
        model.addAttribute("UserData", newUser);
        return "index_html/Sign_up";
    }

    @PostMapping("/save_user_data")
    public String addUserAndSendOtp(user_manage UserData) {

        // Create a UserData object and populate it
        loginService.add_user(UserData);

        // Save the user data using the service

        // Generate OTP
        String otp = otpService.generateOtp();

        // Send OTP to user's email
        otpService.sendOtpEmail(UserData.getEmail(), otp);

        // Store OTP with email and timestamp for validation
        otpStore.put(UserData.getEmail(), new OtpDetails(otp, LocalDateTime.now()));

        return "redirect:/verify?email=" + UserData.getEmail();
    }

    @PostMapping("/save_user_data_2")
    public String add_user(user_manage UserData) {
        // Save the user data
        loginService.add_user(UserData);

        return "redirect:forms";
    }

    @GetMapping("/verify")
    public String verifyOtpForm(@RequestParam("email") String email, Model model) {

        model.addAttribute("email", email);
        return "index_html/verify";
    }

    @PostMapping("/verify_otp")
    @Transactional
    public String verifyOtp(@RequestParam("email") String email,String otp) {

        OtpDetails otpDetails = otpStore.get(email);
        System.out.println(otpDetails);

        if (otpDetails != null) {
            LocalDateTime now = LocalDateTime.now();
            LocalDateTime otpGenerationTime = otpDetails.getGenerationTime();

            // Check if OTP is older than 30 minutes
            if (otpGenerationTime.isBefore(now.minusMinutes(30))) {
                otpStore.remove(email);
                return "redirect:/error405";
            }

            if (otpDetails.getOtp().equals(otp)) {
                // OTP is valid, proceed with the signup
                String query = "UPDATE user_manage u SET u.verified = true WHERE u.email = :email";
                entityManager.createQuery(query)
                        .setParameter("email", email)
                        .executeUpdate();

                otpStore.remove(email); // Clear OTP after successful verification

                // Retrieve user details by email
                return "redirect:/log_in";
            }
        }
        // OTP is invalid
        return "redirect:/error406";
    }

    @GetMapping("/log_in")
    public String log_in (Model model ){
        user_manage user_manage = new user_manage();
        model.addAttribute("CompareData", user_manage);

        return "index_html/Log_In";
    }

    @PostMapping("/save_3")
    public String log_in(@RequestParam String email, @RequestParam String password, Model model, HttpSession session) {
        Optional<user_manage> user = loginService.authenticate(email, password);

        if (user.isPresent()) {

            String username = user.get().getName();
            String profileImagePath = loginService.getUserProfileImage(username);

            // Set session attributes
            session.setAttribute("username", username);
            session.setAttribute("userImagePath", profileImagePath);
            System.out.println("Image Path: " + profileImagePath);
            System.out.println("Session created with username: " + username);

            return "redirect:/index"; // Redirect to the index page or home page
        } else {
            model.addAttribute("error", "Invalid email or password.");
            return "redirect:/error407"; // Redirect to the error page
        }
    }

    @GetMapping("/")
    public String index(HttpSession session){
        if (!isLoggedIn(session)) {
            return "redirect:/log_in";
        }
        return "/index_html/index";
    }

    @GetMapping("/about")
    public String about(HttpSession session){
        if (!isLoggedIn(session)) {
            return "redirect:/log_in";
        }

        return "/index_html/about";
    }

    @GetMapping("/genre")
    public String genre(HttpSession session){
        if (!isLoggedIn(session)) {
            return "redirect:/log_in";
        }
        return "/index_html/genre";
    }

    @GetMapping("/contact")
    public String contact(HttpSession session){
        if (!isLoggedIn(session)) {
            return "redirect:/log_in";
        }
        return "/index_html/contact";
    }

    @GetMapping("/index")
    public String index_3(HttpSession session , Model model){
        if (!isLoggedIn(session)) {
            return "redirect:/log_in";
        }
        String username = (String) session.getAttribute("username");
        model.addAttribute("username", username);
        return "/index_html/index";
    }

    @GetMapping("/flow_card")
    public String flow(HttpSession session){
        if (!isLoggedIn(session)) {
            return "redirect:/log_in";
        }
        return "/index_html/flowcard";
    }

    @GetMapping("/search")
    public String searchPage(HttpSession session) {
        if (!isLoggedIn(session)) {
            return "redirect:/log_in";
        }
        return "/index_html/search.html";
    }

    @GetMapping("/subs")
    public String subs(HttpSession session){
        if (!isLoggedIn(session)) {
            return "redirect:/log_in";
        }
        return "/index_html/plan";
    }

    @GetMapping("/home")
    public String home(){
        return "/index_html/home";
    }

    @GetMapping("/log_out")
    public String log_out(HttpSession session) {
        if (!isLoggedIn(session)) {
            return "redirect:/log_in";
        }
        session.invalidate();
        System.out.println("Session invalidated");
        return "/index_html/home";
    }

    @PostMapping("/edit_2/{id}")
    public String edituserbyid(@PathVariable(value = "id") long id, Model model) {
        user_manage user_manage = loginService.edituserbyid(id);
        model.addAttribute("UserData", user_manage);
        return "index_html/Edituser";

    }

    @GetMapping("/upload_img")
    public String upload_img(String name , Model model ,HttpSession session) {
        if (!isLoggedIn(session)) {
            return "redirect:/log_in";
        }
        model.addAttribute("name" ,name);
        return "index_html/upload";
    }

    @PostMapping("/image_insert")
    public String addUser(@RequestParam("name") String name,
                          @RequestParam("prf_img") MultipartFile prf_img,
                          Model model) {
        try {
            // Save user and profile image
            loginService.addUser(name, prf_img);

            // Redirect to success page or any other page
            return "index_html/index";
        } catch (RuntimeException e) {
            // Handle the exception and return error page or message
            model.addAttribute("errorMessage", "Error occurred while saving user data: " + e.getMessage());
            return "error/404";
        }
    }

    @PostMapping("/submit_form")
    public String submitForm(
            @RequestParam("name") String name,
            @RequestParam("subject") String subject,
            @RequestParam("sender") String senderEmail,
            @RequestParam("phone") String phone,
            @RequestParam("message") String message) {

        String emailContent = "Name: " + name + "\n" +
                "Email: " + senderEmail + "\n" +
                "Phone: " + phone + "\n\n" +
                "Message:\n" + message;

        otpService.sendEmail(senderEmail, subject, emailContent);
        return "index_html/index";
    }



    // Inner class to store OTP details
    private static class OtpDetails {
        private String otp;
        private LocalDateTime generationTime;

        public OtpDetails(String otp, LocalDateTime generationTime) {
            this.otp = otp;
            this.generationTime = generationTime;
        }

        public String getOtp() {
            return otp;
        }

        public LocalDateTime getGenerationTime() {
            return generationTime;
        }
    }
}
