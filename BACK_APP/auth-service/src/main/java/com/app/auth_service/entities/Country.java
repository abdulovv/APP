package com.app.auth_service.entities;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "countries")
public class Country {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @Column(name = "availability", nullable = false)
    private Boolean availability;

    @Column(name = "phone_code", nullable = false)
    private String phoneCode;

    @Column(name = "currency_symbol", nullable = false)
    private String currencySymbol;

    @Column(name = "exchange_rate", nullable = false)
    private Double exchangeRate;

    @Column(name = "min_phone_length", nullable = false)
    private Integer minPhoneLength;

    @Column(name = "max_phone_length", nullable = false)
    private Integer maxPhoneLength;
}
