package com.example.myspringproject.repo;

import com.example.myspringproject.web.entity.Films;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FilmRepository extends JpaRepository<Films, Long> {

}
