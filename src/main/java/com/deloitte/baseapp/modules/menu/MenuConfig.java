package com.deloitte.baseapp.modules.menu;

import com.deloitte.baseapp.modules.menu.DTO.MenuResponseDTO;
import com.deloitte.baseapp.modules.menu.converter.MenuListConverter;
import com.deloitte.baseapp.modules.menu.entities.Menu;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.modelmapper.TypeToken;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.lang.reflect.Type;
import java.util.List;

@Configuration
public class MenuConfig {


    // TODO: test with the approach such that we send menu wth its children
    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();

        TypeMap<Menu, MenuResponseDTO> propertyMapper =
                modelMapper.createTypeMap(Menu.class, MenuResponseDTO.class);

        propertyMapper.addMappings(mapper -> mapper.using(new MenuListConverter())
                .map(Menu::getChildren, MenuResponseDTO::setChildren));

        return modelMapper;
    }

//    // TODO: test with the approach such that we send menu wth its children
//    @Bean
//    public ModelMapper modelMapper() {
//        ModelMapper modelMapper = new ModelMapper();
//        Type DTOListType = new TypeToken<List<MenuResponseDTO>>() {
//        }.getType();
//        Type MenuListType = new TypeToken<List<Menu>>() {
//        }.getType();
//
//        Converter<List<Menu>, List<MenuResponseDTO>> converter =
//                c -> modelMapper.map(MenuListType, DTOListType);
//
//        TypeMap<Menu, MenuResponseDTO> propertyMapper =
//                modelMapper.createTypeMap(Menu.class, MenuResponseDTO.class);
//
//        propertyMapper.addMappings(mapper -> mapper.using(converter)
//                .map(Menu::getChildren, MenuResponseDTO::setChildren));
//
//        return modelMapper;
//    }


    //TODO: test if converter is needed.
//    @Bean
//    public ModelMapper modelMapper() {
//        ModelMapper modelMapper = new ModelMapper();
//
//
//        TypeMap<Menu, MenuResponseDTO> propertyMapper =
//                modelMapper.createTypeMap(Menu.class, MenuResponseDTO.class);
//
//        propertyMapper.addMappings(mapper -> mapper
//                .map(Menu::getChildren, MenuResponseDTO::setChildren));
//
//        return modelMapper;
//    }


//    public List<IohTasklistDetailsResponseDTO> mapToIohTasklistDetailsDTOById(Long id) {
//
//        List<TAppSiteEqp> tAppSiteEqpList = super.getTAppSiteEqpsById(id);
//        ModelMapper iohTasklistDetailsMapper = new ModelMapper();
//
//        Converter<TMtMake, TMtMakeDTO> makeToDTOConverter =
//                c -> iohTasklistDetailsMapper
//                        .map(c.getSource(), TMtMakeDTO.class);
//
//        // define a propertyMapper to perform the mapping.
//        TypeMap<TAppSiteEqp, IohTasklistDetailsResponseDTO> propertyMapper =
//                iohTasklistDetailsMapper.createTypeMap(TAppSiteEqp.class, IohTasklistDetailsResponseDTO.class);
//
//        // add additional mapping rule
//        propertyMapper.addMappings(
//                mapper -> mapper.using(makeToDTOConverter)
//                        .map(TAppSiteEqp::getTMtMake
//                                , IohTasklistDetailsResponseDTO::setTMtMakeDTO)
//        );
//        Type DTOType = new TypeToken<List<IohTasklistDetailsResponseDTO>>() {
//        }.getType();
//
//        return iohTasklistDetailsMapper.map(tAppSiteEqpList, DTOType);
//

//                        System.out.printf("id: %s, name: %s, code: %s, parent: %s, children: %s, event: %s, href: %s, priority: %s, is-active: %s%n"
//                                , menu.getId(), menu.getName(), menu.getCode(), menu.getParentId(),
//                                menu.getChildren(), menu.getClickEvent(), menu.getHref(), menu.getPriority(), menu.getIsActive());


}
