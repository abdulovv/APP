package com.app.auth_service.entities;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.Instant;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "purchases_history")
public class PurchaseHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
            name = "user_id",
            nullable = false,
            foreignKey = @ForeignKey(name = "fk_purchases_user")
    )
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "payment_id",
            foreignKey = @ForeignKey(name = "fk_purchases_payment")
    )
    private PaymentCard payment;

    @Column(name = "card_brand")
    private String cardBrand;

    @Column(name = "card_last4")
    private String cardLast4;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "country_id",
            foreignKey = @ForeignKey(name = "fk_purchases_country")
    )
    private Country country;

    @Column(name = "amount_usd", nullable = false)
    private BigDecimal amountUsd;

    @Column(name = "currency_symbol", nullable = false)
    private String currencySymbol;

    @Column(name = "exchange_rate", nullable = false)
    private Double exchangeRate;

    @Column(name = "amount_local", nullable = false)
    private BigDecimal amountLocal;

    @Column(name = "period_days", nullable = false)
    private Short periodDays;

    @Enumerated(EnumType.STRING)
    @Column(name = "source", nullable = false)
    private PurchaseSource source;

    @Builder.Default
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private PurchaseStatus status = PurchaseStatus.PAID;

    @Column(name = "old_expires_at")
    private Instant oldExpiresAt;

    @Column(name = "new_expires_at")
    private Instant newExpiresAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "performed_by",
            foreignKey = @ForeignKey(name = "fk_purchases_admin")
    )
    private User performedBy;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false)
    private Instant createdAt;
}