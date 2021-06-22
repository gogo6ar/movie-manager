package com.example.myspringproject.repo;

import com.example.myspringproject.web.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category,Long> {
    @Query(nativeQuery = true, value = "SELECT c.film_id FROM Category c WHERE c.category =:category")
    List<Long> findAllByCategory(String category);

//    @Transactional
//    @Query(nativeQuery = true, value = "Delete FROM category c WHERE c.film_id =:id")

    void deleteAllByFilmsId(Long filmsId);

    @Query(nativeQuery = true, value = "SELECT count(c.category) FROM category c GROUP BY c.category ORDER BY count(c.category) Desc")
    List<Integer> countCategories();

    @Query(nativeQuery = true, value = "SELECT c.category FROM category c GROUP BY c.category ORDER BY count(c.category) Desc")
    List<String> titleCategories();
}
