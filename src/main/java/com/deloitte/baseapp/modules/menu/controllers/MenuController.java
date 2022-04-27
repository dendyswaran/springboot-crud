package com.deloitte.baseapp.modules.menu.controllers;

import com.deloitte.baseapp.commons.GenericController;
import com.deloitte.baseapp.commons.MessageResponse;
import com.deloitte.baseapp.modules.menu.entities.Menu;
import com.deloitte.baseapp.modules.menu.repositories.MenuRepository;
import com.deloitte.baseapp.utils.QueryFileReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/menus")
public class MenuController extends GenericController<Menu> {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public MenuController(MenuRepository repository) {
        super(repository);
    }

    /**
     *  Endpoint to returns all primary menus
     *
     * @return
     */
    @GetMapping("/parents")
    public MessageResponse findAllParent() {
        try {
            final QueryFileReader queryFileReader = new QueryFileReader();
            final String queryString = queryFileReader.read("menu_findAllParents.sql");
            final List<Menu> results = jdbcTemplate.query(queryString, new BeanPropertyRowMapper<>(Menu.class));

            return new MessageResponse(results, "Success");
        } catch (Exception e) {
            return MessageResponse.ErrorWithCode(e.getMessage(), 400);
        }
    }

}
