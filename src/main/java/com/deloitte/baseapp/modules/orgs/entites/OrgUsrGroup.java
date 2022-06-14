package com.deloitte.baseapp.modules.orgs.entites;

import com.deloitte.baseapp.commons.AuditModel;
import com.deloitte.baseapp.commons.tModules.TGenericEntity;
import com.deloitte.baseapp.modules.teams.entities.OrgUsrTeam;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Set;
import java.util.UUID;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name="T_ORG_USRGRP", uniqueConstraints = {
        @UniqueConstraint(columnNames = "id")
})
@Getter
@Setter
@ToString
public class OrgUsrGroup extends AuditModel implements TGenericEntity<OrgUsrGroup,UUID> {
    @Id
    @GeneratedValue(generator="uuid2")
    @GenericGenerator(name="uuid2", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name="id", nullable=false)
    private UUID id;

    private String code;
    private String name;

    @ManyToOne
    @JoinColumn(name="org_id")
    private Org org;

    @OneToMany(mappedBy = "orgUser")
    @ToString.Exclude
    private Set<OrgUsrTeam> orgUsrTeams;

    @OneToMany(mappedBy = "orgUsrGroup")
    @ToString.Exclude
    private Set<OrgUsrUsrGroup> orgUsrUsrGroups;

    @Override
    public void update(OrgUsrGroup source) {
        this.name = source.getName();
        this.id = source.getId();
        this.code = source.getCode();
    }

    @Override
    public OrgUsrGroup createNewInstance() {
        OrgUsrGroup newInstance = new OrgUsrGroup();
        newInstance.update(this);
        return newInstance;
    }

}
