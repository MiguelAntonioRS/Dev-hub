package com.devhub.opendevplatform.controller;

import com.devhub.opendevplatform.model.Resource;
import com.devhub.opendevplatform.model.User;
import com.devhub.opendevplatform.repository.ResourceRepository;
import com.devhub.opendevplatform.repository.UserRepository;
import com.devhub.opendevplatform.service.RatingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/ratings")
public class RatingController {

    @Autowired
    private RatingService ratingService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ResourceRepository resourceRepository;

    @PostMapping("/{resourceId}")
    public String rate(@PathVariable Long resourceId,
                     @RequestParam int stars,
                     Authentication authentication) {
        User user = userRepository.findByUsername(authentication.getName()).orElse(null);
        Resource resource = resourceRepository.findById(resourceId).orElse(null);
        
        if (user != null && resource != null && stars >= 1 && stars <= 5) {
            ratingService.rateResource(user, resource, stars);
        }
        
        return "redirect:/resources/" + resourceId;
    }
}