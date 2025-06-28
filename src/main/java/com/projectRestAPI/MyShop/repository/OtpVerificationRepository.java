package com.projectRestAPI.MyShop.repository;

import com.projectRestAPI.MyShop.model.OtpVerification;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface OtpVerificationRepository extends BaseRepository<OtpVerification,Long> {

    @Transactional
    void deleteByEmail(String email);

    Optional<OtpVerification> findByEmail(String email);

    Optional<OtpVerification> findFirstByEmailOrderByExpirationTimeDesc(String email);

    Optional<OtpVerification> findByEmailAndPurpose(String email, String purpose);

}
