package com.deloitte.baseapp.modules.menu.DTO;

import com.deloitte.baseapp.modules.menu.entities.EMenuClickEvent;
import com.deloitte.baseapp.modules.menu.entities.Menu;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Getter
@Setter
public class MenuResponseDTO {
    private Long id;
    private String name;
    private String code;
    private Menu children;
    private EMenuClickEvent clickEvent;
}
