package com.example.myspringproject.repo;

import com.example.myspringproject.web.entity.User;
import com.example.myspringproject.web.entity.UserRating;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface UserRatingRepository extends JpaRepository<UserRating, Long> {
//    @Query(nativeQuery = true, value = "DELETE FROM user_rating u WHERE u.user_id =:id")
//    void deleteAllByUser(User userId);

    void deleteAllByUserId(Optional<User> user);
}
