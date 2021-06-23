package com.example.myspringproject.service;

import com.example.myspringproject.repo.CommentRepository;
import com.example.myspringproject.repo.FilmRepository;
import com.example.myspringproject.repo.UserRepository;
import com.example.myspringproject.web.dto.requests.CommentRequest;
import com.example.myspringproject.web.entity.Comment;
import com.example.myspringproject.web.entity.Films;
import com.example.myspringproject.web.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {
    private final UserRepository userRepository;
    private final FilmRepository filmRepository;
    private final CommentRepository commentRepository;

    @Override
    public void addComment(CommentRequest request) {

        Optional<User> user = userRepository.findById(request.getUserId());
        Optional<Films> films = filmRepository.findById(request.getFilmId());
        LocalDate localDate = LocalDate.now();

        Comment comment = Comment.builder()
                .films(films.get())
                .comment(request.getComment())
                .user(user.get())
                .date(localDate)
                .build();

        commentRepository.save(comment);
    }

}
