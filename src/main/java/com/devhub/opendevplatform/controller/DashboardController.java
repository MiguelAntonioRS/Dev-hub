package com.devhub.opendevplatform.controller;

import com.devhub.opendevplatform.model.Resource;
import com.devhub.opendevplatform.model.User;
import com.devhub.opendevplatform.model.Vote;
import com.devhub.opendevplatform.repository.ResourceRepository;
import com.devhub.opendevplatform.repository.UserRepository;
import com.devhub.opendevplatform.repository.VoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/dashboard")
public class DashboardController {

    @Autowired
    private ResourceRepository resourceRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private VoteRepository voteRepository;

    @GetMapping
    public String showDashboard(Authentication authentication, Model model) {
        String username = authentication.getName();
        User user = userRepository.findByUsername(username).orElse(null);
        
        if (user != null) {
            List<Resource> myResources = resourceRepository.findByAuthor(username);
            List<Vote> myVotes = voteRepository.findByUser(user);
            
            model.addAttribute("user", user);
            model.addAttribute("myResources", myResources);
            model.addAttribute("myVotes", myVotes);
            model.addAttribute("resourcesCount", myResources.size());
            model.addAttribute("votesCount", myVotes.size());
        }
        
        return "dashboard";
    }
}