package com.cts.OnePay.user.dto.userDtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserRegisterRequest {
    @NotNull
    private String fullName;

    @Email(message = "Please provide valid email")
    @NotNull
    private String email;

    @NotNull
    @Size(min = 10,message = "Phone number should be of 10 digits")
    private String phone;

    @NotNull
    private String passwordHash;
}
