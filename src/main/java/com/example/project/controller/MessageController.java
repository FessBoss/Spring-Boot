package com.example.project.controller;

import com.example.project.model.Message;
import com.example.project.model.User;
import com.example.project.service.FileUploadService;
import com.example.project.service.MessageDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


@Controller
public class MessageController {
    @Autowired
    private MessageDataService messageDataService;

    @Autowired
    FileUploadService fileUploadService;

    @GetMapping("/messages")
    public String getMessages(@AuthenticationPrincipal User user,
                              Model model) {
        List<Message> messages = messageDataService.findAll();
        model.addAttribute("user", user);
        model.addAttribute("messages", messages);
        return "messages";
    }

    @PostMapping("/messages")
    public String postMessages(@AuthenticationPrincipal User user,
                               @RequestParam String text,
                               @RequestParam String tag,
                               @RequestParam("file") MultipartFile file) {
        Message message = new Message(text, tag, user);

        if (file != null && !file.isEmpty()) {
            fileUploadService.upload(file);
            message.setFilename(file.getOriginalFilename());
        }
        messageDataService.save(message);
        return "redirect:/messages";
    }

    @GetMapping("/filter")
    public String filter(@RequestParam(name="tag",required = false) String tag, Model model) {
        List<Message> messages = messageDataService.findByTag(tag);
        model.addAttribute("messages", messages);
        return "messages";
    }
}
