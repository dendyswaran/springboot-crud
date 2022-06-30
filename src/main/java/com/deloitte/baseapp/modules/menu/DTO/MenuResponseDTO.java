package com.deloitte.baseapp.modules.menu.DTO;

import com.deloitte.baseapp.modules.menu.entities.EMenuClickEvent;
import com.deloitte.baseapp.modules.menu.entities.Menu;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class MenuResponseDTO {
    private Long id;
    private String name;
    private String code;
    private Integer priority;
    private EMenuClickEvent clickEvent;
    private String pathname;
    private String href;
    private List<Menu> children;
}
