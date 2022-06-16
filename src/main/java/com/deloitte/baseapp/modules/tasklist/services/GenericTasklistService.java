package com.deloitte.baseapp.modules.tasklist.services;

//import com.deloitte.baseapp.modules.tasklist.dto.IohTasklistResponseDTO;

import com.deloitte.baseapp.modules.tasklist.entities.TAppSite;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Optional;

// TODO: TO ASK: Is Generic service possible?

@Slf4j
@Service
public class GenericTasklistService<T> extends TasklistService {

    @Autowired
    private ModelMapper modelMapper;

    public List<T> mapToIohTasklistDTO() {

        log.info("Attempting to map list of TAppSite to list of T");
        List<TAppSite> tAppSites = super.getTAppSites();

        Type DTOListType = new TypeToken<List<T>>() {
        }.getType();

        return modelMapper.map(tAppSites, DTOListType);
    }

//    public T mapToIohTasklistDTOById(Long id) {
//        log.info("Attempting to map TAppSite to T");
//
//        Optional<TAppSite> tAppSiteOpt = super.getTAppSiteById(id);
//        ModelMapper modelMapper = new ModelMapper();
//
//        Type DTOtype = new TypeToken<T>() {}.getType();
//
//        return tAppSiteOpt.map(
//                        tAppSite -> modelMapper.map(tAppSite, T))
//                .orElse(null);
//    }

}
