package com.deloitte.baseapp.modules.menu.converter;

import com.deloitte.baseapp.modules.menu.DTO.MenuResponseDTO;
import com.deloitte.baseapp.modules.menu.comparator.MenuPriorityComparator;
import com.deloitte.baseapp.modules.menu.entities.Menu;
import org.modelmapper.Converter;
import org.modelmapper.spi.MappingContext;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class MenuConverter {

    /**
     *
     * This function map our Set of Menu object into List of MenuResponseDTO object to be
     * structured in a hierarchical json structure.
     *
     * Before transforming the menu object into MenuResponseDTO object, this converter will first sort Menu object
     * using the MenuPriorityComparator.
     */
    public static final Converter<Set<Menu>, List<MenuResponseDTO>> menuToMenuResponseDTOConverter =
            (context) -> {
                Set<Menu> source = context.getSource();
                return source.stream()
                        .sorted(new MenuPriorityComparator())
                        .map(child -> {
                            MappingContext<Menu, MenuResponseDTO> subContext = context.create(child, MenuResponseDTO.class);
                            return context.getMappingEngine().map(subContext);
                        }).collect(Collectors.toList());
            };
}
