package com.example.BaiTest.services.implement;

import com.example.BaiTest.components.JwtTokenUtils;
import com.example.BaiTest.components.LocalizationUtils;
import com.example.BaiTest.dtos.CourseDto;
import com.example.BaiTest.dtos.User.UserDTO;
import com.example.BaiTest.dtos.User.UserLoginDTO;
import com.example.BaiTest.exceptions.DataNotFoundException;

import com.example.BaiTest.model.*;
import com.example.BaiTest.repository.RefreshTokensRepo;
import com.example.BaiTest.repository.RolesRepo;
import com.example.BaiTest.repository.UserRepo;
import com.example.BaiTest.responses.ListUserResponse;
import com.example.BaiTest.responses.LoginResponse;
import com.example.BaiTest.responses.UserResponse;
import com.example.BaiTest.services.iservices.IUserService;
import com.example.BaiTest.utils.MessageKeys;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.UUID;
import java.util.function.Supplier;

import java.awt.print.Pageable;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class UserService implements IUserService {
    private final UserRepo userRepository;
    private final RolesRepo roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenUtils jwtTokenUtil;
    private final AuthenticationManager authenticationManager;
    private final LocalizationUtils localizationUtils;
    private final UserDetailsService userDetailsService;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private RefreshTokensRepo refreshTokensRepo;

    @Value("${jwt.expirationRefreshToken}")
    private int expirationRefreshToken;


    @Override
    public User createUser(UserDTO userDTO) throws Exception {
        //register user
        String email = userDTO.getEmail();
        // Kiểm tra xem email đã tồn tại hay chưa
        if(userRepository.existsByEmail(email)) {
            throw new DataIntegrityViolationException("Email already exists");
        }
        Roles userRole = roleRepository.findById(1).orElseThrow(()
                -> new IllegalStateException("Role not found with ID 2"));
        //convert from userDTO => user
        User newUser = User.builder()
                .avatar(" ")
                .email(userDTO.getEmail())
                .userName(userDTO.getUserName())
                .password(passwordEncoder.encode(userDTO.getPassword()))
                .address(userDTO.getAddress())
                .dateOfBirth(userDTO.getDateOfBirth())
                .gender(userDTO.getGender())
                .firstName(userDTO.getFirstName())
                .lastName(userDTO.getLastName())
                .isActive(1)
                .role(userRole)
                .build();

        // Kiểm tra nếu có accountId, không yêu cầu password
//        if (userDTO.getFacebookAccountId() == 0 && userDTO.getGoogleAccountId() == 0) {
//            String password = userDTO.getPassword();
//            String encodedPassword = passwordEncoder.encode(password);
//            newUser.setPassword(encodedPassword);
//        }
        return userRepository.save(newUser);
    }

    @Override
    public UserResponse getAllUser(int numberPage, int limit) {
        PageRequest pageRequest = PageRequest.of(numberPage -1, limit);
        Page<User> userPage = userRepository.findAll(pageRequest);

        List<ListUserResponse> listUserResponse = userPage.getContent()
                .stream()
                .map((user) -> this.userToListUserResponse(user))
                .collect(Collectors.toList());

        return new UserResponse(listUserResponse,
                                userPage.getTotalPages(),
                                userPage.getTotalElements(),
                                userPage.getNumber(),
                                userPage.getSize());
    }
    @Override
    public LoginResponse login(
            UserLoginDTO userLoginDTO
    ) throws Exception {
        User existingUser = userRepository.findByEmail(userLoginDTO.getEmail()).orElse(null);
        if(existingUser == null) {
            throw new DataNotFoundException(MessageKeys.EMAIL_DOES_NOT_EXISTS);
        }
        if(existingUser.getIsActive() == 0){
            throw new DisabledException(MessageKeys.USER_ACCOUNT_IS_DISABLED);
        }
        //lấy ra user
//        User existingUser = optionalUser.get();


        //Chuyền email,password, role vào authenticationToken để xac thực ngươi dùng
        UsernamePasswordAuthenticationToken auToken = new UsernamePasswordAuthenticationToken(
                userLoginDTO.getEmail(),
                userLoginDTO.getPassword(),
                existingUser.getAuthorities()
        );
        //Xác thực người dùng (nếu xác thực không thành công VD: sai pass ) thì sẽ ném ra ngoại lệ
        authenticationManager.authenticate(auToken);

        //Lấy role của user
        User userDetails = (User) userDetailsService.loadUserByUsername(userLoginDTO.getEmail());
        List<String> roles = userDetails.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList());

        //sinh ngẫu nhiên 1 token từ existingUser
        String token = jwtTokenUtil.generateToken(existingUser);

        RefreshTokens refreshTokens = new RefreshTokens();
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        refreshTokens.setUser(existingUser);
        refreshTokens.setToken(UUID.randomUUID().toString());
        refreshTokens.setModifiedDate(timestamp);
        refreshTokens.setExpiredDate(Timestamp.from(Instant.now().plusSeconds(expirationRefreshToken)));
        refreshTokensRepo.save(refreshTokens);

        LoginResponse loginResponse = LoginResponse.builder()
                .id(existingUser.getId())
                .userName(existingUser.getUserName())
                .token(token)
                .refreshToken(refreshTokens.getToken())
                .roles(roles)
                .build();
        return loginResponse;
    }

    @Override
    public long getTotalUserCount() {
        return userRepository.count();
    }

    @Override
    public long getTotalUserLocked() {
        long totalUserlocked = 0;
        for (User user: userRepository.findAll()){
            if(user.getIsActive() == 0 ){
                totalUserlocked ++;
            }
        }
        return totalUserlocked;
    }

    @Override
    public boolean lockUser(int userID) throws Exception {
            User user = userRepository.findById(userID).orElse(null);
            if(user != null){
                if (user.getIsActive() != 0 ){
                    user.setIsActive(0);
                    userRepository.save(user);
                    return true;
                } else {
                    return false;
                }
            }
        throw new DataNotFoundException(MessageKeys.EMAIL_DOES_NOT_EXISTS);
    }

    public LoginResponse userToLoginResponse(User user){
        return modelMapper.map(user, LoginResponse.class);
    }

    public ListUserResponse userToListUserResponse(User user){
        ListUserResponse listUserResponse = ListUserResponse.builder()
                .id(user.getId())
                .userName(user.getUserName())
                .address(user.getAddress())
                .gender(user.getGender())
                .isActive(user.getIsActive())
                .lastName(user.getLastName())
                .firstName(user.getFirstName())
                .email(user.getEmail())
                .role(user.getRole().getAuthorityName())
                .dateOfBirth(user.getDateOfBirth())
                .avatar(user.getAvatar())
                .build();
        return listUserResponse;
    }
}
