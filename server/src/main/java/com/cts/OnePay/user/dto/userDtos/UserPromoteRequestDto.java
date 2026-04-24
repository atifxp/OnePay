package com.cts.OnePay.user.dto.userDtos;

import com.cts.OnePay.user.model.enums.Role;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class UserPromoteRequestDto {
    @NotNull
    private Long userId;
    @NotNull
    private Role role;
}
