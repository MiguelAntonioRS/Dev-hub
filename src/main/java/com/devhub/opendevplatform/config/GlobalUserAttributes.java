package com.devhub.opendevplatform.config;

import com.devhub.opendevplatform.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
public class GlobalUserAttributes {

    @Autowired
    private UserRepository userRepository;

    @ModelAttribute
    public void addUserInitial(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && auth.getName() != null && !auth.getName().isEmpty()) {
            String initial = auth.getName().substring(0, 1).toUpperCase();
            model.addAttribute("userInitial", initial);
        } else {
            model.addAttribute("userInitial", "U");
        }
    }
}