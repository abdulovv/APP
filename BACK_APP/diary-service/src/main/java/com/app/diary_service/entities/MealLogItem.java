package com.app.diary_service.entities;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "meal_log_items")
public class MealLogItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "meal_log_id", nullable = false)
    private MealLog mealLog;

    @Column(name = "product_id", nullable = false)
    private Long productId;

    @Column(name = "weight", nullable = false, precision = 10, scale = 2)
    private BigDecimal weight;
}
