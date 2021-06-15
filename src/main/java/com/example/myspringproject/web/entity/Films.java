package com.example.myspringproject.web.entity;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "films")
public class Films {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    private String title;
    private String idIMDb;
    private String imgLink;
    private String numberOfEpisodes;
    private String titleType;
    private String year;

    @OneToMany(mappedBy = "films")
    private List <Comment> comments = new ArrayList<>();


}
