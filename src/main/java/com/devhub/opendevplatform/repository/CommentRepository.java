package com.devhub.opendevplatform.repository;

import com.devhub.opendevplatform.model.Comment;
import com.devhub.opendevplatform.model.Resource;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByResourceOrderByCreatedAtDesc(Resource resource);
    List<Comment> findByUserId(Long userId);
}