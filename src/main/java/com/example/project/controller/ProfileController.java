package com.example.project.controller;

import com.example.project.model.Message;
import com.example.project.model.User;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Set;


@Controller
@RequestMapping("/profile")
public class ProfileController {

    @GetMapping("{user}")
    public String getProfile(@AuthenticationPrincipal User currentUser,
                             @PathVariable User user,
                             Model model) {
        Set<Message> messages = user.getMessages();

        model.addAttribute("messages", messages);
        model.addAttribute("isCurrentUser", currentUser.equals(user));
        return "profile";
    }
}
