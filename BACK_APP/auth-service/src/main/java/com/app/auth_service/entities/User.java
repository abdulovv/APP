package com.app.auth_service.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "firstname")
    private String firstname;
    @Column(name = "lastname")
    private String lastname;
    @Column(name = "email")
    private String email;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "country_id",
            nullable = false,
            foreignKey = @ForeignKey(name = "fk_users_country")
    )
    private Country country;
    @Column(name = "phone_number")
    private String phoneNumber;
    @Column(name = "password")
    private String password;
    @Column(name = "birth_date")
    private LocalDate birthDate;
}
