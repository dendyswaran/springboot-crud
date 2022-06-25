package com.deloitte.baseapp.modules.menu;

import com.deloitte.baseapp.modules.menu.DTO.MenuResponseDTO;
import com.deloitte.baseapp.modules.menu.converter.MenuConverter;
import com.deloitte.baseapp.modules.menu.entities.Menu;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MenuConfig {


    // This approach only work with 2 layers

//    @Bean
//    public ModelMapper modelMapper() {
//        ModelMapper modelMapper = new ModelMapper();
//
//        TypeMap<Menu, MenuResponseDTO> propertyMapper =
//                modelMapper.createTypeMap(Menu.class, MenuResponseDTO.class);
//
//        propertyMapper.addMappings(mapper ->
//                mapper.using(new MenuListConverter())
//                        .map(Menu::getChildren, MenuResponseDTO::setChildren)
//        );
//        return modelMapper;
//    }


    // This approach work with all layers
    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();

        TypeMap<Menu, MenuResponseDTO> propertyMapper =
                modelMapper.createTypeMap(Menu.class, MenuResponseDTO.class);

        propertyMapper.addMappings(mapper ->
                mapper.using(MenuConverter.menuToMenuResponseDTOConverter)
                        .map(Menu::getChildren, MenuResponseDTO::setChildren)


        );
        return modelMapper;
    }

//                        System.out.printf("id: %s, name: %s, code: %s, parent: %s, children: %s, event: %s, href: %s, priority: %s, is-active: %s%n"
//                                , menu.getId(), menu.getName(), menu.getCode(), menu.getParentId(),
//                                menu.getChildren(), menu.getClickEvent(), menu.getHref(), menu.getPriority(), menu.getIsActive());


}
