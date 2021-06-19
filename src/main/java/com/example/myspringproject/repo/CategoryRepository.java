package com.example.myspringproject.repo;

import com.example.myspringproject.web.entity.Category;
import com.example.myspringproject.web.entity.Films;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category,Long> {
    @Query(nativeQuery = true, value = "SELECT c.film_id FROM Category c WHERE c.category =:category")
    List<Long> findAllByCategory(String category);

}
