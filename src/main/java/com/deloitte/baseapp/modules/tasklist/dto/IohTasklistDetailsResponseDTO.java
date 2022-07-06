package com.deloitte.baseapp.modules.tasklist.dto;

import com.deloitte.baseapp.modules.tasklist.object.EqpType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class IohTasklistDetailsResponseDTO {


    private Long id;
    private String cd;
    private String dscp;
    private String model;
    private String serialNo;
    private String scrpReuseTyp;
    private EqpType eqpType;
    //    private TMtMake tMtMake;

    // TODO: these are not supposed to be here (keep fetching for all rows)
    //  Can consider fetch during initial
    private TMtMakeDTO tMtMakeDTO;
    //    private TAppSite tAppSite; // TODO: Opt-> This can be removed if we alr preloaded the tasklist information in Redux.
//    private IohTasklistResponseDTO iohTasklistResponseDTO;

}
