package com.cts.OnePay.user.dto;

import com.cts.OnePay.user.model.enums.VerificationStatus;
import com.cts.OnePay.user.model.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserResponseDto {

    private Long userId;


    private String fullName;


    private String email;


    private String phone;

    private Role role;

    private VerificationStatus accountStatus = VerificationStatus.PENDING;
}
