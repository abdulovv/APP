package com.app.product_service.entities;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(
        name = "product_nutrients",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uq_product_nutrients_product_nutrient",
                        columnNames = {"product_id", "nutrient_id"}
                )
        }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductNutrient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
            name = "product_id",
            foreignKey = @ForeignKey(name = "fk_product_nutrients_product")
    )
    private Product product;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
            name = "nutrient_id",
            foreignKey = @ForeignKey(name = "fk_product_nutrients_nutrient")
    )
    private Nutrient nutrient;

    @Column(name = "value", nullable = false)
    private BigDecimal value;
}
