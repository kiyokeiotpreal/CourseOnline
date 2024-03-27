package com.example.BaiTest.repository;

import com.example.BaiTest.model.RefreshTokens;
import com.example.BaiTest.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RefreshTokensRepo extends JpaRepository<RefreshTokens, Integer> {
    Optional<RefreshTokens> findByToken(String refreshToken);
    @Modifying
    int deleteByUser(User user);



}
