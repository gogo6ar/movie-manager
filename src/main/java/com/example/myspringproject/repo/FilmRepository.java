package com.example.myspringproject.repo;

import com.example.myspringproject.web.entity.Films;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface FilmRepository extends JpaRepository<Films, Long> {

    List<Films> findAllByTitle(String title);

    @Query(nativeQuery = true, value = "Select title from Films f where f.title =:title")
    List<String> findTitle(String title);

    @Query(nativeQuery = true, value = "Select idIMDb from Films f where f.idIMDb =:idIMDb")
    List<String> findIdIMDb(String idIMDb);

}
