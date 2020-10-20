package com.example.project.controller;

import com.example.project.model.Message;
import com.example.project.model.User;
import com.example.project.service.UserDataService;
import org.springframework.beans.factory.annotation.Autowired;
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
    @Autowired
    private UserDataService userDataService;

    @GetMapping("{user}")
    public String getProfile(@AuthenticationPrincipal User currentUser,
                             @PathVariable User user,
                             Model model) {
        Set<Message> messages = user.getMessages();

        model.addAttribute("userChannel", user);
        model.addAttribute("subscriptionsCount", user.getSubscriptions().size());
        model.addAttribute("subscribersCount", user.getSubscribers().size());
        model.addAttribute("isSubscriber", user.getSubscribers().contains(currentUser));
        model.addAttribute("messages", messages);
        model.addAttribute("isCurrentUser", currentUser.equals(user));
        return "profile";
    }

    @GetMapping("subscribe/{user}")
    public String subscribe(@PathVariable User user, @AuthenticationPrincipal User currentUser) {
        userDataService.subscribe(currentUser, user);
        return "redirect:/profile/" + user.getId();
    }

    @GetMapping("unsubscribe/{user}")
    public String unsubscribe(@PathVariable User user, @AuthenticationPrincipal User currentUser) {
        userDataService.unsubscribe(currentUser, user);
        return "redirect:/profile/" + user.getId();
    }

    @GetMapping("{type}/{user}/list")
    public String subscribers(@PathVariable User user, @PathVariable String type, Model model) {
        model.addAttribute("userChannel", user);
        model.addAttribute("type", type);

        if ("subscriptions".equals(type)) {
            model.addAttribute("users", user.getSubscriptions());
        } else {
            model.addAttribute("users", user.getSubscribers());
        }

        return "subscribers";
    }

}
