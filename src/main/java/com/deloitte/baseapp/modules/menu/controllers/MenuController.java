package com.deloitte.baseapp.modules.menu.controllers;

import com.deloitte.baseapp.commons.GenericController;
import com.deloitte.baseapp.commons.MessageResponse;
import com.deloitte.baseapp.modules.menu.DTO.MenuResponseDTO;
import com.deloitte.baseapp.modules.menu.entities.Menu;
import com.deloitte.baseapp.modules.menu.repositories.MenuRepository;
import com.deloitte.baseapp.modules.menu.services.MenuService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@CrossOrigin(origins = "*")
@Slf4j
@RestController
@RequestMapping("/api/menus")
public class MenuController extends GenericController<Menu> {

    @Autowired
    private MenuService menuService;

    public MenuController(MenuRepository repository) {
        super(repository, "menu");
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/parents")
    public MessageResponse findAllParent() {
        try {
            final List<Menu> results = menuService.findAllParent();
            return new MessageResponse<>(results, "Success");
        } catch (Exception e) {
            return MessageResponse.ErrorWithCode(e.getMessage(), 400);
        }
    }

    @GetMapping("/parents/{parentId}")
    public MessageResponse findAllByParentId(@PathVariable("parentId") final Long parentId) {
        try {
            final List<Menu> results = menuService.findAllByParentId(parentId);
            return new MessageResponse<>(results, "Success");
        } catch (Exception e) {
            return MessageResponse.ErrorWithCode(e.getMessage(), 400);
        }
    }

    @PostMapping("/insert-by-csv-upload")
    public MessageResponse insertByCSVUpload(@RequestParam("file") final MultipartFile file) {
        try {
            final CompletableFuture<Boolean> result = menuService.insertByCSVUpload(file);
            return new MessageResponse(result.get(), "Success");
        } catch (Exception e) {
            return MessageResponse.ErrorWithCode(e.getMessage(), 400);
        }
    }

    // TODO: to be removed
    @GetMapping("/get-menu-by-id/{id}")
    public MessageResponse getMenuById(@PathVariable Long id) {
//        Menu menu = menuService.findMenuById(id);

        log.info("getting menu");

        // TODO: add in error handling

        List<MenuResponseDTO> menuDTOs = menuService.mapMenuToMenuResponseDTO();

//        System.out.println(menu.getChildren());

        return new MessageResponse<>(menuDTOs);
    }

}
