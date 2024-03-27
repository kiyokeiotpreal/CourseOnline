package com.example.BaiTest.services.implement;

import com.example.BaiTest.dtos.TokenRefreshRequest;
import com.example.BaiTest.exceptions.TokenRefreshException;
import com.example.BaiTest.model.RefreshTokens;
import com.example.BaiTest.model.User;
import com.example.BaiTest.repository.RefreshTokensRepo;
import com.example.BaiTest.repository.UserRepo;
import com.example.BaiTest.responses.TokenRefreshResponse;
import com.example.BaiTest.services.iservices.IRefreshToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Service
public class RefreshTokenService implements IRefreshToken {
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private RefreshTokensRepo refreshTokensRepo;

    @Value("${jwt.expirationRefreshToken}")
    private int expirationRefreshToken;

    public Optional<RefreshTokens> findByToken(String refreshToken) {
        return refreshTokensRepo.findByToken(refreshToken);
    }

    public RefreshTokens createRefreshToken(int userId){
        RefreshTokens refreshTokens = new RefreshTokens();

        refreshTokens.setToken(UUID.randomUUID().toString());
        refreshTokens.setExpiredDate(Timestamp.from(Instant.now().plusSeconds(expirationRefreshToken)));
        refreshTokens.setUser(userRepo.findById(userId).get());

        refreshTokens = refreshTokensRepo.save(refreshTokens);
        return refreshTokens;
    }

    public RefreshTokens verifyExpiration(RefreshTokens refreshToken) {
        if (refreshToken.getExpiredDate().compareTo(Timestamp.from(Instant.now())) < 0) {
            refreshTokensRepo.delete(refreshToken);
            throw new TokenRefreshException(refreshToken.getToken(), "Refresh token was expired. Please make a new signin request");
        }
        return refreshToken;
    }

    @Transactional
    public int deleteByUserId(int userId) {
        return refreshTokensRepo.deleteByUser(userRepo.findById(userId).get());
    }

//    @Override
//    public TokenRefreshResponse refreshToken(TokenRefreshRequest tokenRefreshRequest) {
//        String requestRefreshToken = tokenRefreshRequest.getRefreshToken();
//        String accessToken = tokenRefreshRequest.getAccessToken();
//
//
//        Optional<RefreshTokens> refreshTokens = refreshTokensRepo.findByToken(requestRefreshToken);
//        RefreshTokens refreshTokens1 = refreshTokens.get();
//        User user = userRepo.findByRefreshTokens(refreshTokens1);
//        if(refreshTokens.isPresent()){
//            try {
//                String token = jwtTokenUtils.generateToken(user);
//                return ResponseEntity.ok(new TokenRefreshResponse(token, requestRefreshToken,"Bearer"));
//            } catch (Exception e) {
//                throw new RuntimeException(e);
//            }
//        }
//        return null;
//    }
}
