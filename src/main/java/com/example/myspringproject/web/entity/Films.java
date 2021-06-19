package com.example.myspringproject.web.entity;

import lombok.*;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import javax.servlet.annotation.HttpConstraint;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Entity
@Getter
@Builder
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "films")
public class Films {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String title;
    @Column(unique = true)
    private String idIMDb;
    private String imgLink;
    private Integer numberOfEpisodes;
    private String titleType;
    private Integer year;

    @OneToMany(mappedBy = "films")
    private List <Comment> comments = new ArrayList<>();

    @OneToMany(mappedBy = "films")
    private List<FilmEmotion> emotions = new ArrayList<>();

    @OneToMany(mappedBy = "films")
    private List<Category> categories = new ArrayList<>();

    public Map<EmotionType, Integer> getCountOfEmotions() {
        Map<EmotionType, Integer> map = new HashMap<>();
        int heartCount = 0;
        int sadCount = 0;
        if (emotions != null) {
            for (FilmEmotion emotion : emotions) {
                if (emotion.getEmotionType().equals(EmotionType.HEART)) {
                    heartCount++;
                }

                else if(emotion.getEmotionType().equals(EmotionType.SAD)) {
                    sadCount++;
                }
            }
        }

        map.put(EmotionType.HEART, heartCount);
        map.put(EmotionType.SAD, sadCount);

        return map;
    }
}
