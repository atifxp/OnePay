package com.cts.OnePay.user.service;

import com.cts.OnePay.user.dto.userDtos.UserLoginRequestDto;
import com.cts.OnePay.user.dto.userDtos.UserResponseDto;
import com.cts.OnePay.user.model.Session;
import com.cts.OnePay.user.model.User;
import com.cts.OnePay.user.repository.SessionRepository;
import com.cts.OnePay.user.repository.UserRepository;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.session.SessionAuthenticationException;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

@Service
@AllArgsConstructor
public class AuthServiceImpl implements AuthService{


    private final UserRepository userRepository;
    private final SessionRepository sessionRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public Map<String, String> register(User newUser){
        //check if user exists
        userRepository.findByPhone(newUser.getPhone())
                .orElseThrow(()-> new EntityExistsException("User is already registered"));

        newUser.setPasswordHash(passwordEncoder.encode(newUser.getPasswordHash()));
        userRepository.save(newUser);
        return Map.of("message", "Registration Successful");

    }

    @Transactional(rollbackOn = Exception.class)
    public Map<String, Object> login(UserLoginRequestDto user, String ipAddress){

        String accessToken = null;
        String refreshToken = null;

        //check for user
        User exists = userRepository.findByPhone(user.getPhone())
                .orElseThrow(() -> new AuthenticationCredentialsNotFoundException("User is not Registered"));

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(exists.getPhone(), user.getPassword())
        );

        if(authentication.isAuthenticated()){
            accessToken = jwtService.generateAccessToken(exists);
            refreshToken = jwtService.generateRefreshToken(exists);

            //Fetch any existing session/Create new session & save refreshToken
            Session newSession = sessionRepository.findByUser_UserId(exists.getUserId())
                    .orElseGet(() -> new Session());

            newSession.setUser(exists);
            newSession.setRefreshToken(refreshToken);
            newSession.setExpiresAt(jwtService.extractClaims(refreshToken).getExpiration().toInstant());
            newSession.setIpAddress(ipAddress);

            //save session
            sessionRepository.save(newSession);

        }

        return Map.of(
                "accessToken", accessToken,
                "user", modelMapper.map(exists, UserResponseDto.class),
                "message", "Login Successful"
        );
    }

    public Map<String, Object> refresh(String phoneNo){
        //check if user exists or not
        User exists = userRepository.findByPhone(phoneNo)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));

        //check for session
        Session fetchedSession = sessionRepository.findByUser_UserId(exists.getUserId())
                .orElseThrow(()-> new SessionAuthenticationException("Session Expired"));

        //refresh token expired
        if(fetchedSession.getExpiresAt().isBefore(Instant.now())){
            sessionRepository.delete(fetchedSession);
            throw new SessionAuthenticationException("Session Expired");
        }

        //refresh token not expired
        String newAccessToken = jwtService.generateAccessToken(exists);

        return Map.of(
                "access-token", newAccessToken,
                "message", "Session Restored Successfully"
        );
    }

    public Map<String,String> logout(Long userId){
        //delete session
        Session session = sessionRepository.findByUser_UserId(userId)
                .orElseThrow(()-> new SessionAuthenticationException("Session does not exist"));

        sessionRepository.delete(session);

        return Map.of("message", "Logged out Successfully");
    }

}
