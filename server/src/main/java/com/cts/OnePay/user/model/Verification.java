package com.cts.OnePay.user.model;

import com.cts.OnePay.audit.Auditable;
import com.cts.OnePay.user.model.enums.VerificationStatus;
import com.cts.OnePay.user.model.enums.DocType;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
public class Verification extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long verificationId;

    @JoinColumn(name = "userId",referencedColumnName = "userId")
    @OneToOne(fetch = FetchType.LAZY)
    @NotNull
    private User user;

    @Enumerated(EnumType.STRING)
    @NotNull
    private DocType docType;

    @NotNull
    private String docNumber;

    @JoinColumn(name="verifierId", referencedColumnName = "userId")
    @ManyToOne(fetch = FetchType.LAZY)
    private User verifier = null;

    @Enumerated(EnumType.STRING)
    private VerificationStatus verificationStatus = VerificationStatus.PENDING;

}
