package com.deloitte.baseapp.modules.tasklist.services;

import com.deloitte.baseapp.modules.tasklist.dto.DecomTasklistDetailsResponseDTO;
import com.deloitte.baseapp.modules.tasklist.dto.DecomTasklistResponseDTO;
import com.deloitte.baseapp.modules.tasklist.entities.TAppSite;
import com.deloitte.baseapp.modules.tasklist.entities.TAppSiteEqp;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Type;
import java.util.List;

@Slf4j
@Service
public class DecomTasklistService extends TasklistService {

    @Autowired
    private ModelMapper modelMapper;

    public List<DecomTasklistResponseDTO> mapToDecomTasklistDTO() {

        log.info("Attempting to map list of TAppSite to list of DecomTasklistResponseDTO");
        List<TAppSite> tAppSites = super.getTAppSites();

        Type DTOtype = new TypeToken<List<DecomTasklistResponseDTO>>() {
        }.getType();

        return modelMapper.map(tAppSites, DTOtype);
    }

    public DecomTasklistResponseDTO mapToDecomTasklistDTOById(Long id) {
        log.info("Attempting to map TAppSite to DecomTasklistResponseDTO");
        TAppSite tAppSite = super.getTAppSiteById(id);

        return modelMapper.map(tAppSite, DecomTasklistResponseDTO.class);
    }


    //TODO: see what happens when List is null.
    public List<DecomTasklistDetailsResponseDTO> mapToDecomTasklistDetailsDTOById(Long id) {
        List<TAppSiteEqp> tAppSiteEqpList = super.getTAppSiteEqpsById(id);

        Type DTOType = new TypeToken<List<DecomTasklistDetailsResponseDTO>>() {
        }.getType();

        return modelMapper.map(tAppSiteEqpList, DTOType);
    }

}