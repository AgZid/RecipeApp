package com.app.recipe.controller;

import com.app.recipe.exception.InvalidArgumentException;
import com.app.recipe.service.EmailService;
import com.app.recipe.service.SelectedRecipeService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.mail.MessagingException;
import java.io.IOException;
import java.util.Map;

@RestController
@RequestMapping("/app/recipes")
@CrossOrigin(origins = "http://localhost:4200")
public class EmailController {

    private EmailService emailService;
    private SelectedRecipeService selectedRecipeService;

    public EmailController(EmailService emailService, SelectedRecipeService selectedRecipeService) {
        this.emailService = emailService;
        this.selectedRecipeService = selectedRecipeService;
    }

    @GetMapping("/sendingEmail")
    public void sendEmail() throws InvalidArgumentException, MessagingException, IOException {
        Map<String, Double> allSelectedIngredients = selectedRecipeService.findAllSelectedIngredients();
        emailService.sendEmail(allSelectedIngredients);
    }
}
