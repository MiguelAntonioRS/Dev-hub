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

@Controller
@RequestMapping({"/users", "/u"})
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

    @PostMapping("/register")
    public String registerUser(@ModelAttribute User user, Model model) {
        try {
            user.setRole("USER");
            userService.registerUser(user);
            return "redirect:/users/login?registered";
        } catch (Exception e) {
            model.addAttribute("error", "Username or email already exists");
            model.addAttribute("user", user);
            return "register";
        }
    }

    @GetMapping("/login")
    public String showLoginForm() {
        return "login";
    }

    @GetMapping("/{userId}")
    public String userProfile(@PathVariable Long userId, Authentication authentication, Model model) {
        User profileUser = userRepository.findById(userId).orElse(null);
        if (profileUser == null) {
            return "redirect:/resources";
        }

        model.addAttribute("profileUser", profileUser);
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