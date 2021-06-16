package com.example.myspringproject.repo;

import com.example.myspringproject.web.entity.UserRating;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRatingRepository extends JpaRepository<UserRating, Long> {
}
