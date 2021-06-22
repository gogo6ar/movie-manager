package com.example.myspringproject.service;

import com.example.myspringproject.repo.ResetPasswordRepository;
import com.example.myspringproject.repo.UserRepository;
import com.example.myspringproject.web.dto.UserDto;
import com.example.myspringproject.web.entity.ResetPassword;
import com.example.myspringproject.web.entity.User;
import lombok.RequiredArgsConstructor;
import net.bytebuddy.utility.RandomString;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ResetPasswordServiceImpl implements ResetPasswordService {
    private final UserRepository userRepository;
    private final ResetPasswordRepository resetPasswordRepository;
    private final JavaMailSender mailSender;
    private final PasswordEncoder passwordEncoder;


    @Override
    public void resetPassword (Long id) throws MessagingException, UnsupportedEncodingException {
        Optional<User> user = userRepository.findById(id);

        ResetPassword resetPassword = new ResetPassword();

        String randomCode = RandomString.make(64);
        resetPassword.setVerificationCode(randomCode);
        resetPassword.setEnabled(false);
        resetPassword.setUserId(user.get());
        resetPasswordRepository.save(resetPassword);

        sendVerificationEmail(user.get(), resetPassword);
    }

    @Override
    public UserDto changePasswordAfterReset(Long id, String newPassword) throws Exception {
        Optional<User> user = userRepository.findById(id);

        if (user.isEmpty()) {
            throw new UsernameNotFoundException("This user doesn't exist");
        }

        if (user.get().getPassword() != null) {
            throw new Exception("This user doesn't reset his password");
        }

        user.get().setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user.get());

        UserDto userDto = UserDto.from(user.get(), user.get().getListOfUserRating());

        return userDto;
    }

    private void sendVerificationEmail(User user, ResetPassword resetPassword)
            throws MessagingException, UnsupportedEncodingException {
        String siteURL = "http://localhost:8080/";
        String toAddress = user.getEmail();
        String fromAddress = "gherta.nicolai@gmail.co";
        String senderName = "Film-manager";
        String subject = "Please reset your password";
        String content = "Dear [[name]],<br>"
                + "Please click the link below to reset your password:<br>"
                + "<h3><a href=\"[[URL]]\" target=\"_self\">VERIFY</a></h3>"
                + "Thank you,<br>"
                + "Your company name.";

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        helper.setFrom(fromAddress, senderName);
        helper.setTo(toAddress);
        helper.setSubject(subject);

        content = content.replace("[[name]]", user.getFirstName() + " " + user.getLastName());
        String verifyURL = siteURL + "users/reset-password-check/"+ user.getId() +"?code=" + resetPassword.getVerificationCode();

        content = content.replace("[[URL]]", verifyURL);

        helper.setText(content, true);

        mailSender.send(message);
    }

    public boolean verify(String verificationCode) {
        ResetPassword resetPassword = resetPasswordRepository.findByVerificationCode(verificationCode);

        if (resetPassword == null || resetPassword.isEnabled()) {
            return false;
        } else {
            resetPassword.setVerificationCode(null);
            resetPassword.setEnabled(true);
            resetPasswordRepository.save(resetPassword);

            return true;
        }
    }

}
