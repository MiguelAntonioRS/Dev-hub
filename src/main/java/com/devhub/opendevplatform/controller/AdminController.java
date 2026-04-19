package com.devhub.opendevplatform.controller;

import com.devhub.opendevplatform.model.Resource;
import com.devhub.opendevplatform.model.Resource.ResourceStatus;
import com.devhub.opendevplatform.service.ResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private ResourceService resourceService;

    @GetMapping("/resources")
    public String manageResources(
            @RequestParam(required = false) String status,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            Model model) {
        
        ResourceStatus resourceStatus = ResourceStatus.PENDING;
        if ("approved".equals(status)) {
            resourceStatus = ResourceStatus.APPROVED;
        } else if ("rejected".equals(status)) {
            resourceStatus = ResourceStatus.REJECTED;
        }
        
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());
        Page<Resource> resourcePage = resourceService.findByStatusPageable(resourceStatus, pageable);
        
        model.addAttribute("resources", resourcePage.getContent());
        model.addAttribute("totalPages", resourcePage.getTotalPages());
        model.addAttribute("currentPage", resourcePage.getNumber());
        model.addAttribute("currentStatus", status);
        model.addAttribute("pendingCount", resourceService.countByStatus(ResourceStatus.PENDING));
        
        return "adminResources";
    }
}