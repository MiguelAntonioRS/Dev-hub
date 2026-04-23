package com.devhub.opendevplatform.controller;

import com.devhub.opendevplatform.model.User;
import com.devhub.opendevplatform.repository.UserRepository;
import com.devhub.opendevplatform.service.FollowService;
import com.devhub.opendevplatform.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Controller
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private FollowService followService;
    
    @Autowired
    private UserService userService;

    @GetMapping("/register")
    public String showRegisterForm(Model model) {
        if (!model.containsAttribute("user")) {
            model.addAttribute("user", new User());
        }
return "register";
    }

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

    @GetMapping("/login")
    public String showLoginForm() {
        return "login";
    }

    @GetMapping("/profile")
    public String myProfile(Authentication authentication, Model model) {
        User user = userRepository.findByUsername(authentication.getName()).orElse(null);
        if (user != null) {
            return "redirect:/users/" + user.getId();
        }
        return "redirect:/resources";
    }

    @GetMapping("/{userId}")
    public String userProfile(@PathVariable Long userId, Authentication authentication, Model model) {
        User profileUser = userRepository.findById(userId).orElse(null);
        if (profileUser == null) {
            return "redirect:/resources";
        }

        model.addAttribute("profileUser", profileUser);
        model.addAttribute("profileInitial", profileUser.getUsername().substring(0, 1).toUpperCase());
        model.addAttribute("followerCount", followService.getFollowerCount(profileUser));
        model.addAttribute("followingCount", followService.getFollowingCount(profileUser));

        if (authentication != null && !authentication.getName().isEmpty()) {
            User currentUser = userRepository.findByUsername(authentication.getName()).orElse(null);
            boolean isSameUser = currentUser != null && currentUser.getId().equals(userId);
            boolean isFollowing = currentUser != null && followService.isFollowing(currentUser, profileUser);
            model.addAttribute("isSameUser", isSameUser);
            model.addAttribute("isFollowing", isFollowing);
        } else {
            model.addAttribute("isSameUser", true);
            model.addAttribute("isFollowing", false);
        }

        return "userProfile";
    }
}