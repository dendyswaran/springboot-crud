package com.deloitte.baseapp.modules.orgs.entites;

import com.deloitte.baseapp.commons.AuditModel;
import com.deloitte.baseapp.commons.tModules.TGenericEntity;
import com.deloitte.baseapp.modules.tAccount.entities.OrgUser;
import com.deloitte.baseapp.modules.teams.entities.OrgTeam;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
@Table(name="T_ORG",
    uniqueConstraints = {
        @UniqueConstraint(columnNames = "id")
})
@Getter
@Setter
@ToString
public class Org extends AuditModel implements TGenericEntity<Org, UUID> {
    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id", nullable = false)
    private UUID id;

    private String code;
    private String name;

    @OneToMany(mappedBy = "org")
    @JsonBackReference
    private Set<OrgUser> user;

    @OneToMany(mappedBy = "org")
    @ToString.Exclude
    @JsonBackReference
    private Set<OrgUsrGroup> orgGroup;

    @OneToMany(mappedBy = "org")
    @ToString.Exclude
    @JsonBackReference
    private Set<OrgTeam> orgTeams;

    public Org(String name, String code) {
        this.name = name;
        this.code = code;
    }

    public Org() {

    }

    @Override
    public void update(Org source) {
        this.name = source.getName();
        this.code = source.getCode();
    }

    @Override
    public Org createNewInstance() {
        Org newInstance = new Org();
        newInstance.update(this);
        return newInstance;
    }
}
