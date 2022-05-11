package com.deloitte.baseapp.modules.menu.services;

import com.deloitte.baseapp.commons.LogInfo;
import com.deloitte.baseapp.modules.menu.entities.Menu;
import com.deloitte.baseapp.modules.menu.repositories.MenuRepository;
import com.deloitte.baseapp.utils.CSVFileReader;
import com.deloitte.baseapp.utils.QueryFileReader;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@Slf4j
@Service
public class MenuService {

    private final static String CLASSNAME = "MenuService";

    @Autowired
    private MenuRepository menuRepository;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Cacheable(value = "menuCache", unless= "#result.size() == 0")
    public List<Menu> findAllParent() throws Exception {
        final QueryFileReader<Menu> queryFileReader = new QueryFileReader<>(jdbcTemplate, Menu.class);
        return queryFileReader.queryMulti("menu_findAllParents.sql");
    }

    public List<Menu> findAllByParentId(final Long parentId) throws Exception {
        final QueryFileReader<Menu> queryFileReader = new QueryFileReader<>(jdbcTemplate, Menu.class);
        return queryFileReader.queryMulti("menu_findAllByParent.sql", ps -> ps.setLong(1, parentId));
    }

    @Async
    public CompletableFuture<Boolean> insertByCSVUpload(final MultipartFile file) {
        return CompletableFuture.supplyAsync(() -> {

            try {
//                final List<Menu> menuList = new CSVFileReader<Menu>().readFromFile(file.getInputStream(), Menu.class);
//                menuList.forEach(menu -> {
//                    //TODO: insert to db
//                    log.info(LogInfo.print(CLASSNAME,
//                            "insertByCSVUpload", menu.getName() + " has been inserted.."));
//                });

                new CSVFileReader<Menu>().readFromFolder(Menu.class, menuList -> {
                    menuList.forEach(menu -> {
//                    //TODO: insert to db
                        log.info(LogInfo.print(CLASSNAME,
                                "insertByCSVUpload", menu.getName() + " has been inserted.."));
                    });
                });
            } catch (Exception e) {
                log.error(LogInfo.print(CLASSNAME,
                        "insertByCSVUpload", e.getMessage()));

                return Boolean.FALSE;
            }

            return Boolean.TRUE;
        });
    }
}
