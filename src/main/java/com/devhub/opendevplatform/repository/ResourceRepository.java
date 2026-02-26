package com.devhub.opendevplatform.repository;

import com.devhub.opendevplatform.model.Resource;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ResourceRepository extends JpaRepository<Resource, Long> {

    List<Resource> findByTitleContainingIgnoreCase(String keyword);
}