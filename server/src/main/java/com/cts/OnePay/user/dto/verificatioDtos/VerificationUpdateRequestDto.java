package com.cts.OnePay.user.dto.verificatioDtos;

import com.cts.OnePay.user.model.enums.VerificationStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class VerificationUpdateRequestDto {
    private Long verifierId;
    private VerificationStatus verificationStatus;
}
