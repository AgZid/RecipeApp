package com.app.recipe.modal;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Entity
public class Preparation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Integer stepNo;
    private String stepAction;

    @ManyToOne
    @JoinColumn(name = "recipeId")
    @JsonBackReference
    private Recipe recipe;

}
