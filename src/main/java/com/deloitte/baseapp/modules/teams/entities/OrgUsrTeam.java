package com.deloitte.baseapp.modules.teams.entities;

import com.deloitte.baseapp.commons.AuditModel;
import com.deloitte.baseapp.commons.tModules.TGenericEntity;
import com.deloitte.baseapp.modules.tAccount.entities.OrgUser;
import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name="T_ORG_USR_TEAM")
@Getter
@Setter
@ToString
public class OrgUsrTeam extends AuditModel implements TGenericEntity<OrgUsrTeam, UUID> {
    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name="uuid", strategy = "org.hibernate.id.UUIDGenerator")
    private UUID id;

    @ManyToOne
    @JoinColumn(name="org_usr_id")
    @JsonBackReference
    private OrgUser orgUser;

    @ManyToOne
    @JoinColumn(name="org_team_id")
    private OrgTeam orgTeam;

    @Override
    public void update(OrgUsrTeam source) {

    }

    @Override
    public OrgUsrTeam createNewInstance() {
        return null;
    }
}
