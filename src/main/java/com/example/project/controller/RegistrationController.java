package com.example.project.controller;

import com.example.project.model.User;
import com.example.project.service.UserDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class RegistrationController {
    @Autowired
    private UserDataService userDataService;

    @GetMapping("/registration")
    public String GetRegistration(@AuthenticationPrincipal User user,
                                  Model model) {
        model.addAttribute("userAuth", user);
        model.addAttribute("user", new User());
        return "registration";
    }

    @PostMapping("/registration")
    public String PostRegistration(@ModelAttribute User user, Model model) {
        if (!userDataService.addUser(user)) {
            model.addAttribute("message", "User exists!");
            return "registration";
        } else {
            model.addAttribute("message", "User create!");
            return "redirect:/login";
        }
    }
}
