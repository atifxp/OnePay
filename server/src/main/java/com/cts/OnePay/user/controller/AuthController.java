package com.cts.OnePay.user.controller;

import com.cts.OnePay.user.dto.userDtos.UserLoginRequestDto;
import com.cts.OnePay.user.dto.userDtos.UserRegisterRequest;
import com.cts.OnePay.user.dto.userDtos.UserResponseDto;
import com.cts.OnePay.user.model.MyUserDetails;
import com.cts.OnePay.user.model.User;
import com.cts.OnePay.user.service.AuthService;
import com.cts.OnePay.user.service.JwtService;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    @Value("${app.jwt.refresh-expiration-ms}")
    private long refreshExpiration;

    private final AuthService authService;
    private final JwtService jwtService;

    @PostMapping("/register")
    public ResponseEntity<Map<String,String>> register(@RequestBody @Valid UserRegisterRequest user){
        Map<String, String> response = authService.register(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody UserLoginRequestDto dto, HttpServletRequest request){
        String ipAddress = request.getRemoteAddr();
        Map<String,Object> response = authService.login(dto,ipAddress);

        String token = response.get("access-token").toString();
        //age of cookie = age of refresh token
        long maxAgeSeconds =  refreshExpiration/1000;

        ResponseCookie cookie = ResponseCookie.from("access-token", token)
                .httpOnly(true)
                .secure(true)
                .sameSite("None")
                .path("/")
                .maxAge(maxAgeSeconds)      //age of cookie before browser deletes it
                .build();

        response.remove("access-token");

        return ResponseEntity.status(HttpStatus.OK).header(HttpHeaders.SET_COOKIE,cookie.toString()).body(response);
    }

    @PostMapping("/refresh")
    public ResponseEntity<?> refresh(HttpServletRequest req){

        String accessToken = jwtService.extractCookie("access-token",req);
        String phoneNo = null;
        ResponseCookie cookie = null;
        Map<String,Object> response = new HashMap<>();

        try{
            phoneNo = jwtService.extractPhoneNo(accessToken);
            response.put("message", "Session still active, no need to refresh");
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(response);

        } catch (ExpiredJwtException e) {
            //get claims from exposed claims
            phoneNo = e.getClaims().get("phone",String.class);

            response = authService.refresh(phoneNo);
            //set new cookie

            String newAccessToken = response.get("access-token").toString();
            long maxAgeSeconds =  refreshExpiration/1000;

            cookie = ResponseCookie.from("access-token", newAccessToken)
                    .httpOnly(true)
                    .secure(true)
                    .sameSite("None")
                    .path("/")
                    .maxAge(maxAgeSeconds)      //age of cookie before browser deletes it
                    .build();

            response.remove("access-token");
        }





        return ResponseEntity.status(HttpStatus.OK).header(HttpHeaders.SET_COOKIE,cookie.toString()).body(response);
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(@AuthenticationPrincipal MyUserDetails userDetails, HttpServletRequest req, HttpServletResponse res){

        Map<String, String> response = authService.logout(userDetails.getUserId());
        ResponseCookie deleteCookie = ResponseCookie.from("access-token", "")
                .httpOnly(true)
                .secure(true)
                .sameSite("None")
                .path("/")
                .maxAge(0)
                .build();

        return ResponseEntity.status(HttpStatus.OK).header(HttpHeaders.SET_COOKIE,deleteCookie.toString()).body(response);
    }

}
