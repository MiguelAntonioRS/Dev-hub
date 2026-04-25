package com.devhub.opendevplatform.controller;

import com.devhub.opendevplatform.model.Resource;
import com.devhub.opendevplatform.model.User;
import com.devhub.opendevplatform.repository.ResourceRepository;
import com.devhub.opendevplatform.repository.UserRepository;
import com.devhub.opendevplatform.service.ResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class SearchController {

    @Autowired
    private ResourceService resourceService;

    @Autowired
    private ResourceRepository resourceRepository;

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/search")
    public String search(@RequestParam(required = false) String q, Model model) {
        if (q != null && !q.isEmpty()) {
            List<Resource> resources = resourceService.search(q);
            List<User> users = userRepository.findAll().stream()
                    .filter(u -> u.getUsername().toLowerCase().contains(q.toLowerCase()) ||
                               (u.getBio() != null && u.getBio().toLowerCase().contains(q.toLowerCase())))
                    .collect(Collectors.toList());
            
            model.addAttribute("query", q);
            model.addAttribute("resources", resources);
            model.addAttribute("users", users);
        } else {
            model.addAttribute("resources", java.util.Collections.emptyList());
            model.addAttribute("users", java.util.Collections.emptyList());
        }
        
        return "search";
    }
}