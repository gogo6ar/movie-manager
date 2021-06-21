package com.example.myspringproject.service;

import javax.mail.MessagingException;
import java.io.UnsupportedEncodingException;

public interface ResetPasswordService {
    void resetPassword (Long id) throws MessagingException, UnsupportedEncodingException;
}
