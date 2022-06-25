package com.deloitte.baseapp.modules.menu.services;

import com.deloitte.baseapp.commons.LogInfo;
import com.deloitte.baseapp.modules.menu.DTO.MenuResponseDTO;
import com.deloitte.baseapp.modules.menu.entities.Menu;
import com.deloitte.baseapp.modules.menu.repositories.MenuRepository;
import com.deloitte.baseapp.utils.CSVFileReader;
import com.deloitte.baseapp.utils.QueryFileReader;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.ObjectNotFoundException;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

@Slf4j
@Service
public class MenuService {

    private final static String CLASSNAME = "MenuService";

    @Autowired
    private MenuRepository menuRepository;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private ModelMapper modelMapper;

    @Cacheable(value = "menuCache", unless = "#result.size() == 0")
    public List<Menu> findAllParent() throws Exception {
        final QueryFileReader<Menu> queryFileReader = new QueryFileReader<>(jdbcTemplate, Menu.class);
        return queryFileReader.queryMulti("menu_findAllParents.sql");
    }

    public List<Menu> findAllByParentId(final Long parentId) throws Exception {
        final QueryFileReader<Menu> queryFileReader = new QueryFileReader<>(jdbcTemplate, Menu.class);
        return queryFileReader.queryMulti("menu_findAllByParent.sql", ps -> ps.setLong(1, parentId));
    }

    public List<Menu> findAllMenu() {
        List<Menu> menuList = menuRepository.findAll();
        if (menuList.isEmpty()) {
            throw new IllegalStateException("Menu object is non existence");
        }
        return menuList;
    }

    public List<Menu> findAllRootMenu() {
        List<Menu> menuList = menuRepository.findByParentIsNull();
        if (menuList.isEmpty()) {
            throw new IllegalStateException("Menu object is non existence");
        }
        return menuList;
    }

    public Menu findMenuById(Long id) {
        Optional<Menu> menuOpt = menuRepository.findById(id);
        if (menuOpt.isEmpty()) {
            throw new ObjectNotFoundException(id, "Menu");
        }
        return menuOpt.get();
    }

    @Async
    public CompletableFuture<Boolean> insertByCSVUpload(final MultipartFile file) {
        return CompletableFuture.supplyAsync(() -> {

            try {

                new CSVFileReader<Menu>().readFromFolder(Menu.class, menuList ->
                                menuList.forEach(menu -> {
                                    String href = String.format("/%s", menu.getPathname()); // format for



                                    // if menu have a parent.
                                    if (menu.getParentId() > 0) {
                                        Long parentId = menu.getParentId();
                                        Menu parentMenu = findMenuById(parentId);
                                        menu.setParent(parentMenu);

                                        System.out.println(parentId);
                                        System.out.println(parentMenu);

                                        // combine parent href with child pathname to create new href and replace the var.
                                        href = String.format("%s/%s", parentMenu.getHref(), menu.getPathname());
                                    }
                                    menu.setHref(href);
//                        menuRepository.saveAndFlush(menu.createNewInstance());
                                    menuRepository.saveAndFlush(menu);

                                    log.info(LogInfo.print(CLASSNAME,
                                            "insertByCSVUpload", menu.getName() + " has been inserted.."));
                                })
                );
            } catch (Exception e) {
                log.error(LogInfo.print(CLASSNAME,
                        "insertByCSVUpload", e.getMessage()));

                return Boolean.FALSE;
            }

            return Boolean.TRUE;
        });
    }


//    public List<MenuResponseDTO> mapMenuToMenuResponseDTO() {
//        List<Menu> menuList = findAllMenu();
//
//        Type DTOType = new TypeToken<List<MenuResponseDTO>>(){}.getType();
//
//        return modelMapper.map(menuList, DTOType);
//    }

    public List<MenuResponseDTO> mapMenuToMenuResponseDTO() {
        List<Menu> menuList = findAllRootMenu();
        Type DTOType = new TypeToken<List<MenuResponseDTO>>(){}.getType();

        return modelMapper.map(menuList, DTOType);
    }



}
