package com.example.myspringproject.web.dto;

import com.example.myspringproject.web.entity.User;
import com.example.myspringproject.web.entity.UserRating;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserRatingDto {
    private Long id;
//    private UserDto userId;
    private UserDto userVoteId;
    private Byte rating;

    public static UserRatingDto from(UserRating userRating) {
        UserDto userDto = UserDto.from(userRating.getUserId());
        UserDto userVoteDto = UserDto.from(userRating.getUserVoteId());

        UserRatingDto result = new UserRatingDto();
        result.setId(userRating.getId());
        result.setRating(userRating.getRating());
//        result.setUserId(userDto);
        result.setUserVoteId(userVoteDto);
        return result;
    }
}
