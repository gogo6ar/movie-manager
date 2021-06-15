package com.example.myspringproject.web.dto;

import com.example.myspringproject.web.entity.Comment;
import com.example.myspringproject.web.entity.Films;
import com.example.myspringproject.web.entity.User;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class CommentDto {
    private Long id;
    private String comment; //
    private UserDto user;
    private FilmsDto films;


    public static CommentDto from(Comment comment) {
        UserDto userDto = UserDto.from(comment.getUser());
        FilmsDto filmsDto = FilmsDto.from(comment.getFilms());

        CommentDto result = new CommentDto();
        result.setId(comment.getId());
        result.setComment(comment.getComment());
        result.setFilms(filmsDto);
        result.setUser(userDto);
        return result;
    }
}
