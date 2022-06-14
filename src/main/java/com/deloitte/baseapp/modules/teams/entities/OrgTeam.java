package com.deloitte.baseapp.modules.teams.entities;

import com.deloitte.baseapp.commons.AuditModel;
import com.deloitte.baseapp.commons.tModules.TGenericEntity;
import com.deloitte.baseapp.modules.orgs.entites.Org;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name="T_ORG_TEAM")
@Getter
@Setter
@ToString
public class OrgTeam extends AuditModel implements TGenericEntity<OrgTeam, UUID> {

    @Id
    @GeneratedValue(generator="uuid2")
    @GenericGenerator(name="uuid", strategy = "org.hibernate.id.UUIDGenerator")
    private UUID id;

    private String code;
    private String name;

    @ManyToOne
    @JoinColumn(name="org_id")
    @JsonBackReference
    private Org org;

    @OneToMany(mappedBy = "orgTeam")
    @JsonBackReference
    private Set<OrgUsrTeam> orgUsrTeams;

    @Override
    public void update(OrgTeam source) {
        this.name = source.getName();
        this.code = source.getCode();
    }

    @Override
    public OrgTeam createNewInstance() {
        OrgTeam newInstance = new OrgTeam();
        newInstance.update(this);
        return newInstance;
    }

}
