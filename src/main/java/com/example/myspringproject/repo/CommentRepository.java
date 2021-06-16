package com.example.myspringproject.repo;

import com.example.myspringproject.web.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    @Transactional
    void deleteAllById(Long id);
}
