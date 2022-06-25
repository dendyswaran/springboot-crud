package com.deloitte.baseapp.modules.menu.converter;

import com.deloitte.baseapp.modules.menu.DTO.MenuResponseDTO;
import com.deloitte.baseapp.modules.menu.entities.Menu;
import org.modelmapper.Converter;
import org.modelmapper.spi.MappingContext;

import java.util.List;
import java.util.stream.Collectors;

public class MenuConverter {

    public static final Converter<List<Menu>, List<MenuResponseDTO>> menuToMenuResponseDTOConverter =
            (context) -> {
                List<Menu> source = context.getSource();
                return source.stream()
                        .map(child -> {
                            MappingContext<Menu, MenuResponseDTO> subContext = context.create(child, MenuResponseDTO.class);
                            return context.getMappingEngine().map(subContext);
                        }).collect(Collectors.toList());
            };
}
