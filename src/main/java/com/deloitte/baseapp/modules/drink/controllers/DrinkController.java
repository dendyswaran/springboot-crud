package com.deloitte.baseapp.modules.drink.controllers;

import com.deloitte.baseapp.commons.GenericController;
import com.deloitte.baseapp.commons.GenericRepository;
import com.deloitte.baseapp.modules.drink.entities.Drink;
import com.deloitte.baseapp.modules.drink.repositories.DrinkRepository;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/drinks")
public class DrinkController extends GenericController<Drink> {

    public DrinkController(DrinkRepository repository) {
        super(repository, "drink");
    }
}
