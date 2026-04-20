package com.devhub.opendevplatform.service;

import com.devhub.opendevplatform.model.Comment;
import java.util.List;

public interface CommentService {
    Comment addComment(Comment comment);
    List<Comment> getCommentsByResource(Long resourceId);
    List<Comment> getCommentsByUser(Long userId);
    void deleteComment(Long id);
}