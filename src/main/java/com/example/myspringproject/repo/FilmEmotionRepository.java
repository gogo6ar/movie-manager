package com.example.myspringproject.repo;

import com.example.myspringproject.web.entity.FilmEmotion;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FilmEmotionRepository extends JpaRepository<FilmEmotion, Long> {
}
