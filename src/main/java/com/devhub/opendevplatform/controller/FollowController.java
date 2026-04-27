package com.devhub.opendevplatform.controller;

import com.devhub.opendevplatform.model.User;
import com.devhub.opendevplatform.repository.UserRepository;
import com.devhub.opendevplatform.service.FollowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/follow")
public class FollowController {

    @Autowired
    private FollowService followService;

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/{userId}")
    public String followUser(@PathVariable Long userId, Authentication authentication) {
        if (authentication == null) {
            return "redirect:/users/login";
        }
        User follower = userRepository.findByUsername(authentication.getName()).orElse(null);
        User target = userRepository.findById(userId).orElse(null);
        
        if (follower != null && target != null) {
            followService.follow(follower, target);
        }
        return "redirect:/u/" + userId;
    }

    @PostMapping("/{userId}/unfollow")
    public String unfollowUser(@PathVariable Long userId, Authentication authentication) {
        if (authentication == null) {
            return "redirect:/users/login";
        }
        User follower = userRepository.findByUsername(authentication.getName()).orElse(null);
        User target = userRepository.findById(userId).orElse(null);
        
        if (follower != null && target != null) {
            followService.unfollow(follower, target);
        }
        return "redirect:/u/" + userId;
    }
}