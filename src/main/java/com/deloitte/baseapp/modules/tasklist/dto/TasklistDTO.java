package com.deloitte.baseapp.modules.tasklist.dto;

import com.deloitte.baseapp.modules.tasklist.entities.TAppSite;
import com.deloitte.baseapp.modules.tasklist.entities.TAppSiteEqp;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class TasklistDTO {

    private TAppSite tAppSite;
    private List<TAppSiteEqp>tAppSiteEqpList;

}
