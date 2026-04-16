package com.cts.OnePay.transaction.model;

import com.cts.OnePay.user.model.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.UUID;


@Entity
@Table(
        name = "wallet",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "userId_unique",
                        columnNames = "userId"
                )
        }
)
@NoArgsConstructor @AllArgsConstructor @Getter @Setter
public class Wallet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long walletId;

    @OneToOne(fetch = FetchType.LAZY )
    @JoinColumn(name = "userId",referencedColumnName = "userId")
    @NotNull
    private User user;

    @Min(value = 0, message = "Balance cannot be less than 0")
    @Column(precision = 15, scale = 2)
    private BigDecimal balance= BigDecimal.ZERO;
    private String currency= "INR";
}
