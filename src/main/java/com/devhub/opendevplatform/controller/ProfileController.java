package com.devhub.opendevplatform.controller;

import com.devhub.opendevplatform.model.User;
import com.devhub.opendevplatform.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/profile")
public class ProfileController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping
    public String showProfile(Authentication authentication, Model model) {
        String username = authentication.getName();
        User user = userRepository.findByUsername(username).orElse(null);
        model.addAttribute("user", user);
        return "profile";
    }

    @PostMapping
    public String updateProfile(
            Authentication authentication,
            @RequestParam(required = false) String bio,
            @RequestParam(required = false) String github,
            @RequestParam(required = false) String twitter,
            @RequestParam(required = false) String avatar) {
        
        String username = authentication.getName();
        User user = userRepository.findByUsername(username).orElse(null);
        
        if (user != null) {
            if (bio != null) user.setBio(bio);
            if (github != null) user.setGithub(github);
            if (twitter != null) user.setTwitter(twitter);
            if (avatar != null && !avatar.isEmpty()) user.setAvatar(avatar);
            userRepository.save(user);
        }
        
        return "redirect:/profile?success=true";
    }
}