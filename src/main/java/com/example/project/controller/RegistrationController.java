package com.example.project.controller;

import com.example.project.model.User;
import com.example.project.service.CaptchaService;
import com.example.project.service.UserDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.util.Map;

@Controller
public class RegistrationController {
    @Autowired
    private UserDataService userDataService;

    @Autowired
    private CaptchaService captchaService;

    @GetMapping("/registration")
    public String GetRegistration(Model model) {
        model.addAttribute("user", new User());
        return "registration";
    }

    @PostMapping("/registration")
    public String PostRegistration(@RequestParam("g-recaptcha-response") String captchaResponse,
                                   @ModelAttribute @Valid User user,
                                   BindingResult bindingResult,
                                   Model model) {
        boolean isValidCaptcha = captchaService.validateCaptcha(captchaResponse);

        if(!isValidCaptcha){
            model.addAttribute("captchaError", "Fill captcha");
        }

        if (user.getPassword() != null && !user.getPassword().equals(user.getPassword2())) {
            model.addAttribute("passwordError", "Passwords are different");
            return "registration";
        }

        if (bindingResult.hasErrors() || !isValidCaptcha) {
            Map<String, String> errors = ControllerUtils.getErrors(bindingResult);
            model.mergeAttributes(errors);

            return "registration";
        }

        if (!userDataService.addUser(user)) {
            model.addAttribute("usernameError", "User exists!");
            return "registration";
        } else {
            model.addAttribute("message", "User create!");
            return "redirect:/login";
        }
    }
}
