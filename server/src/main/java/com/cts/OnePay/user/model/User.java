package com.cts.OnePay.user.model;

import com.cts.OnePay.audit.Auditable;
import com.cts.OnePay.user.model.enums.Role;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(
        name = "user",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "email_unique",
                        columnNames ="email"
                ),
                @UniqueConstraint(
                        name = "phone_unique",
                        columnNames = "phone"
                )
        }
)
public class User extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @NotNull
    private String fullName;

    @Email(message = "Please provide valid email")
    @NotNull
    private String email;

    @NotNull
    @Min(value = 10,message = "Phone number should be of 10 digits")
    private String phone;

    @NotNull
    private String passwordHash;

    @Enumerated(EnumType.STRING)
    private Role role = Role.CUSTOMER;


}
