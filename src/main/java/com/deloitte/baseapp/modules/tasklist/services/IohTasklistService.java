package com.deloitte.baseapp.modules.tasklist.services;


import com.deloitte.baseapp.modules.tasklist.dto.IohTasklistDetailsResponseDTO;
import com.deloitte.baseapp.modules.tasklist.dto.IohTasklistResponseDTO;
import com.deloitte.baseapp.modules.tasklist.dto.TMtMakeDTO;
import com.deloitte.baseapp.modules.tasklist.entities.TAppSite;
import com.deloitte.baseapp.modules.tasklist.entities.TAppSiteEqp;
import com.deloitte.baseapp.modules.tasklist.entities.TMtMake;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Type;
import java.util.List;

@Slf4j
@Service
public class IohTasklistService extends TasklistService {

    @Autowired
    private ModelMapper modelMapper;


    public List<IohTasklistResponseDTO> mapToIohTasklistDTO() {

        log.info("Attempting to map list of TAppSite to list of IohTasklistResponseDTO");
        List<TAppSite> tAppSites = super.getTAppSites();

        Type DTOtype = new TypeToken<List<IohTasklistResponseDTO>>() {
        }.getType();

        return modelMapper.map(tAppSites, DTOtype);
    }

    public IohTasklistResponseDTO mapToIohTasklistDTOById(Long id) {
        log.info("Attempting to map TAppSite to IohTasklistResponseDTO");

        TAppSite tAppSite = super.getTAppSiteById(id);

        return modelMapper.map(tAppSite, IohTasklistResponseDTO.class);
    }

    // TODO: how to add mapping more elegantly?
    //  if addMapping to original mapper --> all mapper has the mapping rule, is it ok?
    //  or create another instance.

    // TODO: maybe can remove error handling here and pass it to controller.
    public List<IohTasklistDetailsResponseDTO> mapToIohTasklistDetailsDTOById(Long id) {

        List<TAppSiteEqp> tAppSiteEqpList = super.getTAppSiteEqpsById(id);
        ModelMapper iohTasklistDetailsMapper = new ModelMapper();

        Converter<TMtMake, TMtMakeDTO> makeToDTOConverter =
                c -> iohTasklistDetailsMapper
                        .map(c.getSource(), TMtMakeDTO.class);

        // define a propertyMapper to perform the mapping.
        TypeMap<TAppSiteEqp, IohTasklistDetailsResponseDTO> propertyMapper =
                iohTasklistDetailsMapper.createTypeMap(TAppSiteEqp.class, IohTasklistDetailsResponseDTO.class);

        // add additional mapping rule
        propertyMapper.addMappings(
                mapper -> mapper.using(makeToDTOConverter)
                        .map(TAppSiteEqp::getTMtMake
                                , IohTasklistDetailsResponseDTO::setTMtMakeDTO)
        );
        Type DTOType = new TypeToken<List<IohTasklistDetailsResponseDTO>>() {
        }.getType();

        return iohTasklistDetailsMapper.map(tAppSiteEqpList, DTOType);


    }

}

// TODO: To be removed: Old code for reference only

//    public List<IohTasklistDetailsResponseDTO> mapToIohTasklistDetailsDTOById(Long id) {
//        List<TAppSiteEqp> tAppSiteEqpList = super.getTAppSiteEqpsById(id);
//
//        if (tAppSiteEqpList != null) {
//            ModelMapper modelMapper = new ModelMapper();
//            Type DTOType = new TypeToken<List<IohTasklistDetailsResponseDTO>>() {
//            }.getType();
//            return modelMapper.map(tAppSiteEqpList, DTOType);
//        }
//        return null;
//    }


//    public List<IohTasklistResponseDTO> mapToIohTasklistDTO() {
//
//        log.info("Attempting to map list of TAppSite to list of IohTasklistResponseDTO");
//
//        List<TAppSite> tAppSites = super.getTAppSites();
//        ModelMapper modelMapper = new ModelMapper();
//
//        // Map List of TAppSite to List of TAppSite DTO
//        List<IohTasklistResponseDTO> listOfIohTasklist = tAppSites
//                .stream()
//                .map(tAppSite -> modelMapper.map(tAppSite, IohTasklistResponseDTO.class))
//                .collect(Collectors.toList());
//
//        System.out.println(listOfIohTasklist);
//
//        return listOfIohTasklist;
//    }

//    public List<IohTasklistDetailsResponseDTO> mapToIohTasklistDetailsDTOById(Long id) {
//        List<TAppSiteEqp> tAppSiteEqpList = super.getTAppSiteEqpsById(id);
//
//        if (tAppSiteEqpList != null) {
//
//            ModelMapper modelMapper = new ModelMapper();
//
//            // converts TAppSite to IohTasklistResponseDTO
//            Converter<TAppSite, IohTasklistResponseDTO> converter =
//                    c -> modelMapper
//                            .map(c.getSource(), IohTasklistResponseDTO.class);
//
//            TypeMap<TAppSiteEqp, IohTasklistDetailsResponseDTO> propertyMapper =
//                    modelMapper.createTypeMap(TAppSiteEqp.class, IohTasklistDetailsResponseDTO.class);
//
//            propertyMapper.addMappings(
//                    mapper -> mapper.using(converter)
//                            .map(TAppSiteEqp::getTAppSite
//                                    , IohTasklistDetailsResponseDTO::setIohTasklistResponseDTO)
//            );
//
//            Type DTOType = new TypeToken<List<IohTasklistDetailsResponseDTO>>() {
//            }.getType();
//
//            return modelMapper.map(tAppSiteEqpList, DTOType);
//        }
//        return null;
//    }

//    public List<IohTasklistDetailsResponseDTO> mapToIohTasklistDetailsDTOById(Long id) {
//        try {
//
//            List<TAppSiteEqp> tAppSiteEqpList = super.getTAppSiteEqpsById(id);
//
//            if (tAppSiteEqpList != null) {
//
//                ModelMapper modelMapper = new ModelMapper();
//
//                // converter to convert TAppSite object to DTO
//                Converter<TAppSite, IohTasklistResponseDTO> appSiteToDTOConverter =
//                        c -> modelMapper
//                                .map(c.getSource(), IohTasklistResponseDTO.class);
//
//                // converter to convert TMtMake object to DTO
//                Converter<TMtMake, TMtMakeDTO> makeToDTOConverter =
//                        c -> modelMapper
//                                .map(c.getSource(), TMtMakeDTO.class);
//
//                // define a propertyMapper to perform the mapping.
//                TypeMap<TAppSiteEqp, IohTasklistDetailsResponseDTO> propertyMapper =
//                        modelMapper.createTypeMap(TAppSiteEqp.class, IohTasklistDetailsResponseDTO.class);
//
//                // Add and apply converter to modelMapper.
//                propertyMapper.addMappings(
//                        mapper -> mapper.using(appSiteToDTOConverter)
//                                .map(TAppSiteEqp::getTAppSite
//                                        , IohTasklistDetailsResponseDTO::setIohTasklistResponseDTO)
//                );
//
//                propertyMapper.addMappings(
//                        mapper -> mapper.using(makeToDTOConverter)
//                                .map(TAppSiteEqp::getTMtMake
//                                        , IohTasklistDetailsResponseDTO::setTMtMakeDTO)
//                );
//
//                Type DTOType = new TypeToken<List<IohTasklistDetailsResponseDTO>>() {
//                }.getType();
//
//                return modelMapper.map(tAppSiteEqpList, DTOType);
//            }
//        } catch (Exception e) {
//            log.error(e.getMessage());
//        }
//        return null;
//    }
