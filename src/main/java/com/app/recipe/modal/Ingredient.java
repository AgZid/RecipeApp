package com.app.recipe.modal;

import com.app.recipe.Enumeration.MeasureUnit;
import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.PositiveOrZero;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Entity
@ToString
public class Ingredient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotEmpty
    private String product;

    @PositiveOrZero
    private Double quantity;

    @Enumerated(value = EnumType.STRING)
    private MeasureUnit measurementUnit;

    @ManyToOne
    @JoinColumn(name = "recipeId")
    @JsonBackReference
    private Recipe recipe;
}

