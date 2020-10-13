package com.example.project.controller;

import com.example.project.model.User;
import com.example.project.service.MailSender;
import com.example.project.service.UserDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/user")
@PreAuthorize("hasAuthority('ADMIN')")
public class UserController {
    @Autowired
    private UserDataService userDataService;

    @Autowired
    private MailSender mailSender;

    @GetMapping
    public String userList(@AuthenticationPrincipal User user,
                           Model model) {
        model.addAttribute("user", user);
        model.addAttribute("users", userDataService.findAll());
        return "userList";
    }

    @GetMapping("{user}")
    public String userEdit(@PathVariable User user, Model model) {
        model.addAttribute("user", user);
        model.addAttribute("list", user.getRoles());
        return "userEdit";
    }

    @PostMapping
    public String userSave(@ModelAttribute("user") User user) {
        userDataService.saveUser(user);
        return "redirect:/user";
    }

    @PostMapping("send_mail")
    public String sendMail(@RequestParam String mailTo,
                           @RequestParam String subject,
                           @RequestParam String message) {
        mailSender.send(mailTo, subject, message);
        return "redirect:/user";
    }
}
