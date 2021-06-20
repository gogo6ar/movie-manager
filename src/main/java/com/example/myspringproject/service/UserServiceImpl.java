package com.example.myspringproject.service;

import com.example.myspringproject.repo.*;
import com.example.myspringproject.web.dto.UserDto;
import com.example.myspringproject.web.dto.requests.RegisterRequest;
import com.example.myspringproject.web.dto.requests.UpdatePasswordRequest;
import com.example.myspringproject.web.dto.requests.UpdateUserRequest;
import com.example.myspringproject.web.entity.Comment;
import com.example.myspringproject.web.entity.FilmEmotion;
import com.example.myspringproject.web.entity.User;
import com.example.myspringproject.web.entity.UserRating;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{
    private final UserRepository userRepository;
    private final UserRatingRepository userRatingRepository;
    private final FavouriteFilmsRepository favouriteFilmsRepository;
    private final PasswordEncoder passwordEncoder;
    private final CommentRepository commentRepository;
    private final FilmEmotionRepository filmEmotionRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException(String.format("User with email [%s] not found", email)));
    }

    @Override
    public void updateUser(Long id, UpdateUserRequest request) {
        Optional<User> existingUser = userRepository.findById(id);

        User user = User.builder()
                .id(id)
                .email(request.getEmail())
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .password(existingUser.get().getPassword())
                .role(request.getRole())
                .build();
        userRepository.save(user);
    }

    @Override
    public UserDto getUserById(Long id) {
        Optional<User> user = userRepository.findById(id);
        UserDto userDto = UserDto.from(user.get(), user.get().getListOfUserRating());
        return userDto;
    }

    @Override
    public List<UserDto> getTop10Users() {
        List<User> listOfUsers = userRepository.getTop10Users();
        List<UserDto> listOfUsersDto = new ArrayList<>();

        for (User user : listOfUsers) {
            listOfUsersDto.add(UserDto.from(user, user.getListOfUserRating()));
        }
        return listOfUsersDto;
    }

    @Override
    public List<UserDto> findAll() {
        return userRepository.findAll().stream().map(UserDto::from).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void deleteUserById(Long id) {
        Optional<User> user = userRepository.findById(id);

        if (user.isEmpty()) {
            throw new UsernameNotFoundException("This user doesn't exist");
        }
        User defaultUser = userRepository.getById(1L);

        favouriteFilmsRepository.deleteAllByUserId(id);

        List<UserRating> listOfUserRatingByUserId = userRatingRepository.getAllByUserId(user.get());
        if (listOfUserRatingByUserId != null && listOfUserRatingByUserId.size() > 0) {
            for (UserRating e : listOfUserRatingByUserId) {
                userRatingRepository.deleteAllByUserId(e.getUserId());
            }
        }
        List<UserRating> listOfUserRatingByUserVoteId = userRatingRepository.getAllByUserVoteId(user.get());
        if (listOfUserRatingByUserVoteId.size() > 0 && listOfUserRatingByUserVoteId != null) {
            for (UserRating e : listOfUserRatingByUserVoteId) {
                e.setUserVoteId(defaultUser);
                userRatingRepository.save(e);
            }
        }

        List<Comment> listOfComments = commentRepository.getAllByUserId(id);
        if (listOfComments != null && listOfComments.size() > 0) {
            for (int i = 0; i < listOfComments.size(); i++) {
                listOfComments.get(i).setUser(defaultUser);
                commentRepository.save(listOfComments.get(i));
            }
        }

        List<FilmEmotion> filmEmotion = filmEmotionRepository.getAllByUserId(id);
        if (filmEmotion != null && filmEmotion.size() > 0) {
            for (int i = 0; i < filmEmotion.size(); i++) {
                filmEmotion.get(i).setUser(defaultUser);
                filmEmotionRepository.save(filmEmotion.get(i));
            }
        }

        userRepository.deleteById(id);
    }

    @Override
    public void updatePassword(Long id, UpdatePasswordRequest request) throws Exception {
        Optional<User> user = userRepository.findById(id);
        if (!passwordEncoder.matches(request.getLastPassword(), user.get().getPassword())) {
            throw new Exception("Password is incorrect");
        }

        user.get().setPassword(request.getNewPassword());
        userRepository.save(user.get());
    }

    @Override
    public void resetPassword(Long id, String password) throws Exception {
        Optional<User> user = userRepository.findById(id);
        if (!passwordEncoder.matches(password, user.get().getPassword())) {
            throw new Exception("Password is incorrect");
        }
        //email
    }

    public UserDto create(RegisterRequest request) {
        Optional<User> existingUser = userRepository.findByEmail(request.getEmail());
        if (existingUser.isPresent()) {
            throw new UserAlreadyExistsException(String.format("User with [%s] already exists! Consider logging in.", request.getEmail()));
        }

        User user = User.builder()
                .email(request.getEmail())
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(request.getRole())
                .build();
        userRepository.save(user);
        UserDto userDto = UserDto.from(user, user.getListOfUserRating());
        return userDto;
    }
}

