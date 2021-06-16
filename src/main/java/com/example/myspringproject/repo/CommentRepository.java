package com.example.myspringproject.repo;

import com.example.myspringproject.web.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    void deleteAllById(Integer id);
}
