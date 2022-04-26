package com.deloitte.baseapp.modules.menu.controllers;

import com.deloitte.baseapp.commons.GenericController;
import com.deloitte.baseapp.modules.menu.entities.Menu;
import com.deloitte.baseapp.modules.menu.repositories.MenuRepository;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/menus")
public class MenuController extends GenericController<Menu> {

    public MenuController(MenuRepository repository) {
        super(repository);
    }
}
