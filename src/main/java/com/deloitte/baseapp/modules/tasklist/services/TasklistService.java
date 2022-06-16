package com.deloitte.baseapp.modules.tasklist.services;

import com.deloitte.baseapp.modules.tasklist.entities.TAppSite;
import com.deloitte.baseapp.modules.tasklist.entities.TAppSiteEqp;
import com.deloitte.baseapp.modules.tasklist.entities.TMtMake;
import com.deloitte.baseapp.modules.tasklist.entities.TMtWfStatus;
import com.deloitte.baseapp.modules.tasklist.object.EqpType;
import com.deloitte.baseapp.modules.tasklist.repositories.TAppSiteRepository;
import com.deloitte.baseapp.modules.tasklist.repositories.TMtMakeRepository;
import com.deloitte.baseapp.modules.tasklist.repositories.TMtWfStatusRepository;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    // Dummy data generator
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
        tAppSite.setTMtWfStatus(tMtWfStatusList.get(0));

        for (int i = 0; i < randomRowCount; i++) {
            int randomNumEqp = ThreadLocalRandom.current().nextInt(1000, 10000);
            boolean randomBoolean = ThreadLocalRandom.current().nextBoolean();

            EqpType eqpType = randomBoolean ? EqpType.REUSE : EqpType.SCRAP;

            TAppSiteEqp tAppSiteEqp = new TAppSiteEqp();

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
