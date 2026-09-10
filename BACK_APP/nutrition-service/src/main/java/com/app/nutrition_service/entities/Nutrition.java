package com.app.nutrition_service.entities;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "nutrition")
public class Nutrition {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "name")
    private String name;
    @Column(name = "portion_name")
    private String portionName;
    @Column(name = "portion_weight")
    private BigDecimal portionWeight;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "category_id",
            foreignKey = @ForeignKey(name = "fk_nutrition_category")
    )
    private Category category;
    @OneToMany(mappedBy = "nutrition", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<NutritionNutrient> nutrients = new ArrayList<>();
}
