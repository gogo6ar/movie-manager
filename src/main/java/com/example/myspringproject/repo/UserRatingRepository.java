package com.example.myspringproject.repo;

import com.example.myspringproject.web.entity.User;
import com.example.myspringproject.web.entity.UserRating;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRatingRepository extends JpaRepository<UserRating, Long> {
//    @Query(nativeQuery = true, value = "DELETE FROM user_rating u WHERE u.user_id =:id")
//    void deleteAllByUser(User userId);

    void deleteAllByUserId(User userId);

    List<UserRating> getAllByUserId(User userId);

    List<UserRating>getAllByUserVoteId(User userVoteId);
}
