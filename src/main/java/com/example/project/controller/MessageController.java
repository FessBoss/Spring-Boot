package com.example.project.controller;

import com.example.project.model.Message;
import com.example.project.service.MessageDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;


@Controller
public class MessageController {

    @Autowired
    MessageDataService messageDataService;

    @GetMapping("/messages")
    public String getMessages(Model model) {
        List<Message> messages = messageDataService.findAll();
        model.addAttribute("messages", messages);
        return "messages";
    }

    @PostMapping("/messages")
    public String postMessages(@RequestParam String text, @RequestParam String tag) {
        Message message = new Message(text, tag);
        messageDataService.save(message);
        return "redirect:/messages";
    }

    @GetMapping("/filter")
    public String filter(@RequestParam(name="tag",required = false) String tag, Model model) {
        if (tag == null || tag.isEmpty()) {
            return "redirect:/messages";
        } else {
            List<Message> messages = messageDataService.findByTag(tag);
            model.addAttribute("messages", messages);
            return "messages";
        }
    }
}
