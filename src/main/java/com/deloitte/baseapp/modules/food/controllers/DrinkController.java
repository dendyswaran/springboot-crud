package com.deloitte.baseapp.modules.food.controllers;

import com.deloitte.baseapp.commons.GenericController;
import com.deloitte.baseapp.commons.MessageResponse;
import com.deloitte.baseapp.modules.food.entities.Drink;
import com.deloitte.baseapp.modules.food.repositories.DrinkRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/drinks")
public class DrinkController extends GenericController<Drink> {

    public DrinkController(DrinkRepository repository) {
        super(repository);
    }
}
