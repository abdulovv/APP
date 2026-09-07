package com.app.auth_service.entities;

import jakarta.persistence.*;
import lombok.*;


@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Entity
@Table(name = "countries")
public class Country {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "name")
    private String name;
    @Column(name = "availability")
    private Boolean availability;
    @Column(name = "phone_code")
    private String phoneCode;
    @Column(name = "currency_symbol")
    private String currencySymbol;
    @Column(name = "exchange_rate")
    private Float exchangeRate;

}
