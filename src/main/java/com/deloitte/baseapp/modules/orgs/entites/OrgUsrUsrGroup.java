package com.deloitte.baseapp.modules.orgs.entites;

import com.deloitte.baseapp.commons.AuditModel;
import com.deloitte.baseapp.commons.tModules.TGenericEntity;
import com.deloitte.baseapp.modules.tAccount.entities.OrgUser;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "T_ORG_USR_USRGRP")
@Getter
@Setter
public class OrgUsrUsrGroup extends AuditModel implements TGenericEntity<OrgUsrUsrGroup, UUID> {
    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid", strategy = "org.hibernate.id.UUIDGenerator")
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "org_usr_id")
    @JsonManagedReference
    private OrgUser orgUser;

    @ManyToOne
    @JoinColumn(name = "org_usrgrp_id")
    @JsonManagedReference
    private OrgUsrGroup orgUsrGroup;


    @Override
    public void update(OrgUsrUsrGroup source) {

    }

    @Override
    public OrgUsrUsrGroup createNewInstance() {
        return null;
    }
}
