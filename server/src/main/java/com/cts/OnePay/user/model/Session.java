package com.cts.OnePay.user.model;

import com.cts.OnePay.audit.Auditable;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Entity
@Table(
        name = "session",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "userId_unique",
                        columnNames = "userId"
                )
        }
)
public class Session extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long sessionId;

    @JoinColumn(name = "userId", referencedColumnName = "userId")
    @OneToOne(fetch = FetchType.LAZY)
    private User user;

    @NotNull
    private String refreshToken;
    @NotNull
    private String ipAddress;

    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime expiresAt;
}
