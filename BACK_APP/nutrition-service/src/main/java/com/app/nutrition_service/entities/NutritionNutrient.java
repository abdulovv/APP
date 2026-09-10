package com.app.nutrition_service.entities;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(
        name = "nutrition_nutrients",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uq_nutrition_nutrient",
                        columnNames = {"nutrition_id", "nutrient_id"}
                )
        }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NutritionNutrient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
            name = "nutrition_id",
            foreignKey = @ForeignKey(name = "fk_nn_nutrition")
    )
    private Nutrition nutrition;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
            name = "nutrient_id",
            foreignKey = @ForeignKey(name = "fk_nn_nutrient")
    )
    private Nutrient nutrient;

    @Column(name = "value", nullable = false)
    private BigDecimal value;
}
