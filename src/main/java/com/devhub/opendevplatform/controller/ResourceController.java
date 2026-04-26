package com.devhub.opendevplatform.controller;

import com.devhub.opendevplatform.model.Resource;
import com.devhub.opendevplatform.model.Resource.ResourceStatus;
import com.devhub.opendevplatform.repository.ResourceRepository;
import com.devhub.opendevplatform.repository.UserRepository;
import com.devhub.opendevplatform.repository.VoteRepository;
import com.devhub.opendevplatform.service.ResourceService;
import com.devhub.opendevplatform.service.CommentService;
import com.devhub.opendevplatform.service.RatingService;
import com.devhub.opendevplatform.model.Comment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/resources")
public class ResourceController {

    @Autowired
    private ResourceService resourceService;

    @Autowired
    private ResourceRepository resourceRepository;

    @Autowired
    private VoteRepository voteRepository;

    @Autowired
    private CommentService commentService;

    @Autowired
    private RatingService ratingService;

    @Autowired
    private UserRepository userRepository;

    @GetMapping
    public String listResources(
            @RequestParam(required = false) String category,
            @RequestParam(required = false) String sort,
            @RequestParam(required = false) String q,
            @RequestParam(required = false) String pending,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "12") int size,
            Model model) {
        
        Pageable pageable = PageRequest.of(page, size, Sort.by("votes").descending());
        Page<Resource> resourcePage;

        // If user wants to view pending resources
        if (pending != null && pending.equals("true")) {
            resourcePage = resourceService.findByStatusPageable(ResourceStatus.PENDING, pageable);
        } else if (q != null && !q.isEmpty()) {
            List<Resource> resources = resourceService.search(q);
            model.addAttribute("searchQuery", q);
            model.addAttribute("resources", resources);
            model.addAttribute("totalPages", 0);
            model.addAttribute("currentPage", 0);
            return "resources";
        } else if ("recent".equals(sort)) {
            pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());
            resourcePage = resourceService.findAllPageable(pageable);
        } else if (category != null && !category.isEmpty()) {
            List<Resource> resources = resourceService.findByCategory(category);
            model.addAttribute("resources", resources);
            model.addAttribute("totalPages", 0);
            model.addAttribute("currentPage", 0);
            return "resources";
        } else {
            resourcePage = resourceService.findAllPageable(pageable);
        }
        
        model.addAttribute("resources", resourcePage.getContent());
        model.addAttribute("totalPages", resourcePage.getTotalPages());
        model.addAttribute("currentPage", resourcePage.getNumber());
        model.addAttribute("currentCategory", category);
        model.addAttribute("currentSort", sort);
        return "resources";
    }

    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("resource", new Resource());
        return "addResource";
    }

    @PostMapping("/add")
    public String addResource(@ModelAttribute Resource resource) {
        resource.setStatus(ResourceStatus.APPROVED);
        resourceService.addResource(resource);
        return "redirect:/resources";
    }

    @GetMapping("/{id}")
    public String getResourceDetail(@PathVariable Long id, Model model) {
        Resource resource = resourceService.getResourceById(id);
        List<Comment> comments = commentService.getCommentsByResource(id);
        model.addAttribute("resource", resource);
        model.addAttribute("comments", comments);
        model.addAttribute("averageRating", ratingService.getAverageRating(resource));
        
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && auth.getName() != null) {
            var user = userRepository.findByUsername(auth.getName()).orElse(null);
            if (user != null) {
                model.addAttribute("currentUserId", user.getId());
                model.addAttribute("userRating", ratingService.getUserRating(user, resource));
            }
        }
        return "resourceDetail";
    }

    @PostMapping("/approve/{id}")
    public String approveResource(@PathVariable Long id) {
        resourceService.updateStatus(id, ResourceStatus.APPROVED);
        return "redirect:/admin/resources";
    }

    @PostMapping("/reject/{id}")
    public String rejectResource(@PathVariable Long id) {
        resourceService.updateStatus(id, ResourceStatus.REJECTED);
        return "redirect:/admin/resources";
    }

    @PostMapping("/delete/{id}")
    public String deleteResource(@PathVariable Long id) {
        voteRepository.deleteByResourceId(id);
        resourceRepository.deleteById(id);
        return "redirect:/resources";
    }
}
