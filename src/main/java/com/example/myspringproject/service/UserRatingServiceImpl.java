package com.example.myspringproject.service;

import com.example.myspringproject.repo.UserRatingRepository;
import com.example.myspringproject.repo.UserRepository;
import com.example.myspringproject.web.dto.requests.UserVoteRequest;
import com.example.myspringproject.web.entity.User;
import com.example.myspringproject.web.entity.UserRating;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserRatingServiceImpl implements UserRatingService {
    private final UserRatingRepository userRatingRepository;
    private final UserRepository userRepository;

    @Override
    public void vote(UserVoteRequest request) throws Exception {
        Optional<User> user = userRepository.findById(request.getUserId());
        List<UserRating> userRatingList = user.get().getListOfUserRating();
        for (UserRating rating: userRatingList) {
            if (rating.getUserVoteId().getId() == request.getUserVoteId()) {
                throw new Exception("EX");
            }
        }

        Optional<User> userVote = userRepository.findById(request.getUserVoteId());


        UserRating rating = UserRating.builder()
                .userVoteId(userVote.get())
                .userId(user.get())
                .rating(request.getRating())
                .build();

        userRatingRepository.save(rating);

        //Update rating column
        user.get().setRating(user.get().getUserRating());
        userRepository.save(user.get());
    }
}
