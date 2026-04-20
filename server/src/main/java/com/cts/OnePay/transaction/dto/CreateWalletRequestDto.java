package com.cts.OnePay.transaction.dto;

import com.cts.OnePay.user.model.User;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CreateWalletRequestDto {
    @NotNull(message = "User ID is required")
    private Long userId;
}
