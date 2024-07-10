package com.yang.artcollectorsregistration.controller;

import com.yang.artcollectorsregistration.dto.ArtCollectorDto;
import com.yang.artcollectorsregistration.entity.ArtCollector;
import com.yang.artcollectorsregistration.service.ArtCollectorService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class AuthorizationController {


    private ArtCollectorService artCollectorService;

    @Autowired
    public AuthorizationController(ArtCollectorService artCollectorService) {
        this.artCollectorService = artCollectorService;
    }


    @GetMapping("index")
    public String home() {
        return "index";
    }

    @GetMapping("/login")
    public String loginForm() {
        return "login";
    }

    //handler method will handle artCollector registration request
    @GetMapping("register")
    public String showRegistrationForm(Model model) {
        ArtCollectorDto artCollector = new ArtCollectorDto();
        model.addAttribute("artCollector", artCollector);
        return "art-collector-registration";
    }

    @PostMapping("/register/save")
    public String registration(@Valid @ModelAttribute("artCollector") ArtCollectorDto artCollector, BindingResult result, Model model) {
        ArtCollector existing = artCollectorService.findArtCollectorByEmail(artCollector.getEmail());
        if (existing != null) {
            result.rejectValue("email", null, "There is already an account registered with that email");
        }

        if (result.hasErrors()) {
            model.addAttribute("artCollector", artCollector);
            return "art-collector-registration";
        }

        artCollectorService.saveArtCollector(artCollector);
        return "redirect:/register?success";
    }

    @GetMapping("/artCollectors")
    public String listRegisteredArtCollectors(Model model){
        List<ArtCollectorDto> artCollectors = artCollectorService.findAllArtCollectors();
        model.addAttribute("artCollectors", artCollectors);
        return "registered-art-collectors";

    }
}