package com.example.Spring_2.Controller;

import com.example.Spring_2.Entity_admin.admin_manage;
import com.example.Spring_2.Entity_client.user_manage;
import com.example.Spring_2.Service_Client.LoginService;
import com.example.Spring_2.Service_admin.SignService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;

@org.springframework.stereotype.Controller
public class admin_controller {
    @Autowired
    SignService signService;

    @Autowired
    LoginService loginService;

    private boolean isLoggedIn(HttpSession session) {
        return session.getAttribute("username") != null;
    }

    @GetMapping("/index_2")
    public String index_2(HttpSession session){
        if (!isLoggedIn(session)) {
            return "redirect:/admin";
        }
        return "/admin_html/index_2";
    }

    @GetMapping("/error404")
    public String error(){

        return "/error/Error_404";
    }

    @GetMapping("/error405")
    public String error_2(){
        return "/error/Error_405";
    }

    @GetMapping("/error406")
    public String error_3(){
        return "/error/Error_406";
    }

    @GetMapping("/error407")
    public String error_4(){
        return "/error/Error_407";
    }

    @GetMapping("/forms")
    public String forms(Model model , HttpSession session){
        if (!isLoggedIn(session)) {
            return "redirect:/admin";
        }

        List<admin_manage> admin = signService.displayAdmin();

        List<user_manage> user = loginService.displayUser();

        // Add the list of admins to the model
        model.addAttribute("admin_3", admin);
        // Add the list of users to the model
        model.addAttribute("user", user);

        // If you still need to handle the creation of a new admin, add this
        admin_manage newAdmin = new admin_manage();
        model.addAttribute("admin", newAdmin);

        return "admin_html/forms";
    }

    @PostMapping("/save")
    public String add_admin(admin_manage admin){
        signService.forms(admin);
        return "redirect:/index_2";
    }

    @GetMapping("/admin")
    public String admin_log_in (Model model){

        admin_manage admin_manage = new admin_manage();
        model.addAttribute("admin_2", admin_manage);
        return "admin_html/admin_log_in";
    }

    @PostMapping("/save_2")
    public String login(@RequestParam String email, @RequestParam String password, Model model , HttpSession session) {
        Optional<admin_manage> admin = signService.authenticate(email, password);
        if (admin.isPresent()) {

            String username = admin.get().getName();

            // Set session attributes
            session.setAttribute("username", username);
            System.out.println("Session created with username: " + username);
            return "redirect:/index_2";

        } else {
            model.addAttribute("error", "Invalid email or password.");
            return "redirect:/error404";
        }
    }

    @GetMapping("/admin_log_out")
    public String log_out(HttpSession session) {
        if (!isLoggedIn(session)) {
            return "redirect:/admin";
        }
        session.invalidate();
        System.out.println("Session invalidated");
        return "/index_html/home";
    }

    @GetMapping("/blocks")
    public String blocks(){
        return "/admin_html/blocks";
    }

    @GetMapping("/cards")
    public String cards(){
        return "/admin_html/cards";
    }

    @GetMapping("/carousels")
    public String carousels(){
        return "/admin_html/carousels";
    }

    @GetMapping("/people")
    public String people(){
        return "/admin_html/people";
    }

    @GetMapping("/pricing")
    public String pricing(HttpSession session){
        if (!isLoggedIn(session)) {
            return "redirect:/admin";
        }
        return "/admin_html/pricing";
    }

    @PostMapping("/delete/{id}")
        public String deleteadminbyid(@PathVariable (value = "id") long id) {

        signService.deleteadminbyid(id);
        return "redirect:/forms";
    }

    @PostMapping("/delete_2/{id}")
    public String deleteuserbyid(@PathVariable (value = "id") long id) {

        loginService.deleteUserbyid(id);
        return "redirect:/forms";
    }

    @PostMapping("/edit/{id}")
    public String editadminbyid(@PathVariable(value = "id") long id,Model model) {
        admin_manage admin_manage = signService.editadminbyid(id);
        model.addAttribute("admin", admin_manage);
        return "admin_html/Editadmin";

    }

}
