package com.example.myspringproject.repo;

import com.example.myspringproject.web.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    @Transactional
    void deleteAllById(Long id);

//    @Transactional
//    @Query(nativeQuery = true, value = "Delete FROM comments c WHERE c.film_id =:id")
//    void deleteAllByFilmId(Long filmId);

    void deleteAllByFilmsId(Long filmsId);
}
