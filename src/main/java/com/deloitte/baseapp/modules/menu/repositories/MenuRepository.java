package com.deloitte.baseapp.modules.menu.repositories;

import com.deloitte.baseapp.commons.GenericRepository;
import com.deloitte.baseapp.modules.menu.entities.Menu;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MenuRepository extends GenericRepository<Menu> {

    // Note that the sorting of the menu is done using the converter instead of JPA or SQL using "Order By".
    // This ensures that the nested element (children of Menu) will also be sorted by their priority.
    List<Menu>findByParentIsNull();

}
