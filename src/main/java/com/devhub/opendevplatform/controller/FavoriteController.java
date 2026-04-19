package com.devhub.opendevplatform.controller;

import com.devhub.opendevplatform.model.User;
import com.devhub.opendevplatform.repository.UserRepository;
import com.devhub.opendevplatform.service.FavoriteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/favorites")
public class FavoriteController {

    @Autowired
    private FavoriteService favoriteService;

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/toggle/{resourceId}")
    public String toggleFavorite(Authentication authentication, @PathVariable Long resourceId) {
        String username = authentication.getName();
        User user = userRepository.findByUsername(username).orElse(null);
        
        if (user != null) {
            favoriteService.toggleFavorite(user, resourceId);
        }
        return "redirect:/resources/" + resourceId;
    }

    @GetMapping
    public String myFavorites(Authentication authentication, Model model) {
        String username = authentication.getName();
        User user = userRepository.findByUsername(username).orElse(null);
        
        if (user != null) {
            model.addAttribute("favorites", favoriteService.getUserFavorites(user));
        }
        return "favorites";
    }
}