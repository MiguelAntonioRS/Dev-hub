package com.devhub.opendevplatform.controller;

import com.devhub.opendevplatform.model.User;
import com.devhub.opendevplatform.repository.UserRepository;
import com.devhub.opendevplatform.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/notifications")
public class NotificationController {

    @Autowired
    private NotificationService notificationService;

    @Autowired
    private UserRepository userRepository;

    @GetMapping
    public String showNotifications(Authentication authentication, Model model) {
        String username = authentication.getName();
        User user = userRepository.findByUsername(username).orElse(null);
        
        if (user != null) {
            model.addAttribute("notifications", notificationService.getUserNotifications(user));
            model.addAttribute("unreadCount", notificationService.getUnreadCount(user));
        }
        return "notifications";
    }

    @PostMapping("/read/{id}")
    @ResponseBody
    public void markAsRead(@PathVariable Long id) {
        notificationService.markAsRead(id);
    }

    @PostMapping("/read-all")
    public String markAllAsRead(Authentication authentication) {
        String username = authentication.getName();
        User user = userRepository.findByUsername(username).orElse(null);
        if (user != null) {
            notificationService.markAllAsRead(user);
        }
        return "redirect:/notifications";
    }
}