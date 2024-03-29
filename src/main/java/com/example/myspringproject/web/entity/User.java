package com.example.myspringproject.web.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity
@Getter
@Builder
@Setter
@AllArgsConstructor
@Table(name = "users")
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;
    @Column(name = "first_name")
    private String firstName;
    @Column(name = "last_name")
    private String lastName;
    private String password;
    private String role;
    private Double rating;


    @Column(name = "verification_code", length = 64)
    private String verificationCode;

    @Column(name = "enabled")
    private boolean enabled;

    public User() {
        super();
        this.enabled=false;
    }

    @OneToMany(mappedBy = "userId")
    private List<UserRating> listOfUserRating = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<FavouritesFilms> listOfFavouritesFilms = new ArrayList<>();


    public Double getUserRating(Byte lastVote) {
        Double userRating = lastVote * 1.0;
        Integer count = 1;

       if (listOfUserRating != null) {
           for(UserRating rating : listOfUserRating) {
               userRating += rating.getRating();
               count++;
           }
       }
        userRating /= count;


        return userRating;
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (!(o instanceof User))
            return false;

        User other = (User) o;

        return id != null &&
                id.equals(other.getId());
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled () {
        return enabled;
    }
}
