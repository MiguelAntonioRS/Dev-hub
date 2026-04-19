package com.devhub.opendevplatform.api;

import com.devhub.opendevplatform.model.Resource;
import com.devhub.opendevplatform.service.ResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/resources")
public class ResourceApiController {

    @Autowired
    private ResourceService resourceService;

    @GetMapping
    public ResponseEntity<List<Resource>> getAllResources() {
        return ResponseEntity.ok(resourceService.listAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Resource> getResource(@PathVariable Long id) {
        return resourceService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/search")
    public ResponseEntity<List<Resource>> search(@RequestParam String q) {
        return ResponseEntity.ok(resourceService.search(q));
    }

    @GetMapping("/top")
    public ResponseEntity<List<Resource>> topVoted() {
        return ResponseEntity.ok(resourceService.findTopVoted());
    }

    @GetMapping("/recent")
    public ResponseEntity<List<Resource>> recent() {
        return ResponseEntity.ok(resourceService.findRecent());
    }
}