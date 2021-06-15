package com.example.myspringproject.web.dto;

import com.example.myspringproject.web.entity.Comment;
import com.example.myspringproject.web.entity.EmotionType;
import com.example.myspringproject.web.entity.Films;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class FilmsDto {
    private Long id;
    private String title; //
    private String idIMDb;//
    private String imgLink;
    private String numberOfEpisodes; //
    private String titleType;
    private String year;
    private List<CommentDto> listOfComments;
    private Map<EmotionType, Integer> emotionsCount;

    public static FilmsDto from(Films films, List<Comment> comments) {
        List<CommentDto> commentDtos = new ArrayList<>();
        for (Comment comment : comments) {
            CommentDto commentDto = CommentDto.from(comment);
            commentDtos.add(commentDto);
        }
        FilmsDto result = from(films);
        result.setListOfComments(commentDtos);

        return result;
    }

    public static FilmsDto from(Films films) {
        FilmsDto result = new FilmsDto();
        result.setId(films.getId());
        result.setTitle(films.getTitle());
        result.setIdIMDb(films.getIdIMDb());
        result.setImgLink(films.getImgLink());
        result.setTitleType(films.getTitleType());
        result.setYear(films.getYear());
        result.setNumberOfEpisodes(films.getNumberOfEpisodes());
        result.setEmotionsCount(films.getCountOfEmotions());
        return result;
    }
}
