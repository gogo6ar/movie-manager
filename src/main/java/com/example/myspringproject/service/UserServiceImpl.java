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
import net.bytebuddy.utility.RandomString;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.transaction.Transactional;
import java.io.UnsupportedEncodingException;
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
    private final JavaMailSender mailSender;

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

    public UserDto create(RegisterRequest request) throws MessagingException, UnsupportedEncodingException {
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

        String randomCode = RandomString.make(64);
        user.setVerificationCode(randomCode);
        user.setEnabled(false);
        userRepository.save(user);

        UserDto userDto = UserDto.from(user, user.getListOfUserRating());

        sendVerificationEmail(user, "http://localhost:8080/");


        return userDto;
    }

    private void sendVerificationEmail(User user, String siteURL)
            throws MessagingException, UnsupportedEncodingException {
        String toAddress = user.getEmail();
        String fromAddress = "gherta.nicolai@gmail.co";
        String senderName = "Film-manager";
        String subject = "Please verify your registration";
        String content = "Dear [[name]],<br>"
                + "Please click the link below to verify your registration:<br>"
                + "<h3><a href=\"[[URL]]\" target=\"_self\">VERIFY</a></h3>"
                + "Thank you,<br>"
                + "Your company name.";

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        helper.setFrom(fromAddress, senderName);
        helper.setTo(toAddress);
        helper.setSubject(subject);

        content = content.replace("[[name]]", user.getFirstName() + " " + user.getLastName());
        String verifyURL = siteURL + "users/verify?code=" + user.getVerificationCode();

        content = content.replace("[[URL]]", verifyURL);

        helper.setText(content, true);

        mailSender.send(message);
    }

    @Override
    public boolean verify(String verificationCode) {
        User user = userRepository.findByVerificationCode(verificationCode);

        if (user == null || user.isEnabled()) {
            return false;
        } else {
            user.setVerificationCode(null);
            user.setEnabled(true);
            userRepository.save(user);

            return true;
        }

    }
}

