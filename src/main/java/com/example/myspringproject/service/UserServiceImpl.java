package com.example.myspringproject.service;

import com.example.myspringproject.repo.UserRepository;
import com.example.myspringproject.web.dto.FilmsDto;
import com.example.myspringproject.web.dto.UserDto;
import com.example.myspringproject.web.dto.requests.RegisterRequest;
import com.example.myspringproject.web.dto.requests.UpdateUserRequest;
import com.example.myspringproject.web.entity.Films;
import com.example.myspringproject.web.entity.User;
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
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException(String.format("User with email [%s] not found", email)));
    }

    @Override
    public void updateUser(Long id, UpdateUserRequest request) {
//        userRepository.update(id, request);
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

//    @Override
//    public void updatePassword(long id, UpdatePasswordRequest request) throws SamePasswordException {
//        Optional<User> user = userRepository.findById(id);
//        if (user.isEmpty()) {
//            throw new UserNotFoundException(String.format("User with ID [%s] not found", id));
//        }
//        if (passwordEncoder.matches(request.getNewPassword(), user.get().getPassword())) {
//            throw new SamePasswordException("New and old passwords must be different");
//        }
//        String hashedPassword = passwordEncoder.encode(request.getNewPassword());
//        user.get().setPassword(hashedPassword);
//        userRepository.save(user.get());
//    }

//    @Override
//    public Optional<User> findById(long id) throws UserNotFoundException {
//        Optional<User> user = userRepository.findById(id);
//        if (user.isEmpty()) {
//            throw new UserNotFoundException(
//                    String.format("User with ID [%s] not found", id)
//            );
//        }
//        return user;
//    }
//
//    @Override
//    @Transactional
//    public void deleteUserById(long id) {
//        Optional<User> user = userRepository.findById(id);
//        if (user.isEmpty()) {
//            throw new UserNotFoundException(String.format("User with ID [%s] not found", id));
//        }
//        bookActionRepository.deleteAllByUser(user);
//        userRepository.deleteById(id);
//    }
//
//    @Override
//    public void updateUser(long id, UpdateUserRequest request) {
//        Optional<User> optionalUserByEmail = userRepository.findByEmail(request.getEmail());
//        if (optionalUserByEmail.isPresent()
//                && !optionalUserByEmail.get().getFirstName().equals(request.getFirstName())
//                && !optionalUserByEmail.get().getLastName().equals(request.getLastName())
//                && !optionalUserByEmail.get().getPhone().equals(request.getPhone())) {
//            throw new UserAlreadyExistsException(String.format("[%s] already exists! Consider another one.", request.getEmail()));
//        }
//
//        Optional<User> optionalUser = userRepository.findById(id);
//        if (optionalUser.isEmpty()) {
//            throw new UserNotFoundException(String.format("User with ID [%s] not found", id));
//        }
//        User user = optionalUser.get();
//        user.setEmail(request.getEmail());
//        user.setAge(request.getAge());
//        user.setFirstName(request.getFirstName());
//        user.setLastName(request.getLastName());
//        user.setPhone(request.getPhone());
//        user.setRole(request.getRole());
//        userRepository.save(user);
//    }
}

