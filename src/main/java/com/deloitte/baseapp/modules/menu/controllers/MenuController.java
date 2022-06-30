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

    /**
     * End point of Menu upload by CSV file.
     * The user defines a a csv file formatted in a specific format and
     * this end point will input the data from the CSV file into the underlying Database.
     * <p>
     * This function takes in MultipartFile in csv format, reads them and process them
     *
     * @param file MultipartFile in csv format
     * @return MessageResponse static object indicating the success of failure of the execution.
     */
    @PostMapping("/insert-by-csv-upload")
    public MessageResponse insertByCSVUpload(@RequestParam("file") final MultipartFile file) {
        try {
            final CompletableFuture<Boolean> result = menuService.insertByCSVUpload(file);
            return new MessageResponse(result.get(), "Success");
        } catch (Exception e) {
            return MessageResponse.ErrorWithCode(e.getMessage(), 400);
        }
    }

    /**
     * End point for getting Menu structured in json format. The formatting of the menu is defined by the MenuConverter
     * and the MenuResponseDTO which is applied to all levels (nested) of json object.
     * <p>
     * e.g. structure of the returned DTO object
     * {
     * menu:
     * {
     * childMenu:
     * {
     * grandChildrenMenu:
     * }
     * }
     * <p>
     * }
     *
     * @return upon successful execution this API return a MenuResponseSTO object that
     */
    @GetMapping("/get")
    public MessageResponse<List<MenuResponseDTO>> getMenu() {
        try {

            log.info("getting all menu item");
            List<MenuResponseDTO> menuDTOs = menuService.mapMenuToMenuResponseDTO();
            return new MessageResponse<>(menuDTOs);

        } catch (IllegalStateException illegalStateEx) {

            log.error("No Menu object can be found from the Database. Failed with error message: {}", illegalStateEx.getMessage());
            return MessageResponse.ErrorWithCode(illegalStateEx.getMessage(), 400);

        } catch (Exception ex) {

            log.error("An unexpected error occurred while attempting to get Menu data from the Database. Failed with error message: {}",
                    ex.getMessage());

            return MessageResponse.ErrorWithCode(ex.getMessage(), 400);
        }
    }

}
