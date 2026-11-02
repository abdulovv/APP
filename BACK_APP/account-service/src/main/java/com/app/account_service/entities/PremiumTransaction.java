package com.app.account_service.entities;

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
@Table(name = "premium_transactions")
public class PremiumTransaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
            name = "user_id",
            nullable = false,
            foreignKey = @ForeignKey(name = "fk_premium_transactions_user")
    )
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "payment_card_id",
            foreignKey = @ForeignKey(name = "fk_premium_transactions_payment_card")
    )
    private PaymentCard paymentCard;

    @Column(name = "card_brand")
    private String cardBrand;

    @Column(name = "card_last4")
    private String cardLast4;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "country_id",
            foreignKey = @ForeignKey(name = "fk_premium_transactions_country")
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
    private PremiumTransactionSource source;

    @Builder.Default
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private PremiumTransactionStatus status = PremiumTransactionStatus.PAID;

    @Column(name = "old_expires_at")
    private Instant oldExpiresAt;

    @Column(name = "new_expires_at")
    private Instant newExpiresAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "performed_by",
            foreignKey = @ForeignKey(name = "fk_premium_transactions_performed_by")
    )
    private User performedBy;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false)
    private Instant createdAt;
}