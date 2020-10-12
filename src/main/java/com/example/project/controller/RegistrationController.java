package com.example.project.controller;

import com.example.project.model.Role;
import com.example.project.model.User;
import com.example.project.service.UserDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Collections;

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
        User userFromDB = userDataService.findByUsername(user.getUsername());

        if (userFromDB != null) {
            model.addAttribute("message", "User exists!");
            return "registration";
        } else {
            user.setActive(true);
            user.setRoles(Collections.singleton(Role.USER));
            userDataService.saveUser(user);
            return "redirect:/login";
        }
    }
}
