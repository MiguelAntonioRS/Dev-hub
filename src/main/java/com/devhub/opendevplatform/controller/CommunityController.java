package com.devhub.opendevplatform.controller;

import com.devhub.opendevplatform.model.User;
import com.devhub.opendevplatform.repository.UserRepository;
import com.devhub.opendevplatform.service.FollowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import java.util.Collections;
import java.util.List;

@Controller
public class CommunityController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private FollowService followService;

    @GetMapping("/community")
    public String community(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "12") int size,
            Authentication authentication, Model model) {
        
        Pageable pageable = PageRequest.of(page, size, Sort.by("id").descending());
        Page<User> userPage = userRepository.findAll(pageable);
        
        model.addAttribute("users", userPage.getContent());
        model.addAttribute("totalPages", userPage.getTotalPages());
        model.addAttribute("currentPage", userPage.getNumber());
        
        if (authentication != null && authentication.getName() != null) {
            try {
                User currentUser = userRepository.findByUsername(authentication.getName()).orElse(null);
                if (currentUser != null) {
                    model.addAttribute("currentUser", currentUser);
                    List<User> following = followService.getFollowing(currentUser);
                    model.addAttribute("followingUsers", following);
                }
            } catch (Exception e) {
                model.addAttribute("followingUsers", Collections.emptyList());
            }
        } else {
            model.addAttribute("followingUsers", Collections.emptyList());
        }
        
        return "community";
    }
}