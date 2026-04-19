package com.devhub.opendevplatform.service;

import com.devhub.opendevplatform.model.Resource;
import com.devhub.opendevplatform.model.Resource.ResourceStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface ResourceService {

    Resource addResource(Resource resource);

    List<Resource> searchResources(String keyword);

    List<Resource> listAll();

    Optional<Resource> findById(Long id);

    Resource getResourceById(Long id);

    List<Resource> findTopVoted();

    List<Resource> findRecent();

    List<Resource> findByCategory(String category);

    List<Resource> findByAuthor(String author);

    List<Resource> search(String query);

    Page<Resource> findAllPageable(Pageable pageable);

    Page<Resource> findByStatusPageable(ResourceStatus status, Pageable pageable);

    Resource updateStatus(Long id, ResourceStatus status);

    long countByStatus(ResourceStatus status);
}
