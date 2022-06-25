package com.deloitte.baseapp.modules.menu.repositories;

import com.deloitte.baseapp.commons.GenericRepository;
import com.deloitte.baseapp.modules.menu.entities.Menu;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MenuRepository extends GenericRepository<Menu> {

    List<Menu> findByParentIsNull();

}
