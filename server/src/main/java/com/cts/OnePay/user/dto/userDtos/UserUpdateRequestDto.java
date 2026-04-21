package com.cts.OnePay.user.dto.userDtos;

import com.cts.OnePay.user.model.enums.Role;
import com.cts.OnePay.user.model.enums.VerificationStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserUpdateRequestDto {

    private String fullName;
    private String email;
    private Role role;

}
