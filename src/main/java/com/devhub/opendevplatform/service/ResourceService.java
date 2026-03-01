package com.devhub.opendevplatform.service;

import com.devhub.opendevplatform.model.Resource;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public interface ResourceService {

    Resource addResource(Resource resource);

    List<Resource> searchResources(String keyword);

    List<Resource> listAll();

    Optional<Resource> findById(Long id);

    Resource getResourceById(Long id);
}
