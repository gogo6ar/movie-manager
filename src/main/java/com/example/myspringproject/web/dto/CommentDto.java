package com.example.myspringproject.web.dto;

import com.example.myspringproject.web.entity.Comment;
import lombok.*;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class CommentDto {
    private Long id;
    private String comment; //
    private UserDto user;
    private LocalDate localDate;


    public static CommentDto from(Comment comment) {
        UserDto userDto = UserDto.from(comment.getUser());

        CommentDto result = new CommentDto();
        result.setId(comment.getId());
        result.setComment(comment.getComment());
        result.setUser(userDto);
        result.setLocalDate(comment.getDate());
        return result;
    }
}
