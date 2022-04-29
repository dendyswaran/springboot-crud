package com.deloitte.baseapp.modules.menu.services;

import com.deloitte.baseapp.modules.menu.entities.Menu;
import com.deloitte.baseapp.utils.QueryFileReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.stereotype.Service;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

@Service
public class MenuService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<Menu> findAllParent() throws Exception {
        final QueryFileReader<Menu> queryFileReader = new QueryFileReader<>(jdbcTemplate, Menu.class);
        return queryFileReader.queryMulti("menu_findAllParents.sql");
    }

    public List<Menu> findAllByParentId(final Long parentId) throws Exception {
        final QueryFileReader<Menu> queryFileReader = new QueryFileReader<>(jdbcTemplate, Menu.class);
        return queryFileReader.queryMulti("menu_findAllByParent.sql", ps -> ps.setLong(1, parentId));
    }

}
