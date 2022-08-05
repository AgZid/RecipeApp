package com.app.recipe.modal;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.PositiveOrZero;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Entity
@ToString
public class SelectedRecipe {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @PositiveOrZero
    private Integer numberOfServings;
    private Integer recipeId;
}
