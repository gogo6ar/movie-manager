package com.example.myspringproject.repo;

import com.example.myspringproject.web.entity.UserRating;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserRatingRepository extends JpaRepository<UserRating, Long> {
//    @Query(nativeQuery = true, value = "DELETE FROM user_rating u WHERE u.user_id =:id")
    void deleteAllByUserId(Long id);
}
