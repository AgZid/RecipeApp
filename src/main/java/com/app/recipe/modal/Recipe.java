package com.app.recipe.modal;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Entity
public class Recipe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @NotEmpty(message = "Title should not be empty")
    private String title;
    @PositiveOrZero(message = "Number of servings must be positive number")
    @Max(20)
    private Integer numberOfServings;
    private String totalTime;
    private Double rating;

    @JsonIgnore
    @OneToMany(mappedBy = "recipe", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<Ingredient> ingredients = new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy = "recipe", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<Preparation> preparations = new ArrayList<>();

}
