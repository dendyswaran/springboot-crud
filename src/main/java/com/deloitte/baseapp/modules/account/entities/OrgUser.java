package com.deloitte.baseapp.modules.account.entities;

import com.deloitte.baseapp.commons.TGenericEntity;
import com.deloitte.baseapp.modules.orgs.entites.Org;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
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
public class OrgUser implements TGenericEntity<OrgUser, UUID> {
    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id", nullable = false)
    private UUID id;

    private String code;
    private String name;
    @JsonIgnore
    private String password;
    private String email;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="org_id", referencedColumnName = "id")
    private Org org;

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
