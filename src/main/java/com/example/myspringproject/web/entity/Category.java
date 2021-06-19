package com.example.myspringproject.web.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@Table(name = "category")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @JoinColumn(name = "film_id", nullable = false, updatable = false)
    private Films films;

    private String category;
}
