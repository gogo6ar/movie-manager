package com.example.myspringproject.web.dto;

import com.example.myspringproject.web.entity.Category;
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
    private Integer numberOfEpisodes; //
    private String titleType;
    private Integer year;
    private List<CommentDto> listOfComments;
    private Map<EmotionType, Integer> emotionsCount;
    private List<String> categories;

    public static FilmsDto from(Films films, List<Comment> comments) {
        List<CommentDto> commentDtos = new ArrayList<>();
        if (comments != null && comments.size() != 0)
            for (Comment comment : comments) {
                CommentDto commentDto = CommentDto.from(comment);
                commentDtos.add(commentDto);
            }
        FilmsDto result = from(films);
        result.setListOfComments(commentDtos);

        return result;
    }

    public static FilmsDto from(Films films) {
        List<Category> categories = films.getCategories();
        List<String> categoryDto = new ArrayList<>();

        if (categories != null && categories.size() != 0) {
            for (Category category : categories) {
                categoryDto.add(category.getCategory());
            }
        }

        FilmsDto result = new FilmsDto();
        result.setId(films.getId());
        result.setTitle(films.getTitle());
        result.setIdIMDb(films.getIdIMDb());
        result.setImgLink(films.getImgLink());
        result.setTitleType(films.getTitleType());
        result.setYear(films.getYear());
        result.setNumberOfEpisodes(films.getNumberOfEpisodes());
        result.setEmotionsCount(films.getCountOfEmotions());
        result.setCategories(categoryDto);
        return result;
    }
}
