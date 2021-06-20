package com.example.myspringproject.repo;

import com.example.myspringproject.web.entity.FilmEmotion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface FilmEmotionRepository extends JpaRepository<FilmEmotion, Long> {
    @Query(nativeQuery = true, value = "SELECT user_id from film_emotion where film_emotion.films_id = :films_id")
    List<Long> getUsersIdFromEmotions(@Param("films_id") Long films_id);

    void deleteAllByFilmsId(Long filmsId);

    void deleteAllByUserId(Long userId);

    List<FilmEmotion> getAllByUserId(Long userId);
}
