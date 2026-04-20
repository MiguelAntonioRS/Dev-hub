package com.devhub.opendevplatform.service.impl;

import com.devhub.opendevplatform.model.Comment;
import com.devhub.opendevplatform.model.Resource;
import com.devhub.opendevplatform.model.User;
import com.devhub.opendevplatform.repository.CommentRepository;
import com.devhub.opendevplatform.repository.ResourceRepository;
import com.devhub.opendevplatform.repository.UserRepository;
import com.devhub.opendevplatform.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private ResourceRepository resourceRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public Comment addComment(Comment comment) {
        return commentRepository.save(comment);
    }

    @Override
    public List<Comment> getCommentsByResource(Long resourceId) {
        Optional<Resource> resource = resourceRepository.findById(resourceId);
        return resource.map(commentRepository::findByResourceOrderByCreatedAtDesc)
                .orElse(List.of());
    }

    @Override
    public List<Comment> getCommentsByUser(Long userId) {
        return commentRepository.findByUserId(userId);
    }

    @Override
    public void deleteComment(Long id) {
        commentRepository.deleteById(id);
    }
}