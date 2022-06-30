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

    // TODO: after merge with other branch with ModelMapper typed Bean, please assign an identifier to differentiate the modelmapper Bean.
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

}
