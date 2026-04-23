package com.devhub.opendevplatform.controller;

import com.devhub.opendevplatform.model.User;
import com.devhub.opendevplatform.repository.UserRepository;
import com.devhub.opendevplatform.service.FollowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import java.util.List;

@Controller
public class CommunityController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private FollowService followService;

    @GetMapping("/community")
    public String community(Authentication authentication, Model model) {
        Iterable<User> allUsers = userRepository.findAll();
        model.addAttribute("users", allUsers);
        
        if (authentication != null && !authentication.getName().isEmpty()) {
            User currentUser = userRepository.findByUsername(authentication.getName()).orElse(null);
            if (currentUser != null) {
                model.addAttribute("currentUser", currentUser);
                List<User> following = followService.getFollowing(currentUser);
                model.addAttribute("followingUsers", following);
            }
        }
        
        return "community";
    }
}