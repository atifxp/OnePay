package com.cts.OnePay.user.dto.userDtos;

import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserLoginRequestDto {
    @Min(value = 10, message = "Phone number should be of 10 digits")
    private String phone;
    private String password;
}

