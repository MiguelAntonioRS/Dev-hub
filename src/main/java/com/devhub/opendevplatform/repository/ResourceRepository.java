package com.devhub.opendevplatform.repository;

import com.devhub.opendevplatform.model.Resource;
import com.devhub.opendevplatform.model.Resource.ResourceStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ResourceRepository extends JpaRepository<Resource, Long> {

    List<Resource> findByTitleContainingIgnoreCase(String keyword);

    List<Resource> findByCategory(String category);

    @Query("SELECT r FROM Resource r WHERE r.status = 'APPROVED' ORDER BY r.votes DESC")
    List<Resource> findTopVoted();

    @Query("SELECT r FROM Resource r WHERE r.status = 'APPROVED' ORDER BY r.createdAt DESC")
    List<Resource> findRecent();

    List<Resource> findByStatus(ResourceStatus status);

    List<Resource> findByAuthor(String author);

    @Query("SELECT r FROM Resource r WHERE r.status = 'APPROVED' AND (r.category = :category OR :category IS NULL) ORDER BY r.votes DESC")
    List<Resource> findByCategoryAndStatus(@Param("category") String category);

    @Query("SELECT r FROM Resource r WHERE r.status = 'APPROVED' AND (LOWER(r.title) LIKE LOWER(CONCAT('%', :query, '%')) OR LOWER(r.description) LIKE LOWER(CONCAT('%', :query, '%'))) ORDER BY r.votes DESC")
    List<Resource> search(@Param("query") String query);

    @Query("SELECT r FROM Resource r WHERE r.status = 'APPROVED'")
    Page<Resource> findAllApproved(Pageable pageable);

    @Query("SELECT r FROM Resource r WHERE r.status = :status")
    Page<Resource> findByStatusPageable(@Param("status") ResourceStatus status, Pageable pageable);

    long countByStatus(ResourceStatus status);
}