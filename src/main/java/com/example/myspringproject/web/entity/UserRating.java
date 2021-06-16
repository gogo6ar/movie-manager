package com.example.myspringproject.web.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Builder
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "user_rating")
public class UserRating {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Byte rating;

    @ManyToOne
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @JoinColumn(name = "user_id", nullable = false, updatable = false)
    private User userId;


    @OneToOne
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @JoinColumn(name = "user_voted_id", nullable = false, updatable = false)
    private User userVoteId;
}
