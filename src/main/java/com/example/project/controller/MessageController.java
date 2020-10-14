package com.example.project.controller;

import com.example.project.model.Message;
import com.example.project.model.User;
import com.example.project.service.FileUploadService;
import com.example.project.service.MessageDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;
import java.util.stream.Collector;
import java.util.stream.Collectors;


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
                               @Valid Message message,
                               BindingResult bindingResult,
                               Model model,
                               @RequestParam("file") MultipartFile file) {
        message.setAuthor(user);

        if (bindingResult.hasErrors()) {
            Map<String, String> errorsMap = ControllerUtils.getErrors(bindingResult);
            List<Message> messages = messageDataService.findAll();

            model.mergeAttributes(errorsMap);
            model.addAttribute("user", user);
            model.addAttribute("message", message);
            model.addAttribute("messages", messages);

            return "messages";
        } else {
            if (file != null && !file.isEmpty()) {
                fileUploadService.upload(file);
                message.setFilename(file.getOriginalFilename());
            }
            messageDataService.save(message);
            return "redirect:/messages";
        }
    }

    @GetMapping("/filter")
    public String filter(@RequestParam(name="tag",required = false) String tag, Model model) {
        List<Message> messages = messageDataService.findByTag(tag);
        model.addAttribute("messages", messages);
        return "messages";
    }
}
