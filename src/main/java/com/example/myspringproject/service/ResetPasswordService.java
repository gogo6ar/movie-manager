package com.example.myspringproject.service;

import com.example.myspringproject.web.dto.UserDto;

import javax.mail.MessagingException;
import java.io.UnsupportedEncodingException;

public interface ResetPasswordService {
    public boolean verify(String verificationCode);

    void resetPassword (Long id) throws MessagingException, UnsupportedEncodingException;

    UserDto changePasswordAfterReset(Long id, String newPassword) throws Exception;
}
