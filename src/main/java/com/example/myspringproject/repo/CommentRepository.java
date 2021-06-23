package com.example.myspringproject.repo;

import com.example.myspringproject.web.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;
import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    @Transactional
    void deleteAllById(Long id);

    void deleteAllByFilmsId(Long filmsId);

    void deleteAllByUserId(Long id);

    List<Comment> getAllByUserId(Long userId);
}
