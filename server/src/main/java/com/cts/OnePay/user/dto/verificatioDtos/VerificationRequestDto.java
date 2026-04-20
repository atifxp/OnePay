package com.cts.OnePay.user.dto.verificatioDtos;

import com.cts.OnePay.user.model.enums.DocType;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class VerificationRequestDto {
    @NotNull(message = "Document Type should be specified")
    private DocType docType;

    @NotNull(message = "Document number should be specified")
    private String docNumber;
}
