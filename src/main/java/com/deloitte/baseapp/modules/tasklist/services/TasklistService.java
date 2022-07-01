package com.deloitte.baseapp.modules.tasklist.services;

import com.deloitte.baseapp.modules.tasklist.entities.TAppSite;
import com.deloitte.baseapp.modules.tasklist.entities.TAppSiteEqp;
import com.deloitte.baseapp.modules.tasklist.entities.TMtMake;
import com.deloitte.baseapp.modules.tasklist.entities.TMtWfStatus;
import com.deloitte.baseapp.modules.tasklist.dto.TasklistDTO;
import com.deloitte.baseapp.modules.tasklist.object.EqpType;
import com.deloitte.baseapp.modules.tasklist.repositories.TAppSiteRepository;
import com.deloitte.baseapp.modules.tasklist.repositories.TMtMakeRepository;
import com.deloitte.baseapp.modules.tasklist.repositories.TMtWfStatusRepository;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;

@Slf4j
@Service
public class TasklistService {

    @Autowired
    private TAppSiteRepository tAppSiteRepository;

    @Autowired
    private TMtMakeRepository tMtMakeRepository;

    @Autowired
    private TMtWfStatusRepository tMtWfStatusRepository;


//    @Autowired
//    private ModelMapper modelMapper;

    // TODO: Discuss need to throw error if empty? how to notify?
    public List<TAppSite> getTAppSites() {
        return tAppSiteRepository.findAll();
    }

    public TAppSite getTAppSiteById(Long id) {
        log.info("TasklistService: Attempting to retrieve TAppSite records from database for id: {}", id);

        Optional<TAppSite> tAppSiteOpt = tAppSiteRepository.findById(id);

        if (tAppSiteOpt.isEmpty()) {
            throw new ObjectNotFoundException(id, "TAppSite not found: ");
        }
        return tAppSiteOpt.get();
    }

    public List<TAppSiteEqp> getTAppSiteEqpsById(Long id) {
        log.info("TasklistService: Attempting to get List of TAppSiteEqp for id: {}", id);

        TAppSite tAppSite = getTAppSiteById(id);

        List<TAppSiteEqp> tAppSiteEqpList = tAppSite.getTAppSiteEqpList();

        if (tAppSiteEqpList.isEmpty()) {
            throw new IllegalStateException("TAppSiteEqpList is empty: A TAppSite should have at least one or more equipments");
        }
        return tAppSiteEqpList;
    }

    // TODO: this function overlap with function above already.
    public TasklistDTO getTAppSiteAndEqpsById(Long id){
        log.info("TasklistService: Attempting to get TAppSite and Equipments by ID");

        TAppSite tAppSite = getTAppSiteById(id);
        List<TAppSiteEqp> tAppSiteEqpList = tAppSite.getTAppSiteEqpList();

        if (tAppSiteEqpList.isEmpty()) {
            throw new IllegalStateException("TAppSiteEqpList is empty: A TAppSite should have at least one or more equipments");
        }

        TasklistDTO tasklistDTO = new TasklistDTO();

        // TODO: test if modelMapper problem can be overcome
        tasklistDTO.setTAppSite(tAppSite);
        tasklistDTO.setTAppSiteEqpList(tAppSiteEqpList);

        return tasklistDTO;
    }

    // for paging and searching
    public Page<TAppSite> getAllSites(Pageable pageable, String siteId){
        // exception if no param provided
        if (!siteId.equals("empty")) {
            log.info("TasklistService: Attempting to retrieve TAppSite for searching records");
            return tAppSiteRepository.findAll(pageable, siteId);
        }
        else {
            log.info("TasklistService: Attempting to retrieve TAppSite from database");
            return tAppSiteRepository.findAll(pageable);
        }
    }

    // Dummy data generator

    // TODO: test against existing data in DB to see what else is missing.
    public void InsertDummyIntoTAppSite() {
        log.info("Attempting to insert Dummy data");

        List<TMtMake> tMtMakeList = tMtMakeRepository.findAll();
        List<TMtWfStatus> tMtWfStatusList = tMtWfStatusRepository.findAll();

        int randomNum = ThreadLocalRandom.current().nextInt(1000, 10000);
        int randomRowCount = ThreadLocalRandom.current().nextInt(1, 11);

        TAppSite tAppSite = new TAppSite();

        tAppSite.setCd("C00" + randomNum);
        tAppSite.setEqpCount(20);
        tAppSite.setCreatedBy("Jun Kang_" + randomNum);
        tAppSite.setNm("PT " + randomNum);
        tAppSite.setDtDecom(LocalDateTime.now());
        tAppSite.setDtScrap(LocalDateTime.now().plusDays(1));
        tAppSite.setDtOem(LocalDateTime.now().plusDays(2));
        tAppSite.setTMtWfStatus(tMtWfStatusList.get(0));

        for (int i = 0; i < randomRowCount; i++) {
            int randomNumEqp = ThreadLocalRandom.current().nextInt(1000, 10000);
            boolean randomBoolean = ThreadLocalRandom.current().nextBoolean();
            boolean randomBoolean2 = ThreadLocalRandom.current().nextBoolean();

            EqpType eqpType = randomBoolean ? EqpType.REUSE : EqpType.SCRAP;

            TAppSiteEqp tAppSiteEqp = new TAppSiteEqp();

            tAppSiteEqp.setHlthChckFlg(randomBoolean2);
            tAppSiteEqp.setSerialNo("serial_" + randomNumEqp);
            tAppSiteEqp.setCd("ABC" + randomNumEqp);
            tAppSiteEqp.setTMtMake(tMtMakeList.get(0));
            tAppSiteEqp.setEqpType(eqpType);
            tAppSiteEqp.setModel("model_" + randomNumEqp);
            tAppSiteEqp.setDscp("description for " + randomNumEqp);

            tAppSite.addTAppSiteEqp(tAppSiteEqp);
        }

        tAppSiteRepository.save(tAppSite);

    }


    // ###########################################

//    // These 2 functions are repeating itself for ioh, decom and scrap tasklist --> thinking to create a generic version.
//    public List<IohTasklistResponseDTO> mapToDTOList(Type DTOType) {
//
//        log.info("Attempting to map list of TAppSite to list of IohTasklistResponseDTO test");
//        List<TAppSite> tAppSites = getTAppSites();
//
//        return modelMapper.map(tAppSites, DTOType);
//    }
//
//    public IohTasklistResponseDTO mapToIohTasklistDTOById(Long id, String className) {
//        log.info("Attempting to map TAppSite to IohTasklistResponseDTO");
//
//        // TODO: possible altenative --> use reflection API to translate to class instance and throw into modelmapper.
//        Optional<TAppSite> tAppSiteOpt = getTAppSiteById(id);
//
//        return tAppSiteOpt.map(
//                        tAppSite -> modelMapper.map(tAppSite, IohTasklistResponseDTO.class))
//                .orElse(null);
//    }

    // ###########################################

}
