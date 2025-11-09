package com.build.currency_converter.controller;

import com.build.currency_converter.entity.User;
import com.build.currency_converter.service.UserService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller

public class AuthController {

    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/login")
    public String login(Model model,String error,String logout){
        if (error != null){
            model.addAttribute("message","Email ou senha invalida");
        }
        if (logout != null){
            model.addAttribute("user",new User());
        }
        return "login";
    }

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new User());
        return "register";
    }

    @PostMapping("/register")
    public String register(@Valid User user, BindingResult result,Model model){
        if (result.hasErrors()){
            return "register";
        }
        try{
            userService.registerUser(user);
            return "redirect:/login?success=true";
        }catch (IllegalArgumentException e){
            model.addAttribute("Error",e.getMessage());
            return "register";
        }
    }
}
