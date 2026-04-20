package com.cts.OnePay.user.dto.verificatioDtos;

import com.cts.OnePay.user.dto.userDtos.UserResponseDto;
import com.cts.OnePay.user.model.User;
import com.cts.OnePay.user.model.enums.DocType;
import com.cts.OnePay.user.model.enums.VerificationStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class VerificationPendingResponseDto {

    private Long verificationId;

    private UserResponseDto user;

    private DocType docType;

    private String docNumber;

    private VerificationStatus verificationStatus = VerificationStatus.PENDING;

}
