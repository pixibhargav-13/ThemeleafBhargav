package com.bhargav.themeleafbhargav.controller;

import com.bhargav.themeleafbhargav.model.User;
import com.bhargav.themeleafbhargav.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class RegistrationController {

    private final EmailService emailService;

    @Autowired
    public RegistrationController(EmailService emailService) {
        this.emailService = emailService;
    }

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new User());
        return "registration";
    }

    @PostMapping("/register")
    public String processRegistration(@ModelAttribute("user") User user) {
        // Call the EmailService to send registration confirmation
        sendRegistrationConfirmation(user);
        return "confirmation";
    }


    private void sendRegistrationConfirmation(User user) {
        String subject = "Registration Confirmation";
        String toAddress = user.getEmail();
        String body = "Dear " + user.getName() + ",\n\nThank you for registering with us!";

        emailService.sendEmail(subject, toAddress, body);
    }
}
