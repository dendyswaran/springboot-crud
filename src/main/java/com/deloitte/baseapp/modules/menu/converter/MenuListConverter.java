package com.deloitte.baseapp.modules.menu.converter;

import com.deloitte.baseapp.modules.menu.DTO.MenuResponseDTO;
import com.deloitte.baseapp.modules.menu.entities.Menu;
import org.modelmapper.AbstractConverter;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

public class MenuListConverter extends AbstractConverter<List<Menu>, List<MenuResponseDTO>> {

    @Override
    protected List<MenuResponseDTO> convert(List<Menu> menus) {

        ModelMapper modelMapper = new ModelMapper();

        Type DTOListType = new TypeToken<List<MenuResponseDTO>>() {
        }.getType();

        return modelMapper.map(menus, DTOListType);
    }
}