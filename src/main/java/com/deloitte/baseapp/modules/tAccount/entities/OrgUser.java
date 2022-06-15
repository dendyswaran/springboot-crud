package com.deloitte.baseapp.modules.tAccount.entities;

import com.deloitte.baseapp.commons.AuditModel;
import com.deloitte.baseapp.commons.tModules.TGenericEntity;
import com.deloitte.baseapp.modules.orgs.entites.Org;
import com.deloitte.baseapp.modules.orgs.entites.OrgUsrUsrGroup;
import com.deloitte.baseapp.modules.MTStatus.entities.MtStatus;
import com.deloitte.baseapp.modules.teams.entities.OrgUsrTeam;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name="T_ORG_USR",
        uniqueConstraints = {
        @UniqueConstraint(columnNames="id"),
        @UniqueConstraint(columnNames="email")
})
@Getter
@Setter
@ToString
public class OrgUser extends AuditModel implements TGenericEntity<OrgUser, UUID> {
    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id", nullable = false, updatable = false)
    private UUID id;

    private String code;
    private String name;
    @JsonIgnore
    private String password;
    private String email;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="org_id", referencedColumnName = "id")
    @JsonIgnoreProperties
    private Org org;

    @OneToMany(mappedBy = "orgUser")
    @ToString.Exclude
    private Set<OrgUsrUsrGroup> orgUsrUsrGroups;

    @JsonManagedReference
    @OneToMany(mappedBy = "orgUser")
    @ToString.Exclude
    private Set<OrgUsrTeam> orgUsrTeams;


    @ManyToOne
    @JoinColumn(name = "mt_status_id")
    @JsonManagedReference
    private MtStatus mtStatus;

    @Override
    public void update(OrgUser source) {
        this.id = source.getId();
        this.code = source.getCode();
        this.name = source.getName();
        this.password = source.getPassword();
        this.email = source.getEmail();
    }

    @Override
    public OrgUser createNewInstance() {
        OrgUser newInstance = new OrgUser();
        newInstance.update(this);
        return newInstance;
    }
}
