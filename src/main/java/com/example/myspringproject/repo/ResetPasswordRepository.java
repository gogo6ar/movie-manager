package com.example.myspringproject.repo;

import com.example.myspringproject.web.entity.ResetPassword;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ResetPasswordRepository extends JpaRepository<ResetPassword, Long> {

    @Query(nativeQuery = true, value = "SELECT u FROM reset_password u WHERE u.verificationCode = ?1")
    ResetPassword findByVerificationCode(String code);
}
