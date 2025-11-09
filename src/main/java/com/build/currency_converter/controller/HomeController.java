package com.build.currency_converter.controller;

import com.build.currency_converter.entity.ConversionHistory;
import com.build.currency_converter.service.ConversionService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller

public class HomeController {

    private final ConversionService conversionService;

    public HomeController(ConversionService conversionService) {
        this.conversionService = conversionService;
    }

    @GetMapping("/home")
    public String home(Model model) {
        model.addAttribute("history", conversionService.getUserHistory());
        return "home";
    }

    @PostMapping("/convert")
    public String convert(
            @RequestParam String sourceCurrency,
            @RequestParam String targetCurrency,
            @RequestParam Double amount,
            Model model) {
        try {
            ConversionHistory conversion = conversionService.convertCurrency(sourceCurrency, targetCurrency, amount);
            model.addAttribute("result", conversion);
            model.addAttribute("history", conversionService.getUserHistory());
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            model.addAttribute("history", conversionService.getUserHistory());
        }
        return "home";
    }
}