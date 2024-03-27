package com.example.BaiTest.repository;

import com.example.BaiTest.model.RefreshTokens;
import com.example.BaiTest.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepo extends JpaRepository<User, Integer> {
    boolean existsByEmail(String email);
    Optional<User> findByEmail(String email);
    User findOneByEmailAndPassword(String email, String password);

    User findByRefreshTokens(RefreshTokens refreshTokens);
    long countByIsActive(int isActive);

}
