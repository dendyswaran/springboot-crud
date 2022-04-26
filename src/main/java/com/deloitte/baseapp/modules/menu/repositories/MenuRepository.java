package com.deloitte.baseapp.modules.menu.repositories;

import com.deloitte.baseapp.commons.GenericRepository;
import com.deloitte.baseapp.modules.menu.entities.Menu;
import org.springframework.stereotype.Repository;

@Repository
public interface MenuRepository extends GenericRepository<Menu> {
}
